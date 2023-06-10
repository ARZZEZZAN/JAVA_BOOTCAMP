package edu.school21.sockets.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String login;
    private String password;

    public User() {

    }
}
