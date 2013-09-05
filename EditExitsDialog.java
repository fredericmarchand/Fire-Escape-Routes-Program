/**Assignment#4: EditExitsDialog
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.util.ArrayList;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class EditExitsDialog extends JDialog {
	// The buttons and main panel
    private JButton   editButton;
    private JButton   doneButton;
    private JList	  exitList;

    private ArrayList exits;

    // A constructor that takes the model and client as parameters
    public EditExitsDialog(Frame owner, String title, ArrayList startingExits){

    	super(owner, title, true);
        exits = startingExits;

		JPanel buttons = new JPanel();
        buttons.add(editButton = new JButton("Edit"));
        buttons.add(doneButton = new JButton("Done"));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        exitList = new JList(new Vector(exits));
        JScrollPane scrollPane = new JScrollPane(exitList,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(scrollPane);
        getContentPane().add(buttons);

        setSize(180,200);
        setResizable(false);

        // Listen for JList double clicks
        exitList.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent event){
        		if (event.getClickCount() == 2)
        			handleExitEditing();
            }
        });

        // Listen for Edit button click
        editButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event){
        		handleExitEditing();
            }
        });

        // Listen for cancel button click
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                dispose();
            }
        });

        // Listen for window closing: treat like cancel button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                dispose();
            }
        });
	}

    private void handleExitEditing() {
    	int index = exitList.getSelectedIndex();
        if (index >= 0) {
            Exit ex = (Exit)exits.get(index);
			ExitDialog dialog =  new ExitDialog(null,
						"Exit#" + index + " Information",
						null, ex);
			dialog.setVisible(true);
			exitList.setListData(new Vector(exits));
			exitList.setSelectedIndex(index);
		}
    }

	public static void main(String args[]) {
     	JDialog d = new EditExitsDialog(null, "Edit Exit Info", FloorPlan.example1().getExits());
     	d.setVisible(true);
    }
}