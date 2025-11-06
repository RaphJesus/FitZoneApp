package fitzone.app;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static FitZoneService service = new FitZoneService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        runMainMenu();
    }

    private static void runMainMenu() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    runTrainerMenu();
                    break;
                case 2:
                    runWorkoutMenu();
                    break;
                case 3:
                    runSubscriptionMenu();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    listAllTrainers();
                    break;
                case 6:
                    listAllSubscriptions();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("--- FitZone+ Main Menu ---");
        System.out.println("1. Manage Trainers");
        System.out.println("2. Manage Workouts");
        System.out.println("3. Manage Subscriptions");
        System.out.println("4. Generate General Report");
        System.out.println("5. List All Trainers (Quick View)");
        System.out.println("6. List All Subscriptions (Quick View)");
        System.out.println("0. Exit");
        System.out.print("Please enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void waitForKeypress() {
        System.out.println("\nPress Enter to return to the menu...");
        scanner.nextLine();
    }

    private static void runTrainerMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Manage Trainers Menu ---");
            System.out.println("1. Add New Trainer");
            System.out.println("2. Remove Trainer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            int choice = getUserChoice();
            System.out.println();

            switch (choice) {
                case 1:
                    handleAddTrainer();
                    break;
                case 2:
                    handleRemoveTrainer();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void runWorkoutMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Manage Workouts Menu ---");
            System.out.println("1. Add New Workout Type");
            System.out.println("2. Remove Workout Type");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            int choice = getUserChoice();
            System.out.println();

            switch (choice) {
                case 1:
                    handleAddWorkoutType();
                    break;
                case 2:
                    handleRemoveWorkoutType();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void runSubscriptionMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Manage Subscriptions Menu ---");
            System.out.println("1. Add New Subscription");
            System.out.println("2. Modify Subscription Base Price");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice: ");

            int choice = getUserChoice();
            System.out.println();

            switch (choice) {
                case 1:
                    handleAddSubscription();
                    break;
                case 2:
                    handleModifySubscriptionPrice();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void handleAddTrainer() {
        System.out.println("--- Add New Trainer ---");
        System.out.print("Enter trainer's name: ");
        String name = scanner.nextLine();

        List<WorkoutType> types = service.getWorkoutTypes();
        if (types.isEmpty()) {
            System.out.println("No workout types available. Please add a workout type first.");
            return;
        }

        System.out.println("Available specializations:");
        for (int i = 0; i < types.size(); i++) {
            System.out.println((i + 1) + ". " + types.get(i).getName());
        }
        System.out.print("Choose specialization number: ");
        int typeIndex = Integer.parseInt(scanner.nextLine()) - 1;
        WorkoutType chosenType = types.get(typeIndex);

        System.out.print("Enter contract type (1 = Employee, 2 = Contractor): ");
        int contractType = Integer.parseInt(scanner.nextLine());

        if (contractType == 1) {
            System.out.print("Enter monthly salary: ");
            double salary = Double.parseDouble(scanner.nextLine());
            service.addTrainer(new EmployeeTrainer(name, chosenType, salary));
        } else if (contractType == 2) {
            System.out.print("Enter hourly rate: ");
            double rate = Double.parseDouble(scanner.nextLine());
            service.addTrainer(new ContractorTrainer(name, chosenType, rate));
        } else {
            System.out.println("Invalid contract type.");
            return;
        }
        System.out.println("Trainer successfully added!");
    }

    private static void handleRemoveTrainer() {
        System.out.println("--- Remove Trainer ---");
        List<Trainer> trainers = service.getTrainers();
        if (trainers.isEmpty()) {
            System.out.println("No trainers registered to remove.");
            return;
        }

        System.out.println("Current Trainers:");
        for (Trainer trainer : trainers) {
            System.out.println("- " + trainer.getName());
        }

        System.out.print("\nEnter the name of the trainer to remove: ");
        String name = scanner.nextLine();

        boolean success = service.removeTrainer(name);
        if (success) {
            System.out.println("Trainer '" + name + "' removed successfully.");
        } else {
            System.out.println("Trainer '" + name + "' not found.");
        }
    }

    private static void handleAddWorkoutType() {
        System.out.println("--- Add New Workout Type ---");
        System.out.print("Enter workout name (e.g., Spinning): ");
        String name = scanner.nextLine();

        IntensityLevel level = null;
        while (level == null) {
            System.out.print("Enter intensity (LOW, MEDIUM, HIGH): ");
            String intensityInput = scanner.nextLine().toUpperCase();
            try {
                level = IntensityLevel.valueOf(intensityInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid intensity. Please try again.");
            }
        }

        System.out.print("Enter base price: ");
        double price = Double.parseDouble(scanner.nextLine());

        service.addWorkoutType(new WorkoutType(name, level, price));
        System.out.println("Workout type '" + name + "' added successfully.");
    }

    private static void handleRemoveWorkoutType() {
        System.out.println("--- Remove Workout Type ---");
        List<WorkoutType> types = service.getWorkoutTypes();
        if (types.isEmpty()) {
            System.out.println("No workout types to remove.");
            return;
        }

        System.out.println("Available workout types:");
        for (WorkoutType type : types) {
            System.out.println("- " + type.getName());
        }

        System.out.print("\nEnter the name of the workout type to remove: ");
        String name = scanner.nextLine();

        int status = service.removeWorkoutType(name);

        if (status == 0) {
            System.out.println("Workout type '" + name + "' removed successfully.");
        } else if (status == 1) {
            System.out.println("Workout type '" + name + "' not found.");
        } else if (status == 2) {
            System.out.println("Cannot remove '" + name + "'. It is currently assigned to one or more trainers.");
        }
    }

    private static void handleAddSubscription() {
        System.out.println("--- Add New Subscription ---");
        System.out.print("Enter subscription name (e.g., Premium 90 Days): ");
        String name = scanner.nextLine();
        System.out.print("Enter base price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter duration (in days): ");
        int duration = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter type (1 = Standard, 2 = Premium): ");
        int type = Integer.parseInt(scanner.nextLine());

        if (type == 1) {
            service.addSubscription(new StandardSubscription(name, price, duration));
        } else if (type == 2) {
            System.out.print("Enter premium discount rate (e.g., 0.2 for 20%): ");
            double discount = Double.parseDouble(scanner.nextLine());
            service.addSubscription(new PremiumSubscription(name, price, duration, discount));
        } else {
            System.out.println("Invalid type.");
            return;
        }
        System.out.println("Subscription '" + name + "' added successfully.");
    }

    private static void handleModifySubscriptionPrice() {
        System.out.println("--- Modify Subscription Price ---");
        List<Subscription> subscriptions = service.getSubscriptions();
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions defined to modify.");
            return;
        }

        System.out.println("Current Subscriptions:");
        for (Subscription sub : subscriptions) {
            System.out.println("- " + sub.getName() + " (Current price: " + sub.getBasePrice() + ")");
        }

        System.out.print("\nEnter the name of the subscription to modify: ");
        String name = scanner.nextLine();

        Subscription sub = service.findSubscriptionByName(name);
        if (sub == null) {
            System.out.println("Subscription not found.");
            return;
        }

        System.out.print("Enter new base price for '" + name + "': ");
        double newPrice = Double.parseDouble(scanner.nextLine());

        service.modifySubscriptionPrice(name, newPrice);
        System.out.println("Price updated successfully.");
    }

    private static void listAllTrainers() {
        System.out.println("\n--- All Trainers ---");
        List<Trainer> trainers = service.getTrainers();
        if (trainers.isEmpty()) {
            System.out.println("No trainers registered.");
        } else {
            for (Trainer trainer : trainers) {
                System.out.println(trainer);
                System.out.println("-----------------");
            }
        }
        waitForKeypress();
    }

    private static void listAllSubscriptions() {
        System.out.println("\n--- All Subscriptions ---");
        List<Subscription> subscriptions = service.getSubscriptions();
        if (subscriptions.isEmpty()) {
            System.out.println("No subscriptions defined.");
        } else {
            for (Subscription sub : subscriptions) {
                System.out.println(sub);
                System.out.println("-----------------");
            }
        }
        waitForKeypress();
    }

    private static void generateReport() {
        System.out.println("\n--- Generating General Report ---");
        String report = service.generateWorkoutReport();
        System.out.println(report);
        waitForKeypress();
    }
}