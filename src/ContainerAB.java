import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ContainerAB {
public static void main(String[] args) {
		
		try {
			
			Runtime rt = Runtime.instance();
			
			ProfileImpl impl = new ProfileImpl(false);
			impl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			
			AgentContainer container = rt.createAgentContainer(impl);
			
			AgentController controller = container.createNewAgent("AgentBroker", AgentAB.class.getName(),new Object[] {});
			controller.start();
			
			System.out.println("======RUNNING Agent Reservateur ======");
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
