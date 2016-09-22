sourcefiles = src/br/unb/biologiaanimal/edf/EDF.java src/br/unb/biologiaanimal/edf/EDFReader.java src/br/unb/biologiaanimal/edf/EDFConstants.java src/br/unb/biologiaanimal/edf/EDFUtil.java src/br/unb/biologiaanimal/edf/EDFWriter.java
classfiles = -C src br/unb/biologiaanimal/edf/EDF.class -C src br/unb/biologiaanimal/edf/EDFReader.class -C src br/unb/biologiaanimal/edf/EDFConstants.class -C src br/unb/biologiaanimal/edf/EDFUtil.class -C src br/unb/biologiaanimal/edf/EDFWriter.class
testfiles = test/br/unb/biologiaanimal/test/Test.java
runtestfiles = test/br/unb/biologiaanimal/test/Run.java
classname = br.unb.biologiaanimal.edf.EDF
testclassname = br.unb.biologiaanimal.test.Test
runtestclassname = br.unb.biologiaanimal.test.Run
targetname = target/edf.jar

classes:
	javac -target 1.4 -source 1.4 -nowarn $(sourcefiles)

test: classes
	java -cp src $(classname)

jar: classes
	jar cvfm $(targetname) manifest.txt $(classfiles)

compiletests:
	javac -cp $(testfiles)

unittest: jar compiletests
	java -cp test $(testclassname)

compileruntest: 
	javac -cp $(targetname) $(runtestfiles)

runtest: jar compileruntest
	java -cp $(targetname);test $(runtestclassname)

do: jar
	java -jar $(targetname)
