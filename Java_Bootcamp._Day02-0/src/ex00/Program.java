import java.util.*;
import java.io.*;
public class Program {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        start();
    }
    private static void start() throws FileNotFoundException, IOException {
        FileInputStream inputStream = new FileInputStream("ex00/signatures.txt");
        FileOutputStream outputStream = new FileOutputStream("ex00/result.txt", true);
        Map<String, String> map = new HashMap<>(){};
        readFile(inputStream, map);
        fileProcessing(outputStream, map);
    }
    private static void fileProcessing(FileOutputStream outputStream, Map<String, String> map) throws FileNotFoundException {
        String inputFile = new String();
        scanner = new Scanner(System.in);
        System.out.print("Write the absolute path to the file -> ");
        if(scanner.hasNext() && !(inputFile = scanner.nextLine()).equalsIgnoreCase("exit")) {
            try {
                int processed = 0;
                FileInputStream fileTo = new FileInputStream(inputFile);
                StringBuffer bytes = new StringBuffer();
                for(int i = 0; i < 10; i++) {
                    bytes.append(String.format("%02X ", fileTo.read()));
                }
                for (String key : map.keySet()) {
                    String string = bytes.toString().substring(0, map.get(key).length());
                    if(map.get(key).startsWith(string)) {
                        outputStream.write(key.getBytes());
                        outputStream.write('\n');
                        System.out.println("PROCESSED");
                        processed = 1;
                        start();
                    }
                }
                if(processed == 0) {
                    System.err.println("File is not defined");
                    start();
                }
            } catch (IOException io) {
                scanner.close();
                System.out.println(io);
            }
        }
        scanner.close();
    }
    private static void readFile(FileInputStream inputStream, Map map) throws FileNotFoundException {
        scanner = new Scanner(inputStream);
        String line = new String();
        String[] lines = new String[2];
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            lines = line.split(", ");
            map.put(lines[0], lines[1]);
        }
    }
}