/**Assignment#4: SafetyMapView
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SafetyMapView extends JFrame {

	// These are the tiles and buttons
	private JPanel			tiles;
	private JButton[]		buttons;
	private JCheckBox 		editWallsButton;
	private JCheckBox 		selectExitsButton;
	private JButton			computeSafetyPlanButton;
	private JButton			resetSafetyPlanButton;

	// Menu items
	private JMenuItem				openItem;
	private JMenuItem				saveItem;
	private JMenuItem				exitItem;
	private JMenuItem 				editInfoItem;
	private JRadioButtonMenuItem	showItem;

	public JButton getFloorTileButton(int i) { return buttons[i]; }
	public JPanel getTilePanel() { return tiles; }
	public JCheckBox getEditWallsButton() { return editWallsButton; }
	public JCheckBox getSelectExitsButton() { return selectExitsButton; }
	public JButton getComputePlanButton(){ return computeSafetyPlanButton; }
	public JButton getResetButton(){ return resetSafetyPlanButton; }
	public JRadioButtonMenuItem getShowItem(){ return showItem; }

	// Methods for adding listeners to the open/save/distance options
	public void setOpenHandler(ActionListener x) {
		openItem.addActionListener(x); }
	public void setSaveHandler(ActionListener x) {
		saveItem.addActionListener(x); }
	public void setEditInfoHandler(ActionListener x) {
		editInfoItem.addActionListener(x); }

	// This constructor builds the panel
	public SafetyMapView(String title, FloorPlan floorPlan) {
		super(title);

		// Create the panel with the floor tiles on it
		createFloorPlanPanel(floorPlan);

		// Layout the rest of the window's components
        setupComponents();
        addMenus();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 500);
        setVisible(true);
    }


    // Create the panel to contain the buttons that display the floor plan
    private void createFloorPlanPanel(FloorPlan floorPlan) {
    	// Setup the panel with the buttons
        buttons = new JButton[floorPlan.size()*floorPlan.size()];
        tiles = new JPanel();
        tiles.setLayout(new GridLayout(floorPlan.size(),floorPlan.size()));

		// Add the buttons to the tile panel
        for (int r=0; r<floorPlan.size(); r++) {
        	for (int c=0; c<floorPlan.size(); c++) {
        		int i = r * floorPlan.size() + c;
        		buttons[i] = new JButton();
        		buttons[i].setBorder(BorderFactory.createLineBorder(Color.lightGray));
        		buttons[i].setBackground(Color.white);
        		tiles.add(buttons[i]);
          	}
        }
	}

    // Here we add all the components to the window accordingly
    private void setupComponents() {
    	// Layout the components using a gridbag
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        getContentPane().setLayout(layout);

        // Add the tiles
        layoutConstraints.gridx = 0; layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 6;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.insets = new Insets(2, 2, 2, 2);
        layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
        layoutConstraints.weightx = 1.0; layoutConstraints.weighty = 1.0;
        layout.setConstraints(tiles, layoutConstraints);
        getContentPane().add(tiles);

        JLabel aLabel = new JLabel("MODE:");
        layoutConstraints.gridx = 1; layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.NONE;
        layoutConstraints.insets = new Insets(2, 2, 2, 2);
        layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
        layoutConstraints.weightx = 0.0; layoutConstraints.weighty = 0.0;
        layout.setConstraints(aLabel, layoutConstraints);
        getContentPane().add(aLabel);

		// Add the EditWalls button
        editWallsButton = new JCheckBox("Edit Walls");
        layoutConstraints.gridx = 1; layoutConstraints.gridy = 1;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.insets = new Insets(2, 2, 2, 2);
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        layoutConstraints.weightx = 0.0; layoutConstraints.weighty = 0.0;
        layout.setConstraints(editWallsButton, layoutConstraints);
        getContentPane().add(editWallsButton);

		// Add the SelectExits button
        selectExitsButton = new JCheckBox("Select Exits");
        layoutConstraints.gridx = 1; layoutConstraints.gridy = 2;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.insets = new Insets(2, 2, 2, 2);
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        layoutConstraints.weightx = 0.0; layoutConstraints.weighty = 0.0;
        layout.setConstraints(selectExitsButton, layoutConstraints);
        getContentPane().add(selectExitsButton);

        //Add the compute label
        JLabel label2 = new JLabel("COMPUTE:");
        layoutConstraints.gridx = 1; layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.NONE;
        layoutConstraints.insets = new Insets(2, 2, 2, 2);
        layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
        layoutConstraints.weightx = 0.0; layoutConstraints.weighty = 0.0;
        layout.setConstraints(label2, layoutConstraints);
        getContentPane().add(label2);

		// Add the Compute button
        computeSafetyPlanButton = new JButton("Compute Safety Plan");
        layoutConstraints.gridx = 1; layoutConstraints.gridy = 4;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layoutConstraints.insets = new Insets(2, 2, 2, 2);
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        layoutConstraints.weightx = 0.0; layoutConstraints.weighty = 0.0;
        layout.setConstraints(computeSafetyPlanButton, layoutConstraints);
        getContentPane().add(computeSafetyPlanButton);

        //Add the reset button
        resetSafetyPlanButton = new JButton("Reset Safety Plan");
        layoutConstraints.gridx = 1; layoutConstraints.gridy = 5;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.insets = new Insets(2, 2, 2, 2);
        layoutConstraints.anchor = GridBagConstraints.NORTH;
        layoutConstraints.weightx = 0.0; layoutConstraints.weighty = 0.0;
        layout.setConstraints(resetSafetyPlanButton, layoutConstraints);
        getContentPane().add(resetSafetyPlanButton);

     }

     private void addMenus() {
     	JMenuBar menubar = new JMenuBar();
     	setJMenuBar(menubar);

     	// Make the file menu
     	JMenu file = new JMenu("File");
     	file.setMnemonic('F');
     	menubar.add(file);

     	openItem = new JMenuItem("Open Floor Plan");
     	saveItem = new JMenuItem("Save Floor Plan");
     	exitItem = new JMenuItem("Quit");
     	openItem.setMnemonic('O');
     	saveItem.setMnemonic('S');
     	exitItem.setMnemonic('Q');
     	file.add(openItem);
     	file.add(saveItem);
     	file.add(exitItem);
     	exitItem.addActionListener(
     		new ActionListener() {
     			public void actionPerformed(ActionEvent e) {
     				System.exit(0);
     			}});

     	// Make the options menu
     	JMenu view = new JMenu("Options");
     	view.setMnemonic('O');
     	menubar.add(view);

     	showItem = new JRadioButtonMenuItem("Show Distances");
     	showItem.setMnemonic('D');
     	view.add(showItem);

     	JMenu edit = new JMenu("Exits");
		edit.setMnemonic('E');
		view.add(edit);

		editInfoItem = new JMenuItem("Edit Info");
		editInfoItem.setMnemonic('E');
		edit.add(editInfoItem);
     }

     public static void main(String args[]) {
     	new SafetyMapView("Fire Safety Routes", FloorPlan.example1());
     }
}