package fitzone.app;

import java.util.ArrayList;
import java.util.List;

public class FitZoneService {

    private List<Trainer> trainers;
    private List<Subscription> subscriptions;
    private List<WorkoutType> workoutTypes;

    private FileStorageService storage;

    public FitZoneService() {
        this.storage = new FileStorageService();
        this.workoutTypes = storage.loadWorkoutTypes();
        this.subscriptions = storage.loadSubscriptions();
        this.trainers = storage.loadTrainers(this.workoutTypes);
    }

    public void addTrainer(Trainer trainer) {
        this.trainers.add(trainer);
        storage.saveTrainers(this.trainers);
    }

    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
        storage.saveSubscriptions(this.subscriptions);
    }

    public void addWorkoutType(WorkoutType workoutType) {
        this.workoutTypes.add(workoutType);
        storage.saveWorkoutTypes(this.workoutTypes);
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<WorkoutType> getWorkoutTypes() {
        return workoutTypes;
    }

    public Trainer findTrainerByName(String name) {
        for (Trainer trainer : trainers) {
            if (trainer.getName().equalsIgnoreCase(name)) {
                return trainer;
            }
        }
        return null;
    }

    public WorkoutType findWorkoutTypeByName(String name) {
        for (WorkoutType type : workoutTypes) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public Subscription findSubscriptionByName(String name) {
        for (Subscription sub : subscriptions) {
            if (sub.getName().equalsIgnoreCase(name)) {
                return sub;
            }
        }
        return null;
    }

    public boolean removeTrainer(String name) {
        Trainer trainer = findTrainerByName(name);
        if (trainer != null) {
            boolean success = trainers.remove(trainer);
            if (success) {
                storage.saveTrainers(this.trainers);
            }
            return success;
        }
        return false;
    }

    public boolean isWorkoutTypeInUse(WorkoutType workout) {
        for (Trainer trainer : trainers) {
            if (trainer.getSpecialization().equals(workout)) {
                return true;
            }
        }
        return false;
    }

    public int removeWorkoutType(String name) {
        WorkoutType workout = findWorkoutTypeByName(name);
        if (workout == null) {
            return 1;
        }
        if (isWorkoutTypeInUse(workout)) {
            return 2;
        }
        workoutTypes.remove(workout);
        storage.saveWorkoutTypes(this.workoutTypes);
        return 0;
    }

    public boolean modifySubscriptionPrice(String name, double newPrice) {
        Subscription sub = findSubscriptionByName(name);
        if (sub != null) {
            sub.setBasePrice(newPrice);
            storage.saveSubscriptions(this.subscriptions);
            return true;
        }
        return false;
    }

    public String generateWorkoutReport() {
        StringBuilder report = new StringBuilder();
        report.append("--- FitZone+ General Report ---\n");

        report.append("\n--- 1. All Trainers ---\n\n");
        if (trainers.isEmpty()) {
            report.append("No trainers registered.\n");
        } else {
            for (Trainer trainer : trainers) {
                report.append(trainer.toString()).append("\n");
                report.append("-----------------------------------\n");
            }
        }

        report.append("\n--- 2. All Workout Types ---\n\n");
        if (workoutTypes.isEmpty()) {
            report.append("No workout types defined.\n");
        } else {
            for (WorkoutType type : workoutTypes) {
                report.append(type.toString()).append("\n");
                report.append("-----------------------------------\n");
            }
        }

        report.append("\n--- 3. All Subscriptions ---\n\n");
        if (subscriptions.isEmpty()) {
            report.append("No subscriptions defined.\n");
        } else {
            for (Subscription sub : subscriptions) {
                report.append(sub.toString()).append("\n");
                report.append("-----------------------------------\n");
            }
        }

        report.append("\n--- End of Report ---");
        return report.toString();
    }
}