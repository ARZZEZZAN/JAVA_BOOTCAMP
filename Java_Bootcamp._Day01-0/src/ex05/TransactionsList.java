import java.util.UUID;

public interface TransactionsList {
    void addTransaction(Transaction transaction);
    void deleteTransactionById(UUID id);
    Transaction[] toArray();
}
