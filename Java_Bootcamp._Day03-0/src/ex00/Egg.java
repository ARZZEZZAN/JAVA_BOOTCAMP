public class Egg implements Runnable {
    private int count = 0;
    public Egg(int count) {
        this.count = count;
    }
    @Override
    public void run() {
        for(int i = 0; i < count; i++) {
            System.out.println("Egg");
        }
    }
}
