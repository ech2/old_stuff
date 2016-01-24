<CsoundSynthesizer>
    <CsOptions>
        -odac -d
    </CsOptions>
    <CsInstruments>
        sr = 44100
        kr = 4410
        0dbfs = 1
        nchnls = 1

        instr 1
            a1 oscil 1, p4, 1
            out	a1 * 0.25
        endin
    </CsInstruments>

    <CsScore>
        f 0 6000
        f 1 0 16384 10 1 0.5 0.333
    </CsScore>

</CsoundSynthesizer>
