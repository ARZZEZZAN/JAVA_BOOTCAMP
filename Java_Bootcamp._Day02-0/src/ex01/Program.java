import java.io.*;
import java.util.*;

public class Program {
    private static List<String> dictionary = new ArrayList<>(){};
    private static ArrayList<String> fileList1 = new ArrayList<>(){};
    private static ArrayList<String> fileList2 = new ArrayList<>(){};
    private static double similarity;
    public static void main(String[] args) {
        if(args.length != 2) {
            System.err.println("Must be 2 files");
            System.exit(-1);
        }
        try {
            BufferedWriter dict = new BufferedWriter(new FileWriter("dictionary.txt"));
            BufferedReader file1 = new BufferedReader(new FileReader(args[0]));
            BufferedReader file2 = new BufferedReader(new FileReader(args[1]));
            addToDictionary(file1, fileList1);
            addToDictionary(file2, fileList2);
            file1.close();
            file2.close();
            for (String elem : dictionary) {
                dict.write(elem);
                dict.newLine();
            }
            dict.close();
            similarity();
        } catch (IOException iox) {
            System.out.println(iox);
        }
    }
    private static void addToDictionary(BufferedReader file, List<String> fileList) throws IOException {
        String str = new String();
        while((str = file.readLine()) != null) {
            str = str.replaceAll("\\p{Punct}", "");
            String[] words = str.split(" ");
            for(String word : words) {
                if(!word.isEmpty()) {
                    fileList.add(word);
                    if (!dictionary.contains(word)) {
                        dictionary.add(word);
                    }
                }
            }
        }
    }
    private static void similarity() {
        Map<String, Integer> fileVector1 = new HashMap<>(){};
        Map<String, Integer> fileVector2 = new HashMap<>(){};
        int count = 0;
        for (String word : dictionary) {
            count = 0;
            for (String sym : fileList1) {
                if(word.equals(sym)) {
                    count += 1;
                    fileVector1.put(word, count);
                }
            }
            if(!fileVector1.containsKey(word)) {
                fileVector1.put(word, 0);
            }
            count = 0;
            for (String sym : fileList2) {
                if(word.equals(sym)) {
                    count += 1;
                    fileVector2.put(word, count);
                }
            }
            if(!fileVector2.containsKey(word)) {
                fileVector2.put(word, 0);
            }
        }
        ArrayList<Integer> fileSet1 = new ArrayList<>(fileVector1.values()){};
        ArrayList<Integer> fileSet2 = new ArrayList<>(fileVector2.values()){};

        int numerator = 0;
        double denominator1 = 0.0, denominator2 = 0.0;
        for(int i = 0; i < fileSet2.size(); i++) {
            numerator += fileSet1.get(i) * fileSet2.get(i);
            denominator1 += fileSet1.get(i) * fileSet1.get(i);
            denominator2 += fileSet2.get(i) * fileSet2.get(i);
        }
        similarity = numerator /  (Math.sqrt(denominator1) * Math.sqrt(denominator2));
        System.out.printf("%.2f", Math.floor(similarity * 100) / 100);
    }
}

