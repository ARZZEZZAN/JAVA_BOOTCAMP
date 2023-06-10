package edu.school21.springApp.Processor;

public class PreProcessorToLower implements PreProcessor {

    @Override
    public String process(String message) {
        return message.toLowerCase();
    }
}
