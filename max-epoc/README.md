# Emotiv EPOC Max external

Cycling '74 Max external for gathering Emotiv EPOC data.

## Installation

1. Download the [package](https://github.com/oscii/max-epoc/releases).
2. Unzip to the packages directory
  - On Mac: `~/Documents/Max 7/packages` with Max 7 and `/Applications/Max 6.1/packages` with Max 6.
  - On Windows: I don't have Emotiv Research SDK for Windows, so if you have some time, you can contribute the Windows build.

## Usage

Check the `example.epoc.maxpat` file in `examples` directory.

## Functionality

This external can collect the follwing data:

1. Emotional state (frustration, excitement, etc.).
2. Cognitive commands (push, pull, etc.).
3. Raw sensor data (AF3, FC5, etc.).
4. Accelerometer data.
5. Battery charge.
6. Signal strength.

## Limitation

1. Only one user/headset supported currently.
2. Only one instance of this external can exist in the patch. This is due to the fact,
that Emotiv API allows only one conection to their EmoEngine, and I'm too lazy to write a workaround.

## Disclaimer

I suspect, that Emotiv's SDK tools are very unstable and could cause Max crash, so use with care.
Also this external was developed with Research SDK and I don't have an opportunity to check its work with another SDKs/headsets.

If you have any problems or feature request, please submit an issue.
