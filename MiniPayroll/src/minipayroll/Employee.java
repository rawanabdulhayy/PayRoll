/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minipayroll;

/**
 *
 * @author solyc
 */
public abstract class Employee {
    private String name;
    private int ID;
    private int age;
    private double salary;
    private static int COUNT=0;
    public Employee(String name , int age){
        COUNT++;
        this.ID = COUNT;
        this.age = age;
        this.name = name;
        
    }

    @Override
    public String toString() {
        String output = "Name: " + name + "\n";
        output += "ID: " + String.valueOf(ID) + "\n";
        output += "Age: " + String.valueOf(age) + "\n";
        output += "Salary: " + String.valueOf(salary) + "\n";
        return output;
    }

    void edit(String newName, int newAge, double newSalary){
        this.name = newName;
        this.age = newAge;
        this.salary = newSalary;
    }
      
    public String getName() { return name; }
    public int getID() { return ID; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }

    public void setName(String name) { this.name = name; };
    public void setAge(int age) { this.age = age; };
    public void setSalary(double salary) { this.salary = salary; };
    
}
