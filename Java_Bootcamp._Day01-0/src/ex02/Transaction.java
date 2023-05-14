import java.util.*;
public class Transaction {
    private UUID Identifier;
    private User Recipient;
    private User Sender;
    private Integer TransferAmount;
    private Category TransferCategory;

    enum Category {
        DEBIT, CREDIT
    }
    public Transaction(User sender, User recipient, Integer transferAmount, Category transferCategory) {
        if((transferAmount < 0 && transferCategory == Category.DEBIT)) {
            System.err.println("Wrong transaction");
        } else if ((sender.getBalance() < transferAmount && transferCategory ==  Category.DEBIT)
                || (sender.getBalance() < -transferAmount && transferCategory ==  Category.CREDIT)) {
            System.err.println("Insufficient funds in the account for user " + sender.getName());
        } else {
            Identifier = UUID.randomUUID();
            Recipient = recipient;
            Sender = sender;
            TransferAmount = transferAmount;
            TransferCategory = transferCategory;
            transactionHandler(TransferCategory, sender, recipient, TransferAmount);
        }
    }
    public static void transactionHandler(Category category, User sender, User recipient, Integer amount) {
        if (amount < 0) {
            amount = -amount;
        }
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }
    public UUID getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(UUID identifier) {
        Identifier = identifier;
    }

    public User getRecipient() {
        return Recipient;
    }

    public void setRecipient(User recipient) {
        Recipient = recipient;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        Sender = sender;
    }

    public Integer getTransferAmount() {
        return TransferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        TransferAmount = transferAmount;
    }

    public Category getTransferCategory() {
        return TransferCategory;
    }

    public void setTransferCategory(Category transferCategory) {
        TransferCategory = transferCategory;
    }

    @Override
    public String toString() {
        return "Transaction(" + Identifier + ")" +
                "{"
                + Sender
                + " ==>("
                + TransferAmount
                + ") "
                + Recipient
                + " with category - "
                + TransferCategory
                + "}";
    }
}
