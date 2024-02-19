public class Expense {
    private String WHAT;
    private float AMOUNT;

    public Expense(String WHAT, float AMOUNT) {
        this.WHAT = WHAT;
        this.AMOUNT = AMOUNT;
    }

    public String getWHAT() {
        return WHAT;
    }

    public void setWHAT(String WHAT) {
        this.WHAT = WHAT;
    }

    public float getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(float AMOUNT) {
        this.AMOUNT = AMOUNT;
    }
}
