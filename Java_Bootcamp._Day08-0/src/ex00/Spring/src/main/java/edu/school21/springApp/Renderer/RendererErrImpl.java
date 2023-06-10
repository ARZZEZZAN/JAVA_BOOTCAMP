package edu.school21.springApp.Renderer;

import edu.school21.springApp.Processor.PreProcessor;

public class RendererErrImpl implements Renderer {

    private PreProcessor processor;

    public RendererErrImpl(PreProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void renderer(String message) {
        System.err.println(processor.process(message));
    }
}
