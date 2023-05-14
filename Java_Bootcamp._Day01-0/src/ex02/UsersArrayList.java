class UserNotFoundException extends RuntimeException {}

public class UsersArrayList implements  UsersList {
    private User[] users;
    private Integer countUsers;
    private Integer size;

    public UsersArrayList() {
        size = 10;
        users = new User[size];
        countUsers = 0;
    }
    @Override
    public void addUser(User user) {
        if(countUsers >= size) {
            User[] tmp = new User[size * 2];
            for (int i = 0; i < users.length; i++) {
                tmp[i] = users[i];
            }
            users = tmp;
        }
        users[countUsers] = user;
        countUsers++;
    }

    @Override
    public User getUserById(Integer id) {
       for (var user : users) {
           if (user.getIdentifier() == id) {
               return user;
           }
       }
       throw new UserNotFoundException();
    }

    @Override
    public User getUserByIndex(Integer idx) {
       if(idx <= users.length && idx >= 0) {
           return users[idx];
       }
       throw new UserNotFoundException();
    }

    @Override
    public Integer getCountOfUsers() {
        return countUsers;
    }
}
