package minipayroll;

public class Trainee extends Employee {

    String university;
    float GPA;
    String academicYear;

    Trainee(String name, int age, float GPA, String university, String acadedmicYear) {
        super(name, age);
        this.GPA = GPA;
        this.university = university;
        this.academicYear = acadedmicYear;
        super.setSalary(5000);
    }

    @Override
    public String toString() {
        String output = super.toString();
        output += "University: " + String.valueOf(university) + "\n";
        output += "GPA: " + String.valueOf(GPA) + "\n";
        output += "Academic year: " + String.valueOf(academicYear) + "\n";
        return output;
    }

    void edit(String newName, int newAge, String newUniversity, float newGPA, String newAcademicYear) {
        this.university = newUniversity;
        this.GPA = newGPA;
        this.academicYear = newAcademicYear;
        super.edit(newName, newAge, 5000);
    }

}
