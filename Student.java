public class Student {
    private String studentId;
    private String studentName;
    private Module[] modules;
    private String grade;


    public Student(String studentId) {
        this.studentId = studentId;
        this.modules = new Module[3];
        for (int i = 0; i < 3; i++) {
            this.modules[i] = new Module("Module " + (i + 1));
        }
    }

    // Getter and setters for studentId, studentName, modules and grade

    public String getStudentId() {
        return studentId;
    }


    public String getStudentName() {
        return studentName;
    }


    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    public Module[] getModules() {
        return modules;
    }


    public void setModules(Module[] modules) {
        this.modules = modules;
        // Recalculate the grade when marks are set
        calculateGrade();
    }


    public String getGrade() {
        return grade;
    }

    // calculate the student's grade based on the average of module marks
    private void calculateGrade() {
        int total = 0;
        for (Module module : modules) {
            total += module.getModuleMark();
        }
        double average = total / 3.0;
        if (average >= 80) {
            grade = "Distinction";
        } else if (average >= 70) {
            grade = "Merit";
        } else if (average >= 40) {
            grade = "Pass";
        } else {
            grade = "Fail";
        }
    }
}