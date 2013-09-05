/**Assignment#4: FloorPlanFilter
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
public class FloorPlanFilter extends javax.swing.filechooser.FileFilter {
	public boolean accept(java.io.File f) {
		return f.isDirectory() || f.getName().endsWith(".fpln");
	}

    public String getDescription() {
    	return "Floor Plans (.fpln)";
	}
}