package edu.school21.printer.app;

import edu.school21.printer.logic.ImageHandler;
import edu.school21.printer.logic.Args;

import com.beust.jcommander.JCommander;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) {
       try {
           Path path = Paths.get("/Users/killeral/JavaBootcamp/day04/ex02/ImagesToChar/src/resources/it.bmp");
           Args jargs = new Args();
           JCommander jCommander = new JCommander(jargs);
           jCommander.parse(args);
           ImageHandler imageHandler = new ImageHandler(jargs, path);
           imageHandler.printImage();
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
       }
    }
}
