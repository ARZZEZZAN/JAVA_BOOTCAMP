import java.util.UUID;
class IllegalTransactionException extends RuntimeException {}
public class TransactionsService {
    private UsersList userList = new UsersArrayList();

    public TransactionsService() {}
    public void addUser(User user) {
        userList.addUser(user);
    }
    public int getBalance(User user) {
        return user.getBalance();
    }
    public Transaction[] getUserTransactions(User user) {
        return user.getTransactionsList().toArray();
    }
    public void removeTransaction(UUID transactionId, Integer userId) {
        userList.getUserById(userId).getTransactionsList().deleteTransactionById(transactionId);
    }
    public void executeTransaction(Integer senderId, Integer recipientId, Integer transferAmount) {
        User sender = userList.getUserById(senderId);
        User recipient = userList.getUserById(recipientId);
        if(senderId == recipientId || sender.getBalance() < transferAmount || transferAmount < 0) {
            throw new IllegalTransactionException();
        }

        Transaction debit = new Transaction(sender, recipient, transferAmount, Transaction.Category.DEBIT);
        Transaction credit = new Transaction(sender, recipient, -transferAmount, Transaction.Category.CREDIT);
        debit.setIdentifier(credit.getIdentifier());
        credit.setIdentifier(debit.getIdentifier());

        sender.getTransactionsList().addTransaction(credit);
        recipient.getTransactionsList().addTransaction(debit);

        sender.setBalance(sender.getBalance() - transferAmount);
        recipient.setBalance(recipient.getBalance() + transferAmount);
    }
    public Transaction[] getUnpairedTransactions() {
        int flag = 0;
        TransactionsLinkedList unpairedTransactions = new TransactionsLinkedList();
        Transaction[] allTransactions = getAllTransactions();
        if(allTransactions.length == 1) {
            unpairedTransactions.addTransaction(allTransactions[0]);
        } else {
            for (int i = 0; i < allTransactions.length; i++) {
                if (flag == 0 && i != 0) {
                    unpairedTransactions.addTransaction(allTransactions[i - 1]);
                }
                for (int j = 0; j < allTransactions.length; j++) {
                    flag = 0;
                    if (j == i) {
                        continue;
                    }
                    if (allTransactions[i].getIdentifier() == allTransactions[j].getIdentifier()) {
                        flag = 1;
                        break;
                    }
                }
            }
        }
        return unpairedTransactions.toArray();
    }
    private Transaction[] getAllTransactions() {
        if (userList.getCountOfUsers() == 0) {
            System.out.println("User's list is empty");
            return null;
        }
        int i = 0;
        TransactionsLinkedList allTransactions = new TransactionsLinkedList();
        User[] users = toUsersArray();
        for(User user : users) {
            if(user.getTransactionsList().getCountTransactions() != 0) {
                for (Transaction transaction : user.getTransactionsList().toArray()) {
                    allTransactions.addTransaction(transaction);
                    i++;
                }
            }
        }
        return allTransactions.toArray();
    }
    public User[] toUsersArray() {
        User[] allUsers = new User[userList.getCountOfUsers()];
        for (int i = 0; i < userList.getCountOfUsers(); i++) {
            allUsers[i] = userList.getUserById(i + 1);
        }
        return allUsers;
    }
    public UsersList getUserList() {
        return userList;
    }
}
