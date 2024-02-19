import java.util.List;

public class Person {
    private String WHO;
    private List<Week> WEEK;

    public Person(String WHO, List<Week> WEEK) {
        this.WHO = WHO;
        this.WEEK = WEEK;
    }

    public String getWHO() {
        return WHO;
    }

    public void setWHO(String WHO) {
        this.WHO = WHO;
    }

    public List<Week> getWEEK() {
        return WEEK;
    }

    public void setWEEK(List<Week> WEEK) {
        this.WEEK = WEEK;
    }
}
