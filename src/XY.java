import java.util.Objects;

public class XY implements Cartesian {
	private double x, y;
	public XY(double x, double y) {
		x(x);
		y(y);
	}
	//Adds multiple
	public XY(Cartesian... cs) {
		this(0, 0);
		for(Cartesian c : cs) {
			inc(c);
		}
	}
	public void x(double x) { this.x = x; }
	public void y(double y) { this.y = y; }
	public double x() { return x; }
	public double y() { return y; }
	
	public XY clone() {
		return new XY(x, y);
	}
	public boolean equals(XY xy) {
		return x == xy.x && y == xy.y;
	}
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
