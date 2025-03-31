public class Module {
    private String moduleName;
    private int moduleMark;


    public Module(String moduleName) {
        this.moduleName = moduleName;
        this.moduleMark = 0;
    }

    // Getters and setters for moduleName, moduleMark

    public String getModuleName() {
        return moduleName;
    }


    public int getModuleMark() {
        return moduleMark;
    }


    public void setModuleMark(int moduleMark) {
        this.moduleMark = moduleMark;
    }
}