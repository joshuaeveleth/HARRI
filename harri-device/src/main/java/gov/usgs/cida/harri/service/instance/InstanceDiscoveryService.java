package gov.usgs.cida.harri.service.instance;

import gov.usgs.cida.harri.service.discovery.ProcessDiscovery;
import gov.usgs.cida.harri.service.discovery.ProcessMD;
import gov.usgs.cida.harri.service.discovery.ProcessType;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleal.cling.binding.annotations.*;

@UpnpService(
        serviceId = @UpnpServiceId("InstanceDiscoveryService"),
        serviceType = @UpnpServiceType(value = "InstanceDiscoveryService", version = 1)
)

/**
 * @author thongsav
 */
public class InstanceDiscoveryService {
    private static Logger LOG = LoggerFactory.getLogger(InstanceDiscoveryService.class);
	
    @UpnpStateVariable(defaultValue = "no_id_provided")
    private String harriManagerId = "no_id_provided";
    
    @UpnpStateVariable(defaultValue = "")
    private String getAllTomcatInstancesResponse = "";
	
    @UpnpAction(out = @UpnpOutputArgument(name = "GetAllTomcatInstancesResponse"))
    public String getAllTomcatInstances(@UpnpInputArgument(name = "HarriManagerId")
                          String harriManagerId) {
    	this.harriManagerId = harriManagerId;
        LOG.info("GetAllInstances action called by HARRI Manager with ID: " + this.harriManagerId);
        
        List<ProcessMD> ps;
        try {
			ps = ProcessDiscovery.getProcesses();
		} catch (IOException e) {
			return getAllTomcatInstancesResponse;
		}
        
        for(ProcessMD p : ps) {
        	if(p.getType().equals(ProcessType.TOMCAT)) {
        		Tomcat tc = (Tomcat) p.createInstance();
        		tc.populate();
        		getAllTomcatInstancesResponse += 
        				p.getPid() + ":" + 
        				p.getStartupOptions().get("catalina.home")+":"+
        				tc.getHttpPort()+":"+
        				tc.getManagerUsername()+
        				"\n";
        	}
        }
        
        return getAllTomcatInstancesResponse;
    }
}