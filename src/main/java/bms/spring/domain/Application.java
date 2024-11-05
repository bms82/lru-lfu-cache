package bms.spring.domain;

public class Application {

    Long processId;
    String applicationName;
    Long memUsed;
    Long cpuUsed;

    public Application(Long processId, String applicationName) {
        this.processId = processId;
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return applicationName + " with PID: " + processId;
    }
}
