
public interface Cartesian {
	public double x();
	public double y();
	public void x(double x);
	public void y(double y);
	public default void incX(double x) { x(x() + x); }
	public default void incY(double y) { y(y() + y); }
	public default void scale(double scalar) {
		x(x() * scalar);
		y(y() * scalar);
	}
	
	public default double magnitude() {
		return Math.sqrt(x() * x() + y() * y());
	}
	public default double angle() {
		return Math.atan2(y(), x());
	}
	public default XY difference(Cartesian c) {
		return new XY(x() - c.x(), y() - c.y());
	}
	public default XY polarOffset(double angle, double magnitude) {
		return new XY(x() + magnitude * Math.cos(angle), y() + magnitude * Math.sin(angle));
	}
	public default void set(Cartesian c) {
		x(c.x());
		y(c.y());
	}
	public default void inc(Cartesian c) {
		incX(c.x());
		incY(c.y());
	}
	public default double angleTo(Cartesian c) {
		return c.difference(this).angle();
	}
	public default double angleFrom(Cartesian c) {
		return difference(c).angle();
	}
	public default double distance(Cartesian c) {
		return difference(c).magnitude();
	}
}
