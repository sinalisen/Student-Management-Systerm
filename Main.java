import java.util.*;
import java.io.*;

public class Main {
    private static Student[] students = new Student[100];
    private static int studentCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // while loop to continuously display the main menu until the user chooses to exit
        while (!exit) {
            System.out.println("\n----------------MENU----------------\n");
            System.out.println("1. Check available seats");
            System.out.println("2. Register student (with ID)");
            System.out.println("3. Delete student");
            System.out.println("4. Find student (with student ID)");
            System.out.println("5. Store student details into a file");
            System.out.println("6. Load student details from the file to the system");
            System.out.println("7. View the list of students based on their names");
            System.out.println("8. Add student details and generate reports");
            System.out.println("9. Exit\n");
            System.out.print("Please choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume a newline

                // This is the switch-case statement to handle different user choices
                switch (choice) {
                    case 1:
                        checkAvailableSeats();
                        break;
                    case 2:
                        registerStudent(scanner);
                        break;
                    case 3:
                        deleteStudent(scanner);
                        break;
                    case 4:
                        findStudent(scanner);
                        break;
                    case 5:
                        storeStudentDetails();
                        break;
                    case 6:
                        loadStudentDetails();
                        break;
                    case 7:
                        viewStudents();
                        break;
                    case 8:
                        addStudentDetailsMenu(scanner);
                        break;
                    case 9:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid input.");
                scanner.nextLine(); // Clear invalid input from the scanner
            }

        }

        scanner.close();
    }

    //This is the method to check available seats
    private static void checkAvailableSeats() {
        System.out.println("Available seats: " + (students.length - studentCount));
    }

    //This is the method to register a new student
    private static void registerStudent(Scanner scanner) {
        if (studentCount >= students.length) {
            System.out.println("No available seats.");
            return;
        }
        System.out.println("Enter student ID (8 characters):");
        String id = scanner.nextLine();
        while (id.length() != 8) {
            System.out.println("Student ID must be exactly 8 characters. Please re-enter:");
            id = scanner.nextLine();
        }

        students[studentCount++] = new Student(id);
        System.out.println("Student registered.");
    }

    //This is the method to delete a student
    private static void deleteStudent(Scanner scanner) {
        System.out.println("Enter student ID to delete:");
        String id = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId().equals(id)) {
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[--studentCount] = null;
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student ID not found.");
        } else {
            System.out.println("Student deleted.");
        }
    }

    //This is the method to find a student by ID
    private static void findStudent(Scanner scanner) {
        System.out.println("Enter student ID to find:");
        String id = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getStudentId().equals(id)) {
                System.out.println("Student ID: " + id + ", Name: " + students[i].getStudentName());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student ID not found.");
        }
    }

    //This is the method to store details to a file
    private static void storeStudentDetails() {
        try (PrintWriter writer = new PrintWriter(new File("students.txt"))) {
            for (int i = 0; i < studentCount; i++) {
                Student student = students[i];
                writer.print(student.getStudentId() + "," + student.getStudentName());
                for (Module module : student.getModules()){
                    writer.print("," + module.getModuleMark());
                }
                writer.println("," + student.getGrade());
            }
            System.out.println("Student details stored.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    //This is the method to load student details from a file
    private static void loadStudentDetails() {
        try (Scanner fileScanner = new Scanner(new File("students.txt"))) {
            studentCount = 0;
            while (fileScanner.hasNextLine() && studentCount < students.length) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                int[] marks =new int[3];
                for (int i = 0; i < 3; i++){
                    marks[i] = Integer.parseInt(parts[2 + i]);
                }
                Student student = new Student(id);
                student.setStudentName(name);
                for (int i = 0; i < 3; i++){
                    student.getModules()[i].setModuleMark(marks[i]);
                }
                students[studentCount++] = student;
            }
            System.out.println("Student details loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    //This is the method to view students based on their names
    private static void viewStudents() {
        String[] studentNames = new String[studentCount];
        for (int i = 0; i < studentCount; i++) {
           studentNames[i] = students[i].getStudentName();
        }
        Arrays.sort(studentNames, Comparator.nullsFirst(String::compareTo));
        for (String name : studentNames) {
            for (int i = 0; i < studentCount; i++) {
                if (name != null && name.equals(students[i].getStudentName())) {
                    System.out.println("Student ID: " + students[i].getStudentId() + ", Name: " + name);
                }
            }
        }
    }

    //Submenu to add student details and generate reports
    private static void addStudentDetailsMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--------Add Student Details--------\n");
            System.out.println("a. Add student name");
            System.out.println("b. Add module marks 1, 2, and 3");
            System.out.println("c. Generate summary");
            System.out.println("d. Generate complete report");
            System.out.println("e. Back to main menu\n");
            System.out.print("Please choose an option: ");

            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    addStudentName(scanner);
                    break;
                case "b":
                    addModuleMarks(scanner);
                    break;
                case "c":
                    generateSummary();
                    break;
                case "d":
                    generateCompleteReport();
                    break;
                case "e":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //This is the method to add a student name
    private static void addStudentName(Scanner scanner) {
        System.out.println("Enter student ID (8 characters):");
        String id = scanner.nextLine();
        while (id.length() != 8) {
            System.out.println("Student ID must be exactly 8 characters. Please re-enter:");
            id = scanner.nextLine();
        }
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student ID not found.");
            return;
        }

        System.out.println("Enter student name:");
        String name = scanner.nextLine();
        student.setStudentName(name);
        System.out.println("Student name added.");
    }

    //This is the method to add module marks
    private static void addModuleMarks(Scanner scanner) {
        System.out.println("Enter student ID (8 characters):");
        String id = scanner.nextLine();
        while (id.length() != 8) {
            System.out.println("Student ID must be exactly 8 characters. Please re-enter:");
            id = scanner.nextLine();
        }
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student ID not found.");
            return;
        }

        Module[] modules = student.getModules();
        for (int i = 0; i < modules.length; i++) {
            System.out.println("Enter marks for " + modules[i].getModuleName() + ":");
            int marks = scanner.nextInt();
            scanner.nextLine(); // Consume a newline
            while (marks < 0 || marks > 100) {
                System.out.println("Marks should be between 0 and 100. Please re-enter:");
                marks = scanner.nextInt();
                scanner.nextLine(); // Consume a newline
            }
            modules[i].setModuleMark(marks);
        }

        student.setModules(modules);
        System.out.println("Module marks added.");
    }

    private static Student findStudentById(String id){
        for (int i = 0; i < studentCount; i++){
            if (students[i].getStudentId().equals(id)){
                return students[i];
            }
        }
        return null;
    }

    //This is the method to generate a summary report
    private static void generateSummary() {
        int passCount = 0;
        int failCount = 0;
        int module1PassCount = 0;
        int module2PassCount = 0;
        int module3PassCount = 0;
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            int[] moduleMarks = Arrays.stream(student.getModules())
                    .mapToInt(Module::getModuleMark)
                    .toArray();
            if (moduleMarks.length == 3) {
                int total = Arrays.stream(moduleMarks).sum();
                double average = total / 3.0;
                if (average >= 40) {
                    passCount++;
                } else {
                    failCount++;
                }
                if (moduleMarks[0] >= 40) module1PassCount++;
                if (moduleMarks[1] >= 40) module2PassCount++;
                if (moduleMarks[2] >= 40) module3PassCount++;
            } else {
                System.out.println("\nError: Student with ID " + student.getStudentId() + " does not have valid module marks.");
            }
        }
        System.out.println("\nSummary:");
        System.out.println("Total student registrations: " + studentCount);
        System.out.println("Number of students passed: " + passCount);
        System.out.println("Number of students failed: " + failCount+"\n");
        System.out.println("Number of students who scored more than 40 marks in Module 1: " + module1PassCount);
        System.out.println("Number of students who scored more than 40 marks in Module 2: " + module2PassCount);
        System.out.println("Number of students who scored more than 40 marks in Module 3: " + module3PassCount);
    }

    //This is the method to generate a complete report
    private static void generateCompleteReport() {
        System.out.println("Generating complete report...");
        sortStudentsByAverageMarks();
        System.out.println("\nComplete Report:");
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            System.out.println("Student ID: " + student.getStudentId() + ", Name: " + student.getStudentName());
            int[] moduleMarks = Arrays.stream(student.getModules()).mapToInt(Module::getModuleMark).toArray();
            if (moduleMarks.length == 3) {
                int total = Arrays.stream(moduleMarks).sum();
                double average = total / 3.0;
                System.out.println("Module Marks: " + Arrays.toString(moduleMarks));
                System.out.println("Total: " + total + ", Average: " + average + ", Grade: " + student.getGrade()+"\n");
            } else {
                System.out.println("Error: Incorrect number of module marks for student ID: " + student.getStudentId()+"\n");
            }
        }
    }

    //This is the method to sort students by average marks
    private static void sortStudentsByAverageMarks() {
        Arrays.sort(students, 0, studentCount, (s1, s2) -> {
            double avg1 = Arrays.stream(s1.getModules()).mapToInt(Module::getModuleMark).average().orElse(0.0);
            double avg2 = Arrays.stream(s2.getModules()).mapToInt(Module::getModuleMark).average().orElse(0.0);
            return Double.compare(avg2, avg1);
        });
    }
}

