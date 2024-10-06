/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minipayroll;

public class Engineer extends Employee {

    private int workingHours;
    private final Grade grade;

    Engineer(String name, int age, int workingHours, String position, double payRate, double taxRate) {
        super(name, age);
        this.grade = new Grade(position, payRate, taxRate);
        this.workingHours = workingHours;
        super.setSalary(getAdjustedSalary());
    }

    private double getAdjustedSalary() {
        return grade.payRate * workingHours * (1 - grade.taxRate);
    }

    @Override
    public String toString() {
        String output = super.toString();
        output += "Working hours: " + String.valueOf(workingHours) + "\n";
        output += "Geade details:-\n" + grade.toString();
        return output;
    }

    void edit(String newName, int newAge, int newWorkingHours, String newPosition, double newPayRate, double newTaxRate) {
        this.workingHours = newWorkingHours;
        this.grade.setPosition(newPosition, newPayRate, newTaxRate);
        super.edit(newName, newAge, getAdjustedSalary());
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public Grade getGrade() {
        return grade;
    }

}
