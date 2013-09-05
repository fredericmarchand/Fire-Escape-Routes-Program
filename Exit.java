/**Assignment#4: Exit
 *Name: Frédéric Marchand
 *Student Number: 100817579
 **/
import java.awt.Color;
import java.awt.Point;

public class Exit implements java.io.Serializable {
	private String 		name;
	private Color		color;
	private Point		location;

	public String  getName() { return name; }
	public Color   getColor() { return color; }
	public Point   getLocation() { return location; }

	public void setName(String newName) { name = newName; }
	public void setColor(Color newColor) { color = newColor; }
	public void setLocation(Point newLoc) { location = newLoc; }

	public Exit() {
		this(0,0);
	}

	public Exit(int r, int c) {
		name = "UNKNOWN";
		color = Color.red;
		location = new Point(r,c);
	}

	public String toString() {
		return name + " exit";
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Exit)) return false;
		Exit e = (Exit)obj;
		return ((e.name.equals(name)) && (e.location.equals(location)));
	}

	public boolean isAt(int r, int c) {
		return (location.x == r) && (location.y == c);
	}
}