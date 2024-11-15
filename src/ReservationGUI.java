import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dao.regionDAO;
import jade.gui.GuiEvent;
import model.Region;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ReservationGUI {
	
	private AgentReservation agent;
	private JFrame frmSmartParking;
	private JComboBox<String> comboBox;
	private JButton btnRechercher;
	private JLabel lblReservationParking;
	private JLabel txtRgion;
	private JPanel panel_1;
	private JPanel panel_4;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ReservationGUI window = new ReservationGUI();
//					window.frmSmartParking.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public ReservationGUI(AgentReservation a) {
	agent = a;
		initialize();
		
	}
	public JFrame getFrame() {
		return frmSmartParking;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSmartParking = new JFrame();
		frmSmartParking.setForeground(new Color(102, 204, 153));
		frmSmartParking.getContentPane().setBackground(new Color(0, 153, 102));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		frmSmartParking.getContentPane().add(panel, BorderLayout.NORTH);
		
		lblReservationParking = new JLabel("Reservation Parking");
		lblReservationParking.setForeground(new Color(255, 255, 255));
		panel.add(lblReservationParking);
		
		btnRechercher = new JButton("Rechercher");
		btnRechercher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRechercher.setBackground(new Color(0, 0, 0));
		btnRechercher.setForeground(new Color(255, 255, 255));
		frmSmartParking.getContentPane().add(btnRechercher, BorderLayout.SOUTH);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 153, 102));
		frmSmartParking.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		txtRgion = new JLabel("R\u00E9gion : ");
		panel_1.add(txtRgion);
		txtRgion.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_4 = new JPanel();
		panel_4.setBackground(new Color(51, 153, 102));
		panel_4.setForeground(new Color(51, 153, 102));
		panel_1.add(panel_4);
		comboBox = new JComboBox<String>();
		panel_1.add(comboBox);
		comboBox.setForeground(new Color(0, 0, 0));
		comboBox.setBackground(new Color(240, 255, 240));
		
		List<Region> listregions = regionDAO.getRegions();
		for (int i=0 ; i < listregions.size(); i++) {
			System.out.println(listregions.get(i).nom);
			comboBox.addItem(listregions.get(i).nom);
		}
		
		btnRechercher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent ae) {
				if (ae.getSource() == btnRechercher) {
					
					GuiEvent ge = new GuiEvent(this, 1);
					Map<String,Object> params =  new HashMap<>();
					params.put("region", comboBox.getSelectedItem());
					ge.addParameter(params);
					agent.onGuiEvent(ge);
				}
			}
		});
		
		frmSmartParking.setTitle("SMART PARKING ");
		frmSmartParking.setBackground(Color.PINK);
		frmSmartParking.setBounds(100, 100, 334, 209);
		frmSmartParking.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	


}
