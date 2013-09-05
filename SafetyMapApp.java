/**Assignment#4: SafetyMapApp
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

// This is the main application, which is the controller
public class SafetyMapApp implements DialogClientInterface {

	// The different "modes" that the application may be in at any time
	public static final int	WAIT_MODE = 0;
	public static final int	EDIT_WALL_MODE = 1;
	public static final int	SELECT_EXITS_MODE = 2;
	public static final int FLOOR_COMPUTED_MODE = 3;//new mode for when the floor is computed

	// These are the model-specific variables
	private FloorPlan			floorPlan;		// this is for convenience, although it is also stored in the planner
	private int					size;			// this is for convenience, although it is also stored in the floorPlan
	private int					mode; 			// keep track of the mode we are in
	private Point	 			selectedExit; 	// Exit on which popup menu was made
	private ArrayList<Point>	path;

	// This is the VIEW which we are controlling
	private SafetyMapView  	view;

	// Here are the pop-up menu instance variables
	JPopupMenu popupMenu;
    JMenuItem  editItem;
    JMenuItem  removeItem;

    private SafetyPlanner planner;

	// This constructor builds the window
	public SafetyMapApp() {
		// Create a new FloorPlan
		floorPlan = FloorPlan.example1();
		size = floorPlan.size();
		mode = WAIT_MODE;

		// Create a view
		view = new SafetyMapView("Fire Safety Routes", floorPlan);

        // Add the appropriate listeners and the popup menu
        setupPopupMenu();
        setupListeners();

		planner = new SafetyPlanner(floorPlan);

        // Refresh the screen, we're ready to go
		update();
    }

    // Return a point indicating the row and column of a button in the grid
    private Point getGridLocation(AWTEvent e) {
    	for (int i=0; i<size*size; i++) {
            if (e.getSource() == view.getFloorTileButton(i))
            	return new Point(i/size, i%size);
        }
		return null;
    }

    // Setup all appropriate listeners
    private void setupListeners() {

    	// Listener for all button clicks
    	ActionListener butList = new ActionListener() {
           	public void actionPerformed(ActionEvent e) {
           		handleTileSelection(e);
           		handlePathing(e);
            }
        };

        // Listener for the popup menu on each button
        MouseListener mouseList = new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                	Point p = getGridLocation(e);
                	if ((p!= null) && floorPlan.hasExitAt(p.x,p.y)) {
                		selectedExit = p;
                    	popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        };

        for (int r=0; r<size; r++) {
        	for (int c=0; c<size; c++) {
        		int i = r * size + c;
        		view.getFloorTileButton(i).addMouseListener(mouseList);
        		view.getFloorTileButton(i).addActionListener(butList);
          	}
        }

        // Now the other Buttons
        view.getEditWallsButton().addActionListener(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			editWalls();
            	}
        	});
        view.getSelectExitsButton().addActionListener(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			selectExits();
            	}
        	});
        view.getComputePlanButton().addActionListener(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			computeSafetyPlan();
            	}
        	});
        view.getResetButton().addActionListener(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			reset();
            	}
        	});
        view.setOpenHandler(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			openFloorPlan();
            	}
        	});
        view.setSaveHandler(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			saveFloorPlan();
            	}
        	});
        view.setEditInfoHandler(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			showExitsDialog();
            	}
        	});

        // Setup Listeners for the popup menu options
        editItem.addActionListener(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			editExit();
            	}
        	});
        removeItem.addActionListener(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			removeExit();
            	}
        	});

        view.getShowItem().addActionListener(new ActionListener() {
           		public void actionPerformed(ActionEvent e) {
           			update();
            	}
        	});
		view.getShowItem().setSelected(false);
       	view.getResetButton().setEnabled(false);
    }

    // Setup the popup menu
    private void setupPopupMenu() {
    	popupMenu = new JPopupMenu();
    	editItem = new JMenuItem("Edit");
    	removeItem = new JMenuItem("Remove");
		popupMenu.add(editItem);
		popupMenu.add(removeItem);
    }

    private void handlePathing(ActionEvent e){//this method is used when a button is clicked when the floor is computed
    	Point p = getGridLocation(e);
		if (p == null) return;
		int a = p.x;
		int b = p.y;
    	if(mode == FLOOR_COMPUTED_MODE){
    		path = planner.pathFrom(a, b);
    		for (int r=0; r<size; r++) {
        		for (int c=0; c<size; c++) {
        			JButton aButton = view.getFloorTileButton(r*size + c);
        			for(Point l: path){
        				if(r == l.x && c == l.y)
							aButton.setBackground(Color.white);
        			}
        		}
    		}
    	}
    }

    private void computeSafetyPlan(){//this method is used to compute the safety plan
    	mode = FLOOR_COMPUTED_MODE;
    	planner.resetDistances();
    	for(Exit e: floorPlan.getExits())
			planner.propagateFrom(e.getLocation().x, e.getLocation().y, floorPlan.getExits().indexOf(e));

		view.getResetButton().setEnabled(true);
		view.getComputePlanButton().setEnabled(false);
		update();
    }

    private void reset(){//this method resets all locations on the floorplan
    	mode = WAIT_MODE;
		planner.resetDistances();
		view.getResetButton().setEnabled(false);
		view.getComputePlanButton().setEnabled(true);
		update();
    }

	// Handle the EditWalls button press
	private void editWalls() {
     	if (mode == EDIT_WALL_MODE)
     		mode = WAIT_MODE;
     	else
     		mode = EDIT_WALL_MODE;
     	update();
	}

	// Handle the EditWalls button press
	private void selectExits() {
     	if (mode == SELECT_EXITS_MODE)
     		mode = WAIT_MODE;
     	else
     		mode = SELECT_EXITS_MODE;
     	update();
	}

	// Handle a remove from the popup menu
	private void removeExit() {
		if (JOptionPane.showConfirmDialog(null,
				"Are you sure you want to remove this exit ?",
            	"Confirm Please ...",
            	JOptionPane.YES_NO_OPTION) == 0) {
	        floorPlan.removeExit(selectedExit.x, selectedExit.y);
			update();
		}
	}

	// Handle an edit from the popup menu
	private void editExit() {
		Exit ex = floorPlan.exitAt(selectedExit.x, selectedExit.y);
		ExitDialog dialog =  new ExitDialog(view,
			"Exit#" + floorPlan.getExits().indexOf(ex) + " Information",
			this, ex);
		dialog.setVisible(true);
	}

	// Handle the Edit Information option from the menubar
	private void showExitsDialog() {
		EditExitsDialog dialog = new EditExitsDialog(view, "Edit Exit Info",
			floorPlan.getExits());
		dialog.setVisible(true);
		update();
	}

	// Handle a Floor Tile Selection
	private void handleTileSelection(ActionEvent e) {
     	Point p = getGridLocation(e);
		if (p == null) return;
		int r = p.x;
		int c = p.y;
        switch(mode) {
        	case WAIT_MODE:
        		view.getSelectExitsButton().setEnabled(true);
				view.getEditWallsButton().setEnabled(true);
        		if (floorPlan.hasExitAt(r,c)) {
        			Exit exit = floorPlan.exitAt(r,c);
        			JOptionPane.showMessageDialog(view, exit.getName() + " exit at ("
        				+ exit.getLocation().x + ", " + exit.getLocation().y + ")",
        				"Exit #" + floorPlan.getExits().indexOf(exit),
        				JOptionPane.INFORMATION_MESSAGE);
        		}
        		break;
        	case EDIT_WALL_MODE:
        		if (floorPlan.wallAt(r,c))
        			floorPlan.setWallAt(r,c,false);
        		else
        			floorPlan.setWallAt(r,c,true);
        		break;
        	case SELECT_EXITS_MODE:
        		if (floorPlan.hasExitAt(r,c))
        			floorPlan.removeExit(r,c);
        		else
        			floorPlan.addExit(r,c);
        		break;
        	case FLOOR_COMPUTED_MODE://new mode
        		view.getSelectExitsButton().setEnabled(false);
        		view.getEditWallsButton().setEnabled(false);
				break;
        }
        update();
	}

	// Save the current floor plan to a file
	private void saveFloorPlan() {
		JFileChooser chooser = new JFileChooser(".");
		int status = chooser.showSaveDialog(view);
		if (status == JFileChooser.APPROVE_OPTION)
			floorPlan.saveTo(
				chooser.getCurrentDirectory().getAbsolutePath() +
				java.io.File.separator +
				chooser.getSelectedFile().getName());
	}

	// Load a floor plan from a file
	private void openFloorPlan() {
		JFileChooser chooser = new JFileChooser(".");
		chooser.setFileFilter(new FloorPlanFilter());
		int status = chooser.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			String fileName = chooser.getCurrentDirectory().getAbsolutePath() +
				java.io.File.separator +
				chooser.getSelectedFile().getName();
			FloorPlan fp = FloorPlan.loadFrom(fileName);
			if (fp == null)
				JOptionPane.showMessageDialog(view,
               		"Error Loading FloorPlan", "Error",
                    JOptionPane.ERROR_MESSAGE);
        	else {
        		planner = new SafetyPlanner(fp);
				floorPlan = fp;
				update();
			}
		}
	}

	// Update the panel containing the floor tiles
	private void updateTiles() {
     	// Now draw the tiles accordingly
        for (int r=0; r<size; r++) {
        	for (int c=0; c<size; c++) {
        		JButton aButton = view.getFloorTileButton(r*size + c);

        		// Set the text for the floor tile
        		//aButton.setText("");

        		// Set the backround color for the floor tile
        		if (floorPlan.wallAt(r,c))
        			aButton.setBackground(Color.gray);
        		else if (floorPlan.hasExitAt(r,c))
        			aButton.setBackground(floorPlan.exitAt(r,c).getColor());
        		else
        			aButton.setBackground(Color.white);
				//the following code has been added to update the look of all tiles for when the floorplan is computed
        		if(view.getShowItem().isSelected()){
        			aButton.setText(""+planner.getDistance(r, c));
        			if((floorPlan.wallAt(r, c))||((mode == FLOOR_COMPUTED_MODE)&&(!planner.hasBeenTraversed(r, c))))
        				aButton.setText("");
        			else if (!planner.hasBeenTraversed(r, c))
        				aButton.setText("0");
        			for(Exit e: floorPlan.getExits()){
        				if(((planner.getExitIndex(r, c) == floorPlan.getExits().indexOf(e))&&(!floorPlan.wallAt(r, c)))&&(planner.hasBeenTraversed(r, c)))
        					aButton.setBackground(e.getColor());
        			}
        		}
				else
        			aButton.setText("");
          	}
        }
     }

     private void update() {
     	// Disable everything, re-enable below as necessary
     	view.getEditWallsButton().setEnabled((mode == WAIT_MODE) || (mode == EDIT_WALL_MODE));
     	view.getSelectExitsButton().setEnabled((mode == WAIT_MODE) || (mode == SELECT_EXITS_MODE));

     	// Update the floor tile appearance accordingly
     	updateTiles();
     }

     public void dialogFinished() { update(); }
	 public void dialogCancelled() {}

     // This gets it all going
     public static void main(String[] args) {
          new SafetyMapApp();
     }
}