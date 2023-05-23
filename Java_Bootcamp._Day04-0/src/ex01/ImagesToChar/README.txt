# delete directory if exists
rm -rf target
# create directory
mkdir target
# compile the project
javac -d ./target src/java/edu.school21.printer/*/*.java
# copy resources to target
cp -R src/resources target/.
# create archive
1 jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target
2 jar cfmv ./target/images-to-chars-printer.jar src/manifest.txt -C target edu -C target resources
# run program
java -jar target/images-to-chars-printer.jar . 0

