package fitzone.app;

public class EmployeeTrainer extends Trainer {
    private double monthlySalary;

    public EmployeeTrainer(String name, WorkoutType specialization, double monthlySalary) {
        super(name, specialization);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public String getContractType() {
        return "Permanent Employee";
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    @Override
    public String getCompensationDetails() {
        return "Compensation: " + String.format("%.2f", monthlySalary) + " RON (Monthly Salary)";
    }
}