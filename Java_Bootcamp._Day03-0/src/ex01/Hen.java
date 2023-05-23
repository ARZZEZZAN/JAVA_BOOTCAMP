public class Hen implements Runnable {
    private Consumer consumer;
    private int count = 0;
    public Hen(int count, Consumer consumer) {
        this.count = count;
        this.consumer = consumer;
    }
    @Override
    public void run() {
        for(int i = 0; i < count; i++) {
            try {
                consumer.printHen();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
