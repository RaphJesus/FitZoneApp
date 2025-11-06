package fitzone.app;

public abstract class Subscription {
    protected String name;
    protected double basePrice;
    protected int durationInDays;

    public Subscription(String name, double basePrice, int durationInDays) {
        this.name = name;
        this.basePrice = basePrice;
        this.durationInDays = durationInDays;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public abstract double calculatePrice();

    @Override
    public String toString() {
        return "Subscription: " + name + " (" + durationInDays + " days)" +
                "\n  Base Price: " + basePrice + " RON" +
                "\n  Final Price: " + String.format("%.2f", this.calculatePrice()) + " RON";
    }
}