import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;

public class Results extends JFrame {
	
	//private AgentReservation agent;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Results frame = new Results();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Results(AgentReservation a) {
		//agent = a;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 153, 102));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel txtBienvenueDansSmart = new JLabel("Bienvenu sur SMART PARKING :");
		panel.add(txtBienvenueDansSmart);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 153, 102));
		contentPane.add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 153, 102));
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(51, 153, 102));
		contentPane.add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, BorderLayout.CENTER);
		
		JLabel lblInformationSurRservation = new JLabel("Information sur r\u00E9servation parking :");
		panel_4.add(lblInformationSurRservation);
		
		JLabel informtext = new JLabel("");
		panel_4.add(informtext);

		if (a.p!=null) {
			informtext.setText("Votre réservation dans le parking P"+a.p.id+" a été bien effectuée dans la region "+a.region+" avec le prix : "+a.p.prix+"DH.");
		}
	}

}
