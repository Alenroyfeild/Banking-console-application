package src.utils;
//this class is Single ton class. Because instead of declaring  variables as static we can use Single ton class Variables.
public class Utilties {
    public int findex = 1;
    public int lindex = 2;
    public int cifindex = 2;
    public int numAccounts = 3;
    public int accGenarateCode=4;
    public int cifGenarateCode=3;
    public static Utilties instance = null;

    private Utilties() {

    }

    public static Utilties getInstance() {
        if (instance == null)
            instance = new Utilties();
        return instance;
    }
}
