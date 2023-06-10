package edu.school21.springApp.Printer;

import edu.school21.springApp.Renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }
    @Override
    public void print(String message) {
        renderer.renderer(prefix + " " + message);
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;

    }
}
