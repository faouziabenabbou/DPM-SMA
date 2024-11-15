import java.util.Map;

import dao.regionDAO;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import model.Parking;

//1. S'enregistrer dans l'AMS
//2. Publie ses services dans DF
//3. Request Park Place 

public class AgentReservation extends GuiAgent {
	protected ReservationGUI gui;
	protected Results resultsgui;
	Parking p;
	String region;
	String err;

	protected void setup() {

		DFAgentDescription afd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Gestion de parking");
		sd.setName("reservation park");
		afd.addServices(sd);

		try {
			DFService.register(this, afd);
			System.out.println(getLocalName()+" Enregistrement dans l'annuaire DF");
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gui = new ReservationGUI(this);
		gui.getFrame().setVisible(true);


		addBehaviour(new Behaviour() {
			Boolean foundAR= false;

			public void action() {

				try {
					Thread.sleep(8000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
				request.setContent(Integer.toString(regionDAO.getIdregion(region)));
				DFAgentDescription brafd = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("Gestion de parking");
				sd.setName("broker park");
				brafd.addServices(sd);
				DFAgentDescription[] results;

				try {
					results = DFService.search(myAgent, brafd);
					if (results.length>0) {System.out.println("a trouvé brokers suivants : ");

					for (int i=0;i<results.length;i++) {
						request.addReceiver(results[i].getName());
						System.out.println("broker"+ i+1 +"--:"+results[i].getName());
					}
					foundAR = true;
					myAgent.send(request);
					}

				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			public boolean done() {
				return foundAR;
			}

		});
		
		addBehaviour(new Behaviour() {
			
			@Override
			public boolean done() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void action() {
				
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ACLMessage reply = myAgent.receive();

				if (reply!=null) {
					if ( reply.getPerformative()==ACLMessage.INFORM) {
						try {
							p = (Parking)reply.getContentObject();
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if ( reply.getPerformative()==ACLMessage.FAILURE) {
						err=reply.getContent();
					}
					resultsgui = new Results((AgentReservation) myAgent);
					resultsgui.setVisible(true);
				} else block();
				
			}
		});
	}
	@Override
	protected void onGuiEvent(GuiEvent ge) {
		System.out.println("on gui event");

		Map<String,Object> params = (Map<String, Object>) ge.getParameter(0);
		region=(String)params.get("region");
		ContainerAP container = new ContainerAP(region);

		gui.getFrame().setVisible(false);





	}

}
