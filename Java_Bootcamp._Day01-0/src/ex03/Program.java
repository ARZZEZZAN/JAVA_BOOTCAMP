import java.util.*;
public class Program {
    public static void main(String[] args) {
        User user1 = new User("Armen", 10000);
        User user2 = new User("Vardan", 8000);
        TransactionsLinkedList transactions = user1.getTransactionsList();

        Transaction trans = new Transaction(user1, user2, 5000, Transaction.Category.DEBIT);
        transactions.addTransaction(trans);
        Transaction trans1 = new Transaction(user1, user2, 2000, Transaction.Category.DEBIT);
        transactions.addTransaction(trans1);
        Transaction trans2 = new Transaction(user1, user2, 500, Transaction.Category.DEBIT);
        transactions.addTransaction(trans2);
        for(Object i : transactions.toArray()) {
            System.out.println(i);
        }

        System.out.println(user1 + " have " + transactions.getCountTransactions() + " transactions");
        transactions.deleteTransactionById(trans1.getIdentifier());
        System.out.println(user1 + " have " + transactions.getCountTransactions() + " transactions");
        transactions.deleteTransactionById(trans2.getIdentifier());
        System.out.println(user1 + " have " + transactions.getCountTransactions() + " transactions");
        transactions.deleteTransactionById(trans.getIdentifier());
        System.out.println(user1 + " have " + transactions.getCountTransactions() + " transactions");

    }
}
