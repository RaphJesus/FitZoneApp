package fitzone.app;

public abstract class Trainer {
    protected String name;
    protected WorkoutType specialization;

    public Trainer(String name, WorkoutType specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public String getName() {
        return name;
    }

    public WorkoutType getSpecialization() {
        return specialization;
    }

    public abstract String getContractType();

    public abstract String getCompensationDetails();

    @Override
    public String toString() {
        return "Name: " + name + " [" + getContractType() + "]" +
                "\n  Specialization: " + specialization.getName() +
                "\n  " + getCompensationDetails();
    }
}