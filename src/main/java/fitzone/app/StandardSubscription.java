package fitzone.app;

public class StandardSubscription extends Subscription {

    public StandardSubscription(String name, double basePrice, int durationInDays) {
        super(name, basePrice, durationInDays);
    }

    @Override
    public double calculatePrice() {
        return this.basePrice;
    }

    @Override
    public String toString() {
        return "[STANDARD] " + super.toString();
    }
}