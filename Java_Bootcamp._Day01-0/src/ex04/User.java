public class User {
    private Integer Identifier;
    private String Name;
    private Integer Balance;

    private TransactionsLinkedList transactionsList;

    public User() {
        Identifier = UserIdsGenerator.getInstance().idGenerator();
        Name = "Unknown";
        Balance = 0;
        transactionsList = new TransactionsLinkedList();
    }
    public User(String name, Integer balance) {
        if (balance < 0) {
            System.err.println("Balance can't be less then zero");
        } else {
            Identifier = UserIdsGenerator.getInstance().idGenerator();
            Name = name;
            Balance = balance;
            transactionsList = new TransactionsLinkedList();
        }
    }

    public Integer getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(Integer identifier) {
        Identifier = identifier;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getBalance() {
        return Balance;
    }

    public void setBalance(Integer balance) {
        if (balance >= 0) {
            Balance = balance;
        } else {
            Balance = 0;
        }
    }
    public TransactionsLinkedList getTransactionsList() {
        return transactionsList;
    }
    @Override
    public String toString() {
        return Name + "(id - " + Identifier + "){" + "Balance: " + Balance + "}";
    }
}
