package edu.school21.springApp.Printer;

import edu.school21.springApp.Renderer.Renderer;

import java.time.format.DateTimeFormatter;

public class PrinterWithDateTimeImpl implements Printer {
    private Renderer renderer;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        renderer.renderer(format + " " + message);
    }
}
