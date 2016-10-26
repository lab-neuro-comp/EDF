package br.unb.biologiaanimal.edf;

import java.util.HashMap;
import java.util.Set;
import java.text.MessageFormat;
import java.io.IOException;

/**
 * The class to hold the EDF file information.
 * @author Cristiano Silva Jr. cristianoalvesjr@gmail.com
 * @version 0.0.1
 */
public class EDF
{
    private String file;
    private EDFReader reader;

    /**
     * Generates new EDF object based on the filepath.
     * @param filePath the path to the file. Can be relative or absolute.
     */
    public EDF(String filePath)
    {
        this.file = filePath;
        this.reader = new EDFReader(file);
    }

    /* ###############
       # GETS & SETS #
       ############### */
    /**
     * Gets the file path.
     * @return the file path
     */
    public String getFile()
    {
        return reader.getPath();
    }

    /**
     * Generic method to get some data. For testing purposes only.
     */
    public String getData()
    {
        String outlet = "";
        String channel = "ECG";
        byte[] record = reader.getRecord(channel);
        short[] data = EDFUtil.translate(record);
        double convertionFactor = getConvertionFactor(channel);

        for (int i = 0; i < data.length; ++i)
        {
            double value = data[i] * convertionFactor;
            outlet += value + "\n";
        }

        return outlet;
    }

    /**
     * Gets raw EDF header
     */
    public HashMap getHeader()
    {
        return reader.getHeader();
    }

    /**
     * Gets raw EDF records
     */
    public HashMap getRecords()
    {
        return reader.getRecords();
    }

    /**
     * Get the labels contained in this EDF file.
     */
    public String[] getLabels()
    {
        return reader.getLabels();
    }

    /**
     * Gets the sampling rate of the recording.
     */
    public int getSamplingRate()
    {
        int numberSignals = reader.getNumberSignals();
        String raw = (String) getHeader().get("samplesrecord");
        String[] samples = EDFUtil.separateString(raw, numberSignals);
        return Integer.parseInt(samples[0].trim());
    }

    /* ###################
       # WRITING METHODS #
       ################### */
    public String sayHi()
    {
        return EDFWriter.sayHi();
    }

    public void write(String what)
    throws IOException
    {
        EDFWriter writer = new EDFWriter();
        writer.write(what);
    }

    /**
     * Formats the read records into the ASCII format and saves it into memory.
     * @param filePath the path to the file to be written.
     */
    public void toAscii(String filePath)
    throws IOException
    {
        EDFWriter writer = new EDFWriter(filePath);
        String[] labels = reader.getLabels();
        double[][] signals = new double[labels.length][2];
        int limit = 0;

        // Getting records
        for (int i = 0; i < labels.length; ++i)
        {
            String label = labels[i];
            double[] signal = getSignal(label);
            signals[i] = signal;
            if (limit < signal.length) {
                limit = signal.length;
            }
        }

        // Writiing numbers into file
        for (int i = 0; i < limit; ++i)
        {
            for (int j = 0; j < labels.length; ++j)
            {
                double[] signal = signals[j];
                double value = (signal.length > i)? signal[i] : 0;
                writer.write(value + " ");
            }
            writer.write("\n");
        }

        writer.close();
    }

    /**
     * Writes a determined record to a file.
     * @param filePath  the path to the file to be written
     * @param channel   the label of the record to be saved on memory
     * @throws java.util.IOException
     */
    public void toSingleChannelAscii(String filePath, String channel)
    throws IOException
    {
        double[] signal = getSignal(channel);
        EDFWriter writer = new EDFWriter(filePath);

        for (int i = 0; i < signal.length; ++i)
        {
            writer.write(signal[i] + "\n");
        }

        writer.close();
    }

    // TODO Translate data to CSV table
    /**
     * Translated the loaded EDF file into a CSV table
     */
    public void toCsv(String filePath)
    throws IOException
    {
        EDFWriter writer = new EDFWriter(filePath);
        String[] labels = reader.getLabels();
        double[][] signals = new double[labels.length][2];
        HashMap header = reader.getHeader();
        int limit = 0;

        // TODO Generate header
        writer.write("title:" + (String) header.get("title") + ";");
        // TODO Get recording time info
        // TODO Get sampling
        writer.write("subject:" + (String) header.get("patient") + ";");
        // TODO Get labels
        // TODO Get how many channels there are
        // TODO Get units
        writer.write("\n");

        // TODO Write channels
        // Getting records
        for (int i = 0; i < labels.length; ++i)
        {
            String label = labels[i];
            double[] signal = getSignal(label);
            signals[i] = signal;
            if (limit < signal.length) {
                limit = signal.length;
            }
        }

        // Writiing numbers into file
        for (int i = 0; i < limit; ++i)
        {
            for (int j = 0; j < labels.length; ++j)
            {
                // TODO Get annotations channel
                if (true) {
                    double[] signal = signals[j];
                    double value = (signal.length > i)? signal[i] : 0;
                    writer.write(((j == 0)? "" : "; ") + value);
                }
            }
            writer.write("\n");
        }

        writer.close();
    }

    /* ##########################
       # LABEL SPECIFIC METHODS #
       ########################## */

    /**
     * Gets the index related to a label
     */
    private int getLabelIndex(String label)
    {
        String[] labels = reader.getLabels();
        int limit = reader.getNumberSignals();
        int result = -1;

        for (int i = 0; (i < limit) && (result < 0); ++i)
        {
            if (label.equals(labels[i])) {
                result = i;
            }
        }

        return result;
    }

    /**
     * Gets the convertion factor related to the given label
     * @param label  the channel's label whose convertion factor we want
     * @return the convertion factor
     */
    public double getConvertionFactor(String label)
    {
        return reader.getConvertionFactors()[getLabelIndex(label)];
    }

    /**
     * Get a signal based on its label.
     * @param label  the signal label
     * @return the translated record
     */
    public double[] getSignal(String label)
    {
        double convertionFactor = this.getConvertionFactor(label);
        short[] raw = EDFUtil.translate(reader.getRecord(label));
        int[] data = new int[raw.length];
        double[] signal = new double[data.length];

        // TODO Fix this convertion
        for (int i = 0; i < data.length; ++i)
        {
            // Shifting signals to get unsigned version
            data[i] = raw[i] + Short.MAX_VALUE;

            // Converting signals
            signal[i] = data[i] * convertionFactor;
        }

        return signal;
    }
}
