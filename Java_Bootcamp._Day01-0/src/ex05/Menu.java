import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private TransactionsService transactionsService;
    private Scanner scanner;

    public Menu() {
        transactionsService = new TransactionsService();
        scanner = new Scanner(System.in);
    }

    public void Start(boolean root) {
        printMenu(root);
        System.out.print("-> ");
        if(root) {
            userDevActions();
        } else {
            userActions();
        }
    }
    private void printMenu(boolean dev) {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if(dev) {
            System.out.println("5. DEV – remove a transfer by ID");
            System.out.println("6. DEV – check transfer validity");
            System.out.println("7. Finish execution");
        } else {
            System.out.println("5. Finish execution");
        }
    }
    private void userDevActions() {
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                addUser(true);
                break;
            case 2:
                userBalance(true);
                break;
            case 3:
                createTransfer(true);
                break;
            case 4:
                getTransactions(true);
                break;
            case 5:
                removeTransferById(true);
                break;
            case 6:
                checkTransfer(true);
                break;
            case 7:
                System.exit(0);
                break;
            default:
                System.err.println("Try again but with correct statement");
                System.out.println("---------------------------------------------------------");
                Start(true);
        }

    }
    private void userActions() {
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                addUser(false);
                break;
            case 2:
                userBalance(false);
                break;
            case 3:
                createTransfer(false);
                break;
            case 4:
                getTransactions(false);
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.err.println("Try again but with correct statement");
                System.out.println("---------------------------------------------------------");
                Start(false);
        }

    }
    private void addUser(boolean dev) {
        try {
            System.out.println("Enter a user name and a balance, example: \"Armen 10000\"");
            String name = scanner.next();
            Integer balance = scanner.nextInt();
            User newUser = new User(name, balance);
            transactionsService.addUser(newUser);
            System.out.printf("User with id = %d is added\n", newUser.getIdentifier());
            Start(dev);
        } catch (RuntimeException rex) {
            System.out.println(rex);
            addUser(dev);
        }
    }
    private void userBalance(boolean dev) {
        try {
            System.out.println("Enter a user ID");
            Integer id = scanner.nextInt();
            User user = transactionsService.getUserList().getUserById(id);
            System.out.printf("%s - %d\n", user.getName(), user.getBalance());
            System.out.println("---------------------------------------------------------");
            Start(dev);
        } catch (RuntimeException rex) {
            System.out.println(rex);
            Start(dev);
        }
    }
    private void createTransfer(boolean dev) {
        try {
            System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
            Integer senderId = scanner.nextInt();
            Integer recipientId = scanner.nextInt();
            Integer transferAmount = scanner.nextInt();
            transactionsService.executeTransaction(senderId, recipientId, transferAmount);
            System.out.println("The transfer is completed");
            System.out.println("---------------------------------------------------------");
            Start(dev);
        } catch (RuntimeException rex) {
            System.out.println(rex);
            Start(dev);
        }
    }
    private void getTransactions(boolean dev) {
        try {
            System.out.println("Enter a user ID");
            Integer userId = scanner.nextInt();
            User user = transactionsService.getUserList().getUserById(userId);
            Transaction[] transactions = transactionsService.getUserTransactions(user);
            for (Transaction trans : transactions) {
                System.out.printf("To %s(id = %d) %d with id = %s\n",
                        user.getName(), user.getIdentifier(),
                        trans.getTransferAmount(), trans.getIdentifier());
            }
            System.out.println("---------------------------------------------------------");
            Start(dev);
        } catch (RuntimeException rex) {
            System.out.println(rex);
            Start(dev);
        }
    }
    private void removeTransferById(boolean dev) {
        try {
            System.out.println("Enter a user ID and a transfer ID");
            Integer userId = scanner.nextInt();
            String transId = scanner.next();
            UUID transferId = UUID.fromString(transId);
            User user = transactionsService.getUserList().getUserById(userId);
            int amount = 0;
            for(Transaction trans : transactionsService.getUserTransactions(user)) {
                if(trans.getIdentifier().equals(transferId)) {
                    amount = trans.getTransferAmount();
                }
            }
            transactionsService.removeTransaction(transferId, userId);
            System.out.printf("Transfer To %s(id = %d) %d removed\n",
                    user.getName(), user.getIdentifier(), amount);
            System.out.println("---------------------------------------------------------");
            Start(dev);
        } catch (RuntimeException rex) {
            System.out.println(rex);
            Start(dev);
        }
    }
    private void checkTransfer(boolean dev) {
        try {
            for (Transaction transaction : transactionsService.getUnpairedTransactions()) {
                System.out.printf("%s(id = %d) has an unacknowledged transfer id = " +
                                "%s " +
                                "from %s(id = %d) for %d\n",
                        transaction.getRecipient().getName(), transaction.getRecipient().getIdentifier(),
                        transaction.getIdentifier(), transaction.getSender().getName(),
                        transaction.getSender().getIdentifier(), transaction.getTransferAmount());
                System.out.println("---------------------------------------------------------");
                Start(dev);
            }
        } catch (RuntimeException rex) {
            System.out.println(rex);
            Start(dev);
        }
    }

}
