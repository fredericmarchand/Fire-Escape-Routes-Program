/**Assignment#4: ExitPanel
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ExitPanel extends JPanel {

	// These are the tiles and buttons
	private JTextField		nameField;
	private JTextField		locationField;
	private JButton 		colorButton;

	public String getChosenName() { return nameField.getText(); }
	public Color getChosenColor() { return colorButton.getBackground(); }

	// This constructor builds the panel
	public ExitPanel(Exit exit) {
    	// Layout the components using a gridbag
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        setLayout(layout);

        JLabel aLabel = new JLabel("Name:");
        layoutConstraints.gridx = 0; layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1;
        layoutConstraints.fill = GridBagConstraints.NONE;
        layoutConstraints.insets = new Insets(5,5,5,5);
        layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
        layoutConstraints.weightx = 0.0; layoutConstraints.weighty = 0.0;
        layout.setConstraints(aLabel, layoutConstraints);
        add(aLabel);

        aLabel = new JLabel("Location:");
        layoutConstraints.gridy = 1;
        layout.setConstraints(aLabel, layoutConstraints);
        add(aLabel);

        aLabel = new JLabel("Color:");
        layoutConstraints.gridy = 2;
        layout.setConstraints(aLabel, layoutConstraints);
        add(aLabel);

		// Add the name field
        nameField = new JTextField(exit.getName());
        layoutConstraints.gridx = 1; layoutConstraints.gridy = 0;
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        layoutConstraints.weightx = 1.0;
        layout.setConstraints(nameField, layoutConstraints);
        add(nameField);

		// Add the location field

        locationField = new JTextField("("+exit.getLocation().x + " , " +
        	exit.getLocation().y + ")");
        locationField.setEditable(false);
        layoutConstraints.gridy = 1;
        layout.setConstraints(locationField, layoutConstraints);
        add(locationField);

		// Add the color button
        colorButton = new JButton();
        colorButton.setBackground(exit.getColor());
        layoutConstraints.gridy = 2;
        layoutConstraints.fill = GridBagConstraints.BOTH;
        layout.setConstraints(colorButton, layoutConstraints);
        add(colorButton);

        // Add a listener for the color button
        colorButton.addActionListener(new ActionListener() {
           	public void actionPerformed(ActionEvent e) {
           		JColorChooser chooser = new JColorChooser();
				try {
					Color chosen = chooser.showDialog(null, "Exit Color",
						colorButton.getBackground());
     				if (chosen != null)
     					colorButton.setBackground(chosen);
				}
				catch (HeadlessException ex) {}
            }
        });

     }

     public static void main(String args[]) {
     	JPanel d = new ExitPanel(new Exit(2, 7));
     	JFrame f = new JFrame();
     	f.getContentPane().add(d);
     	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	f.setSize(250,150);
     	f.setVisible(true);
     }
}