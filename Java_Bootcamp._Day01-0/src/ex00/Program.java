public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "Armen", 500);
        User user2 = new User(2, "Vardan", 340);
        System.out.println(user1);
        System.out.println(user2);
        Transaction trans1 = new Transaction(user1, user2, 100, Transaction.Category.CREDIT);
        System.out.println(trans1);
    }
}
