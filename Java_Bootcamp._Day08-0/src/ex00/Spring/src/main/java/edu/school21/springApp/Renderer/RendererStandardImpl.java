package edu.school21.springApp.Renderer;

import edu.school21.springApp.Processor.PreProcessor;

public class RendererStandardImpl implements Renderer {

    private PreProcessor processor;

    public RendererStandardImpl(PreProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void renderer(String message) {
        System.out.println(processor.process(message));
    }
}
