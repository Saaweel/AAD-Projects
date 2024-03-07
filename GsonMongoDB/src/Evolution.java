public class Evolution {
    private String num;
    private String name;

    // Getters and Setters for Evolution
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Evolution [name=" + name + ", num=" + num + "]";
    }
}