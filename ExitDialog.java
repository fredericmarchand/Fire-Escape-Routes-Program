/**Assignment#4: ExitDialog
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ExitDialog  extends JDialog {
	// Store a pointer to the model for changes later
    private Exit exit;

    // The buttons and main panel
    private JButton   okButton;
    private JButton   cancelButton;
    private ExitPanel exitPanel;

    // The client (i.e. caller of this dialog box)
    private DialogClientInterface client;

    // A constructor that takes the model and client as parameters
    public ExitDialog(Frame owner, String title,
    				  DialogClientInterface cli, Exit ex){

    	super(owner, title, true);
        client = cli;
        exit = ex;

        // Make a panel with two buttons (placed side by side)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton = new JButton("OK"));
        buttonPanel.add(cancelButton = new JButton("CANCEL"));

        // Make the dialog box by adding the exit panel and the button panel
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        exitPanel  = new ExitPanel(exit);
        getContentPane().add(exitPanel);
        getContentPane().add(buttonPanel);
		setSize(250,150);
        setResizable(false);

        // Listen for ok button click
        okButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
            	okButtonClicked();
            }
        });

        // Listen for cancel button click
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                cancelButtonClicked();
            }
        });

        // Listen for window closing: treat like cancel button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                cancelButtonClicked();
            }
        });
	}

    private void okButtonClicked(){
    	// Update model to show changed name or color
        exit.setName(exitPanel.getChosenName());
        exit.setColor(exitPanel.getChosenColor());

        if (client != null) client.dialogFinished();
		dispose();
    }

    private void cancelButtonClicked(){
        if (client != null) client.dialogCancelled();
		dispose();
    }

	public static void main(String args[]) {
     	JDialog d = new ExitDialog(null, "Exit#3 Information", null, new Exit(2,7));
     	d.setVisible(true);
    }
}