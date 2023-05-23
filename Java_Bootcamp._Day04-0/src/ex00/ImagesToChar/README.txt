# delete directory if exists
rm -rf target
# create directory
mkdir target
# compile the project
javac -d ./target src/java/edu.school21.printer/*/*.java
# run the project
java -classpath target edu.school21.printer.app.Program . 0 /Users/killeral/JavaBootcamp/day04/ex00/it.bmp


