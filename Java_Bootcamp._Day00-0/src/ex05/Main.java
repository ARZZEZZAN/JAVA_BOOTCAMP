import java.util.*;
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String[] students = new String[10];
        String[][] shedule = new String[7][10];
        String[][][][] attendance = new String[10][30][10][1];
        String name = new String();
        String date = new String();
        String time = new String();
        String status = new String();
        int i = 0, j = 0, k = 0;
        while(scanner.hasNext() && !(name = scanner.next()).equals(".") && i < 10) {
            if (name.length() < 10) {
                students[i] = name;
            } else {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
            i++;
        }
        while(scanner.hasNext() && !(time = scanner.next()).equals(".")) {
            isScannered();
            date = scanner.next();
            fillShedule(shedule, date, time);
            j++;
        }
        while(scanner.hasNext() && !(name = scanner.next()).equals(".")) {
            int week = 0;
            isScannered();
            time = scanner.next();
            isScannered();
            date = scanner.next();
            isScannered();
            status = scanner.next();
            week = findWeek(Integer.parseInt(date), shedule, time);
            for (i = 0; i < students.length && !students[i].equals(name); i++);
            attendance[i][Integer.parseInt(date) - 1][week][0] = status;
        }
        for (i = 0; i <= 30; i++) {
            printBoard(i, shedule);
        }
        System.out.println();

        for (i = 0; i < students.length && students[i] != null; i++) {
            System.out.printf("%10s", students[i]);
            printBoard(attendance[i], shedule, students[i].length());
        }
    }
    public static int findWeek(int date, String[][] classes, String time) {
        int dayOfWeek = date % 7;
        int i = 0;
        for (; i < 1 && !classes[dayOfWeek][i].equals(time); i++);
        if (i < 1) {
            return i;
        }
        return 0;
    }
    private static void fillShedule(String[][] shedule, String tmp, String time) {
        if(tmp.equals("MO")) {
            shedule[0][0] = time;
            shedule[0][1] = tmp;
        } else if(tmp.equals("TU")) {
            shedule[1][0] = time;
            shedule[1][1] = tmp;
        } else if(tmp.equals("WE")) {
            shedule[2][0] = time;
            shedule[2][1] = tmp;
        } else if(tmp.equals("TH")) {
            shedule[3][0] = time;
            shedule[3][1] = tmp;
        } else if(tmp.equals("FR")) {
            shedule[4][0] = time;
            shedule[4][1] = tmp;
        } else if(tmp.equals("SA")) {
            shedule[5][0] = time;
            shedule[5][1] = tmp;
        } else if(tmp.equals("SU")) {
            shedule[6][0] = time;
            shedule[6][1] = tmp;
        }
    }
    private static void printBoard(String[][][] attendance, String[][] shedule, int len) {
        boolean isFirst = true;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 1 && shedule[(i + 1) % 7][j] != null; j++) {
                if (attendance[i][j][0] != null && attendance[i][j][0].equals("HERE")) {
                    System.out.printf("        %2d|", 1);
                } else if (attendance[i][j][0] != null && attendance[i][j][0].equals("NOT_HERE")) {
                    System.out.printf("        %2d|", -1);
                } else {
                    System.out.print("          |");
                }
            }
        }
        System.out.println();
    }
    private static void printBoard(int i, String[][] shedule) {
        int j = 0;
        if (i == 0) {
            System.out.print("          ");
        }
        int day = ++i % 7;
        while(j < 1 && shedule[day][j] != null) {
            System.out.print(shedule[day][j] + ":00 ");
            if (day == 0) {
                System.out.printf("MO %2d|", i);
            } else if (day == 1) {
                System.out.printf("TU %2d|", i);
            } else if (day == 2) {
                System.out.printf("WE %2d|", i);
            } else if (day == 3) {
                System.out.printf("TH %2d|", i);
            } else if (day == 4) {
                System.out.printf("FR %2d|", i);
            } else if (day == 5) {
                System.out.printf("SA %2d|", i);
            } else if (day == 6) {
                System.out.printf("SU %2d|", i);
            }
            j++;
        }
    }
    private static boolean isValid(String str) {
        for (int i = 0; i < 10; i++) {
            if (str.equals(i)) {
                return true;
            }
        }
        return false;
    }
    private static void isScannered() {
        if(scanner.hasNext()) {
        } else {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
    }
}
