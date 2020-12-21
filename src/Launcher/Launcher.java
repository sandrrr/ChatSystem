package Launcher;

public class Launcher {
    //DEBUG
    public static int DEBUG = 0;

    //DEBUG
    public static void printDebug(String x) {
        if (DEBUG > 0) {
            System.out.println("\u001B[34m" + x + "\u001B[0m");
        }
    }

    public static void main(String[] args) {
        //DEBUG
        if (args.length > 1 && args[0].equals("-d")) {
            DEBUG = Integer.parseInt(args[1]);
        }

        Main.main(args);
    }
}
