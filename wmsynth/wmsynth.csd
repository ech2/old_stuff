<Cabbage>
form size(800, 600), caption("Wintermuse Shadows"), pluginID("WM^S")

; Filter
label bounds(111, 13, 50, 15), text("Filter", ""),
rslider bounds(20, 30, 70, 70), channel("FilterAttack"), range(0, 1, 0, 1, 0.001), text("Attack", ""),
rslider bounds(74, 30, 70, 70), channel("FilterDecay"), range(0, 1, 0, 1, 0.001), text("Decay", ""),
rslider bounds(128, 30, 70, 70), channel("FilterSustain",""), range(0, 1, 1, 1, 0.001), text("Sustain", ""),
rslider bounds(182, 30, 70, 70), channel("FilterRelease",""), range(0, 1, 0, 1, 0.001), text("Release", ""),

rslider bounds(250, 30, 70, 70), channel("FilterCutoff",""), range(0, 1, 0.5, 1, 0.001), text("Cutoff", ""),
rslider bounds(305, 30, 70, 70), channel("FilterResonance",""), range(0, 1, 0, 1, 0.001), text("Q", ""),
rslider bounds(360, 30, 70, 70), channel("FilterEnv",""), range(0, 1, 0, 1, 0.001), text("Env", ""),
rslider bounds(415, 30, 70, 70), channel("FilterLFO",""), range(0, 4, 0, 1, 0.001), text("LFO", ""),

; Amplitude
label bounds(86, 120, 100, 15), text("Amplitude", ""),
rslider bounds(20, 138, 70, 70), channel("AmpAttack"), range(0, 1, 0, 1, 0.001), text("Attack", ""),
rslider bounds(74, 138, 70, 70), channel("AmpDecay"), range(0, 1, 0, 1, 0.001), text("Decay", ""),
rslider bounds(128, 138, 70, 70), channel("AmpSustain",""), range(0, 1, 1, 1, 0.001), text("Sustain", ""),
rslider bounds(182, 138, 70, 70), channel("AmpRelease",""), range(0, 1, 0, 1, 0.001), text("Release", ""),
rslider bounds(250, 138, 70, 70), channel("OscMix",""), range(0, 1, 0, 1, 0.001), text("OscMix", ""),
rslider bounds(305, 138, 70, 70), channel("AmpLFO",""), range(0, 4, 0, 1, 0.001), text("LFO", ""),

; Oscillators
label bounds(113, 227, 100, 15), text("Oscillators", ""),
rslider bounds(20, 244, 70, 70), channel("Osc1Shape"), range(0, 3, 2, 1, 0.001), text("Shape", ""),
rslider bounds(74, 244, 70, 70), channel("Osc1Coarse"), range(-12, 12, 0, 1, 0.001), text("Coarse", ""),
rslider bounds(128, 244, 70, 70), channel("Osc1Fine",""), range(-1, 1, 0, 1, 0.001), text("Fine", ""),
rslider bounds(182, 244, 70, 70), channel("Osc1Detune",""), range(0, 1, 0, 1, 0.001), text("Detune", ""),
rslider bounds(236, 244, 70, 70), channel("Osc1Width",""), range(0, 1, 0, 1, 0.001), text("Width", ""),

rslider bounds(20, 315, 70, 70), channel("Osc2Shape"), range(0, 3, 2, 1, 0.001), text("Shape", ""),
rslider bounds(74, 315, 70, 70), channel("Osc2Coarse"), range(-12, 12, 0, 1, 0.001), text("Coarse", ""),
rslider bounds(128, 315, 70, 70), channel("Osc2Fine",""), range(-1, 1, 0, 1, 0.001), text("Fine", ""),
rslider bounds(182, 315, 70, 70), channel("Osc2Detune",""), range(0, 1, 0, 1, 0.001), text("Detune", ""),
rslider bounds(236, 315, 70, 70), channel("Osc2Width",""), range(0, 1, 0, 1, 0.001), text("Width", ""),

;LFOs
label bounds(347, 227, 100, 15), text("LFOs", ""),
rslider bounds(305, 244, 70, 70), channel("LFO1Shape"), range(0, 2, 0, 1, 0.001), text("Shape", ""),
rslider bounds(360, 244, 70, 70), channel("LFO1Freq"), range(0, 20, 1, 1, 0.001), text("Freq", ""),
rslider bounds(415, 244, 70, 70), channel("LFO1Amp"), range(0, 1, 1, 1, 0.001), text("Amp", ""),
rslider bounds(305, 315, 70, 70), channel("LFO2Shape"), range(0, 2, 0, 1, 0.001), text("Shape", ""),
rslider bounds(360, 315, 70, 70), channel("LFO2Freq"), range(0, 20, 1, 1, 0.001), text("Freq", ""),
rslider bounds(415, 315, 70, 70), channel("LFO2Amp"), range(0, 1, 1, 1, 0.001), text("Amp", ""),

; Effects
label bounds(59, 404, 100, 15), text("Delay", ""),
rslider bounds(20, 421, 70, 70), channel("DelaySend"), range(0, 1, 0, 1, 0.001), text("Mix", ""),
rslider bounds(74, 421, 70, 70), channel("DelayTime"), range(0.1, 0.9, 0.1, 1, 0.001), text("Time", ""),
rslider bounds(128, 421, 70, 70), channel("DelayFeedback"), range(0, 0.9, 0.1, 1, 0.001), text("Feedback", ""),

label bounds(207, 404, 100, 15), text("Reverb", ""),
rslider bounds(196, 421, 70, 70), channel("ReverbSend"), range(0.1, 0.9, 0, 1, 0.001), text("Mix", ""),
rslider bounds(250, 421, 70, 70), channel("ReverbSize"), range(0, 0.9, 1, 0.2, 0.001), text("Size", ""),

keyboard bounds(20, 500, 409, 76)
</Cabbage>
<CsoundSynthesizer>
<CsOptions>
  -n -d -+rtmidi=NULL -M0 --midi-key-cps=4 --midi-velocity-amp=5
</CsOptions>
<CsInstruments>
sr = 88100
ksmps = 64
nchnls = 2
0dbfs = 1

gkLFO1  init 0
gkLFO2  init 0

gaEffL  init 0
gaEffR  init 0


opcode ADSR, kk, i
    setksmps 16
    iAmpVelocity xin

    iAmpAttack      chnget "AmpAttack"
    iAmpDecay       chnget "AmpDecay"
    iAmpSustain     chnget "AmpSustain"
    iAmpRelease     chnget "AmpRelease"
    iFilterAttack   chnget "FilterAttack"
    iFilterDecay    chnget "FilterDecay"
    iFilterSustain  chnget "FilterSustain"
    iFilterRelease  chnget "FilterAttack"

    kAmpAdsr    mxadsr iAmpAttack*3+0.001, iAmpDecay*3+0.0001, iAmpSustain, iAmpRelease*3+0.0001
    kFiltAdsr   mxadsr iFilterAttack*3+0.001, iFilterDecay*3+0.0001, iFilterSustain, iFilterRelease*3+0.0001

    xout kAmpAdsr*iAmpVelocity, kFiltAdsr*iAmpVelocity
endop

opcode Filter, aa, aakkk
    ain1, ain2, kcutoff, kres, kfbk xin
    aout1       lpf18 ain1, kcutoff*19000, kres, 0
    aout2       lpf18 ain2, kcutoff*19000, kres, 0
    xout aout1, aout2
endop

opcode MorphLFO, k, kkk
    setksmps 16
    kShape, kFreq, kAmp xin  ; kShape has range [0, 2]---from sine to saw
    kShape limit kShape, 0, 2

    kphase phasor kFreq+0.001
    kInterp = frac(kShape)
    kTable1 = floor(kShape)
    kTable2 = floor(kShape) + ceil(frac(kShape))
    kout tabmorph kphase*8192, kInterp, kTable1, kTable2, 10, 11, 12, 13

    xout (kout*0.5 + 0.5)*kAmp
endop

opcode MixLFO, k, kkk
    setksmps 16
    kMultLFO1 init 0
    kMultLFO2 init 0
    kLFO1, kLFO2, kmix xin  ; kmix has range [0, 4]

    iSinTableNumber = 10
    iQuarterSinLenght = ftlen(iSinTableNumber) / 4
    tb0_init iSinTableNumber

    kmix limit kmix, 0, 4

    if (0 <= kmix && kmix < 1) then         ; LFO1 raises, LFO2 is 0
        kMultLFO1 = tb0(kmix * (iQuarterSinLenght - 1))
        kMultLFO2 = 0
    elseif (1 <= kmix && kmix < 2) then     ; LFO1 is 1, LFO2 raises
        kMultLFO1 = 1
        kMultLFO2 = tb0((2 - kmix) * (iQuarterSinLenght - 1))
    elseif (2 <= kmix && kmix < 3) then     ; LFO1 decreases, LFO2 is 1
        kMultLFO1 = tb0((3 - kmix) * (iQuarterSinLenght - 1) + iQuarterSinLenght)
        kMultLFO2 = 1
    elseif (3 <= kmix && kmix < 4) then    ; LFO1 is 0, LFO2 decreases
        kMultLFO1 = 0
        kMultLFO2 = tb0((4 - kmix) * (iQuarterSinLenght - 1) + iQuarterSinLenght)
    endif

    xout kLFO1*kMultLFO1 + kLFO2*kMultLFO2
endop

opcode Mix2Coeffs, kk, k
  setksmps 16
  kMix xin
  iSinTableNumber = 10
  iQuarterSinLenght = ftlen(iSinTableNumber) / 4
  tb1_init iSinTableNumber

  kMix limit kMix, 0, 1
  kMult1 = tb1(iQuarterSinLenght - iQuarterSinLenght*kMix)
  kMult2 = tb1(iQuarterSinLenght*kMix)

  xout kMult1, kMult2
endop

opcode MorphOsc, a, kkk
    ; TODO replace with virus-like wavetable based oscillator
    setksmps 16
    kFreq, kShape, kPw xin  ; kShape has range [0, 3]---from sine to pulse

    kShape limit kShape, 0, 3
    aphase phasor kFreq
    kInterp = frac(kShape)
    kTable1 = floor(kShape)
    kTable2 = floor(kShape) + ceil(frac(kShape))
    ;printf "%f %f %f %f\n", 1, kShape, kInterp, kTable1, kTable2

    aout tabmorphak aphase, kInterp, kTable1, kTable2, 10, 11, 12, 13
    xout aout
endop

opcode SuperOsc, aa, kkkkk
    ; TODO, idea: exponential or logarithmic kDet mapping with output range from 0 to 3
    setksmps 16
    kFreq, kShape, kDet, kWid, kVol xin
    kVol limit kVol, 0, 1

    gkDT[]  fillarray .0024, .019, -.019, -.0023, .0046, -.0046, .0093, -.0093
    gkP[]   fillarray .5, -.5, -.5, .5, .5, -.5, -.5, .5

    kD[]    = gkDT*kDet + 1
    kW[]    = gkP*kWid + 0.5
    kWi[]   = 1 - kW

    kpw     oscil 0.01, 0.5, 1
    kpw     += 0.5

    a1  MorphOsc kFreq*kD[0],kShape,kpw
    a2  MorphOsc kFreq*kD[1],kShape,kpw
    a3  MorphOsc kFreq*kD[2],kShape,kpw
    a4  MorphOsc kFreq*kD[3],kShape,kpw
    a5  MorphOsc kFreq*kD[4],kShape,kpw
    a6  MorphOsc kFreq*kD[5],kShape,kpw
    a7  MorphOsc kFreq*kD[6],kShape,kpw
    a8  MorphOsc kFreq*kD[7],kShape,kpw

    amixL   =   a1*kW[0]  + a2*kW[1]  + a3*kW[2]  + a4*kW[3]  + a5*kW[4]  + a6*kW[5]  + a7*kW[6]  + a8*kW[7]
    amixR   =   a1*kWi[0] + a2*kWi[1] + a3*kWi[2] + a4*kWi[3] + a5*kWi[4] + a6*kWi[5] + a7*kWi[6] + a8*kWi[7]
    amixL   *=  0.125
    amixR   *=  0.125
    xout amixL*kVol, amixR*kVol
endop



instr 1
    kFilterCutoff     chnget "FilterCutoff"
    kFilterResonance  chnget "FilterResonance"
    kFilterEnv        chnget "FilterEnv"
    kFilterLFO        chnget "FilterLFO"

    ; Oscillators
    kOsc1Shape     chnget "Osc1Shape"
    kOsc1Coarse    chnget "Osc1Coarse"
    kOsc1Fine      chnget "Osc1Fine"
    kOsc1Detune    chnget "Osc1Detune"
    kOsc1Width     chnget "Osc1Width"
    kOsc2Shape     chnget "Osc2Shape"
    kOsc2Coarse    chnget "Osc2Coarse"
    kOsc2Fine      chnget "Osc2Fine"
    kOsc2Detune    chnget "Osc2Detune"
    kOsc2Width     chnget "Osc2Width"
    kOscMix        chnget "OscMix"
    kAmpLFO        chnget "AmpLFO"

    ; End parameter definition

    iNote         notnum
    iAmpVelocity  ampmidi 1
    kOsc1Freq = cpsmidinn(iNote+kOsc1Coarse+kOsc1Fine)
    kOsc2Freq = cpsmidinn(iNote+kOsc2Coarse+kOsc2Fine)

    kAmpAdsr, kFilterAdsr ADSR iAmpVelocity

    kAmpLFOVal            MixLFO gkLFO1, gkLFO2, kAmpLFO
    kFilterLFOVal         MixLFO gkLFO1, gkLFO2, kFilterLFO

    kOsc1Vol, kOsc2Vol    Mix2Coeffs kOscMix
    kOsc1Vol              = kOsc1Vol - kOsc1Vol*kAmpLFOVal
    kOsc2Vol              = kOsc2Vol - kOsc2Vol*kAmpLFOVal
    aOsc1L, aOsc1R        SuperOsc kOsc1Freq, kOsc1Shape, kOsc1Detune, kOsc1Width, kOsc1Vol
    aOsc2L, aOsc2R        SuperOsc kOsc2Freq, kOsc2Shape, kOsc2Detune, kOsc2Width, kOsc2Vol

    kF                    = (1.0-kFilterCutoff)*kFilterAdsr*kFilterEnv + kFilterCutoff
    kF                    = kF - kF*kFilterLFOVal
    aout1, aout2          Filter (aOsc1L+aOsc2L)*0.5, (aOsc1R+aOsc2R)*0.5, kF, kFilterResonance, 0

    aout1   = aout1*kAmpAdsr
    aout2   = aout2*kAmpAdsr
    gaEffL  += aout1
    gaEffR  += aout2

	  outs aout1, aout2
endin

instr LFOs
    kLFO1Shape  chnget "LFO1Shape"
    kLFO1Freq   chnget "LFO1Freq"
    kLFO1Amp    chnget "LFO1Amp"
    kLFO2Shape  chnget "LFO2Shape"
    kLFO2Freq   chnget "LFO2Freq"
    kLFO2Amp    chnget "LFO2Amp"

    gkLFO1      MorphLFO kLFO1Shape, kLFO1Freq, kLFO1Amp
    gkLFO2      MorphLFO kLFO2Shape, kLFO2Freq, kLFO2Amp
endin

instr Delay
    kEffLev chnget "DelaySend"
    kRate   chnget "DelayTime"
    kFdbk   chnget "DelayFeedback"
    atapR   init 0

    aIn     = (gaEffL+gaEffR)*.5

    abufL   delayr 1
    atapL   deltap kRate*1.05
    delayw aIn*kEffLev + atapR*kFdbk

    abufR   delayr 1
    atapR   deltap kRate*0.956
    delayw atapL*kFdbk

    gaEffL  += atapL
    gaEffR  += atapR

    outs atapL,atapR
endin

instr Reverb
    kEffLev   chnget "ReverbSend"
    kSize     chnget "ReverbSize"
    a1,a2     reverbsc gaEffL*kEffLev, gaEffR*kEffLev, kSize, 10000
    outs a1,a2
    gaEffL = 0
    gaEffR = 0
endin

</CsInstruments>

<CsScore>
i "Globals" 0  1 110 0   0     0
;i1 1  1 110 0   0.2   0
;i1 2  1 110 0   0.6   0
;i1 3  1 110 0   2     0
;i1 4  1 110 0   0     0.25
;i1 5  1 110 0   0.2   0.25
;i1 6  1 110 0   0.6   0.25
;i1 7  1 110 0   2     0.25
;i1 8  1 110 0   0     0.5
;i1 9  1 110 0   0.2   0.5
;i1 10 1 110 0   0.6   0.5
;i1 11 1 110 0   2     0.5
;i1 12 1 110 0   0     0.75
;i1 13 1 110 0   0.2   0.75
;i1 14 1 110 0   0.6   0.75
;i1 15 1 110 0   2     0.75

f0 [3600*24*365]
f1 0 1024 7 0 512 1 512 0

; MorphOsc waveforms, also used with LFOs (sine, tri, saw, square)
f10 0 8192 10 1
f11 0 8192 7 0 2048 1 4096 -1 2048 0
f12 0 8192 7 -1 8192 1
f13 0 8192 7 -1 4095 -1 1 1 4096 1

; Always-on instruments
i "LFOs" 0 [3600*24*365]
i "Delay" 0 [3600*24*365]
i "Reverb" 0 [3600*24*365]
</CsScore>
</CsoundSynthesizer>
