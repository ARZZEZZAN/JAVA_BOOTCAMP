package edu.school21.springApp.Program;

import edu.school21.springApp.Printer.Printer;
import edu.school21.springApp.Printer.PrinterWithPrefixImpl;
import edu.school21.springApp.Processor.PreProcessor;
import edu.school21.springApp.Processor.PreProcessorToUpperImpl;
import edu.school21.springApp.Renderer.Renderer;
import edu.school21.springApp.Renderer.RendererStandardImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
//        PreProcessor preProcessor = new PreProcessorToUpperImpl();
//        Renderer renderer = new RendererStandardImpl(preProcessor);
//        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
//        printer.setPrefix("Prefix");
//        printer.print("Hello!");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer = context.getBean("printerPref3", Printer.class);
        printer.print("Hello!");
    }
}