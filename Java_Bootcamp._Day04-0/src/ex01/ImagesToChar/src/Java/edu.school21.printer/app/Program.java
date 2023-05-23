package edu.school21.printer.app;

import edu.school21.printer.logic.ImageHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) throws IOException {
        if(args.length != 2) {
            System.out.println("Wrong input parameters");
            System.exit(-1);
        }
        if(args[0].length() != 1 && args[1].length() != 1) {
            System.out.println("Wrong input parameters");
            System.exit(-1);
        }
        Path path = Paths.get("/Users/killeral/JavaBootcamp/day04/ex01/ImagesToChar/src/resources/it.bmp");
        if(Files.exists(path)) {
            char white = args[0].charAt(0);
            char black = args[1].charAt(0);
            ImageHandler imageHandler = new ImageHandler(white, black, path);
            imageHandler.printImage();
        }

    }
}
