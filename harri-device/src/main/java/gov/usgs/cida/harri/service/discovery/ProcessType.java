package gov.usgs.cida.harri.service.discovery;

/**
 *
 * @author isuftin
 */
public enum ProcessType {
    TOMCAT("tomcat", "org.apache.catalina.startup.Bootstrap start"), 
    DJANGO("modwsgi", ""), 
    APACHE("apache", "");
    
    private String processName;
    private String psIdentifier;
    ProcessType(String processName, String psIdentifier) {
        this.processName = processName;
        this.psIdentifier = psIdentifier;
    }
    
    public String getName() {
        return this.processName;
    }
    
    @Override
    public String toString() {
        return this.processName;
    }

    public String getProcesssIdentifier() {
        return this.psIdentifier;
    }
}
