import java.util.*;
public class Program {
    public static void main(String[] args) {
        User user1 = new User("Armen", 10000);
        User user2 = new User("Vardan", 8000);
        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser(user1);
        transactionsService.addUser(user2);
        transactionsService.executeTransaction(user1.getIdentifier(), user2.getIdentifier(), 2000);
        System.out.println(user1);
        System.out.println(user2);
        for (var i : user1.getTransactionsList().toArray()) {
            System.out.println(i);

        }
        for (var i : user2.getTransactionsList().toArray()) {
            System.out.println(i);
        }
        System.out.println(transactionsService.getUserList().getCountOfUsers());
        transactionsService.removeTransaction(user1.getTransactionsList().toArray()[0].getIdentifier(), user1.getIdentifier());
        for(Transaction trans : transactionsService.getUnpairedTransactions()) {
            System.out.println(trans);
        }
    }
}
