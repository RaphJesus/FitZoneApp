package fitzone.app;

public class ContractorTrainer extends Trainer {
    private double hourlyRate;

    public ContractorTrainer(String name, WorkoutType specialization, double hourlyRate) {
        super(name, specialization);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String getContractType() {
        return "External Contractor";
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public String getCompensationDetails() {
        return "Compensation: " + String.format("%.2f", hourlyRate) + " RON (Hourly Rate)";
    }
}