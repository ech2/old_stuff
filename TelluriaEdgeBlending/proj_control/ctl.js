
var meshCoords = [];
var softEdgeWidths = [];

var sizeX_norm = 2.0;
var sizeY_norm = sizeX_norm * (800.0 / 2560.0);

var boundsX = {min: -1.0, max: 1.0};
var boundsY = {min: -(sizeY_norm / 2), max: sizeY_norm / 2};

var stepX = sizeX_norm / 3.0;
var stepY = sizeY_norm / 3.0;

var lcdSize = {x: 640.0, y:400.0};

var editMeshIndex = 0;
var editPointIndex = -1;

var tolerance = 0.015;

var colorRegular = [254, 178, 87];
var colorHighlighted = [252, 92, 148];

var scaleX = getScaleX();
var scaleY = getScaleY();

var sizeY_norm_th = sizeX_norm * (800.0 / 3840.0);
var stepY_th = sizeY_norm_th / 3.0;
var boundsY_th = {min: -(sizeY_norm_th / 2), max: sizeY_norm_th / 2};

// Init state
resetDefaults();
for (var i = 0; i < meshCoords.length; i++) {
    editMesh(i);
    drawMesh();
}
editMesh(0);



function drawMesh() {
    outlet(0, "draw", "brgb", 27, 29, 30);
    outlet(0, "draw", "frgb", colorRegular);
    outlet(0, "draw", "clear");
    for (var i = 0; i < 32; i += 2) {
        var x = scaleX(meshCoords[editMeshIndex][i]);
        var y = scaleY(meshCoords[editMeshIndex][i + 1]);
        if (Math.floor(i / 2) == editPointIndex) {
            outlet(0, "draw", "paintoval", x - 5, y - 5, x + 5, y + 5, colorHighlighted);
        } else {
            outlet(0, "draw", "paintoval", x - 5, y - 5, x + 5, y + 5, colorRegular);
        }
    }
    outlet(0, "draw", "bang");
    sendToQC();
}


// Mouse events

function click(mx, my) {
    var result = -1;
    for (var i = 0; i < 32; i += 2) {
        var x = meshCoords[editMeshIndex][i];
        var y = meshCoords[editMeshIndex][i + 1];
        if (mx < x + tolerance && mx > x - tolerance &&
            my < y + tolerance && my > y - tolerance) {
            result = Math.floor(i / 2);
        }
    }
    editPointIndex = result;
    drawMesh();
}

function drag(x, y) {
    if (editPointIndex == -1) {
        return;
    }
    meshCoords[editMeshIndex][editPointIndex * 2] = x;
    meshCoords[editMeshIndex][editPointIndex * 2 + 1] = y;
    drawMesh();
}

function release() {

}

////////////

function setSoftEdgeWidths(left, right) {
    softEdgeWidths[editMeshIndex] = [left, right];
    outlet(0, "soft_edge_widths", softEdgeWidths[editMeshIndex]);
    sendToQC_SE(editMeshIndex);
}

function sendToQC() {
    sendToQC_XY(editMeshIndex);
    sendToQC_SE(editMeshIndex);
}

function sendToQC_XY(meshIndex) {
    var oscXY = getOscAddress_XYArray(meshIndex);
    outlet(0, "qc_data", meshIndex, oscXY, meshCoords[meshIndex]);
}
function sendToQC_SE(meshIndex) {
    var oscSE = getOscAddress_SoftEdgeWidths(meshIndex);
    outlet(0, "qc_data", meshIndex, oscSE, softEdgeWidths[meshIndex]);

}

function getOscAddress_XYArray(meshIndex) {
    return "/" + (meshIndex) + "/xy_array";
}
function getOscAddress_SoftEdgeWidths(meshIndex) {
    return "/" + (meshIndex) + "/soft_edge_widths";
}

function editMesh(meshIndex) {
    editMeshIndex = meshIndex;
    scaleX = getScaleX();
    scaleY = getScaleY();
    outlet(0, "edit_mesh", editMeshIndex);
    drawMesh();
    outlet(0, "soft_edge_widths", softEdgeWidths[editMeshIndex]);
}

function getMesh(meshIndex) {
    outlet(0, meshCoords[meshIndex]);
}


function resetCurrentMesh() {
    if (editMeshIndex >= 0 && editMeshIndex < 10) {
        if (editMeshIndex % 2 == 0) {
            meshCoords[editMeshIndex] = genDualHeadLeft();
        } else {
            meshCoords[editMeshIndex] = genDualHeadRight();
        }
    } else {
        if (editMeshIndex == 10) {
            meshCoords[editMeshIndex] = genTrippleHeadLeft();
        } else if (editMeshIndex == 11) {
            meshCoords[editMeshIndex] = genTrippleHeadMiddle();
        } else {
            meshCoords[editMeshIndex] = genTrippleHeadRight();
        }
    }
    drawMesh();
}

function resetDefaults() {
    meshCoords = [];
    for (var i = 0; i < 13; i++) {
        if (i >= 0 && i < 10) {
            if (i % 2 == 0) {
                meshCoords.push(genDualHeadLeft());
            } else {
                meshCoords.push(genDualHeadRight());
            }
        } else {
            if (i == 10) {
                meshCoords.push(genTrippleHeadLeft());
            } else if (i == 11) {
                meshCoords.push(genTrippleHeadMiddle());
            } else {
                meshCoords.push(genTrippleHeadRight());
            }
        }
    }
    softEdgeWidths = [];
    for (var i = 0; i < 13; i++) {
        softEdgeWidths[i] = [0, 0];
    }
}



function getScaleX() {
    var result;
    if (editMeshIndex < 10) {
        if (editMeshIndex % 2 == 0) {
            result = function (x) {
                return scaleX_proto((x + 0.5) * 2);
            }
        } else {
            result = function (x) {
                return scaleX_proto((x - 0.5) * 2);
            }
        }
    } else {
        if (editMeshIndex == 10) {
            result = function (x) {
                return scaleX_proto((x + 0.6666667) * 3);
            }
        } else if (editMeshIndex == 11) {
            result = function (x) {
                return scaleX_proto(x * 3);
            }
        } else {
            result = function (x) {
                return scaleX_proto((x - 0.6666667) * 3);
            }
        }
    }
    return result;
}

// [-1; 1] -> [0; 640]
function scaleX_proto(x) {
    return x * 320 + 320;
}
// [-0.3125; 0.3125] -> [400; 0]
function getScaleY() {
    if (editMeshIndex < 10) {
        return function (y) {
            return scaleY_dh(y);
        }
    } else {
        return function (y) {
            return scaleY_th(y);
        }
    }
}

function scaleY_dh(y) {
    return -640 * y + 200;
}
function scaleY_th(y) {
    return -960 * y + 200;
}

// Default generators

function genDualHeadLeft() {
    var result = [];
    for (var i = 0; i < 16; i++) {
        result.push(-1 * i % 4 * (stepX * 0.5));   // x
        result.push(boundsY.min + Math.floor(i / 4) * stepY);
    }
    return result;
}

function genDualHeadRight() {
    var result = [];
    for (var i = 0; i < 16; i++) {
        result.push(-1 * i % 4 * (stepX * 0.5) + 1); // x
        result.push(boundsY.min + Math.floor(i / 4) * stepY);
    }
    return result;
}

function genTrippleHeadLeft() {
    var result = [];
    for (var i = 0; i < 16; i++) {
        result.push(-1 * i % 4 * (stepX * 0.3333334) - 0.3333334); // x
        result.push(boundsY_th.min + Math.floor(i / 4) * stepY_th);
    }
    return result;
}

function genTrippleHeadMiddle() {
    var result = [];
    for (var i = 0; i < 16; i++) {
        result.push(0.3333334 - i % 4 * (stepX * 0.3333334));   // x
        result.push(boundsY_th.min + Math.floor(i / 4) * stepY_th);
    }
    return result;
}

function genTrippleHeadRight() {
    var result = [];
    for (var i = 0; i < 16; i++) {
        result.push(1 - i % 4 * (stepX * 0.3333334));   // x
        result.push(boundsY_th.min + Math.floor(i / 4) * stepY_th);
    }
    return result;
}