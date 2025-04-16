import java.util.*;
import java.io.*;

class Student {
    private String name;
    private int rollNumber;
    private String grade;
    private String email;

    public Student(String name, int rollNumber, String grade, String email) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.email = email;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return rollNumber + "," + name + "," + grade + "," + email;
    }

    public String displayInfo() {
        return "Roll No: " + rollNumber + " | Name: " + name + " | Grade: " + grade + " | Email: " + email;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String filePath = "students.txt";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
    }

    public boolean removeStudent(int rollNumber) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student s = iterator.next();
            if (s.getRollNumber() == rollNumber) {
                iterator.remove();
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public Student searchStudent(int rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNumber) {
                return s;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s.displayInfo());
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[1];
                    int roll = Integer.parseInt(parts[0]);
                    String grade = parts[2];
                    String email = parts[3];
                    students.add(new Student(name, roll, grade, email));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing data to load.");
        }
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Student s : students) {
                pw.println(s.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        while (true) {
            System.out.println("\n==== Student Management System ====");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter roll number: ");
                    String rollInput = sc.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    if (name.isEmpty() || rollInput.isEmpty() || grade.isEmpty() || email.isEmpty()) {
                        System.out.println("All fields are required!");
                        break;
                    }

                    try {
                        int roll = Integer.parseInt(rollInput);
                        if (sms.searchStudent(roll) != null) {
                            System.out.println("Student with this roll number already exists.");
                            break;
                        }
                        Student student = new Student(name, roll, grade, email);
                        sms.addStudent(student);
                        System.out.println("Student added successfully.");
                    } catch (NumberFormatException e) {
                        System.out.println("Roll number must be numeric.");
                    }
                    break;

                case "2":
                    System.out.print("Enter roll number to remove: ");
                    try {
                        int roll = Integer.parseInt(sc.nextLine());
                        if (sms.removeStudent(roll)) {
                            System.out.println("Student removed.");
                        } else {
                            System.out.println("Student not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid roll number.");
                    }
                    break;

                case "3":
                    System.out.print("Enter roll number to search: ");
                    try {
                        int roll = Integer.parseInt(sc.nextLine());
                        Student found = sms.searchStudent(roll);
                        if (found != null) {
                            System.out.println(found.displayInfo());
                        } else {
                            System.out.println("Student not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid roll number.");
                    }
                    break;

                case "4":
                    sms.displayAllStudents();
                    break;

                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
