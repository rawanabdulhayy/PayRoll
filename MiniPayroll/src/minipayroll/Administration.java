package minipayroll;

import java.util.ArrayList;

public class Administration {

    ArrayList<Engineer> engineers = new ArrayList<>();
    ArrayList<Trainee> trainees = new ArrayList<>();

    public void addNewEngineer(String name, int age, int workingHours, String position, double payRate, double taxRate) {
        Engineer eng = new Engineer(name, age, workingHours, position, payRate, taxRate);
        engineers.add(eng);
        System.out.println("Engineer added successfully");
        System.out.println(eng);
    }

    public Engineer getEngineer(int id) {
        int i = getEngIndexUtil(id);
        if (i == engineers.size()) {
            System.out.println("Cannot find engineer with the specified ID");
            return null;
        }
        return engineers.get(i);
    }

    public void editEngineer(int id, String newName, int newAge, int newWorkingHours, String newPosition, double newPayRate, double newTaxRate) {
        int i = getEngIndexUtil(id);
        if (i == engineers.size()) {
            System.out.println("Cannot find engineer with the specified ID");
            return;
        }
        engineers.get(i).edit(newName, newAge, newWorkingHours, newPosition, newPayRate, newTaxRate);
        System.out.println("Engineer edited:-");
        System.out.println(engineers.get(i));
    }

    public void deleteEngineer(int id) {
        int i = getEngIndexUtil(id);
        if (i == engineers.size()) {
            System.out.println("Cannot find engineer with the specified ID");
            return;
        }
        Engineer removed = engineers.remove(i);
        System.out.println("Removed engineer:-");
        System.out.println(removed);
    }

    public double calculateEngineerSalary(int id) {
        int i = getEngIndexUtil(id);
        if (i == engineers.size()) {
            System.out.println("Cannot find engineer with the specified ID");
            return 0.0;
        }
        return engineers.get(i).getSalary();
    }

    public void viewAllEngineers() {
        for (int i = 0; i < engineers.size(); i++) {
            System.out.println("Engineer #" + (i + 1) + ":-\n" + engineers.get(i));
        }
    }

    public void addNewTrainee(String name, int age, float GPA, String university, String acadedmicYear) {
        Trainee tra = new Trainee(name, age, GPA, university, acadedmicYear);
        trainees.add(tra);
        System.out.println("Trainee added successfully");
        System.out.println(tra);

    }

    public Trainee getTrainee(int id) {
        int i = getTraIndexUtil(id);
        if (i == trainees.size()) {
            System.out.println("Cannot find trainee with the specified ID");
            return null;
        }
        return trainees.get(i);
    }

    public void editTrainee(int id, String newName, int newAge, String newUniversity, String newAcadedmicYear, float newGPA) {
        int i = getTraIndexUtil(id);
        if (i == trainees.size()) {
            System.out.println("Cannot find trainee with the specified ID");
            return;
        }
        trainees.get(i).edit(newName, newAge, newUniversity, newGPA, newAcadedmicYear);
        System.out.println("Trainee edited:-");
        System.out.println(trainees.get(i));
    }

    public void deleteTrainee(int id) {
        int i = getTraIndexUtil(id);
        if (i == trainees.size()) {
            System.out.println("Cannot find trainee with the specified ID");
            return;
        }
        Trainee removed = trainees.remove(i);
        System.out.println("Removed trainee:-");
        System.out.println(removed);
    }

    public void viewAllTrainees() {
        for (int i = 0; i < trainees.size(); i++) {
            System.out.println("Trainee #" + (i + 1) + ":-\n" + trainees.get(i));
        }
    }

    private int getEngIndexUtil(int id) {
        int i = 0;
        for (; i < engineers.size(); i++) {
            if (engineers.get(i).getID() == id) {
                break;
            }
        }
        return i;
    }

    private int getTraIndexUtil(int id) {
        int i = 0;
        for (; i < trainees.size(); i++) {
            if (trainees.get(i).getID() == id) {
                break;
            }
        }
        return i;
    }

}
