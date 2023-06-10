package edu.school21.app.Model;

import edu.school21.app.ORM.OrmColumn;
import edu.school21.app.ORM.OrmColumnId;
import edu.school21.app.ORM.OrmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@OrmEntity(table = "users")
public class User {
    @OrmColumnId
    private Integer id;
    @OrmColumn(name = "FirstName", length = 30)
    private String firstName;
    @OrmColumn(name = "LastName", length = 30)
    private String lastName;
    @OrmColumn(name = "Age")
    private Integer age;
    public User() {

    }
}

