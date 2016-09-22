package br.unb.biologiaanimal.edf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

class EDFReader
{
    private String path;
    private String data;
    private HashMap header;  // <String, String>
    private HashMap records; // <String, Double[]>
    private int numberSignals;

    public EDFReader(String input)
    {
        this.path = input;

        try {
            byte[] buffer = new byte[256];
            FileInputStream stream = new FileInputStream(path);
            this.readHeader(stream);
            this.readRecords(stream);
        }
        catch (Exception any) {
            System.out.println(any);
        }
    }

    /* ################
       # LOAD METHODS #
       ################ */
    /**
     * Reads the header of an EDF file
     * @param stream the file input
     */
    private void readHeader(FileInputStream stream)
    throws IOException
    {
        int limit = EDFConstants.specs.length;
        byte[] buffer;
        String data;
        int i = 0;
        header = new HashMap();

        /* Reading header's header */
        for (i = 0; i < limit; ++i)
        {
            buffer = new byte[EDFConstants.lenghts[i]];
            data = (String) EDFConstants.specs[i];
            if (!data.equals("label")) {
                stream.read(buffer);
                header.put(EDFConstants.specs[i], new String(buffer));
            }
            else {
                break;
            }
        }

        /* Reading header's records */
        numberSignals = paramToInt("numbersignals");
        for (i = i; i < limit; ++i)
        {
            buffer = new byte[EDFConstants.lenghts[i]];
            data = "";
            for (int n = 0; n < numberSignals; ++n)
            {
                stream.read(buffer);
                data += new String(buffer);
            }
            header.put(EDFConstants.specs[i], data);
        }
    }

    /**
     * Reads the records of an EDF file
     * @param stream the file input
     */
    private void readRecords(FileInputStream stream)
    throws IOException
    {
        records = new HashMap();
        int[] numberSamples = getNumberSamples();
        int[] sampling = new int[numberSignals];
        int duration = paramToInt("duration");
        int dataRecords = paramToInt("datarecords");
        byte[][] recordList = new byte[numberSignals][5];
        String[] labels = getLabels();
        byte[] buffer = null;

        // Initalizing variables
        for (int i = 0; i < numberSignals; ++i)
        {
            sampling[i] = duration * numberSamples[i];
            recordList[i] = null;
        }

        // Reading records
        for (int d = 0; d < dataRecords; ++d)
        {
            for (int i = 0; i < numberSignals; ++i)
            {
                duration = 2 * sampling[i];
                buffer = new byte[duration];
                stream.read(buffer);
                if (recordList[i] == null) {
                    recordList[i] = buffer;
                }
                else {
                    recordList[i] = EDFUtil.insert(recordList[i], buffer);
                }
            }
        }

        // Packing records
        for (int i = 0; i < numberSignals; ++i)
        {
            String label = labels[i].trim();
            records.put(label, recordList[i]);
        }
    }

    /* AUXILIAR LOAD FUNCTIONS */
    private int[] getNumberSamples()
    {
        int[] numberSamples = new int[numberSignals];
        String rawSamples = (String) header.get("samplesrecord");
        String[] samples = EDFUtil.separateString(rawSamples, numberSignals);

        for (int i = 0; i < numberSignals; ++i)
        {
            numberSamples[i] = Integer.parseInt(samples[i].trim());
        }

        return numberSamples;
    }

    private int paramToInt(String param)
    {
        return Integer.parseInt(((String) header.get(param)).trim());
    }

    /* #####################
       # INTERFACE METHODS #
       ##################### */
    public String getPath()
    {
        return this.path;
    }

    /**
     * For testing purposes only
     */
    public String getData()
    {
        String outlet = "";
        String[] labels = this.getLabels();
        int limit = labels.length;

        for (int i = 0; i < limit; ++i)
        {
            String label = labels[i];
            byte[] record = this.getRecord(label);
            outlet += label + ": " + record.length + "\n";
        }

        return outlet;
    }

    public HashMap getHeader()
    {
        return this.header;
    }

    public HashMap getRecords()
    {
        return this.records;
    }

    public byte[] getRecord(String label)
    {
        return (byte[]) records.get(label);
    }

    /* #######################
       # TRANSLATION METHODS #
       ####################### */

    public int getNumberSignals()
    {
        return paramToInt("numbersignals");
    }

    /**
     * Gets list of labels in this EDF file
     */
    public String[] getLabels()
    {
        String rawData = (String) header.get("label");
        String[] raw = EDFUtil.separateString(rawData, numberSignals);
        String[] labels = new String[numberSignals];

        for (int i = 0; i < numberSignals; ++i)
        {
            labels[i] = raw[i].trim();
        }

        return labels;
    }

    /**
     * Gets the factors to convert records from compressed form to actual values
     */
    public double[] getConvertionFactors()
    {
        long[] dmax = getDigitalMaxima();
        long[] dmin = getDigitalMinima();
        long[] pmax = getPhysicalMaxima();
        long[] pmin = getPhysicalMinima();
        double[] outlet = new double[numberSignals];

        for (int i = 0; i < numberSignals; ++i)
        {
            outlet[i] = EDFUtil.convert(1, dmax[i], dmin[i], pmax[i], pmin[i]);
        }

        return outlet;
    }

    public long[] getDigitalMaxima()
    {
        return getLimits("digitalmaximum");
    }

    public long[] getDigitalMinima()
    {
        return getLimits("digitalminimum");
    }

    public long[] getPhysicalMaxima()
    {
        return getLimits("physicalmaximum");
    }

    public long[] getPhysicalMinima()
    {
        return getLimits("physicalminimum");
    }

    private long[] getLimits(String param)
    {
        String[] stuff = EDFUtil.separateString((String) header.get(param), 
                                                numberSignals);
        long[] outlet = new long[numberSignals];

        for (int i = 0; i < numberSignals; ++i)
        {
            outlet[i] = Long.parseLong(stuff[i].trim());
        } 

        return outlet;
    }

    // TODO Add method to get each signal by their label
}
