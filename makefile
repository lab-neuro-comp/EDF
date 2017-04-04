sourcefiles = java/br/unb/biologiaanimal/edf/EDF.java java/br/unb/biologiaanimal/edf/EDFReader.java java/br/unb/biologiaanimal/edf/EDFConstants.java java/br/unb/biologiaanimal/edf/EDFUtil.java java/br/unb/biologiaanimal/edf/EDFWriter.java
classfiles = -C java br/unb/biologiaanimal/edf/EDF.class -C java br/unb/biologiaanimal/edf/EDFReader.class -C java br/unb/biologiaanimal/edf/EDFConstants.class -C java br/unb/biologiaanimal/edf/EDFUtil.class -C java br/unb/biologiaanimal/edf/EDFWriter.class
testfiles = test/br/unb/biologiaanimal/test/Run.java
testclassfiles = -C test/br/unb/biologiaanimal/test/Run.class
classname = br.unb.biologiaanimal.edf.EDF
testclassname = br.unb.biologiaanimal.test.Run
targetname = target/edf.jar


do: jar
	java -jar $(targetname)

doc:
	javadoc -d docs $(sourcefiles)

classes:
	javac -target 1.4 -source 1.4 $(sourcefiles)

testclasses:
	javac -target 1.4 -source 1.4 -classpath java $(testfiles)

test: classes testclasses
	java -cp java;test $(testclassname)

jar: classes
	jar cvfm $(targetname) manifest.txt $(classfiles)
