package fitzone.app;

public class WorkoutType {
    private String name;
    private IntensityLevel intensity;
    private double basePrice;

    public WorkoutType(String name, IntensityLevel intensity, double basePrice) {
        this.name = name;
        this.intensity = intensity;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public IntensityLevel getIntensity() {
        return intensity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return name + " (Intensity: " + intensity + ", Price: " + basePrice + " RON)";
    }
}