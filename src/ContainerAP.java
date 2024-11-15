import java.util.List;

import dao.parkingDAO;
import dao.regionDAO;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import model.Parking;
import model.Region;

public class ContainerAP {
	Region region;
	public ContainerAP(String rg) {
		
		region = regionDAO.getregion(rg);
		List<Parking> p = parkingDAO.getParkingOfRegion(region.id);
		initialiseAgents(p);
	}
	
	public void initialiseAgents(List<Parking> p) {
		try {
			
			Runtime rt = Runtime.instance();
			
			ProfileImpl impl = new ProfileImpl(false);
			impl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			
			AgentContainer container = rt.createAgentContainer(impl);
			
			for (int i=0; i<p.size(); i++) {
				AgentController controller = container.createNewAgent("AP"+p.get(i).id, AgentParking.class.getName(),new Object[] {});
				controller.start();
				
				System.out.println("======RUNNING Agent Parking "+p.get(i).id+" ======");
				
			}
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}