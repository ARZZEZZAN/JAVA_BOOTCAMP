import java.util.*;
public class Program {
    public static void main(String[] args) {
        Menu menu = new Menu();
       if(args.length > 0 && args[0].equals("--profile=dev")) {
           menu.Start(true);
       } else {
           menu.Start(false);
       }
    }
}
