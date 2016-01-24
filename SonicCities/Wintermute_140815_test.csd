<CsoundSynthesizer>
<CsOptions>
-m0
</CsOptions>
<CsInstruments>
sr = 44100
ksmps = 32
0dbfs = 1
nchnls = 6

gaMix init 0
;gaDelL init 0
;gaDelR init 0
gaRev init 0
gaout1 init 0
gaout2 init 0
gaout3 init 0
gaout4 init 0
gaout5 init 0
gaoutRev init 0

gkAtt init 0
gkDec init 0
gkFun init 0
gkRes init 0
gkAvg init 0
gkDev init 0
gkMod init 0
gkOff init 0
gkPan init 0
gkAmt init 0
gkRHrate init 0
gkDamp init 0
gkAmpRand init 0
giNumV init 96 ; SET NUMBER OF VOICES HERE
giNumS init 0

alwayson 90
alwayson 91
;alwayson 98
alwayson 99

instr 1
kIter init 1
kseed rand .5, .5
giNumS = floor(giNumV/5)
if (giNumV >= kIter) goto run
	turnoff
run:
	event "i", 2+kIter*0.01,0,-1,kIter,kseed+.5
	kIter += 1
endin

instr 2
ktrig gausstrig 1, gkAvg/giNumV, gkDev, 1 ; check new vers
k1 init 0
k2 	rand gkAmt, .5, 0.1
k2 tonek k2, gkRHrate
k3 randomh 0.9, 1.1, gkPan*0.3
kLFO oscil 1, gkPan*k3,1,p5
kfreq=gkFun*(p4+gkOff)

label:
iLev=i(k1);only at i-pass or re-init pass
iRes=i(gkRes);only at i-pass or re-init pass
kLevMax rand gkAmpRand
kLevMax = 1 - kLevMax
k1 linsegr iLev, i(gkAtt), i(kLevMax), i(gkDec), 0, i(gkDec), 0
rireturn
if ktrig>0 then
reinit label
endif

a1 rand 1, 0.2
aout resonx a1*k1*k1, kfreq*2^((k1*gkMod+k2)/12), kfreq/iRes, 2, 2
aout = aout * 0.001 * (1 + gkDamp * (p4 - giNumV * .5)/giNumV)

if (p4 < giNumS) then
	gaout1 += aout
	goto run
elseif (p4 < 2*giNumS) then
	gaout2 += aout
	goto run
elseif (p4 < 3*giNumS) then
	gaout3 += aout
	goto run
elseif (p4 < 4*giNumS) then
	gaout4 += aout
	goto run
endif
	gaout5 += aout
run:
gaMix += aout
endin


;instr 98 ;Delay
;kFeed	invalue "DelFeed"
;kTime	invalue "DelTime"
;abu1 	delayr 1
;atapL 	deltap kTime
;		delayw gaDelL+atapL*kFeed
;
;abu2 	delayr 1
;atapR 	deltap kTime*1.001
;		delayw gaDelR+atapR*kFeed
;
;		outs atapL, atapR
;gaRevL += atapL
;gaRevR += atapR
;gaDelL = 0
;gaDelR = 0
;endin

instr 99 ;Reverb
;kSize invalue "RevSize"
aL, aR reverbsc gaRev, gaRev, 0.97, 8000
;outs aL, aR
gaoutRev += aL
gaRev = 0
endin

instr 90 ; Master
;kRevSend  invalue "RevSend"
;kDelSend invalue "DelSend"
;kGain invalue "knob3"
gaMix *= 1

;gaDel += gaMixL*kDelSend
gaRev += gaMix*0.4
	;outs gaMixL, gaMixR
a1 buthp gaout1*0.7, 20
a2 buthp gaout2*0.7, 20
a3 buthp gaout3*0.7, 20
a4 buthp gaout4*0.7, 20
a5 buthp gaout5*0.7, 20
a1 butlp gaout1*0.7, 18000
a2 butlp gaout2*0.7, 18000
a3 butlp gaout3*0.7, 18000
a4 butlp gaout4*0.7, 18000
a5 butlp gaout5*0.7, 18000
outh a1, a2, a3, a4, a5, gaoutRev
clear gaout1, gaout2, gaout3, gaout4, gaout5, gaoutRev, gaMix
endin

instr 91 ; GLOBAL CONTROLS
gkAtt invalue "Attack"
gkDec invalue "Decay"

gkFun cpsmidinn 11

gkAmt invalue "gkAmt"
gkRes invalue "FilRes"
gkAvg invalue "GausMean"
gkDev invalue "GausDev"
gkMod invalue "FreqMod"
gkOff invalue "Offset"
gkPan invalue "PanRate"
gkDamp invalue "gkDamp"
gkAmpRand invalue "AmpRand"
gkRHrate invalue "RHRate"
endin
</CsInstruments>
<CsScore>
f1 0 [2^10] 7 0 [2^9] 1 [2^9] 0
i1 0 [60*60*24*365]
</CsScore>
</CsoundSynthesizer>
<bsbPanel>
 <label>Widgets</label>
 <objectName/>
 <x>832</x>
 <y>252</y>
 <width>320</width>
 <height>240</height>
 <visible>true</visible>
 <uuid/>
 <bgcolor mode="nobackground">
  <r>176</r>
  <g>255</g>
  <b>174</b>
 </bgcolor>
 <bsbObject version="2" type="BSBKnob">
  <objectName>DelFeed</objectName>
  <x>82</x>
  <y>351</y>
  <width>80</width>
  <height>80</height>
  <uuid>{30a34575-5bc9-4dc7-a7c1-5fe2ba839df9}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>0.99900000</maximum>
  <value>0.42957000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>83</x>
  <y>321</y>
  <width>80</width>
  <height>25</height>
  <uuid>{7270b61f-2e0c-4b1d-8686-c2154c9a470f}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Fdbk</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>DelTime</objectName>
  <x>239</x>
  <y>351</y>
  <width>80</width>
  <height>80</height>
  <uuid>{8afc7d42-51e6-4a2e-b987-deccbc1ac63c}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.01000000</minimum>
  <maximum>0.99000000</maximum>
  <value>0.42160000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>164</x>
  <y>321</y>
  <width>80</width>
  <height>25</height>
  <uuid>{6cad4e77-a04e-4218-8d4e-c045a1c0fa46}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>-</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>knob6</objectName>
  <x>161</x>
  <y>351</y>
  <width>80</width>
  <height>80</height>
  <uuid>{df671303-e068-4a96-9fbc-0f7be9c70e59}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.01000000</minimum>
  <maximum>0.99500000</maximum>
  <value>0.32520000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>241</x>
  <y>321</y>
  <width>80</width>
  <height>25</height>
  <uuid>{c55df638-ca79-462d-b0c1-4f36332aaa58}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>DelTime</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>DelSend</objectName>
  <x>317</x>
  <y>351</y>
  <width>80</width>
  <height>80</height>
  <uuid>{5cc7b4dd-b0a1-4396-a758-66099bd19f5a}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>1.00000000</maximum>
  <value>0.44000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>RevSend</objectName>
  <x>395</x>
  <y>351</y>
  <width>80</width>
  <height>80</height>
  <uuid>{7973ec89-7d2f-45d2-a27a-974f2e200e3d}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>1.00000000</maximum>
  <value>0.43000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>397</x>
  <y>321</y>
  <width>80</width>
  <height>25</height>
  <uuid>{285786b4-d7be-435a-b108-8e20d15f645e}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>RevSend</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>knob3</objectName>
  <x>525</x>
  <y>311</y>
  <width>161</width>
  <height>139</height>
  <uuid>{91d60af0-3adc-4e5a-8868-d6af4dbb63b7}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>2.00000000</maximum>
  <value>0.10000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>531</x>
  <y>286</y>
  <width>80</width>
  <height>25</height>
  <uuid>{3fc75195-6c05-4378-9a51-fb763020fd3f}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Gain</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>320</x>
  <y>321</y>
  <width>80</width>
  <height>25</height>
  <uuid>{bf4c4a27-2268-43e6-9ef4-d3e6e396b9c2}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>DelSend</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBVSlider">
  <objectName>Attack</objectName>
  <x>395</x>
  <y>147</y>
  <width>20</width>
  <height>100</height>
  <uuid>{69a650ab-fe0a-4f87-bde6-4355ffcd779d}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00100000</minimum>
  <maximum>5.00000000</maximum>
  <value>4.50010000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>-1.00000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBVSlider">
  <objectName>Decay</objectName>
  <x>415</x>
  <y>147</y>
  <width>20</width>
  <height>100</height>
  <uuid>{b1036af2-abc6-4d05-a453-70c6125d8f74}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.01000000</minimum>
  <maximum>5.00000000</maximum>
  <value>3.65270000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>-1.00000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>386</x>
  <y>120</y>
  <width>80</width>
  <height>25</height>
  <uuid>{a762a436-d756-435a-a3a8-65e2c48c012d}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Att</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>413</x>
  <y>119</y>
  <width>80</width>
  <height>25</height>
  <uuid>{d5a7977b-a320-4f32-907b-a7d55a1438cc}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Dec</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBDisplay">
  <objectName>display2</objectName>
  <x>371</x>
  <y>247</y>
  <width>80</width>
  <height>25</height>
  <uuid>{93a18a73-5a4a-4a1e-ad93-a64bd84716e6}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>4.500100</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBDisplay">
  <objectName>display3</objectName>
  <x>415</x>
  <y>247</y>
  <width>80</width>
  <height>25</height>
  <uuid>{78d2cf98-14e9-4162-97e2-e0b61ca3a1c0}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>3.652700</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>knob20</objectName>
  <x>173</x>
  <y>528</y>
  <width>80</width>
  <height>80</height>
  <uuid>{70877aa8-997d-499b-a7cd-2b22078c3b16}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>40.00000000</maximum>
  <value>5.20000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBDisplay">
  <objectName>display21</objectName>
  <x>190</x>
  <y>606</y>
  <width>80</width>
  <height>25</height>
  <uuid>{51adbd9a-e7b2-4385-8368-86f0885ce738}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>11.040190</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>border</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>RevSize</objectName>
  <x>316</x>
  <y>474</y>
  <width>80</width>
  <height>80</height>
  <uuid>{bca8e98d-d1c2-419e-b800-213cd9e88756}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00100000</minimum>
  <maximum>0.99800000</maximum>
  <value>0.82851000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>328</x>
  <y>457</y>
  <width>80</width>
  <height>25</height>
  <uuid>{8c3042af-b971-4c28-a4d9-8143e0830126}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>RevSize</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>FilRes</objectName>
  <x>481</x>
  <y>582</y>
  <width>80</width>
  <height>80</height>
  <uuid>{d84ab25f-d512-4870-92bf-f7bd08cad7fc}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>1.00000000</minimum>
  <maximum>40.00000000</maximum>
  <value>36.88000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>493</x>
  <y>563</y>
  <width>80</width>
  <height>25</height>
  <uuid>{7269bb5f-afd6-4533-b018-ce40e0db142d}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Filt Res</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>GausMean</objectName>
  <x>616</x>
  <y>525</y>
  <width>80</width>
  <height>80</height>
  <uuid>{be047363-6413-444f-9b0c-0c969fd3cced}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.01000000</minimum>
  <maximum>30.00000000</maximum>
  <value>6.30790000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>619</x>
  <y>507</y>
  <width>80</width>
  <height>25</height>
  <uuid>{d45edc81-b309-40b2-826b-859e5bcb0ee3}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Gauss Mean</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>GausDev</objectName>
  <x>723</x>
  <y>648</y>
  <width>80</width>
  <height>80</height>
  <uuid>{efdbe0b6-ab56-4179-8327-5bfe316ca88b}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>0.98000000</maximum>
  <value>0.28420000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>724</x>
  <y>637</y>
  <width>80</width>
  <height>25</height>
  <uuid>{65d3d9a1-c41f-4ca0-9e63-966d4417fe0f}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Gauss Deviation</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>FreqMod</objectName>
  <x>341</x>
  <y>630</y>
  <width>80</width>
  <height>80</height>
  <uuid>{8cf0eb6f-1352-4a5e-bf32-68ac6ad3baa0}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>-12.00000000</minimum>
  <maximum>12.00000000</maximum>
  <value>3.36000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>344</x>
  <y>610</y>
  <width>80</width>
  <height>25</height>
  <uuid>{24421ed8-24b7-4b5f-b647-5dd466ccdae6}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>FreqModLev</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>168</x>
  <y>514</y>
  <width>98</width>
  <height>25</height>
  <uuid>{5a417afc-7aa2-414f-9e67-d15c0ca8869e}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Fundamental Freq</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>Offset</objectName>
  <x>252</x>
  <y>536</y>
  <width>44</width>
  <height>43</height>
  <uuid>{e5202032-7a9c-4819-97fd-0beb15e865bb}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>32.00000000</maximum>
  <value>6.08000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>PanRate</objectName>
  <x>25</x>
  <y>562</y>
  <width>118</width>
  <height>107</height>
  <uuid>{4ddc2fbe-f18d-4166-b65e-35bbb746b07b}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00890000</minimum>
  <maximum>8.17000000</maximum>
  <value>0.58017700</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>59</x>
  <y>665</y>
  <width>80</width>
  <height>25</height>
  <uuid>{684eca22-8225-4b3f-b4bc-5652cef7cecc}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Pan Rate</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>gkAmt</objectName>
  <x>515</x>
  <y>476</y>
  <width>80</width>
  <height>80</height>
  <uuid>{a1f7b822-8dbb-4917-85c4-e1c1e86553fd}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>2.00000000</maximum>
  <value>0.72000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>509</x>
  <y>448</y>
  <width>80</width>
  <height>25</height>
  <uuid>{b549fb45-e8fa-497c-b246-96648b946772}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Amt</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>RHrate</objectName>
  <x>454</x>
  <y>476</y>
  <width>57</width>
  <height>55</height>
  <uuid>{3a827f33-2e96-4271-901e-a3e963a82b53}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00100000</minimum>
  <maximum>10.00000000</maximum>
  <value>7.50025000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>gkDamp</objectName>
  <x>181</x>
  <y>706</y>
  <width>80</width>
  <height>80</height>
  <uuid>{f6769c65-2a58-4de9-9549-9bef79a3f05c}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>-10.00000000</minimum>
  <maximum>10.00000000</maximum>
  <value>-1.00000000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>201</x>
  <y>687</y>
  <width>80</width>
  <height>25</height>
  <uuid>{cb0b1af2-d3a4-49cf-9dd7-c5d0a9a55644}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>Damp</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
 <bsbObject version="2" type="BSBKnob">
  <objectName>AmpRand</objectName>
  <x>134</x>
  <y>222</y>
  <width>62</width>
  <height>47</height>
  <uuid>{f26175fc-0da6-4f73-a6c0-ea97b732f86d}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <minimum>0.00000000</minimum>
  <maximum>0.93000000</maximum>
  <value>0.88350000</value>
  <mode>lin</mode>
  <mouseControl act="jump">continuous</mouseControl>
  <resolution>0.01000000</resolution>
  <randomizable group="0">false</randomizable>
 </bsbObject>
 <bsbObject version="2" type="BSBLabel">
  <objectName/>
  <x>147</x>
  <y>188</y>
  <width>80</width>
  <height>25</height>
  <uuid>{86c33225-f7b5-4bcc-98a9-6bf5cea4d946}</uuid>
  <visible>true</visible>
  <midichan>0</midichan>
  <midicc>0</midicc>
  <label>AmpRand</label>
  <alignment>left</alignment>
  <font>Arial</font>
  <fontsize>10</fontsize>
  <precision>3</precision>
  <color>
   <r>0</r>
   <g>0</g>
   <b>0</b>
  </color>
  <bgcolor mode="nobackground">
   <r>255</r>
   <g>255</g>
   <b>255</b>
  </bgcolor>
  <bordermode>noborder</bordermode>
  <borderradius>1</borderradius>
  <borderwidth>1</borderwidth>
 </bsbObject>
</bsbPanel>
<bsbPresets>
</bsbPresets>
