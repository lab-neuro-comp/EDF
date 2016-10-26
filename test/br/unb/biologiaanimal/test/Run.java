package br.unb.biologiaanimal.test;

import br.unb.biologiaanimal.edf.EDF;
import br.unb.biologiaanimal.edf.EDFUtil;

public class Run {
    public static void main(String[] args) {
        System.out.println("--- # Testing utils");
        System.out.println("Short size: " + Short.SIZE);
        String randomString = "abc efg hij ";
        String[] chopped = EDFUtil.separateString(randomString, 3);
        System.out.println("Separating string:");
        for (int i = 0; i < 3; ++i)
        {
            System.out.println("- " + chopped[i]);
        }
        System.out.println("25 celsius to fahrenheit:");
        System.out.println("  Ans: " + EDFUtil.map(25, 100, 0, 212, 32) + "F");
        System.out.println("--- # Reading EDF file");
        EDF edf = new EDF("data\\edf\\HCT-4-23.edf");
        System.out.println("File: " + edf.getFile());
        System.out.println("Sampling rate: " + edf.getSamplingRate());
        System.out.println("Labels:");
        String[] labels = edf.getLabels();
        String allLabels = "";
        for (int i = 0; i < labels.length; ++i)
        {
            allLabels += (i+1) + ". " + labels[i] + "\n";
        }
        System.out.println(allLabels);
        System.out.println("--- # Let's write something");
        System.out.println("-- " + edf.sayHi());
        System.out.println("-- Writing to ASCII format");
        System.out.println("-- + Single channel");
        try { edf.toSingleChannelAscii("data\\edf\\HCT\\ECG.ascii", "ECG"); }
        catch (Exception any) { System.out.println(any); }
        System.out.println("-- + All channels");
        try { edf.toAscii("data\\edf\\HCT-4-23.ascii"); }
        catch (Exception any) { System.out.println(any); }
        System.out.println("-- + To CSV");
        try { edf.toCsv("data\\edf\\HCT-4-23.java.ascii"); }
        catch (Exception any) { System.out.println(any); }

        System.out.println("...");
    }
}
