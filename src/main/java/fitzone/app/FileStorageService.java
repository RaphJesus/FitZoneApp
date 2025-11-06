package fitzone.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStorageService {

    private static final String WORKOUTS_FILE = "workouts.txt";
    private static final String SUBSCRIPTIONS_FILE = "subscriptions.txt";
    private static final String TRAINERS_FILE = "trainers.txt";
    private static final String DELIMITER = "|";

    // --- Workout ---

    public List<WorkoutType> loadWorkoutTypes() {
        List<WorkoutType> workouts = new ArrayList<>();
        File file = new File(WORKOUTS_FILE);
        if (!file.exists()) {
            return workouts;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\" + DELIMITER);

                if (parts.length == 3) {
                    String name = parts[0];
                    IntensityLevel intensity = IntensityLevel.valueOf(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    workouts.add(new WorkoutType(name, intensity, price));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading workouts file: " + e.getMessage());
        }
        return workouts;
    }

    public void saveWorkoutTypes(List<WorkoutType> workouts) {
        try (PrintWriter out = new PrintWriter(new FileWriter(WORKOUTS_FILE))) {
            for (WorkoutType w : workouts) {
                out.println(String.join(DELIMITER,
                        w.getName(),
                        w.getIntensity().name(),
                        String.valueOf(w.getBasePrice())
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Subscription ---

    public List<Subscription> loadSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        File file = new File(SUBSCRIPTIONS_FILE);
        if (!file.exists()) {
            return subscriptions;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\" + DELIMITER);

                String type = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int duration = Integer.parseInt(parts[3]);

                if (type.equals("STANDARD") && parts.length == 4) {
                    subscriptions.add(new StandardSubscription(name, price, duration));
                } else if (type.equals("PREMIUM") && parts.length == 5) {
                    double discount = Double.parseDouble(parts[4]);
                    subscriptions.add(new PremiumSubscription(name, price, duration, discount));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading subscriptions file: " + e.getMessage());
        }
        return subscriptions;
    }

    public void saveSubscriptions(List<Subscription> subscriptions) {
        try (PrintWriter out = new PrintWriter(new FileWriter(SUBSCRIPTIONS_FILE))) {
            for (Subscription sub : subscriptions) {
                List<String> parts = new ArrayList<>();
                parts.add(sub.getName());
                parts.add(String.valueOf(sub.getBasePrice()));
                parts.add(String.valueOf(sub.getDurationInDays()));

                if (sub instanceof StandardSubscription) {
                    parts.add(0, "STANDARD");
                } else if (sub instanceof PremiumSubscription) {
                    parts.add(0, "PREMIUM");
                    parts.add(String.valueOf(((PremiumSubscription) sub).getPremiumDiscountRate()));
                }
                out.println(String.join(DELIMITER, parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Trainer ---

    public List<Trainer> loadTrainers(List<WorkoutType> allWorkouts) {
        List<Trainer> trainers = new ArrayList<>();
        File file = new File(TRAINERS_FILE);
        if (!file.exists()) {
            return trainers;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\" + DELIMITER);

                if (parts.length == 4) {
                    String type = parts[0];
                    String name = parts[1];
                    String specName = parts[2];
                    double compensation = Double.parseDouble(parts[3]);

                    WorkoutType specialization = allWorkouts.stream()
                            .filter(w -> w.getName().equals(specName))
                            .findFirst()
                            .orElse(null);

                    if (specialization == null) {
                        System.out.println("Warning: Could not find workout type '" + specName + "' for trainer '" + name + "'. Skipping.");
                        continue;
                    }

                    if (type.equals("EMPLOYEE")) {
                        trainers.add(new EmployeeTrainer(name, specialization, compensation));
                    } else if (type.equals("CONTRACTOR")) {
                        trainers.add(new ContractorTrainer(name, specialization, compensation));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading trainers file: " + e.getMessage());
        }
        return trainers;
    }

    public void saveTrainers(List<Trainer> trainers) {
        try (PrintWriter out = new PrintWriter(new FileWriter(TRAINERS_FILE))) {
            for (Trainer trainer : trainers) {
                List<String> parts = new ArrayList<>();
                parts.add(trainer.getName());
                parts.add(trainer.getSpecialization().getName());

                if (trainer instanceof EmployeeTrainer) {
                    parts.add(0, "EMPLOYEE");
                    parts.add(String.valueOf(((EmployeeTrainer) trainer).getMonthlySalary()));
                } else if (trainer instanceof ContractorTrainer) {
                    parts.add(0, "CONTRACTOR");
                    parts.add(String.valueOf(((ContractorTrainer) trainer).getHourlyRate()));
                }
                out.println(String.join(DELIMITER, parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}