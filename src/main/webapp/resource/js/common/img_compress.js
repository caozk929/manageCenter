function compress(file,picParam,callback){ var mpImg=new MegaPixImage(file);var param=$.extend({type:"image/jpeg",maxHeight:500,maxWidth:800,quality:1},picParam);getImageMeta(file,function(err,meta){if(meta&&meta.tiff&&meta.tiff.Orientation){param=$.extend({orientation:meta.tiff.Orientation.value},param)}var canvas=document.createElement('canvas');mpImg.onrender=function(){var base64Str="";if(navigator.userAgent.toLowerCase().indexOf("android")>-1&&param.type=="image/jpeg"){var ctx=canvas.getContext('2d');var imgData=ctx.getImageData(0,0,canvas.width,canvas.height);var encoder=new JPEGEncoder(param.quality*100);base64Str=encoder.encode(imgData);encoder=null}else{base64Str=canvas.toDataURL(picParam.type,picParam.quality)}callback(base64Str,canvas.width,canvas.height)};mpImg.render(canvas,param)})}
function getImageMeta(file,callback){var r=new FileReader;var err=null;var meta=null;r.onload=function(event){if(file.type==="image/jpeg"){try{meta=new JpegMeta.JpegFile(event.target.result,file.name)}catch(ex){err=ex}}callback(err,meta)};r.onerror=function(event){callback(event.target.error,meta)};r.readAsBinaryString(file)}

    function JPEGEncoder(quality) {
        var self = this;
        var fround = Math.round;
        var ffloor = Math.floor;
        var YTable = new Array(64);
        var UVTable = new Array(64);
        var fdtbl_Y = new Array(64);
        var fdtbl_UV = new Array(64);
        var YDC_HT;
        var UVDC_HT;
        var YAC_HT;
        var UVAC_HT;
        var bitcode = new Array(65535);
        var category = new Array(65535);
        var outputfDCTQuant = new Array(64);
        var DU = new Array(64);
        var byteout = [];
        var bytenew = 0;
        var bytepos = 7;
        var YDU = new Array(64);
        var UDU = new Array(64);
        var VDU = new Array(64);
        var clt = new Array(256);
        var RGB_YUV_TABLE = new Array(2048);
        var currentQuality;
        var ZigZag = [0, 1, 5, 6, 14, 15, 27, 28, 2, 4, 7, 13, 16, 26, 29, 42, 3, 8, 12, 17, 25, 30, 41, 43, 9, 11, 18,
            24, 31, 40, 44, 53, 10, 19, 23, 32, 39, 45, 52, 54, 20, 22, 33, 38, 46, 51, 55, 60, 21, 34, 37, 47, 50,
            56, 59, 61, 35, 36, 48, 49, 57, 58, 62, 63];
        var std_dc_luminance_nrcodes = [0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0];
        var std_dc_luminance_values = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
        var std_ac_luminance_nrcodes = [0, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125];
        var std_ac_luminance_values = [1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, 129,
            145, 161, 8, 35, 66, 177, 193, 21, 82, 209, 240, 36, 51, 98, 114, 130, 9, 10, 22, 23, 24, 25, 26, 37,
            38, 39, 40, 41, 42, 52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88,
            89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, 131, 132, 133,
            134, 135, 136, 137, 138, 146, 147, 148, 149, 150, 151, 152, 153, 154, 162, 163, 164, 165, 166, 167, 168,
            169, 170, 178, 179, 180, 181, 182, 183, 184, 185, 186, 194, 195, 196, 197, 198, 199, 200, 201, 202, 210,
            211, 212, 213, 214, 215, 216, 217, 218, 225, 226,
            227, 228, 229, 230, 231, 232, 233, 234, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250];
        var std_dc_chrominance_nrcodes = [0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0];
        var std_dc_chrominance_values = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
        var std_ac_chrominance_nrcodes = [0, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119];
        var std_ac_chrominance_values = [0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, 129, 8,
            20, 66, 145, 161, 177, 193, 9, 35, 51, 82, 240, 21, 98, 114, 209, 10, 22, 36, 52, 225, 37, 241, 23, 24,
            25, 26, 38, 39, 40, 41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84,
            85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, 130,
            131, 132, 133, 134, 135, 136, 137, 138, 146, 147, 148, 149, 150, 151, 152, 153, 154, 162, 163, 164, 165,
            166, 167, 168, 169, 170, 178, 179, 180, 181, 182, 183, 184, 185, 186, 194, 195, 196, 197, 198, 199, 200,
            201, 202, 210, 211, 212, 213, 214, 215, 216, 217, 218, 226, 227, 228, 229, 230, 231, 232, 233, 234, 242,
            243, 244, 245, 246, 247, 248, 249, 250];

        function initQuantTables(sf) {
            var YQT = [16, 11, 10, 16, 24, 40, 51, 61, 12, 12, 14, 19, 26, 58, 60, 55, 14, 13, 16, 24, 40, 57, 69, 56,
                14, 17, 22, 29, 51, 87, 80,
                62, 18, 22, 37, 56, 68, 109, 103, 77, 24, 35, 55, 64, 81, 104, 113, 92, 49, 64, 78, 87, 103, 121, 120,
                101, 72, 92, 95, 98, 112, 100, 103, 99];
            for (var i = 0; i < 64; i++) {
                var t = ffloor((YQT[i] * sf + 50) / 100);
                if (t < 1) t = 1;
                else if (t > 255) t = 255;
                YTable[ZigZag[i]] = t
            }
            var UVQT = [17, 18, 24, 47, 99, 99, 99, 99, 18, 21, 26, 66, 99, 99, 99, 99, 24, 26, 56, 99, 99, 99, 99, 99,
                47, 66, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99,
                99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99];
            for (var j = 0; j < 64; j++) {
                var u = ffloor((UVQT[j] * sf + 50) / 100);
                if (u < 1) u = 1;
                else if (u > 255) u =
                    255;
                UVTable[ZigZag[j]] = u
            }
            var aasf = [1, 1.387039845, 1.306562965, 1.175875602, 1, 0.785694958, 0.5411961, 0.275899379];
            var k = 0;
            for (var row = 0; row < 8; row++) for (var col = 0; col < 8; col++) {
                fdtbl_Y[k] = 1 / (YTable[ZigZag[k]] * aasf[row] * aasf[col] * 8);
                fdtbl_UV[k] = 1 / (UVTable[ZigZag[k]] * aasf[row] * aasf[col] * 8);
                k++
            }
        }
        function computeHuffmanTbl(nrcodes, std_table) {
            var codevalue = 0;
            var pos_in_table = 0;
            var HT = new Array;
            for (var k = 1; k <= 16; k++) {
                for (var j = 1; j <= nrcodes[k]; j++) {
                    HT[std_table[pos_in_table]] = [];
                    HT[std_table[pos_in_table]][0] =
                        codevalue;
                    HT[std_table[pos_in_table]][1] = k;
                    pos_in_table++;
                    codevalue++
                }
                codevalue *= 2
            }
            return HT
        }
        function initHuffmanTbl() {
            YDC_HT = computeHuffmanTbl(std_dc_luminance_nrcodes, std_dc_luminance_values);
            UVDC_HT = computeHuffmanTbl(std_dc_chrominance_nrcodes, std_dc_chrominance_values);
            YAC_HT = computeHuffmanTbl(std_ac_luminance_nrcodes, std_ac_luminance_values);
            UVAC_HT = computeHuffmanTbl(std_ac_chrominance_nrcodes, std_ac_chrominance_values)
        }
        function initCategoryNumber() {
            var nrlower = 1;
            var nrupper = 2;
            for (var cat = 1; cat <=
                15; cat++) {
                for (var nr = nrlower; nr < nrupper; nr++) {
                    category[32767 + nr] = cat;
                    bitcode[32767 + nr] = [];
                    bitcode[32767 + nr][1] = cat;
                    bitcode[32767 + nr][0] = nr
                }
                for (var nrneg = -(nrupper - 1); nrneg <= -nrlower; nrneg++) {
                    category[32767 + nrneg] = cat;
                    bitcode[32767 + nrneg] = [];
                    bitcode[32767 + nrneg][1] = cat;
                    bitcode[32767 + nrneg][0] = nrupper - 1 + nrneg
                }
                nrlower <<= 1;
                nrupper <<= 1
            }
        }
        function initRGBYUVTable() {
            for (var i = 0; i < 256; i++) {
                RGB_YUV_TABLE[i] = 19595 * i;
                RGB_YUV_TABLE[i + 256 >> 0] = 38470 * i;
                RGB_YUV_TABLE[i + 512 >> 0] = 7471 * i + 32768;
                RGB_YUV_TABLE[i + 768 >> 0] = -11059 * i;
                RGB_YUV_TABLE[i + 1024 >> 0] = -21709 * i;
                RGB_YUV_TABLE[i + 1280 >> 0] = 32768 * i + 8421375;
                RGB_YUV_TABLE[i + 1536 >> 0] = -27439 * i;
                RGB_YUV_TABLE[i + 1792 >> 0] = -5329 * i
            }
        }
        function writeBits(bs) {
            var value = bs[0];
            var posval = bs[1] - 1;
            while (posval >= 0) {
                if (value & 1 << posval) bytenew |= 1 << bytepos;
                posval--;
                bytepos--;
                if (bytepos < 0) {
                    if (bytenew == 255) {
                        writeByte(255);
                        writeByte(0)
                    } else writeByte(bytenew);
                    bytepos = 7;
                    bytenew = 0
                }
            }
        }
        function writeByte(value) {
            byteout.push(clt[value])
        }
        function writeWord(value) {
            writeByte(value >> 8 & 255);
            writeByte(value &
                255)
        }
        function fDCTQuant(data, fdtbl) {
            var d0, d1, d2, d3, d4, d5, d6, d7;
            var dataOff = 0;
            var i;
            var I8 = 8;
            var I64 = 64;
            for (i = 0; i < I8; ++i) {
                d0 = data[dataOff];
                d1 = data[dataOff + 1];
                d2 = data[dataOff + 2];
                d3 = data[dataOff + 3];
                d4 = data[dataOff + 4];
                d5 = data[dataOff + 5];
                d6 = data[dataOff + 6];
                d7 = data[dataOff + 7];
                var tmp0 = d0 + d7;
                var tmp7 = d0 - d7;
                var tmp1 = d1 + d6;
                var tmp6 = d1 - d6;
                var tmp2 = d2 + d5;
                var tmp5 = d2 - d5;
                var tmp3 = d3 + d4;
                var tmp4 = d3 - d4;
                var tmp10 = tmp0 + tmp3;
                var tmp13 = tmp0 - tmp3;
                var tmp11 = tmp1 + tmp2;
                var tmp12 = tmp1 - tmp2;
                data[dataOff] = tmp10 + tmp11;
                data[dataOff +
                    4] = tmp10 - tmp11;
                var z1 = (tmp12 + tmp13) * 0.707106781;
                data[dataOff + 2] = tmp13 + z1;
                data[dataOff + 6] = tmp13 - z1;
                tmp10 = tmp4 + tmp5;
                tmp11 = tmp5 + tmp6;
                tmp12 = tmp6 + tmp7;
                var z5 = (tmp10 - tmp12) * 0.382683433;
                var z2 = 0.5411961 * tmp10 + z5;
                var z4 = 1.306562965 * tmp12 + z5;
                var z3 = tmp11 * 0.707106781;
                var z11 = tmp7 + z3;
                var z13 = tmp7 - z3;
                data[dataOff + 5] = z13 + z2;
                data[dataOff + 3] = z13 - z2;
                data[dataOff + 1] = z11 + z4;
                data[dataOff + 7] = z11 - z4;
                dataOff += 8
            }
            dataOff = 0;
            for (i = 0; i < I8; ++i) {
                d0 = data[dataOff];
                d1 = data[dataOff + 8];
                d2 = data[dataOff + 16];
                d3 = data[dataOff + 24];
                d4 = data[dataOff +
                    32];
                d5 = data[dataOff + 40];
                d6 = data[dataOff + 48];
                d7 = data[dataOff + 56];
                var tmp0p2 = d0 + d7;
                var tmp7p2 = d0 - d7;
                var tmp1p2 = d1 + d6;
                var tmp6p2 = d1 - d6;
                var tmp2p2 = d2 + d5;
                var tmp5p2 = d2 - d5;
                var tmp3p2 = d3 + d4;
                var tmp4p2 = d3 - d4;
                var tmp10p2 = tmp0p2 + tmp3p2;
                var tmp13p2 = tmp0p2 - tmp3p2;
                var tmp11p2 = tmp1p2 + tmp2p2;
                var tmp12p2 = tmp1p2 - tmp2p2;
                data[dataOff] = tmp10p2 + tmp11p2;
                data[dataOff + 32] = tmp10p2 - tmp11p2;
                var z1p2 = (tmp12p2 + tmp13p2) * 0.707106781;
                data[dataOff + 16] = tmp13p2 + z1p2;
                data[dataOff + 48] = tmp13p2 - z1p2;
                tmp10p2 = tmp4p2 + tmp5p2;
                tmp11p2 = tmp5p2 +
                    tmp6p2;
                tmp12p2 = tmp6p2 + tmp7p2;
                var z5p2 = (tmp10p2 - tmp12p2) * 0.382683433;
                var z2p2 = 0.5411961 * tmp10p2 + z5p2;
                var z4p2 = 1.306562965 * tmp12p2 + z5p2;
                var z3p2 = tmp11p2 * 0.707106781;
                var z11p2 = tmp7p2 + z3p2;
                var z13p2 = tmp7p2 - z3p2;
                data[dataOff + 40] = z13p2 + z2p2;
                data[dataOff + 24] = z13p2 - z2p2;
                data[dataOff + 8] = z11p2 + z4p2;
                data[dataOff + 56] = z11p2 - z4p2;
                dataOff++
            }
            var fDCTQuant;
            for (i = 0; i < I64; ++i) {
                fDCTQuant = data[i] * fdtbl[i];
                outputfDCTQuant[i] = fDCTQuant > 0 ? fDCTQuant + 0.5 | 0 : fDCTQuant - 0.5 | 0
            }
            return outputfDCTQuant
        }
        function writeAPP0() {
            writeWord(65504);
            writeWord(16);
            writeByte(74);
            writeByte(70);
            writeByte(73);
            writeByte(70);
            writeByte(0);
            writeByte(1);
            writeByte(1);
            writeByte(0);
            writeWord(1);
            writeWord(1);
            writeByte(0);
            writeByte(0)
        }
        function writeSOF0(width, height) {
            writeWord(65472);
            writeWord(17);
            writeByte(8);
            writeWord(height);
            writeWord(width);
            writeByte(3);
            writeByte(1);
            writeByte(17);
            writeByte(0);
            writeByte(2);
            writeByte(17);
            writeByte(1);
            writeByte(3);
            writeByte(17);
            writeByte(1)
        }
        function writeDQT() {
            writeWord(65499);
            writeWord(132);
            writeByte(0);
            for (var i = 0; i < 64; i++) writeByte(YTable[i]);
            writeByte(1);
            for (var j = 0; j < 64; j++) writeByte(UVTable[j])
        }
        function writeDHT() {
            writeWord(65476);
            writeWord(418);
            writeByte(0);
            for (var i = 0; i < 16; i++) writeByte(std_dc_luminance_nrcodes[i + 1]);
            for (var j = 0; j <= 11; j++) writeByte(std_dc_luminance_values[j]);
            writeByte(16);
            for (var k = 0; k < 16; k++) writeByte(std_ac_luminance_nrcodes[k + 1]);
            for (var l = 0; l <= 161; l++) writeByte(std_ac_luminance_values[l]);
            writeByte(1);
            for (var m = 0; m < 16; m++) writeByte(std_dc_chrominance_nrcodes[m + 1]);
            for (var n = 0; n <= 11; n++) writeByte(std_dc_chrominance_values[n]);
            writeByte(17);
            for (var o = 0; o < 16; o++) writeByte(std_ac_chrominance_nrcodes[o + 1]);
            for (var p = 0; p <= 161; p++) writeByte(std_ac_chrominance_values[p])
        }
        function writeSOS() {
            writeWord(65498);
            writeWord(12);
            writeByte(3);
            writeByte(1);
            writeByte(0);
            writeByte(2);
            writeByte(17);
            writeByte(3);
            writeByte(17);
            writeByte(0);
            writeByte(63);
            writeByte(0)
        }
        function processDU(CDU, fdtbl, DC, HTDC, HTAC) {
            var EOB = HTAC[0];
            var M16zeroes = HTAC[240];
            var pos;
            var I16 = 16;
            var I63 = 63;
            var I64 = 64;
            var DU_DCT = fDCTQuant(CDU, fdtbl);
            for (var j = 0; j < I64; ++j) DU[ZigZag[j]] =
                DU_DCT[j];
            var Diff = DU[0] - DC;
            DC = DU[0];
            if (Diff == 0) writeBits(HTDC[0]);
            else {
                pos = 32767 + Diff;
                writeBits(HTDC[category[pos]]);
                writeBits(bitcode[pos])
            }
            var end0pos = 63;
            for (; end0pos > 0 && DU[end0pos] == 0; end0pos--);
            if (end0pos == 0) {
                writeBits(EOB);
                return DC
            }
            var i = 1;
            var lng;
            while (i <= end0pos) {
                var startpos = i;
                for (; DU[i] == 0 && i <= end0pos; ++i);
                var nrzeroes = i - startpos;
                if (nrzeroes >= I16) {
                    lng = nrzeroes >> 4;
                    for (var nrmarker = 1; nrmarker <= lng; ++nrmarker) writeBits(M16zeroes);
                    nrzeroes = nrzeroes & 15
                }
                pos = 32767 + DU[i];
                writeBits(HTAC[(nrzeroes <<
                    4) + category[pos]]);
                writeBits(bitcode[pos]);
                i++
            }
            if (end0pos != I63) writeBits(EOB);
            return DC
        }
        function initCharLookupTable() {
            var sfcc = String.fromCharCode;
            for (var i = 0; i < 256; i++) clt[i] = sfcc(i)
        }
        this.encode = function (image, quality, callback) {
            var time_start = (new Date).getTime();
            if (quality) setQuality(quality);
            byteout = new Array;
            bytenew = 0;
            bytepos = 7;
            writeWord(65496);
            writeAPP0();
            writeDQT();
            writeSOF0(image.width, image.height);
            writeDHT();
            writeSOS();
            var DCY = 0;
            var DCU = 0;
            var DCV = 0;
            bytenew = 0;
            bytepos = 7;
            this.encode.displayName =
                "_encode_";
            var imageData = image.data;
            var width = image.width;
            var height = image.height;
            var quadWidth = width * 4;
            var tripleWidth = width * 3;
            var x, y = 0;
            var r, g, b;
            var start, p, col, row, pos;
            var _process = function (y) {
                if (y >= height) {
                    if (bytepos >= 0) {
                        var fillbits = [];
                        fillbits[1] = bytepos + 1;
                        fillbits[0] = (1 << bytepos + 1) - 1;
                        writeBits(fillbits)
                    }
                    writeWord(65497);
                    var jpegDataUri = "data:image/jpeg;base64," + btoa(byteout.join(""));
                    byteout = [];
                    var duration = (new Date).getTime() - time_start;
                    callback(jpegDataUri)
                } else {
                    x = 0;
                    while (x < quadWidth) {
                        start =
                            quadWidth * y + x;
                        p = start;
                        col = -1;
                        row = 0;
                        for (pos = 0; pos < 64; pos++) {
                            row = pos >> 3;
                            col = (pos & 7) * 4;
                            p = start + row * quadWidth + col;
                            if (y + row >= height) p -= quadWidth * (y + 1 + row - height);
                            if (x + col >= quadWidth) p -= x + col - quadWidth + 4;
                            r = imageData[p++];
                            g = imageData[p++];
                            b = imageData[p++];
                            YDU[pos] = (RGB_YUV_TABLE[r] + RGB_YUV_TABLE[g + 256 >> 0] + RGB_YUV_TABLE[b + 512 >> 0] >>
                                16) - 128;
                            UDU[pos] = (RGB_YUV_TABLE[r + 768 >> 0] + RGB_YUV_TABLE[g + 1024 >> 0] + RGB_YUV_TABLE[b +
                                1280 >> 0] >> 16) - 128;
                            VDU[pos] = (RGB_YUV_TABLE[r + 1280 >> 0] + RGB_YUV_TABLE[g + 1536 >> 0] + RGB_YUV_TABLE[b +
                                1792 >> 0] >> 16) - 128
                        }
                        DCY = processDU(YDU, fdtbl_Y, DCY, YDC_HT, YAC_HT);
                        DCU = processDU(UDU, fdtbl_UV, DCU, UVDC_HT, UVAC_HT);
                        DCV = processDU(VDU, fdtbl_UV, DCV, UVDC_HT, UVAC_HT);
                        x += 32
                    }
                    setTimeout(function () {
                        _process(y + 8)
                    }, 10)
                }
            };
            _process(0)
        };

        function setQuality(quality) {
            if (quality <= 0) quality = 1;
            if (quality > 100) quality = 100;
            if (currentQuality == quality) return;
            var sf = 0;
            if (quality < 50) sf = Math.floor(5E3 / quality);
            else sf = Math.floor(200 - quality * 2);
            initQuantTables(sf);
            currentQuality = quality
        }
        function init() {
            var time_start = (new Date).getTime();
            if (!quality) quality = 50;
            initCharLookupTable();
            initHuffmanTbl();
            initCategoryNumber();
            initRGBYUVTable();
            setQuality(quality);
            var duration = (new Date).getTime() - time_start
        }
        init()
    }
    
    var JpegMeta = {};

/*
 parse an unsigned number of size bytes at offset in some binary string data.
 If endian
 is "<" parse the data as little endian, if endian
 is ">" parse as big-endian.
 */
JpegMeta.parseNum = function parseNum(endian, data, offset, size) {
    var i;
    var ret;
    var big_endian = (endian === ">");
    if (offset === undefined) offset = 0;
    if (size === undefined) size = data.length - offset;
    for (big_endian ? i = offset : i =
        offset + size - 1; big_endian ? i < offset + size : i >= offset; big_endian ? i++ : i--) {
        ret <<= 8;
        ret += data.charCodeAt(i);
    }
    return ret;
};

/*
 parse an signed number of size bytes at offset in some binary string data.
 If endian
 is "<" parse the data as little endian, if endian
 is ">" parse as big-endian.
 */
JpegMeta.parseSnum = function parseSnum(endian, data, offset, size) {
    var i;
    var ret;
    var neg;
    var big_endian = (endian === ">");
    if (offset === undefined) offset = 0;
    if (size === undefined) size = data.length - offset;
    for (big_endian ? i = offset : i =
        offset + size - 1; big_endian ? i < offset + size : i >= offset; big_endian ? i++ : i--) {
        if (neg === undefined) {
            /* Negative if top bit is set */
            neg = (data.charCodeAt(i) & 0x80) === 0x80;
        }
        ret <<= 8;
        /* If it is negative we invert the bits */
        ret += neg ? ~data.charCodeAt(i) & 0xff : data.charCodeAt(i);
    }
    if (neg) {
        /* If it is negative we do two's complement */
        ret += 1;
        ret *= -1;
    }
    return ret;
};

/* Rational number class */
JpegMeta.Rational = function Rational(num, den) {
    this.num = num;
    this.den = den || 1;
    return this;
};

/* Rational number methods */
JpegMeta.Rational.prototype.toString = function toString() {
    if (this.num === 0) {
        return "" + this.num
    }
    if (this.den === 1) {
        return "" + this.num;
    }
    if (this.num === 1) {
        return this.num + " / " + this.den;
    }
    return this.num / this.den; // + "/" + this.den;
};

JpegMeta.Rational.prototype.asFloat = function asFloat() {
    return this.num / this.den;
};


/* MetaGroup class */
JpegMeta.MetaGroup = function MetaGroup(fieldName, description) {
    this.fieldName = fieldName;
    this.description = description;
    this.metaProps = {};
    return this;
};

JpegMeta.MetaGroup.prototype._addProperty = function _addProperty(fieldName, description, value) {
    var property = new JpegMeta.MetaProp(fieldName, description, value);
    this[property.fieldName] = property;
    this.metaProps[property.fieldName] = property;
};

JpegMeta.MetaGroup.prototype.toString = function toString() {
    return "[MetaGroup " + this.description + "]";
};


/* MetaProp class */
JpegMeta.MetaProp = function MetaProp(fieldName, description, value) {
    this.fieldName = fieldName;
    this.description = description;
    this.value = value;
    return this;
};

JpegMeta.MetaProp.prototype.toString = function toString() {
    return "" + this.value;
};



/* JpegFile class */
JpegMeta.JpegFile = function JpegFile(binary_data, filename) {
    /* Change this to EOI if we want to parse. */
    var break_segment = this._SOS;

    this.metaGroups = {};
    this._binary_data = binary_data;
    this.filename = filename;

    /* Go through and parse. */
    var pos = 0;
    var pos_start_of_segment = 0;
    var delim;
    var mark;
    var _mark;
    var segsize;
    var headersize;
    var mark_code;
    var mark_fn;

    /* Check to see if this looks like a JPEG file */
    if (this._binary_data.slice(0, 2) !== this._SOI_MARKER) {
        throw new Error("Doesn't look like a JPEG file. First two bytes are " + this._binary_data.charCodeAt(0) + "," + this._binary_data.charCodeAt(1) + ".");
    }

    pos += 2;

    while (pos < this._binary_data.length) {
        delim = this._binary_data.charCodeAt(pos++);
        mark = this._binary_data.charCodeAt(pos++);

        pos_start_of_segment = pos;

        if (delim != this._DELIM) {
            break;
        }

        if (mark === break_segment) {
            break;
        }

        headersize = JpegMeta.parseNum(">", this._binary_data, pos, 2);

        /* Find the end */
        pos += headersize;
        while (pos < this._binary_data.length) {
            delim = this._binary_data.charCodeAt(pos++);
            if (delim == this._DELIM) {
                _mark = this._binary_data.charCodeAt(pos++);
                if (_mark != 0x0) {
                    pos -= 2;
                    break;
                }
            }
        }

        segsize = pos - pos_start_of_segment;

        if (this._markers[mark]) {
            mark_code = this._markers[mark][0];
            mark_fn = this._markers[mark][1];
        } else {
            mark_code = "UNKN";
            mark_fn = undefined;
        }

        if (mark_fn) {
            this[mark_fn](mark, pos_start_of_segment + 2);
        }

    }

    if (this.general === undefined) {
        throw Error("Invalid JPEG file.");
    }

    return this;
};

JpegMeta.JpegFile.prototype.toString = function () {
    return "[JpegFile " + this.filename + " " + this.general.type + " " + this.general.pixelWidth + "x" + this.general.pixelHeight + " Depth: " + this.general.depth + "]";
};

/* Some useful constants */
JpegMeta.JpegFile.prototype._SOI_MARKER = '\xff\xd8';
JpegMeta.JpegFile.prototype._DELIM = 0xff;
JpegMeta.JpegFile.prototype._EOI = 0xd9;
JpegMeta.JpegFile.prototype._SOS = 0xda;

JpegMeta.JpegFile.prototype._sofHandler = function _sofHandler(mark, pos) {
    if (this.general !== undefined) {
        throw Error("Unexpected multiple-frame image");
    }

    this._addMetaGroup("general", "General");
    this.general._addProperty("depth", "Depth", JpegMeta.parseNum(">", this._binary_data, pos, 1));
    this.general._addProperty("pixelHeight", "Pixel Height", JpegMeta.parseNum(">", this._binary_data, pos + 1, 2));
    this.general._addProperty("pixelWidth", "Pixel Width", JpegMeta.parseNum(">", this._binary_data, pos + 3, 2));
    this.general._addProperty("type", "Type", this._markers[mark][2]);
};

/* JFIF idents */
JpegMeta.JpegFile.prototype._JFIF_IDENT = "JFIF\x00";
JpegMeta.JpegFile.prototype._JFXX_IDENT = "JFXX\x00";

/* EXIF idents */
JpegMeta.JpegFile.prototype._EXIF_IDENT = "Exif\x00";

/* TIFF types */
JpegMeta.JpegFile.prototype._types = {
    /* The format is identifier : ["type name", type_size_in_bytes ] */
    1 : ["BYTE", 1],
    2 : ["ASCII", 1],
    3 : ["SHORT", 2],
    4 : ["LONG", 4],
    5 : ["RATIONAL", 8],
    6 : ["SBYTE", 1],
    7 : ["UNDEFINED", 1],
    8 : ["SSHORT", 2],
    9 : ["SLONG", 4],
    10 : ["SRATIONAL", 8],
    11 : ["FLOAT", 4],
    12 : ["DOUBLE", 8]
};

JpegMeta.JpegFile.prototype._tifftags = {
    /* A. Tags relating to image data structure */
    256 : ["Image width", "ImageWidth"],
    257 : ["Image height", "ImageLength"],
    258 : ["Number of bits per component", "BitsPerSample"],
    259 : ["Compression scheme", "Compression",
        {1 : "uncompressed", 6 : "JPEG compression" }],
    262 : ["Pixel composition", "PhotmetricInerpretation",
        {2 : "RGB", 6 : "YCbCr"}],
    274 : ["Orientation of image", "Orientation",
        /* FIXME: Check the mirror-image / reverse encoding and rotation */
        {1 : "Normal", 2 : "Reverse?",
            3 : "Upside-down", 4 : "Upside-down Reverse",
            5 : "90 degree CW", 6 : "90 degree CW reverse",
            7 : "90 degree CCW", 8 : "90 degree CCW reverse"}],
    277 : ["Number of components", "SamplesPerPixel"],
    284 : ["Image data arrangement", "PlanarConfiguration",
        {1 : "chunky format", 2 : "planar format"}],
    530 : ["Subsampling ratio of Y to C", "YCbCrSubSampling"],
    531 : ["Y and C positioning", "YCbCrPositioning",
        {1 : "centered", 2 : "co-sited"}],
    282 : ["X Resolution", "XResolution"],
    283 : ["Y Resolution", "YResolution"],
    296 : ["Resolution Unit", "ResolutionUnit",
        {2 : "inches", 3 : "centimeters"}],
    /* B. Tags realting to recording offset */
    273 : ["Image data location", "StripOffsets"],
    278 : ["Number of rows per strip", "RowsPerStrip"],
    279 : ["Bytes per compressed strip", "StripByteCounts"],
    513 : ["Offset to JPEG SOI", "JPEGInterchangeFormat"],
    514 : ["Bytes of JPEG Data", "JPEGInterchangeFormatLength"],
    /* C. Tags relating to image data characteristics */
    301 : ["Transfer function", "TransferFunction"],
    318 : ["White point chromaticity", "WhitePoint"],
    319 : ["Chromaticities of primaries", "PrimaryChromaticities"],
    529 : ["Color space transformation matrix coefficients", "YCbCrCoefficients"],
    532 : ["Pair of black and white reference values", "ReferenceBlackWhite"],
    /* D. Other tags */
    306 : ["Date and time", "DateTime"],
    270 : ["Image title", "ImageDescription"],
    271 : ["Make", "Make"],
    272 : ["Model", "Model"],
    305 : ["Software", "Software"],
    315 : ["Person who created the image", "Artist"],
    316 : ["Host Computer", "HostComputer"],
    33432 : ["Copyright holder", "Copyright"],

    34665 : ["Exif tag", "ExifIfdPointer"],
    34853 : ["GPS tag", "GPSInfoIfdPointer"]
};

JpegMeta.JpegFile.prototype._exiftags = {
    /* Tag Support Levels (2) - 0th IFX Exif Private Tags */
    /* A. Tags Relating to Version */
    36864: ["Exif Version", "ExifVersion"],
    40960: ["FlashPix Version", "FlashpixVersion"],

    /* B. Tag Relating to Image Data Characteristics */
    40961: ["Color Space", "ColorSpace"],

    /* C. Tags Relating to Image Configuration */
    37121: ["Meaning of each component", "ComponentsConfiguration"],
    37122: ["Compressed Bits Per Pixel", "CompressedBitsPerPixel"],
    40962: ["Pixel X Dimension", "PixelXDimension"],
    40963: ["Pixel Y Dimension", "PixelYDimension"],

    /* D. Tags Relating to User Information */
    37500: ["Manufacturer notes", "MakerNote"],
    37510: ["User comments", "UserComment"],

    /* E. Tag Relating to Related File Information */
    40964: ["Related audio file", "RelatedSoundFile"],

    /* F. Tags Relating to Date and Time */
    36867: ["Date Time Original", "DateTimeOriginal"],
    36868: ["Date Time Digitized", "DateTimeDigitized"],
    37520: ["DateTime subseconds", "SubSecTime"],
    37521: ["DateTimeOriginal subseconds", "SubSecTimeOriginal"],
    37522: ["DateTimeDigitized subseconds", "SubSecTimeDigitized"],

    /* G. Tags Relating to Picture-Taking Conditions */
    33434: ["Exposure time", "ExposureTime"],
    33437: ["FNumber", "FNumber"],
    34850: ["Exposure program", "ExposureProgram"],
    34852: ["Spectral sensitivity", "SpectralSensitivity"],
    34855: ["ISO Speed Ratings", "ISOSpeedRatings"],
    34856: ["Optoelectric coefficient", "OECF"],
    37377: ["Shutter Speed", "ShutterSpeedValue"],
    37378: ["Aperture Value", "ApertureValue"],
    37379: ["Brightness", "BrightnessValue"],
    37380: ["Exposure Bias Value", "ExposureBiasValue"],
    37381: ["Max Aperture Value", "MaxApertureValue"],
    37382: ["Subject Distance", "SubjectDistance"],
    37383: ["Metering Mode", "MeteringMode"],
    37384: ["Light Source", "LightSource"],
    37385: ["Flash", "Flash"],
    37386: ["Focal Length", "FocalLength"],
    37396: ["Subject Area", "SubjectArea"],
    41483: ["Flash Energy", "FlashEnergy"],
    41484: ["Spatial Frequency Response", "SpatialFrequencyResponse"],
    41486: ["Focal Plane X Resolution", "FocalPlaneXResolution"],
    41487: ["Focal Plane Y Resolution", "FocalPlaneYResolution"],
    41488: ["Focal Plane Resolution Unit", "FocalPlaneResolutionUnit"],
    41492: ["Subject Location", "SubjectLocation"],
    41493: ["Exposure Index", "ExposureIndex"],
    41495: ["Sensing Method", "SensingMethod"],
    41728: ["File Source", "FileSource"],
    41729: ["Scene Type", "SceneType"],
    41730: ["CFA Pattern", "CFAPattern"],
    41985: ["Custom Rendered", "CustomRendered"],
    41986: ["Exposure Mode", "Exposure Mode"],
    41987: ["White Balance", "WhiteBalance"],
    41988: ["Digital Zoom Ratio", "DigitalZoomRatio"],
    41990: ["Scene Capture Type", "SceneCaptureType"],
    41991: ["Gain Control", "GainControl"],
    41992: ["Contrast", "Contrast"],
    41993: ["Saturation", "Saturation"],
    41994: ["Sharpness", "Sharpness"],
    41995: ["Device settings description", "DeviceSettingDescription"],
    41996: ["Subject distance range", "SubjectDistanceRange"],

    /* H. Other Tags */
    42016: ["Unique image ID", "ImageUniqueID"],

    40965: ["Interoperability tag", "InteroperabilityIFDPointer"]
};

JpegMeta.JpegFile.prototype._gpstags = {
    /* A. Tags Relating to GPS */
    0: ["GPS tag version", "GPSVersionID"],
    1: ["North or South Latitude", "GPSLatitudeRef"],
    2: ["Latitude", "GPSLatitude"],
    3: ["East or West Longitude", "GPSLongitudeRef"],
    4: ["Longitude", "GPSLongitude"],
    5: ["Altitude reference", "GPSAltitudeRef"],
    6: ["Altitude", "GPSAltitude"],
    7: ["GPS time (atomic clock)", "GPSTimeStamp"],
    8: ["GPS satellites usedd for measurement", "GPSSatellites"],
    9: ["GPS receiver status", "GPSStatus"],
    10: ["GPS mesaurement mode", "GPSMeasureMode"],
    11: ["Measurement precision", "GPSDOP"],
    12: ["Speed unit", "GPSSpeedRef"],
    13: ["Speed of GPS receiver", "GPSSpeed"],
    14: ["Reference for direction of movement", "GPSTrackRef"],
    15: ["Direction of movement", "GPSTrack"],
    16: ["Reference for direction of image", "GPSImgDirectionRef"],
    17: ["Direction of image", "GPSImgDirection"],
    18: ["Geodetic survey data used", "GPSMapDatum"],
    19: ["Reference for latitude of destination", "GPSDestLatitudeRef"],
    20: ["Latitude of destination", "GPSDestLatitude"],
    21: ["Reference for longitude of destination", "GPSDestLongitudeRef"],
    22: ["Longitude of destination", "GPSDestLongitude"],
    23: ["Reference for bearing of destination", "GPSDestBearingRef"],
    24: ["Bearing of destination", "GPSDestBearing"],
    25: ["Reference for distance to destination", "GPSDestDistanceRef"],
    26: ["Distance to destination", "GPSDestDistance"],
    27: ["Name of GPS processing method", "GPSProcessingMethod"],
    28: ["Name of GPS area", "GPSAreaInformation"],
    29: ["GPS Date", "GPSDateStamp"],
    30: ["GPS differential correction", "GPSDifferential"]
};


JpegMeta.JpegFile.prototype._markers = {
    /* Start Of Frame markers, non-differential, Huffman coding */
    0xc0: ["SOF0", "_sofHandler", "Baseline DCT"],
    0xc1: ["SOF1", "_sofHandler", "Extended sequential DCT"],
    0xc2: ["SOF2", "_sofHandler", "Progressive DCT"],
    0xc3: ["SOF3", "_sofHandler", "Lossless (sequential)"],

    /* Start Of Frame markers, differential, Huffman coding */
    0xc5: ["SOF5", "_sofHandler", "Differential sequential DCT"],
    0xc6: ["SOF6", "_sofHandler", "Differential progressive DCT"],
    0xc7: ["SOF7", "_sofHandler", "Differential lossless (sequential)"],

    /* Start Of Frame markers, non-differential, arithmetic coding */
    0xc8: ["JPG", null, "Reserved for JPEG extensions"],
    0xc9: ["SOF9", "_sofHandler", "Extended sequential DCT"],
    0xca: ["SOF10", "_sofHandler", "Progressive DCT"],
    0xcb: ["SOF11", "_sofHandler", "Lossless (sequential)"],

    /* Start Of Frame markers, differential, arithmetic coding */
    0xcd: ["SOF13", "_sofHandler", "Differential sequential DCT"],
    0xce: ["SOF14", "_sofHandler", "Differential progressive DCT"],
    0xcf: ["SOF15", "_sofHandler", "Differential lossless (sequential)"],

    /* Huffman table specification */
    0xc4: ["DHT", null, "Define Huffman table(s)"],
    0xcc: ["DAC", null, "Define arithmetic coding conditioning(s)"],

    /* Restart interval termination" */
    0xd0: ["RST0", null, "Restart with modulo 8 count “0”"],
    0xd1: ["RST1", null, "Restart with modulo 8 count “1”"],
    0xd2: ["RST2", null, "Restart with modulo 8 count “2”"],
    0xd3: ["RST3", null, "Restart with modulo 8 count “3”"],
    0xd4: ["RST4", null, "Restart with modulo 8 count “4”"],
    0xd5: ["RST5", null, "Restart with modulo 8 count “5”"],
    0xd6: ["RST6", null, "Restart with modulo 8 count “6”"],
    0xd7: ["RST7", null, "Restart with modulo 8 count “7”"],

    /* Other markers */
    0xd8: ["SOI", null, "Start of image"],
    0xd9: ["EOI", null, "End of image"],
    0xda: ["SOS", null, "Start of scan"],
    0xdb: ["DQT", null, "Define quantization table(s)"],
    0xdc: ["DNL", null, "Define number of lines"],
    0xdd: ["DRI", null, "Define restart interval"],
    0xde: ["DHP", null, "Define hierarchical progression"],
    0xdf: ["EXP", null, "Expand reference component(s)"],
    0xe0: ["APP0", "_app0Handler", "Reserved for application segments"],
    0xe1: ["APP1", "_app1Handler"],
    0xe2: ["APP2", null],
    0xe3: ["APP3", null],
    0xe4: ["APP4", null],
    0xe5: ["APP5", null],
    0xe6: ["APP6", null],
    0xe7: ["APP7", null],
    0xe8: ["APP8", null],
    0xe9: ["APP9", null],
    0xea: ["APP10", null],
    0xeb: ["APP11", null],
    0xec: ["APP12", null],
    0xed: ["APP13", null],
    0xee: ["APP14", null],
    0xef: ["APP15", null],
    0xf0: ["JPG0", null], /* Reserved for JPEG extensions */
    0xf1: ["JPG1", null],
    0xf2: ["JPG2", null],
    0xf3: ["JPG3", null],
    0xf4: ["JPG4", null],
    0xf5: ["JPG5", null],
    0xf6: ["JPG6", null],
    0xf7: ["JPG7", null],
    0xf8: ["JPG8", null],
    0xf9: ["JPG9", null],
    0xfa: ["JPG10", null],
    0xfb: ["JPG11", null],
    0xfc: ["JPG12", null],
    0xfd: ["JPG13", null],
    0xfe: ["COM", null], /* Comment */

    /* Reserved markers */
    0x01: ["JPG13", null] /* For temporary private use in arithmetic coding */
    /* 02 -> bf are reserverd */
};

/* Private methods */
JpegMeta.JpegFile.prototype._addMetaGroup = function _addMetaGroup(name, description) {
    var group = new JpegMeta.MetaGroup(name, description);
    this[group.fieldName] = group;
    this.metaGroups[group.fieldName] = group;
    return group;
};

JpegMeta.JpegFile.prototype._parseIfd =
    function _parseIfd(endian, _binary_data, base, ifd_offset, tags, name, description) {
        var num_fields = JpegMeta.parseNum(endian, _binary_data, base + ifd_offset, 2);
        /* Per tag variables */
        var i, j;
        var tag_base;
        var tag_field;
        var type, type_field, type_size;
        var num_values;
        var value_offset;
        var value;
        var _val;
        var num;
        var den;

        var group;

        group = this._addMetaGroup(name, description);

        for (var i = 0; i < num_fields; i++) {
            /* parse the field */
            tag_base = base + ifd_offset + 2 + (i * 12);
            tag_field = JpegMeta.parseNum(endian, _binary_data, tag_base, 2);
            type_field = JpegMeta.parseNum(endian, _binary_data, tag_base + 2, 2);
            num_values = JpegMeta.parseNum(endian, _binary_data, tag_base + 4, 4);
            value_offset = JpegMeta.parseNum(endian, _binary_data, tag_base + 8, 4);
            if (this._types[type_field] === undefined) {
                continue;
            }
            type = this._types[type_field][0];
            type_size = this._types[type_field][1];

            if (type_size * num_values <= 4) {
                /* Data is in-line */
                value_offset = tag_base + 8;
            } else {
                value_offset = base + value_offset;
            }

            /* Read the value */
            if (type == "UNDEFINED") {
                value = _binary_data.slice(value_offset, value_offset + num_values);
            } else if (type == "ASCII") {
                value = _binary_data.slice(value_offset, value_offset + num_values);
                value = value.split('\x00')[0]
                /* strip trail nul */
            } else {
                value = new Array();
                for (j = 0; j < num_values; j++, value_offset += type_size) {
                    if (type == "BYTE" || type == "SHORT" || type == "LONG") {
                        value.push(JpegMeta.parseNum(endian, _binary_data, value_offset, type_size));
                    }
                    if (type == "SBYTE" || type == "SSHORT" || type == "SLONG") {
                        value.push(JpegMeta.parseSnum(endian, _binary_data, value_offset, type_size));
                    }
                    if (type == "RATIONAL") {
                        num = JpegMeta.parseNum(endian, _binary_data, value_offset, 4);
                        den = JpegMeta.parseNum(endian, _binary_data, value_offset + 4, 4);
                        value.push(new JpegMeta.Rational(num, den));
                    }
                    if (type == "SRATIONAL") {
                        num = JpegMeta.parseSnum(endian, _binary_data, value_offset, 4);
                        den = JpegMeta.parseSnum(endian, _binary_data, value_offset + 4, 4);
                        value.push(new JpegMeta.Rational(num, den));
                    }
                    value.push();
                }
                if (num_values === 1) {
                    value = value[0];
                }
            }
            group._addProperty(tags[tag_field][1], tags[tag_field][0], value);
        }
    };

JpegMeta.JpegFile.prototype._jfifHandler = function _jfifHandler(mark, pos) {
    if (this.jfif !== undefined) {
        throw Error("Multiple JFIF segments found");
    }
    this._addMetaGroup("jfif", "JFIF");
    this.jfif._addProperty("version_major", "Version Major", this._binary_data.charCodeAt(pos + 5));
    this.jfif._addProperty("version_minor", "Version Minor", this._binary_data.charCodeAt(pos + 6));
    this.jfif._addProperty("version", "JFIF Version", this.jfif.version_major.value + "." + this.jfif.version_minor.value);
    this.jfif._addProperty("units", "Density Unit", this._binary_data.charCodeAt(pos + 7));
    this.jfif._addProperty("Xdensity", "X density", JpegMeta.parseNum(">", this._binary_data, pos + 8, 2));
    this.jfif._addProperty("Ydensity", "Y Density", JpegMeta.parseNum(">", this._binary_data, pos + 10, 2));
    this.jfif._addProperty("Xthumbnail", "X Thumbnail", JpegMeta.parseNum(">", this._binary_data, pos + 12, 1));
    this.jfif._addProperty("Ythumbnail", "Y Thumbnail", JpegMeta.parseNum(">", this._binary_data, pos + 13, 1));
};


/* Handle app0 segments */
JpegMeta.JpegFile.prototype._app0Handler = function app0Handler(mark, pos) {
    var ident = this._binary_data.slice(pos, pos + 5);
    if (ident == this._JFIF_IDENT) {
        this._jfifHandler(mark, pos);
    } else if (ident == this._JFXX_IDENT) {
        /* Don't handle JFXX Ident yet */
    } else {
        /* Don't know about other idents */
    }
};


/* Handle app1 segments */
JpegMeta.JpegFile.prototype._app1Handler = function _app1Handler(mark, pos) {
    var ident = this._binary_data.slice(pos, pos + 5);
    if (ident == this._EXIF_IDENT) {
        this._exifHandler(mark, pos + 6);
    } else {
        /* Don't know about other idents */
    }
};

/* Handle exif segments */
JpegMeta.JpegFile.prototype._exifHandler = function _exifHandler(mark, pos) {
    if (this.exif !== undefined) {
        throw new Error("Multiple JFIF segments found");
    }

    /* Parse this TIFF header */
    var endian;
    var magic_field;
    var ifd_offset;
    var primary_ifd, exif_ifd, gps_ifd;
    var endian_field = this._binary_data.slice(pos, pos + 2);

    /* Trivia: This 'I' is for Intel, the 'M' is for Motorola */
    if (endian_field === "II") {
        endian = "<";
    } else if (endian_field === "MM") {
        endian = ">";
    } else {
        throw new Error("Malformed TIFF meta-data. Unknown endianess: " + endian_field);
    }

    magic_field = JpegMeta.parseNum(endian, this._binary_data, pos + 2, 2);

    if (magic_field !== 42) {
        throw new Error("Malformed TIFF meta-data. Bad magic: " + magic_field);
    }

    ifd_offset = JpegMeta.parseNum(endian, this._binary_data, pos + 4, 4);

    /* Parse 0th IFD */
    this._parseIfd(endian, this._binary_data, pos, ifd_offset, this._tifftags, "tiff", "TIFF");

    if (this.tiff.ExifIfdPointer) {
        this._parseIfd(endian, this._binary_data, pos, this.tiff.ExifIfdPointer.value, this._exiftags, "exif", "Exif");
    }

    if (this.tiff.GPSInfoIfdPointer) {
        this._parseIfd(endian, this._binary_data, pos, this.tiff.GPSInfoIfdPointer.value, this._gpstags, "gps", "GPS");
        if (this.gps.GPSLatitude) {
            var latitude;
            latitude = this.gps.GPSLatitude.value[0].asFloat() +
                (1 / 60) * this.gps.GPSLatitude.value[1].asFloat() +
                (1 / 3600) * this.gps.GPSLatitude.value[2].asFloat();
            if (this.gps.GPSLatitudeRef.value === "S") {
                latitude = -latitude;
            }
            this.gps._addProperty("latitude", "Dec. Latitude", latitude);
        }
        if (this.gps.GPSLongitude) {
            var longitude;
            longitude = this.gps.GPSLongitude.value[0].asFloat() +
                (1 / 60) * this.gps.GPSLongitude.value[1].asFloat() +
                (1 / 3600) * this.gps.GPSLongitude.value[2].asFloat();
            if (this.gps.GPSLongitudeRef.value === "W") {
                longitude = -longitude;
            }
            this.gps._addProperty("longitude", "Dec. Longitude", longitude);
        }
    }
};

function detectSubsampling(img) {
    var iw = img.naturalWidth, ih = img.naturalHeight;
    if (iw * ih > 1024 * 1024) { // subsampling may happen over megapixel image
        var canvas = document.createElement('canvas');
        canvas.width = canvas.height = 1;
        var ctx = canvas.getContext('2d');
        ctx.drawImage(img, -iw + 1, 0);
        // subsampled image becomes half smaller in rendering size.
        // check alpha channel value to confirm image is covering edge pixel or not.
        // if alpha value is 0 image is not covering, hence subsampled.
        return ctx.getImageData(0, 0, 1, 1).data[3] === 0;
    } else {
        return false;
    }
}

/**
 * Detecting vertical squash in loaded image.
 * Fixes a bug which squash image vertically while drawing into canvas for some images.
 */
function detectVerticalSquash(img, iw, ih) {
    var canvas = document.createElement('canvas');
    canvas.width = 1;
    canvas.height = ih;
    var ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    var data = ctx.getImageData(0, 0, 1, ih).data;
    // search image edge pixel position in case it is squashed vertically.
    var sy = 0;
    var ey = ih;
    var py = ih;
    while (py > sy) {
        var alpha = data[(py - 1) * 4 + 3];
        if (alpha === 0) {
            ey = py;
        } else {
            sy = py;
        }
        py = (ey + sy) >> 1;
    }
    var ratio = (py / ih);
    return (ratio===0)?1:ratio;
}

/**
 * Rendering image element (with resizing) and get its data URL
 */
function renderImageToDataURL(img, options, doSquash) {
    var canvas = document.createElement('canvas');
    renderImageToCanvas(img, canvas, options, doSquash);
    return canvas.toDataURL("image/jpeg", options.quality || 0.8);
}

/**
 * Rendering image element (with resizing) into the canvas element
 */
function renderImageToCanvas(img, canvas, options, doSquash) {
    var iw = img.naturalWidth, ih = img.naturalHeight;
    var width = options.width, height = options.height;
    var ctx = canvas.getContext('2d');
    ctx.save();
    transformCoordinate(canvas, width, height, options.orientation);
    var subsampled = detectSubsampling(img);
    if (subsampled) {
        iw /= 2;
        ih /= 2;
    }
    var d = 1024; // size of tiling canvas
    var tmpCanvas = document.createElement('canvas');
    tmpCanvas.width = tmpCanvas.height = d;
    var tmpCtx = tmpCanvas.getContext('2d');
    var vertSquashRatio = doSquash ? detectVerticalSquash(img, iw, ih) : 1;
    var dw = Math.ceil(d * width / iw);
    var dh = Math.ceil(d * height / ih / vertSquashRatio);
    var sy = 0;
    var dy = 0;
    while (sy < ih) {
        var sx = 0;
        var dx = 0;
        while (sx < iw) {
            tmpCtx.clearRect(0, 0, d, d);
            tmpCtx.drawImage(img, -sx, -sy);
            ctx.drawImage(tmpCanvas, 0, 0, d, d, dx, dy, dw, dh);
            sx += d;
            dx += dw;
        }
        sy += d;
        dy += dh;
    }
    ctx.restore();
    tmpCanvas = tmpCtx = null;
}

/**
 * Transform canvas coordination according to specified frame size and orientation
 * Orientation value is from EXIF tag
 */
function transformCoordinate(canvas, width, height, orientation) {
    switch (orientation) {
        case 5:
        case 6:
        case 7:
        case 8:
            canvas.width = height;
            canvas.height = width;
            break;
        default:
            canvas.width = width;
            canvas.height = height;
    }
    var ctx = canvas.getContext('2d');
    switch (orientation) {
        case 2:
            // horizontal flip
            ctx.translate(width, 0);
            ctx.scale(-1, 1);
            break;
        case 3:
            // 180 rotate left
            ctx.translate(width, height);
            ctx.rotate(Math.PI);
            break;
        case 4:
            // vertical flip
            ctx.translate(0, height);
            ctx.scale(1, -1);
            break;
        case 5:
            // vertical flip + 90 rotate right
            ctx.rotate(0.5 * Math.PI);
            ctx.scale(1, -1);
            break;
        case 6:
            // 90 rotate right
            ctx.rotate(0.5 * Math.PI);
            ctx.translate(0, -height);
            break;
        case 7:
            // horizontal flip + 90 rotate right
            ctx.rotate(0.5 * Math.PI);
            ctx.translate(width, -height);
            ctx.scale(-1, 1);
            break;
        case 8:
            // 90 rotate left
            ctx.rotate(-0.5 * Math.PI);
            ctx.translate(-width, 0);
            break;
        default:
            break;
    }
}

// base on the browser market in China ,
// a browser named uc has a serious bug at version 8.4:
// using the api `createObjectURL` would always return the same url of first called.
// so we try using the canvas.toDataUrl to get a url
function getURLObject() {
    // notice that the official zepto does not detect some Specific browsers which mostly appear in china, like uc. so we are uisng a zepto of qzone touch version.

    return window.URL || window.webkitURL || window.mozURL
}

function getImageURL(srcImage, callback) {
    if (srcImage instanceof Blob) {
        var URL = getURLObject();
        if (URL) {
            callback(URL.createObjectURL(srcImage));
        } else {
            var r = new FileReader;
            r.onload = function (event) {
                callback(event.target.result)
            };
            r.onerror = function (event) {
                callback(null)
            };
            r.readAsDataURL(srcImage)
        }
    }
}



/**
 * MegaPixImage class
 */
function MegaPixImage(srcImage) {
    var self = this;
    getImageURL(srcImage, function(src){
        if (!src) { throw Error("can't create url"); }
        var img = new Image();
        img.src = src;
        self.blob = srcImage;
        srcImage = img;
        if (!srcImage.naturalWidth && !srcImage.naturalHeight) {
            var _this = self;
            srcImage.onload = function() {
                var listeners = _this.imageLoadListeners;
                if (listeners) {
                    _this.imageLoadListeners = null;
                    for (var i=0, len=listeners.length; i<len; i++) {
                        listeners[i]();
                    }
                }
            };
            self.imageLoadListeners = [];
        }
        self.srcImage = srcImage;
    });

}

/**
 * Rendering megapix image into specified target element
 */
MegaPixImage.prototype.render = function(target, options) {
    if (this.imageLoadListeners) {
        var _this = this;
        this.imageLoadListeners.push(function() { _this.render(target, options) });
        return;
    }
    options = options || {};
    var imgWidth = this.srcImage.naturalWidth, imgHeight = this.srcImage.naturalHeight,
        width = options.width, height = options.height,
        maxWidth = options.maxWidth, maxHeight = options.maxHeight,
        doSquash = !this.blob || this.blob.type === 'image/jpeg';

    if (width && !height) {
        height = (imgHeight * width / imgWidth) << 0;
    } else if (height && !width) {
        width = (imgWidth * height / imgHeight) << 0;
    } else {
        width = imgWidth;
        height = imgHeight;
    }
    if (maxWidth && width > maxWidth) {
        width = maxWidth;
        height = (imgHeight * width / imgWidth) << 0;
    }
    if (maxHeight && height > maxHeight) {
        height = maxHeight;
        width = (imgWidth * height / imgHeight) << 0;
    }
    var opt = { width : width, height : height };
    for (var k in options) opt[k] = options[k];

    var tagName = target.tagName.toLowerCase();
    if (tagName === 'img') {
        target.src = renderImageToDataURL(this.srcImage, opt, doSquash);
    } else if (tagName === 'canvas') {
        renderImageToCanvas(this.srcImage, target, opt, doSquash);
    }
    if (typeof this.onrender === 'function') {
        this.onrender(target);
    }
};