import java.util.Objects;

public class XYR implements Cartesian {
	private double x, y, r;
	public XYR(double x, double y, double r) {
		x(x);
		y(y);
		r(r);
	}
	public XYR(double x, double y) {
		this(x, y, 0);
	}
	public XYR() {
		this(0, 0, 0);
	}
	public XYR(XY...xys) {
		this();
		for(XY xy : xys) { inc(xy); }
	}
	public XYR(XYR... xyrs) {
		this();
		for(XYR xyr : xyrs) { inc(xyr); }
	}
	public void x(double x) { this.x = x; }
	public void y(double y) { this.y = y; }
	public void r(double r) { this.r = r; }
	public void incR(double r) { r(r() + r); }
	public double x() { return x; }
	public double y() { return y; }
	public double r() { return r; }
	
	public void set(XYR xyr) {
		Cartesian.super.set(xyr);
		r(xyr.r);
	}
	public void inc(XYR xyr) {
		Cartesian.super.inc(xyr);
		incR(xyr.r);
	}
	
	public XYR clone() {
		return new XYR(x, y, r);
	}
	public boolean equals(XYR xyr) {
		return x == xyr.x && y == xyr.y && r == xyr.r;
	}
	public int hashCode() {
		return Objects.hash(x, y, r);
	}
}


/*
	private double x, y, r;
	public XYR(double x, double y, double r) {
		x(x);
		y(y);
		r(r);
	}
	public XYR(double x, double y) {
		this(x, y, 0);
	}
	//Adds multiple
	public XYR(XYR... xyrs) {
		this(0, 0, 0);
		for(XYR xyr : xyrs) {
			inc(xyr);
		}
	}
	public void x(double x) { this.x = x; }
	public void y(double y) { this.y = y; }
	public void r(double r) { this.r = r; }
	public void incX(double x) { this.x += x; }
	public void incY(double y) { this.y += y; }
	public void incR(double R) { this.r += r; }
	public double x() { return x; }
	public double y() { return y; }
	public double r() { return r; }
	
	public void set(XYR xyr) {
		x(xyr.x);
		y(xyr.y);
		r(xyr.r);
	}
	public void inc(XYR xyr) {
		incX(xyr.x);
		incY(xyr.y);
		incR(xyr.r);
	}
	public XYR clone() {
		return new XYR(x, y, r);
	}
	public boolean equals(XYR xyr) {
		return x == xyr.x && y == xyr.y && r == xyr.r;
	}
	public int hashCode() {
		return Objects.hash(x, y, r);
	}
*/