package br.unb.biologiaanimal.edf;

import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * The class to write EDF information in an standard way
 */
class EDFWriter
{
    private File file;
    private String path;
    private BufferedWriter typewriter;


    /**
     * Creates a new writer for this EDF file to write on standard output
     */
    public EDFWriter()
    throws IOException
    {
        this.construct();
    }

    /**
     * Creates a new writer for this EDF file depiected in these variables
     * @param filePath  the file on which the data will be written
     */
    public EDFWriter(String filePath)
    throws IOException
    {
        if (filePath == null) {
            this.construct();
        }
        else {
            path = filePath;
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            typewriter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        }

    }

    private void construct()
    throws IOException
    {
        path = null;
        file = null;
        typewriter = new BufferedWriter(new PrintWriter(System.out));
    }

    /**
     * Writes data to file in the ASCII format. Beware with side effects!
     * @param data what you want to write in this file
     */
    public void write(String data)
    throws IOException
    {
        typewriter.write(data);
    }

    /**
     * Closes the file opened by this EDFWriter
     */
    public void close()
    throws IOException
    {
        typewriter.close();
    }

    public static String sayHi()
    {
        return "Hello from EDF Writer!";
    }
}
