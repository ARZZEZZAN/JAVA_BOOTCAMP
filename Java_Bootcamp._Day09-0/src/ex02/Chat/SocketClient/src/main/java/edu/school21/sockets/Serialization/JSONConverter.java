package edu.school21.sockets.Serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JSONConverter {
    private static final String TAG_MSG = "message";
    private static final String TAG_TIME = "time";

    public static MessageJSON parseStringToObject(String msg) {
        MessageJSON newMessage = new MessageJSON(msg);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            newMessage = objectMapper.readValue(msg, MessageJSON.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return newMessage;
    }

    public static String makeJSON(String msg) {
        try {
            MessageJSON message = new MessageJSON(msg);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private JSONConverter() {

    }
}
