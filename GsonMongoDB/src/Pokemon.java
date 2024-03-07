import java.util.List;

public class Pokemon {
    private int id;
    private String num;
    private String name;
    private String img;
    private List<String> type;
    private String height;
    private String weight;
    private String candy;
    private int candy_count;
    private String egg;
    private double spawn_chance;
    private double avg_spawns;
    private String spawn_time;
    private List<Double> multipliers;
    private List<String> weaknesses;
    private List<Evolution> prev_evolution;
    private List<Evolution> next_evolution;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCandy() {
        return candy;
    }

    public void setCandy(String candy) {
        this.candy = candy;
    }

    public int getCandy_count() {
        return candy_count;
    }

    public void setCandy_count(int candy_count) {
        this.candy_count = candy_count;
    }

    public String getEgg() {
        return egg;
    }

    public void setEgg(String egg) {
        this.egg = egg;
    }

    public double getSpawn_chance() {
        return spawn_chance;
    }

    public void setSpawn_chance(double spawn_chance) {
        this.spawn_chance = spawn_chance;
    }

    public double getAvg_spawns() {
        return avg_spawns;
    }

    public void setAvg_spawns(double avg_spawns) {
        this.avg_spawns = avg_spawns;
    }

    public String getSpawn_time() {
        return spawn_time;
    }

    public void setSpawn_time(String spawn_time) {
        this.spawn_time = spawn_time;
    }

    public List<Double> getMultipliers() {
        return multipliers;
    }

    public void setMultipliers(List<Double> multipliers) {
        this.multipliers = multipliers;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<Evolution> getPrev_evolution() {
        return prev_evolution;
    }

    public void setPrev_evolution(List<Evolution> prev_evolution) {
        this.prev_evolution = prev_evolution;
    }

    public List<Evolution> getNext_evolution() {
        return next_evolution;
    }

    public void setNext_evolution(List<Evolution> next_evolution) {
        this.next_evolution = next_evolution;
    } 

    @Override
    public String toString() {
        return "Pokemon [avg_spawns=" + avg_spawns + ", candy=" + candy + ", candy_count=" + candy_count + ", egg=" + egg
                + ", height=" + height + ", id=" + id + ", img=" + img + ", multipliers=" + multipliers + ", name=" + name
                + ", next_evolution=" + next_evolution + ", num=" + num + ", prev_evolution=" + prev_evolution
                + ", spawn_chance=" + spawn_chance + ", spawn_time=" + spawn_time + ", type=" + type + ", weaknesses="
                + weaknesses + ", weight=" + weight + "]";
    }
}