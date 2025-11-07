# FitZone+ Management System
[![Java](https://img.shields.io/badge/Java-11%2B-blue?logo=java)](https://www.java.com)

A robust and extensible Java console application for managing a chain of fitness centers. This project was developed with a strong emphasis on **Object-Oriented Design (OOP)**, **abstraction**, and **data persistence**, replacing a chaotic system based on Excel files.

---

Laboratory Homework by Andrusca Radu-Vasile (10LF241)

## ✨ Key Features

The application provides a complete console interface to manage all operational aspects of the gym:

* **🗂️ Trainer Management:**
    * Add new trainers.
    * Differentiate between **Permanent Employees** (with a monthly salary) and **External Contractors** (with an hourly rate).
    * Remove trainers from the system.

* **🏋️ Workout (Class) Management:**
    * Add new types of classes (e.g., "Spinning", "Online Yoga").
    * Each class has a **name**, an **intensity level** (`LOW`, `MEDIUM`, `HIGH`), and a **base price**.
    * Delete workout types, with validation (a class cannot be deleted if it is actively taught by a trainer).

* **💳 Subscription Management:**
    * Add new types of subscriptions.
    * Differentiate between **Standard Subscriptions** and **Premium Subscriptions** (which include a percentage discount).
    * Modify the base price of any existing subscription.

* **💾 Data Persistence:**
    * The application **automatically loads** all data at startup from text files (`workouts.txt`, `subscriptions.txt`, `trainers.txt`).
    * Any change (addition, deletion, modification) is **automatically saved** back to the files, ensuring persistence between sessions.

* **📊 General Reporting:**
    * Generates a comprehensive summary report displaying **all data** in the system:
        * A full list of trainers, including their contract type and salary/rate.
        * A full list of workouts, including intensity and price.
        * A full list of subscriptions, including the final calculated price and applied discounts.

---

## 🚀 Design Principles

The project demonstrates fundamental OOP principles to ensure the extensibility and maintainability required by the client.

* **Abstraction and Inheritance:**
    * The `abstract class Trainer` is extended by `EmployeeTrainer` and `ContractorTrainer`.
    * The `abstract class Subscription` is extended by `StandardSubscription` and `PremiumSubscription`.

* **Encapsulation:**
    * All model fields are `private`.
    * Business logic (e.g., `generateWorkoutReport`, `removeWorkoutType`) is centralized in `FitZoneService`, hiding complexity from the UI (`Main.java`).
    * Storage logic is completely separated into `FileStorageService`.

* **Polymorphism:**
    * Abstract methods (e.g., `calculatePrice()`, `getCompensationDetails()`) are implemented differently in subclasses.
    * A single list (`List<Trainer>`) can hold objects of different types (`EmployeeTrainer`, `ContractorTrainer`), and the correct `toString()` method is called for each.

---

## 🧭 Menu Structure

The application uses a nested menu structure for logical and user-friendly navigation.

--- FitZone+ Main Menu ---

1.Manage Trainers

2.Manage Workouts

3.Manage Subscriptions

4.Generate General Report

5.List All Trainers (Quick View)

6.List All Subscriptions (Quick View)

0.Exit

* **Options 1, 2, 3** open dedicated **sub-menus**.
    * *Example: Pressing '1' (Manage Trainers) opens a new menu:*
    ```
    --- Manage Trainers Menu ---
    1. Add New Trainer
    2. Remove Trainer
    0. Back to Main Menu
    ```
* **Options 4, 5, 6** are **direct actions** (Quick View / Report).
    * These display the requested information and then **wait for the user to press "Enter"** to return to the main menu, allowing the data to be read without being immediately taken out of context.

---

## 💾 Data Persistence

Storage logic is handled by `FileStorageService`.

* **Format:** Data is saved in a simple, plain text format, delimited by `|` (pipe), similar to a CSV file.
* **Files:**
    * `workouts.txt`: Saves workout types.
    * `subscriptions.txt`: Saves subscriptions.
    * `trainers.txt`: Saves trainers.
* **How it works:**
    1.  On application startup, `FitZoneService` instructs `FileStorageService` to read these files and populate the internal lists.
    2.  When the user makes a change (e.g., `service.addTrainer(...)`), `FitZoneService` immediately saves the entire updated list back to the corresponding file.

---

## 📂 Project Structure

fitzone-app/
│
└───src/
    └───fitzone/
        └───app/
            │
            ├── Main.java                 # User Interface (Console Menu)
            ├── FitZoneService.java       # Central Logic (Application Brain)
            ├── FileStorageService.java   # Handles file reading/writing
            │
            ├── IntensityLevel.java       # Enum (LOW, MEDIUM, HIGH)
            ├── WorkoutType.java          # Model (Yoga, CrossFit, etc.)
            │
            ├── Trainer.java              # Model (Abstract)
            ├── EmployeeTrainer.java      # Model (Subclass)
            ├── ContractorTrainer.java    # Model (Subclass)
            │
            ├── Subscription.java         # Model (Abstract)
            ├── StandardSubscription.java # Model (Subclass)
            └── PremiumSubscription.java  # Model (Subclass)

---

## ▶️ How to Run

1.  **Requirements:** Ensure you have **Java JDK 11** (or newer) installed.
2.  **Clone:** Clone this repository:
    ```bash
    git clone [https://github.com/your-username/fitzone-app.git](https://github.com/your-username/fitzone-app.git)
    cd fitzone-app
    ```
3.  **Compile:** Navigate to the `src` folder and compile all `.java` files (respecting the package structure):
    ```bash
    # From the root folder (e.g., fitzone-app/)
    javac -d . src/fitzone/app/*.java
    ```
4.  **Run:** Execute the `Main` class from the root folder, specifying the full package path:
    ```bash
    java fitzone.app.Main
    ```
5.  The application menu will start in your console.

@author RaphJesus
