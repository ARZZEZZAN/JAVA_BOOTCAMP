import java.util.*;
public class Program {
    public static void main(String[] args) {
        User user1 = new User("Armen", 10000);
        User user2 = new User("Vardan",  8000);

        UsersList list = new UsersArrayList();
        list.addUser(user1);
        list.addUser(user2);

        System.out.println(list.getUserByIndex(0) + " == " + user1);
        System.out.println(list.getUserByIndex(1) + " == " + user2);
        System.out.println("count = " + list.getCountOfUsers());

        User user3 = new User("Papa", 20000);
        User user4 = new User("Mama", 150000);

        list.addUser(user3);
        list.addUser(user4);
        System.out.println(list.getUserById(3) + " == " + user3);
        System.out.println(list.getUserById(4) + " == " + user4);
    }
}
