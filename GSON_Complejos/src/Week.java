import java.util.List;

public class Week {
    private int NUMBER;
    private List<Expense> EXPENSE;

    public Week(int NUMBER, List<Expense> EXPENSE) {
        this.NUMBER = NUMBER;
        this.EXPENSE = EXPENSE;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(int NUMBER) {
        this.NUMBER = NUMBER;
    }

    public List<Expense> getEXPENSE() {
        return EXPENSE;
    }

    public void setEXPENSE(List<Expense> EXPENSE) {
        this.EXPENSE = EXPENSE;
    }
}
