package src;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Dimension;
import java.io.File;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class UI {

	private JFrame frame;
	private JTextField txtImport;
	private JTextPane txtPnInfo;
	private JLabel lblTSS;
	private JTextField txtTSS;
	private JButton btnEstimateViaHr;
	private JLabel lblPower;
	private JTextField txtPower;
	private JButton btnCompute;
	private static FitTSSFixer app;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		app = new FitTSSFixer();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 596, 343);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnImport = new JButton("Import your .fit file");
		GridBagConstraints gbc_btnImport = new GridBagConstraints();
		gbc_btnImport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnImport.insets = new Insets(0, 0, 5, 5);
		gbc_btnImport.gridx = 0;
		gbc_btnImport.gridy = 0;
		panel.add(btnImport, gbc_btnImport);
		
		txtImport = new JTextField();
		txtImport.setEditable(false);
		GridBagConstraints gbc_txtImport = new GridBagConstraints();
		gbc_txtImport.gridwidth = 2;
		gbc_txtImport.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtImport.insets = new Insets(0, 0, 5, 0);
		gbc_txtImport.gridx = 1;
		gbc_txtImport.gridy = 0;
		panel.add(txtImport, gbc_txtImport);
		txtImport.setColumns(10);
		
		txtPnInfo = new JTextPane();
		txtPnInfo.setText("Please make sure you've previously filled out the config.properties file with your HR zones (if applicable) and FTP.\r\n\r\nYou can estimate TSS based on heart rate data, if you've got it.\r\n\r\nIf you have a good idea what your TSS was, and just want to add power data to your file, just enter your TSS manually, and leave average power blank (that'll be calculated for you).\r\n\r\nAlternatively, if you have a good idea what your average power was, leave the TSS field blank and enter your average power.\r\n\r\n");
		txtPnInfo.setEditable(false);
		GridBagConstraints gbc_txtPnInfo = new GridBagConstraints();
		gbc_txtPnInfo.insets = new Insets(0, 0, 5, 0);
		gbc_txtPnInfo.gridwidth = 3;
		gbc_txtPnInfo.fill = GridBagConstraints.BOTH;
		gbc_txtPnInfo.gridx = 0;
		gbc_txtPnInfo.gridy = 1;
		panel.add(txtPnInfo, gbc_txtPnInfo);
		
		lblTSS = new JLabel("TSS\r\n");
		lblTSS.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTSS = new GridBagConstraints();
		gbc_lblTSS.anchor = GridBagConstraints.EAST;
		gbc_lblTSS.insets = new Insets(0, 0, 5, 5);
		gbc_lblTSS.gridx = 0;
		gbc_lblTSS.gridy = 2;
		panel.add(lblTSS, gbc_lblTSS);
		
		txtTSS = new JTextField();
		txtTSS.setMinimumSize(new Dimension(199, 20));
		GridBagConstraints gbc_txtTSS = new GridBagConstraints();
		gbc_txtTSS.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTSS.insets = new Insets(0, 0, 5, 5);
		gbc_txtTSS.gridx = 1;
		gbc_txtTSS.gridy = 2;
		panel.add(txtTSS, gbc_txtTSS);
		txtTSS.setColumns(10);
		
		btnEstimateViaHr = new JButton("Estimate via HR");
		GridBagConstraints gbc_btnEstimateViaHr = new GridBagConstraints();
		gbc_btnEstimateViaHr.insets = new Insets(0, 0, 5, 0);
		gbc_btnEstimateViaHr.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEstimateViaHr.gridx = 2;
		gbc_btnEstimateViaHr.gridy = 2;
		panel.add(btnEstimateViaHr, gbc_btnEstimateViaHr);
		
		lblPower = new JLabel("Power");
		GridBagConstraints gbc_lblPower = new GridBagConstraints();
		gbc_lblPower.anchor = GridBagConstraints.EAST;
		gbc_lblPower.insets = new Insets(0, 0, 5, 5);
		gbc_lblPower.gridx = 0;
		gbc_lblPower.gridy = 3;
		panel.add(lblPower, gbc_lblPower);
		
		txtPower = new JTextField();
		GridBagConstraints gbc_txtPower = new GridBagConstraints();
		gbc_txtPower.insets = new Insets(0, 0, 5, 5);
		gbc_txtPower.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPower.gridx = 1;
		gbc_txtPower.gridy = 3;
		panel.add(txtPower, gbc_txtPower);
		txtPower.setColumns(10);
		
		btnCompute = new JButton("Add Power to your ride! ");
		GridBagConstraints gbc_btnCompute = new GridBagConstraints();
		gbc_btnCompute.gridwidth = 3;
		gbc_btnCompute.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCompute.insets = new Insets(0, 0, 0, 5);
		gbc_btnCompute.gridx = 0;
		gbc_btnCompute.gridy = 4;
		panel.add(btnCompute, gbc_btnCompute);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnEstimateViaHr, txtPnInfo, lblTSS, txtTSS, txtPower, lblPower, btnCompute, txtImport}));
		
    	btnImport.addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e)
    		{
    			final JFileChooser fc = new JFileChooser();
    			fc.setCurrentDirectory(new File("."));
    			fc.setDialogTitle("Select your .fit file to edit");
    			FileFilter fitFilter = new FileNameExtensionFilter("FIT File","fit");
    			fc.setFileFilter(fitFilter);
    			fc.showOpenDialog(btnImport);
    			if (fc.getSelectedFile() != null)
    			{
    				File inputFile = fc.getSelectedFile();
    				app.fileSelected(inputFile);
        			txtImport.setText(inputFile.getName());
        			txtTSS.setText("");
        			txtPower.setText("");
    			}
    		}
    	});
    	btnEstimateViaHr.addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e)
    		{
    			txtTSS.setText(app.calculateTSSFromHR() + "");
    			txtPower.setEnabled(false);
    		}
    	});
    	btnCompute.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e)
    		{
    			//If there is something in TSS, and nothing in power
    			if (txtPower.getText().isEmpty() && !txtTSS.getText().isEmpty())
    			{
    				//Calculate the average power, and send that back to the main
    				txtPower.setText(app.calculatePowerFromTSS(txtTSS.getText()) + "");
    				JOptionPane.showMessageDialog(frame,"Finished! File is located in the same directory as the jar you ran.");
    			}
    			//if there is nothing in TSS and something in power
    			else if(!txtPower.getText().isEmpty() && txtTSS.getText().isEmpty())
    			{
    				//just add a power value to the entire ride file
    				app.addPower(txtPower.getText());
    				JOptionPane.showMessageDialog(frame,"Finished! File is located in the same directory as the jar you ran.");
    			}
    			//if both fields have values...
    			else if (!txtPower.getText().isEmpty() && !txtTSS.getText().isEmpty()){
    				JOptionPane.showMessageDialog(frame,"You gave both a TSS and a Power - I'll assume you want the power in there.");
    				app.addPower(txtPower.getText());
    				JOptionPane.showMessageDialog(frame,"Finished! File is located in the same directory as the jar you ran.");
    			}
    			//if no fields have values...
    			else if (txtPower.getText().isEmpty() && txtTSS.getText().isEmpty()){
    				JOptionPane.showMessageDialog(frame,"Try putting in a power value or an estimated TSS value..");
    			}
    		
    		}
    	});
	}
}
