package br.unb.biologiaanimal.edf;

import java.util.HashMap;

class EDFConstants
{
    public static String[] specs = {
        "version",
        "patient",
        "recording",
        "startdate",
        "starttime",
        "bytesheader",
        "reserved",
        "datarecords",
        "duration",
        "numbersignals",
        "label",
        "transducer",
        "physicaldimension",
        "physicalminimum",
        "physicalmaximum",
        "digitalminimum",
        "digitalmaximum",
        "prefiltering",
        "samplesrecord",
        "chanreserved"
    };

    public static int[] lenghts = {
        8,
        80,
        80,
        8,
        8,
        8,
        44,
        8,
        8,
        4,
        16,
        80,
        8,
        8,
        8,
        8,
        8,
        80,
        8,
        32
    };
}
