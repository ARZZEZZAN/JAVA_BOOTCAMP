
public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private static Integer Identifier = 0;

    public UserIdsGenerator() {}

    public static UserIdsGenerator getInstance() {
        if(instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public int idGenerator() {
        return ++Identifier;
    }

}
