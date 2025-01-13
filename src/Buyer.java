public class Buyer {
    private String name;
    private double budget;

    public Buyer(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public boolean canAfford(House house) {
        return budget >= house.getPrice();
    }
}