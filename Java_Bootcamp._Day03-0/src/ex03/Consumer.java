import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Consumer {
    private static int fileCount = 0;
    private BufferedReader fileReader;
    private Object[] urlsArray;
    private Path directory = Paths.get("C:\\Users\\User\\IdeaProjects\\JavaBootcamp\\day04\\ex03\\Files");

    public Consumer(BufferedReader fileReader, Object[] urlsArray) throws IOException {
        this.fileReader = fileReader;
        this.urlsArray = urlsArray;
        if(!Files.exists(directory)) {
            directory = Files.createDirectory(directory);
        }
    }

    public void downloadFile(int id) throws InterruptedException, IOException {
        boolean nextFile = false;
        synchronized (this) {
            fileCount++;
            nextFile = true;
        }
        if(nextFile) {
            try {
                String[] line = urlsArray[fileCount - 1].toString().split("\\s+");
                startDownloading(id, line);
                URL url = new URL(line[1]);
                String fileName = getFileName(url);
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                FileOutputStream fos = new FileOutputStream(directory.resolve(fileName).toFile());
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                finishDownloading(id, line);
                rbc.close();
                fos.close();
            } catch (IOException io) {
                System.out.println(io);
            }
        }
    }

    private static void finishDownloading(int id, String[] line) {
        System.out.println("Thread-" + id + " finish download " +
                "file number " + line[0]);
    }

    private static void startDownloading(int id, String[] line) {
        System.out.println("Thread-" + id + " start download " +
                "file number " + line[0]);
    }

    public String getFileName(URL url) {
        String[] str = url.toString().split("/");
        return str[str.length - 1];
    }

    public synchronized int getFileCount() {
        return fileCount;
    }
}
