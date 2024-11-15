import java.io.IOException;

import dao.parkingDAO;
import dao.regionDAO;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import model.Parking;

public class AgentParking extends Agent {
	
	public void setup() {

		System.out.println("Démarrage de l'agent:"+this.getAID().getName());
		
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Gestion de parking");
		sd.setName("park");
		template.addServices(sd);
		
		
		try {
			DFService.register(this, template);
			System.out.println(getLocalName()+" Enregistrement dans l'annuaire DF");
		} catch (FIPAException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		addBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				Parking p = new Parking();
				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
				ACLMessage cfp = myAgent.receive(mt);
				
				if ( cfp !=null ) {
					String s = myAgent.getName();
					int id = Integer.parseInt(s.substring(2,s.indexOf("@")));
					System.out.println(id);
					p = parkingDAO.getParking(id);
					
					if (p.nbrplacesdispo>0) {
						ACLMessage propose = new ACLMessage(ACLMessage.PROPOSE);
						propose.setContent(""+p.nbrplacesdispo+","+p.prix+"");
						propose.addReceiver(cfp.getSender());
						send(propose);
					}
					else {
						ACLMessage refuse = new ACLMessage(ACLMessage.REFUSE);
						refuse.setContent("Pas d'espace disponible");
						refuse.addReceiver(cfp.getSender());
						send(refuse);
					}
				} else {
					block();
				}
				ACLMessage reply = myAgent.receive();
				
				if (reply!=null) {
					
					String s = myAgent.getName();
					int id = Integer.parseInt(s.substring(2,s.indexOf("@")));
					System.out.println(id);
					p = parkingDAO.getParking(id);
					
					int type = reply.getPerformative();
					
					switch (type) {
					case ACLMessage.ACCEPT_PROPOSAL : 
						if (parkingDAO.updatePlacesDispo(p.id, p.nbrplacesdispo-1) ) {
							ACLMessage confirm =  new ACLMessage(ACLMessage.CONFIRM);
							try {
								confirm.setContentObject(p);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							confirm.addReceiver(reply.getSender());
							myAgent.send(confirm);
						} else {
							ACLMessage disconfirm =  new ACLMessage(ACLMessage.DISCONFIRM);
							disconfirm.setContent("erreur veuillez ressayer");
							disconfirm.addReceiver(reply.getSender());
							myAgent.send(disconfirm);
						}
					}
				}
			}
		});
	}
}
