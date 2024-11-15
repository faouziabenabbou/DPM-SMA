import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class PrincipalContainer {
	public static void main(String[] args) {
		try {
			Runtime rt = Runtime.instance();
			Properties p = new ExtendedProperties();
			p.setProperty("gui","true");
			ProfileImpl impl = new ProfileImpl(p);
			AgentContainer container = rt.createMainContainer(impl);
			container.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}