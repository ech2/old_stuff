//-------------------------------------------------------------------------------------------------------
// VST Plug-Ins SDK
// Version 2.4		$Date: 2006/11/13 09:08:27 $
//
// Category     : VST 2.x SDK Samples
// Filename     : adelay.cpp
// Created by   : Steinberg Media Technologies
// Description  : Simple Delay plugin (Mono->Stereo)
//
// © 2006, Steinberg Media Technologies, All Rights Reserved
//-------------------------------------------------------------------------------------------------------

#include <stdio.h>
#include <string.h>

#ifndef __adelay__
#include "adelay.h"
#endif

//----------------------------------------------------------------------------- 
ADelayProgram::ADelayProgram ()
{
	// default Program Values
	fDelay = 0.5;
	fFeedBack = 0.5;
	fSRDiv = 0.;
    fDryWet = 0.15;

	strcpy (name, "Init");
}

//-----------------------------------------------------------------------------
ADelay::ADelay (audioMasterCallback audioMaster)
	: AudioEffectX (audioMaster, kNumPrograms, kNumParams)
{
	// init
	size = 44100;
	cursor = 0;
	delay = 0;
    srDiv = 1;
	buffer1 = new float[size];
    buffer2 = new float[size];

	programs = new ADelayProgram[numPrograms];
	fDelay = fFeedBack = 0;
    fSRDiv = 0.;
    fDryWet = 0.15;

	if (programs)
		setProgram (0);

	setNumInputs (2);
	setNumOutputs (2);

	setUniqueID ('GANG');

	resume ();
}

//------------------------------------------------------------------------
ADelay::~ADelay ()
{
	if (buffer1)
		delete[] buffer1;
    if (buffer2)
        delete[] buffer2;
	if (programs)
		delete[] programs;
}

//------------------------------------------------------------------------
void ADelay::setProgram (VstInt32 program)
{
	ADelayProgram* ap = &programs[program];

	curProgram = program;
	setParameter (kDelay, ap->fDelay);	
	setParameter (kFeedBack, ap->fFeedBack);
	setParameter (kSRDiv, ap->fSRDiv);
    setParameter (kDryWet, ap->fDryWet);
}

//------------------------------------------------------------------------
void ADelay::setDelay (float fdelay)
{
	fDelay = fdelay;
	programs[curProgram].fDelay = fdelay;
	cursor = 0;
	delay = (long)(fdelay * (float)(size - 1));
}

//------------------------------------------------------------------------
void ADelay::setProgramName (char *name)
{
	strcpy (programs[curProgram].name, name);
}

//------------------------------------------------------------------------
void ADelay::getProgramName (char *name)
{
	if (!strcmp (programs[curProgram].name, "Init"))
		sprintf (name, "%s %d", programs[curProgram].name, curProgram + 1);
	else
		strcpy (name, programs[curProgram].name);
}

//-----------------------------------------------------------------------------------------
bool ADelay::getProgramNameIndexed (VstInt32 category, VstInt32 index, char* text)
{
	if (index < kNumPrograms)
	{
		strcpy (text, programs[index].name);
		return true;
	}
	return false;
}

//------------------------------------------------------------------------
void ADelay::resume ()
{
	memset (buffer1, 0, size * sizeof (float));
    memset (buffer2, 0, size * sizeof (float));
	AudioEffectX::resume ();
}

//------------------------------------------------------------------------
void ADelay::setParameter (VstInt32 index, float value)
{
	ADelayProgram* ap = &programs[curProgram];

	switch (index)
	{
		case kDelay :    setDelay (value);					break;
		case kFeedBack : fFeedBack = ap->fFeedBack = value; break;
        case kSRDiv : {
            int f = value * 8.49;
            srDiv = f <= 1 ? 1 : f >= 8 ? 8 : f;
            fSRDiv = value;
            break;
        }
        case kDryWet :   fDryWet = ap->fDryWet = value;     break;
	}
}

//------------------------------------------------------------------------
float ADelay::getParameter (VstInt32 index)
{
	float v = 0;

	switch (index)
	{
		case kDelay :    v = fDelay;	break;
		case kFeedBack : v = fFeedBack; break;
		case kSRDiv :    v = fSRDiv;	break;
        case kDryWet :   v = fDryWet;	break;
    }
	return v;
}

//------------------------------------------------------------------------
void ADelay::getParameterName (VstInt32 index, char *label)
{
	switch (index)
	{
		case kDelay :    strcpy (label, "Delay");		break;
		case kFeedBack : strcpy (label, "FeedBack");	break;
		case kSRDiv :    strcpy (label, "SR Div");		break;
        case kDryWet :   strcpy (label, "DryWet ;)");		break;

	}
}

//------------------------------------------------------------------------
void ADelay::getParameterDisplay (VstInt32 index, char *text)
{
	switch (index)
	{
		case kDelay :    int2string (delay, text, kVstMaxParamStrLen);			break;
		case kFeedBack : float2string (fFeedBack, text, kVstMaxParamStrLen);	break;
		case kSRDiv :    int2string (srDiv, text, kVstMaxParamStrLen);			break;
        case kDryWet :   int2string (fDryWet, text, kVstMaxParamStrLen);			break;
	}
}

//------------------------------------------------------------------------
void ADelay::getParameterLabel (VstInt32 index, char *label)
{
	switch (index)
	{
		case kDelay :    strcpy (label, "samples");	break;
		case kFeedBack : strcpy (label, "amount");	break;
		case kSRDiv :    strcpy (label, "factor");	break;
        case kDryWet :   strcpy (label, "");	break;
	}
}

//------------------------------------------------------------------------
bool ADelay::getEffectName (char* name)
{
	strcpy (name, "dh");
	return true;
}

//------------------------------------------------------------------------
bool ADelay::getProductString (char* text)
{
	strcpy (text, "dh");
	return true;
}

//------------------------------------------------------------------------
bool ADelay::getVendorString (char* text)
{
	strcpy (text, "ic");
	return true;
}

//---------------------------------------------------------------------------
void ADelay::processReplacing (float** inputs, float** outputs, VstInt32 sampleFrames)
{
	float* in1 = inputs[0];
    float* in2 = inputs[1];
	float* out1 = outputs[0];
	float* out2 = outputs[1];

	while (--sampleFrames >= 0)
	{
		float x1 = *in1++;
        float x2 = *in2++;
		float y1 = buffer1[cursor];
        float y2 = buffer2[cursor];
        
		buffer1[cursor] = x1 + y1 * fFeedBack;
        buffer2[cursor] = x2 + y2 * fFeedBack;
        
        srCount = ++srCount % srDiv;
        if (srCount == 0)
            cursor++;
        
        if (cursor >= delay)
			cursor = 0;

        *out1++ = x1 + fDryWet * (y1 - x1);
        *out2++ = x1 + fDryWet * (y1 - x1);
	}
}
