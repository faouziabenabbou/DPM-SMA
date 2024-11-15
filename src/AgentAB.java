import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;


//s'enregistrer ams
//publie services dans df
//Cyclic 1 : attendre msg AR sans block
//reçoit liste AP
//envoie CFP
//Cyclic 2 : reçoit Propose place,prix/refuse
//  envoie Accept ou Refuse 
// reçoit confirmation
//envoi INFORM pour AR avec id parking

public class AgentAB extends Agent {
	DFAgentDescription[] results;
	AID AR=null;
	public void setup() {

		System.out.println("Démarrage de l'agent:"+this.getAID().getName());

		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Gestion de parking");
		sd.setName("broker park");
		template.addServices(sd);


		//ParallelBehaviour pb = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);

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
				
				try {
					Thread.sleep(8000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage request = myAgent.receive(mt);

				if ( request !=null ) {
					AR = request.getSender();
					
					DFAgentDescription afd = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("Gestion de parking");
					sd.setName("park");
					afd.addServices(sd);

					try {
						results = DFService.search(myAgent, afd);
						if (results != null) {
							ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
							cfp.setContent(request.getContent());
							System.out.println("a trouvé brokers suivants : ");
							for(int i=0;i<results.length;i++) {
								cfp.addReceiver(results[i].getName());
								System.out.println("broker"+ i+1 +"--:"+results[i].getName());
								myAgent.send(cfp);
							}
						} else block();
						  
					} catch (FIPAException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else block();
			}

		});

		addBehaviour(new CyclicBehaviour() {

			int nbProposals = 0;
			int nbRefuses = 0;
			AID bestParking =null;
			int bestprice = 0;
			int bestnbrplaces = 0;

			@Override
			public void action() {

				ACLMessage msg = myAgent.receive();
				if (msg != null) {
					int type = msg.getPerformative();
	
					switch(type) {
					case ACLMessage.REFUSE : 
						System.out.println("parking "+msg.getSender().getName()+" n'a pas de place");
						nbRefuses++;
						break;
					case ACLMessage.PROPOSE : 
						String content =msg.getContent();
						int places = Integer.parseInt(content.substring(0,content.indexOf(",")));
						int price = Integer.parseInt(content.substring(content.indexOf(",")+1));
						System.out.println(content);
						System.out.println(places);
						System.out.println(price);
	
						if ( bestParking == null || price<bestprice || (price == bestprice && places > bestnbrplaces) ) {
							bestParking = msg.getSender();
							bestprice = price;
							bestnbrplaces = places;
						}
						nbProposals++;
						if( nbProposals+nbRefuses >= results.length) {
							ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
							order.setContent("proposition acceptée");
							order.addReceiver(bestParking);
							myAgent.send(order);
							int i =0;
							while (i<results.length && results[i].getName().getName().equals(bestParking.getName())==false) i++;
							for (int j=i;j<results.length-1;j++) results[j]=results[j+1];
							ACLMessage refuse = new ACLMessage(ACLMessage.REFUSE);
							refuse.setContent("proposition refusé");
							for(i=0; i<results.length-1;i++)
								refuse.addReceiver(results[i].getName());
							myAgent.send(refuse);
						}
						if(nbRefuses==results.length) {
							ACLMessage failure = new ACLMessage(ACLMessage.FAILURE);
							failure.setContent("Aucun parking n'est vide");
							failure.addReceiver(AR);
							myAgent.send(failure);
						}
						break;
					case ACLMessage.CONFIRM :
						ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
						try {
							inform.setContentObject(msg.getContentObject());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnreadableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						inform.addReceiver(AR);
						myAgent.send(inform);
						break;
					case ACLMessage.DISCONFIRM :
						ACLMessage failure = new ACLMessage(ACLMessage.FAILURE);
						failure.setContent(msg.getContent());
						failure.addReceiver(AR);
						myAgent.send(failure);
						break;
					}
				}else block();
			}

		});

	//	addBehaviour(pb);
	}

	protected void takeDown() {
		System.out.println("Agent terminé");
	}

}
