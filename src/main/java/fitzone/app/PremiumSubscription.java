package fitzone.app;

public class PremiumSubscription extends Subscription {

    private double premiumDiscountRate;

    public PremiumSubscription(String name, double basePrice, int durationInDays, double premiumDiscountRate) {
        super(name, basePrice, durationInDays);
        this.premiumDiscountRate = premiumDiscountRate;
    }

    public double getPremiumDiscountRate() {
        return premiumDiscountRate;
    }

    @Override
    public double calculatePrice() {
        return this.basePrice * (1.0 - premiumDiscountRate);
    }

    @Override
    public String toString() {
        String discountPercent = String.format("%.0f%%", premiumDiscountRate * 100);
        return "[PREMIUM] " + super.toString() + " (Discount: " + discountPercent + ")";
    }
}