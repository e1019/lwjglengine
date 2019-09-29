package gameEngine.io;

public class Debug {
    public static void fatalError(String reason){
        System.err.println("FATAL ERROR: " + reason);
        System.exit(-1);
    }

    public static void warn(String text){
        System.out.println("Warning: " + text);
    }
    public static void print(String text){
        System.out.println("Debug: " + text);
    }
}
