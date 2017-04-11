sourcefiles = java/br/unb/biologiaanimal/edf/EDF.java java/br/unb/biologiaanimal/edf/EDFReader.java java/br/unb/biologiaanimal/edf/EDFConstants.java java/br/unb/biologiaanimal/edf/EDFUtil.java java/br/unb/biologiaanimal/edf/EDFWriter.java
classfiles = -C $(src) br/unb/biologiaanimal/edf/EDF.class -C $(src) br/unb/biologiaanimal/edf/EDFReader.class -C $(src) br/unb/biologiaanimal/edf/EDFConstants.class -C $(src) br/unb/biologiaanimal/edf/EDFUtil.class -C $(src) br/unb/biologiaanimal/edf/EDFWriter.class
testfiles = test/br/unb/biologiaanimal/test/Test.java
runtestfiles = test/br/unb/biologiaanimal/test/Run.java
classname = br.unb.biologiaanimal.edf.EDF
testclassname = br.unb.biologiaanimal.test.Test
runtestclassname = br.unb.biologiaanimal.test.Run
targetname = target/edf.jar
manifest = manifest.txt
testfolder = test
src = java

jar: classes
	jar cvfm $(targetname) $(manifest) $(classfiles)

classes:
	javac -target 1.4 -source 1.4 -nowarn $(sourcefiles)

try: classes
	java -cp $(src) $(classname)

compiletests:
	javac -cp $(testfiles)

compileruntest:
	javac -cp $(targetname) $(runtestfiles)

test: jar compileruntest
	java -cp $(targetname);$(testfolder) $(runtestclassname)
