package edu.school21.springApp.Processor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String process(String message) {
        return message.toUpperCase();
    }
}
