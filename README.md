# Java support to EDF files

The repository implements the following package:

``` java
package br.unb.biologiaanimal.edf;
```

which provides an interface with EDF files for the Java programming language, so we can use them in our MATLAB applications. The produced Jar file enables the deconstruction of an EDF file into its header and its records; so the raw records can be directly accessed or translated to ASCII friendly formats.

Although it is already being used, this code is work in progress and shall be used carefully.

Help and ideas are always welcome! `:)`

# Getting started

To build this package, run

``` sh
make
```

It will generate an `edf.jar` file on the `target` folder. This Jarfile can be included in your application wherever needed.

## Development ##

The current workings of JEDF are destined to a `v0.3`, which must save the EDF file to the CSV format, mimicking [the analogous Golang implementation of this package](https://github.com/ishiikurisu/edf). `v0.4` must be able to convert the EDF both to CSV and back to the EDF format, so the programmer can make changes on the EDF file and save it back to later concerns.

Future ideas include the implementation of a on-time access to the data: the `EDF` class would know where to find certain parts of the file and load them whenever needed instead of storing the whole file's contents on RAM memory.

# Library

## The `EDF` class ##

Those are the currently available methods for this class.

### Methods ###

#### `public EDF(String filePath)` ####

This is the constructor. It requires a string that describes the path to the

#### `public String getFile()` ####

- Gets the file path.
- Return: the file path;

#### `public HashMap getHeader()` ####

- Gets raw EDF header.

#### `public HashMap getRecords()` ####

- Gets raw EDF records

#### `public String[] getLabels()` ####

- Get the labels contained in this EDF file.

#### `public int getSamplingRate()` ####

- Gets the sampling rate of the recording.


#### `public void toAscii(String filePath) throws IOException` ####

- Formats the read records into the ASCII format and saves it into memory.
- Paramter `filePath`: the path to the file to be written.

#### `public void toSingleChannelAscii(String filePath, String channel) throws IOException` ####

* Writes a determined record to a file.
* Parameter `filePath`: the path to the file to be written
* Parameter `channel`: the label of the record to be saved on memory


#### `public String[] getAnnotations() throws NoSuchFieldException` ####

* Gets a list of the annotations on the EDF file, lest there are annotations.
* Returns an array containing one annotation for each entry.

#### `public double getConvertionFactor(String label)` ####

* Gets the convertion factor related to the given label
* Paramter `label`: the channel's label whose convertion factor we want
* Returns the convertion factor.

#### `public double[] getSignal(String label)` ####

* Get a signal based on its label.
* Parameter `label`: the signal label.
* Returns the translated record.

## The `EDFUtil` class

Mostly holds some utility functions to help with EDF processing.

### Methods

#### `public static String[] separateString(String inlet, int numChops)`

* Separates a string into equal length chops.
