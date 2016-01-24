TelluriaEdgeBlending
====================

Soft edge and surface mapping solution for circular projection in the “Telluria” theatrical performance (The New Stage of Alexandrinsky theatre, Saint-Petersburg, Russia)

## Hardware

We used 13 projectors, connected to the Mac Pro (late 2013)
via five Matrox's DualHead2Go and one TrippleHead2Go.

## Software

There are 5 QuartzComposer compositions called MovieClientTS* which receives
hi-res video (12000x800) from Syphon-supported software. Each client slices its
own part of the video and outputs it through correspoinding Matrox device.

There is the Max patch in `proj_control` directory, which controls the surface
shape and soft edge sizes of each projector. This patch allows to setup blend edges
and surface shape of each projector remotely from any laptop (the Mac Pro itself
is placed on a fixture and not accessible to the staff).
