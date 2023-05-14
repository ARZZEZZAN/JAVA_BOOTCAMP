import java.util.UUID;

class TransactionNotFoundException extends RuntimeException {}

public class TransactionsLinkedList implements TransactionsList{
    private Node start;
    private Node end;
    private Integer countTransactions = 0;

    private static class Node {
        Transaction transaction;
        Node next;
        Node prev;

        public Node(Transaction transaction, Node next, Node prev) {
            this.transaction = transaction;
            this.next = next;
            this.prev = prev;
        }

        private Transaction getTransaction() {
            return transaction;
        }
        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    public TransactionsLinkedList() {}

    @Override
    public void addTransaction(Transaction transaction) {
        Node tmp = end;
        Node newNode = new Node(transaction, null, tmp);
        end = newNode;
        if(tmp == null) {
            start = newNode;
        } else {
            tmp.next = newNode;
        }
        countTransactions++;
    }

    @Override
    public void deleteTransactionById(UUID id) {
        if(id == null) {
            System.err.println("Id equals null");
        }
        if(end == null) {
            System.err.println("Transaction's list is empty");
        }
        for(Node i = start; i != null; i = i.next) {
            if(i.transaction.getIdentifier().equals(id)) {
                remove(i);
                return;
            }
        }
        System.err.println("Transaction with this id(" + id + ") doesn't exist");
    }
    private void remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        if(next != null) {
            next.prev = prev;
        } else {
            end = start;
        }
        if(prev != null) {
            prev.next = next;
        } else {
            start = next;
        }
        if(start == null) {
            end = start;
        }
        countTransactions--;
    }
    @Override
    public Transaction[] toArray() {
        if(countTransactions == 0) {
            System.err.println("Transaction's list is empty");
            return null;
        }
        Transaction[] transactions = new Transaction[countTransactions];
        int j = 0;
        for(Node i = start; i != null; i = i.next) {
            transactions[j] = i.transaction;
            j++;
        }
        return transactions;
    }

    public Integer getCountTransactions() {
        return countTransactions;
    }
}
