import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;


public class Program {
    private static Scanner scanner;
    private static Path path;
    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            System.out.println("Must be path to the folder");
            System.exit(-1);
        }
        path = Paths.get(args[0].substring("--current-folder=".length()));
        isValid(path);
        System.out.println(path);
        start();
    }
    private static void start() throws IOException {
        scanner = new Scanner(System.in);
        int flag = 0;
        System.out.print("-> ");
        String str = scanner.nextLine();
        String[] command = str.split("\\s+");
        if(command[0].equals("exit")) {
            scanner.close();
            System.exit(-1);
        }
        if(command[0].equals("ls") && command.length  == 1) {
            lsAction();
        } else if (command[0].equals("cd") && command.length  == 2) {
            cdAction(command[1]);
        } else if (command[0].equals("mv") && command.length  == 3) {
            mvAction(command[1], command[2]);
        } else {
            System.out.println("Do not support this command " + str);
            start();
        }

    }
    private static void lsAction() throws IOException {
        File dir = new File(path.toString());
        File[] files = dir.listFiles();
        long size = 0;
        if(files.length == 0) {
            System.out.println("Directory is empty");
            start();
        }
        for(int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()) {
                size = getDirectorySize(files[i]) / 1024;
            } else {
                size = files[i].length() / 1024;
            }
            System.out.println(files[i].getName() + " " + size + " KB");
        }
        start();
    }
    private static long getDirectorySize(File file) throws IOException {
        long size = 0;
        try (DirectoryStream<Path> files = Files.newDirectoryStream(file.toPath())) {
            for(Path tmp : files) {
                if(Files.isDirectory(tmp)) {
                    size += getDirectorySize(tmp.toFile());
                } else {
                    size += tmp.toFile().length();
                }
            }

        } catch (IOException io) {
            System.out.println(io);
            start();
        }
        return size;
    }
    private static void cdAction(String directory) throws IOException {
        Path target = path.resolve(directory);
        isValid(target);
        if(Files.isDirectory(target)) {
            path = target;
            System.out.println(path);
            start();
        } else {
            System.out.println("Argument for cd command is not directory");
        }
    }
    private static void mvAction(String file, String targetFile) throws IOException {
        try {
            Path fromFile = path.resolve(file);
            Path toFile = path.resolve(targetFile);
            if(Files.exists(fromFile)) {
                if (!Files.isDirectory(toFile)) {
                    Files.move(fromFile, toFile);
                } else if (Files.isDirectory(toFile)) {
                    Files.move(fromFile, path.resolveSibling(toFile).resolve(file), StandardCopyOption.REPLACE_EXISTING);
                }
                start();
            } else {
                System.out.println("File " + fromFile + " doesn't exist");
                start();
            }
        } catch (IOException io) {
                System.out.println(io);
                start();
            }
        }
    private static void isValid(Path path) throws IOException {
        if(!path.isAbsolute()) {
            System.out.println("Path must be absolute");
            System.exit(-1);
        }
        if(!Files.isDirectory(path) || !Files.exists(path)) {
            System.out.println("File{"+ path.toFile() + "} is not directory or it doesn't exist");
            start();
        }
    }
}
