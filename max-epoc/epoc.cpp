/**
 Emotiv EPOC Max external
 
 Copyright (c) 2015 Eugene Cherny
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

#define DataHandle EE_DataHandle
#include "edk.h"
#include "EmoStateDLL.h"

extern "C" {
#include "ext.h"
#include "ext_obex.h"
}


////////////////////////// object struct
typedef struct _epocdata
{
	t_object	ob;
	t_symbol	*name;
    void		*out3_service;
	void		*out2_gyro;
	void		*out1_raw;
	void		*out0_emo;
    
    EmoEngineEventHandle    e_event;
    EmoStateHandle          emo_state;
    DataHandle data_handle;
    
    bool ready_to_collect;
} t_epocdata;

EE_DataChannel_t epocdata_channel_list[] = {
    ED_AF3, ED_F7, ED_F3, ED_FC5, ED_T7, ED_P7,
    ED_O1, ED_O2, ED_P8, ED_T8, ED_FC6, ED_F4,
    ED_F8, ED_AF4, ED_GYROX, ED_GYROY
};

//t_linklist *epocdata_connected_users = linklist_new();    // TODO multiple users
int epocdata_objects_created = 0;


///////////////////////// function prototypes
//// standard set
void *epocdata_new(t_symbol *s, long argc, t_atom *argv);
void epocdata_free(t_epocdata *x);
void epocdata_assist(t_epocdata *x, void *b, long m, long a, char *s);
void epocdata_bang(t_epocdata *x);
void epocdata_gyro_rezero(t_epocdata *x);
void epocdata_load_user_profile(t_epocdata *x, /*unsigned int user_id,*/ t_symbol * file_path);

//// Helpers
const char * epocdata_cognitiv_action_string(EE_CognitivAction_t action);
const char * epocdata_signal_strenght_string(EE_SignalStrength_t signal);

//// EPOC API
void epocdata_connect(t_epocdata *x);
void epocdata_raw_data(t_epocdata *x);
void epocdata_emo_data(t_epocdata *x);
void epocdata_cognitiv_data(t_epocdata *x);
void epocdata_check_user_event(t_epocdata *x, EE_Event_t *event_type, unsigned int *user_id);
void epocdata_service_info(t_epocdata *x);


//////////////////////// global class pointer variable
void *epocdata_class;


int C74_EXPORT main(void) {
	t_class *c;
	
	c = class_new("epoc", (method)epocdata_new, (method)epocdata_free, (long)sizeof(t_epocdata),
				  0L /* leave NULL!! */, A_GIMME, 0);

    class_addmethod(c, (method)epocdata_bang,   "bang", 0);
    class_addmethod(c, (method)epocdata_assist, "assist", A_CANT, 0);
    class_addmethod(c, (method)epocdata_gyro_rezero, "gyro_rezero", 0);
    class_addmethod(c, (method)epocdata_load_user_profile, "load_user_profile", A_SYM, 0);

    
	CLASS_ATTR_SYM(c, "name", 0, t_epocdata, name);
	
	class_register(CLASS_BOX, c);
	epocdata_class = c;

	return 0;
}


//////////////////////////////////////////////////
void epocdata_assist(t_epocdata *x, void *b, long m, long a, char *s) {
	if (m == ASSIST_INLET) {
		sprintf(s, "(bang) Get new data");
	} else {
        switch (a) {
        case 0:
            sprintf(s, "(list) Emo state");
            break;
        case 1:
            sprintf(s, "(list) Raw data");
            break;
        case 2:
            sprintf(s, "(list) Gyro XY");
            break;
        case 3:
            sprintf(s, "(list) Service information (battery, etc.)");
            break;
        }
	}
}


//////////////////////////////////////////////////
void epocdata_gyro_rezero(t_epocdata *x) {
    EE_HeadsetGyroRezero(0);    // TODO support several users
}


//////////////////////////////////////////////////
void epocdata_bang(t_epocdata *x) {
    epocdata_service_info(x);
    if (EE_EngineGetNextEvent(x->e_event) != EDK_OK) {
        return;
    }
    
    unsigned int user_id = 0;
    EE_EmoEngineEventGetUserId(x->e_event, &user_id);
    if (user_id != 0) {    // TODO support several users
        return;
    }

    EE_Event_t event_type = EE_EmoEngineEventGetType(x->e_event);
    epocdata_raw_data(x);   // TODO check if raw data is available on this device?
    epocdata_check_user_event(x, &event_type, &user_id);
}


//////////////////////////////////////////////////
void epocdata_load_user_profile(t_epocdata *x, t_symbol * file_path) {
    int status = EE_LoadUserProfile(0, file_path->s_name);  // TODO multiple users
    if (status == EDK_OK) {
        object_post((t_object*)x, "User profile has been loaded");
    } else {
        object_error((t_object*)x, "Can't load profile [%02x]", status);
    }
}


//////////////////////////////////////////////////
void epocdata_check_user_event(t_epocdata *x, EE_Event_t *event_type, unsigned int *user_id) {
    if (*event_type == EE_UserAdded) {
        EE_DataAcquisitionEnable(*user_id, true);
    } else if (*event_type == EE_EmoStateUpdated) {
        EE_EmoEngineEventGetEmoState(x->e_event, x->emo_state);
        epocdata_emo_data(x);
        epocdata_cognitiv_data(x);
    } else if (*event_type == EE_UserRemoved) {
        EE_DataAcquisitionEnable(*user_id, false);
    } else {
//        object_post((t_object*)x, "Event: %04x", *event_type);
    }
}


//////////////////////////////////////////////////
void epocdata_raw_data(t_epocdata *x) { // TODO support several users
    if (EE_DataUpdateHandle(0, x->data_handle) != EDK_OK) {
        return;
    };
    unsigned int samples_taken = 0;
    EE_DataGetNumberOfSample(x->data_handle, &samples_taken);

    if (samples_taken != 0) {
        double* data = new double[samples_taken];
        t_atom result_raw[14];
        t_atom result_gyro[2];
        
        for (int sample_idx = 0; sample_idx < (int)samples_taken; ++sample_idx) {
            for (int i = 0; i < 14; i++) {
                EE_DataGet(x->data_handle, epocdata_channel_list[i], data, samples_taken);
                atom_setfloat(result_raw + i, data[sample_idx]);
            }
            for (int i = 14; i < 16; i++) {
                EE_DataGet(x->data_handle, epocdata_channel_list[i], data, samples_taken);
                atom_setfloat(result_gyro + i - 14, data[sample_idx]);
            }
            outlet_list(x->out2_gyro, NULL, 2, result_gyro);
            outlet_list(x->out1_raw, NULL, 14, result_raw);
        }
        delete[] data;
    }
}


//////////////////////////////////////////////////
void epocdata_emo_data(t_epocdata *x) {
    float frustration = ES_AffectivGetFrustrationScore(x->emo_state);
    float excitement = ES_AffectivGetExcitementShortTermScore(x->emo_state);
    float engagement = ES_AffectivGetEngagementBoredomScore(x->emo_state);
    float meditation = ES_AffectivGetMeditationScore(x->emo_state);

    t_atom emo_data[5];
    atom_setsym(emo_data, gensym("emo_state"));
    atom_setfloat(emo_data + 1, frustration);
    atom_setfloat(emo_data + 2, excitement);
    atom_setfloat(emo_data + 3, engagement);
    atom_setfloat(emo_data + 4, meditation);
    
    outlet_list(x->out0_emo, NULL, 5, emo_data);
}


//////////////////////////////////////////////////
const char * epocdata_cognitiv_action_string(EE_CognitivAction_t action) {
    switch (action) {
        case COG_NEUTRAL:
            return "neutral";
        case COG_PUSH:
            return "push";
        case COG_PULL:
            return "pull";
        case COG_LIFT:
            return "lift";
        case COG_DROP:
            return "drop";
        case COG_LEFT:
            return "left";
        case COG_RIGHT:
            return "right";
        case COG_ROTATE_LEFT:
            return "rotate_left";
        case COG_ROTATE_RIGHT:
            return "rotate_right";
        case COG_ROTATE_CLOCKWISE:
            return "rotate_clockwise";
        case COG_ROTATE_COUNTER_CLOCKWISE:
            return "rotate_counter_clockwise";
        case COG_ROTATE_FORWARDS:
            return "rotate_forwards";
        case COG_ROTATE_REVERSE:
            return "rotate_reverse";
        case COG_DISAPPEAR:
            return "disappear";
        default:
            return "unknown";
    }
}


//////////////////////////////////////////////////
const char * epocdata_signal_strenght_string(EE_SignalStrength_t signal) {
    switch (signal) {
        case NO_SIGNAL:
            return "no";
        case BAD_SIGNAL:
            return "bad";
        case GOOD_SIGNAL:
            return "good";
        default:
            return "unknown";
    }
}


//////////////////////////////////////////////////
void epocdata_cognitiv_data(t_epocdata *x) {
    if (ES_CognitivIsActive(x->emo_state)) {
        t_atom action_power[3];
        atom_setsym(action_power, gensym("cognitive_control"));
        atom_setsym(action_power + 1, gensym(epocdata_cognitiv_action_string(ES_CognitivGetCurrentAction(x->emo_state))));
        atom_setfloat(action_power + 2, ES_CognitivGetCurrentActionPower(x->emo_state));
        outlet_list(x->out0_emo, NULL, 4, action_power);
    }
}


//////////////////////////////////////////////////
void epocdata_service_info(t_epocdata *x) {
    int charge_level;
    int max_charge_level;
    ES_GetBatteryChargeLevel(x->emo_state, &charge_level, &max_charge_level);
    t_atom charge_coef[2];
    atom_setsym(charge_coef, gensym("battery"));
    atom_setfloat(charge_coef + 1, (double) charge_level / (double) max_charge_level);
    
    t_atom signal[2];
    atom_setsym(signal, gensym("signal"));
    atom_setsym(signal + 1, gensym(epocdata_signal_strenght_string(ES_GetWirelessSignalStatus(x->emo_state))));
    
    outlet_list(x->out3_service, NULL, 2, charge_coef);
    outlet_list(x->out3_service, NULL, 2, signal);
}


//////////////////////////////////////////////////
void *epocdata_new(t_symbol *s, long argc, t_atom *argv) {
    t_epocdata *x = NULL;
    if ((x = (t_epocdata *)object_alloc((t_class*)epocdata_class))
        && epocdata_objects_created == 0 && EE_EngineConnect() == EDK_OK) {
		x->name = gensym("");
		if (argc && argv) {
			x->name = atom_getsym(argv);
		}
        if (!x->name || x->name == gensym("")) {
			x->name = symbol_unique();
        }
		
        x->out3_service = outlet_new(x, NULL);
        x->out2_gyro = outlet_new(x, NULL);
		x->out1_raw = outlet_new(x, NULL);
        x->out0_emo = outlet_new(x, NULL);
        
        x->e_event = EE_EmoEngineEventCreate();
        x->emo_state = EE_EmoStateCreate();
        x->data_handle = EE_DataCreate();
        EE_DataSetBufferSizeInSec(0.01);
        // TODO battery, cognitive
        
        unsigned int num_users;
        EE_EngineGetNumUser(&num_users);
        if (num_users > 0) {
            x->ready_to_collect = true;
        } else {
            x->ready_to_collect = false;
        }
        epocdata_objects_created++;
    } else {
        if (x != NULL) {
            freeobject((t_object *)x);
        }
        if (epocdata_objects_created > 0) {
            post("Object [epoc] has already been created");
        } else {
            post("Can't connect to EmoEngine");
        }
        x = NULL;
        epocdata_objects_created++;
    }
	return (x);
}


//////////////////////////////////////////////////
void epocdata_free(t_epocdata *x) {
    EE_EmoStateFree(x->emo_state);
    EE_EmoEngineEventFree(x->e_event);
    EE_DataFree(x->data_handle);
    if (--epocdata_objects_created == 0) {
        EE_EngineDisconnect();
    }
}
