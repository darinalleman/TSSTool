//TODO: DELETE THIS 
//			private void setZones() {
//				GridBagLayout layout = new GridBagLayout();
//    	    	GridBagConstraints c = new GridBagConstraints();
//    			JFrame hrDialog = new JFrame("Enter your heart rate zones");
//    			hrDialog.getContentPane().setLayout(layout);
//
//    			JLabel lblZone1 = new JLabel("Zone 1");
//    			c.gridx = 0;
//    			c.gridy = 0;
//    			hrDialog.getContentPane().add(lblZone1,c);
//    			JTextField txtZone1 = new JTextField(8);
//    			c.gridy++;
//    			hrDialog.getContentPane().add(txtZone1, c);
//    			
//    			JLabel lblZone2 = new JLabel("Zone 2");
//    			c.gridx++;
//    			c.gridy--;
//    			hrDialog.getContentPane().add(lblZone2,c);
//    			JTextField txtZone2 = new JTextField(8);
//    			c.gridy++;
//    			hrDialog.getContentPane().add(txtZone2, c);
//    			
//    			JLabel lblZone3 = new JLabel("Zone 3");
//    			c.gridx++;
//    			c.gridy--;
//    			hrDialog.getContentPane().add(lblZone3,c);
//    			JTextField txtZone3 = new JTextField(8);
//    			c.gridy++;
//    			hrDialog.getContentPane().add(txtZone3, c);
//    			
//    			JLabel lblZone4 = new JLabel("Zone 4");
//    			c.gridx++;
//    			c.gridy--;
//    			hrDialog.getContentPane().add(lblZone4,c);
//    			JTextField txtZone4 = new JTextField(8);
//    			c.gridy++;
//    			hrDialog.getContentPane().add(txtZone4, c);
//    			
//    			JLabel lblZone5 = new JLabel("Zone 5");
//    			c.gridx++;
//    			c.gridy--;
//    			hrDialog.getContentPane().add(lblZone5,c);
//    			JTextField txtZone5 = new JTextField(8);
//    			c.gridy++;
//    			hrDialog.getContentPane().add(txtZone5, c);
//    			
//    			JLabel lblFTP = new JLabel("FTP");
//    			c.gridx++;
//    			c.gridy--;
//    			hrDialog.getContentPane().add(lblFTP,c);
//    			JTextField txtFTP = new JTextField(8);
//    			c.gridy++;
//    			hrDialog.getContentPane().add(txtFTP, c);
//    			
//    			JButton bFinish = new JButton("Save Zones");
//    			c.gridx = 0;
//    			c.gridy++;
//    			c.fill = GridBagConstraints.HORIZONTAL;
//    			c.gridwidth = 5;
//    			hrDialog.getContentPane().add(bFinish, c);
//    			
//    			JLabel lblInfo = new JLabel("<html><center>Enter the end of each zone below the zone label. <br>For example, "
//    					+ "the end of zone 1 (start of zone 2) should go in the first box. <br>Your max HR should go in the last box."
//    					+ "<center></html>", SwingConstants.CENTER);
//    			c.gridx = 0;
//    			c.gridy++;
//    			c.fill = GridBagConstraints.HORIZONTAL;
//    			c.gridwidth = 5;
//    			hrDialog.getContentPane().add(lblInfo,c);
//
//    			hrDialog.pack();
//    			hrDialog.setVisible(true);
//    			
//    			bFinish.addActionListener(new ActionListener(){
//    	    		@Override
//    	    		public void actionPerformed(ActionEvent e)
//    	    		{
//    	    			//make sure all inputs are valid...
//    	    			if (Integer.valueOf(txtZone1.getText()) != null &&
//    	    					Integer.valueOf(txtZone2.getText()) != null &&
//    	    							Integer.valueOf(txtZone3.getText()) != null &&
//    	    									Integer.valueOf(txtZone4.getText()) != null &&
//    	    											Integer.valueOf(txtZone5.getText()) != null)
//    	    			{
//    	    				hrZones = new int[5];
//    	    				hrZones[0] = Integer.valueOf(txtZone1.getText());
//    	    				prop.setProperty("zone1", txtZone1.getText());
//    	    				hrZones[1] = Integer.valueOf(txtZone2.getText());
//    	    				prop.setProperty("zone2", txtZone2.getText());
//    	    				hrZones[2] = Integer.valueOf(txtZone3.getText());
//    	    				prop.setProperty("zone3", txtZone3.getText());
//    	    				hrZones[3] = Integer.valueOf(txtZone4.getText());
//    	    				prop.setProperty("zone4", txtZone4.getText());
//    	    				hrZones[4] = Integer.valueOf(txtZone5.getText());
//    	    				prop.setProperty("zone5", txtZone5.getText());
//    	    				prop.setProperty("zone5", txtFTP.getText());
//    	    				prop.setProperty("zonessetup", "true");
//    	    				try {
//    	    					configOutput = new FileOutputStream("config.properties");
//								prop.store(configOutput, null);
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							} finally {
//								if (configOutput != null) {
//									try {
//										configOutput.close();
//									} catch (IOException e2) {
//										e2.printStackTrace();
//									}
//								}
//							}
//    	    				    	    				
//    	    				calculateTSSFromHR();
//
//    	    				tssInput.setText(Integer.toString(estimatedTSS));
//    	    				tssInput.setEditable(false);
//    	    				hrDialog.dispose();
//    	    				//tssLabel.setText("TSS");
//    	    				ftpLabel.setText("FTP");
//    	    				ftpInput.setText(txtFTP.getText());
//    	    			}
//    	    			else{
//    	    				
//    	    			}
//    	    		}
//    			});
//			}