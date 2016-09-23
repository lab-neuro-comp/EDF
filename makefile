sourcefiles = java/br/unb/biologiaanimal/edf/EDF.java java/br/unb/biologiaanimal/edf/EDFReader.java java/br/unb/biologiaanimal/edf/EDFConstants.java java/br/unb/biologiaanimal/edf/EDFUtil.java java/br/unb/biologiaanimal/edf/EDFWriter.java
classfiles = -C java br/unb/biologiaanimal/edf/EDF.class -C java br/unb/biologiaanimal/edf/EDFReader.class -C java br/unb/biologiaanimal/edf/EDFConstants.class -C java br/unb/biologiaanimal/edf/EDFUtil.class -C java br/unb/biologiaanimal/edf/EDFWriter.class
classname = br.unb.biologiaanimal.edf.EDF
targetname = target/edf.jar

doc:
	javadoc -d docs $(sourcefiles)

classes:
	javac -target 1.4 -source 1.4 -nowarn $(sourcefiles)

test: classes
	java -cp java $(classname)

jar: classes
	jar cvfm $(targetname) manifest.txt $(classfiles)

do: jar
	java -jar $(targetname)
