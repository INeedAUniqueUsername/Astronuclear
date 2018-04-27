import java.awt.Color;
import java.awt.Graphics2D;

/*
((2*(a-1))+(3*b))
2a1-*3b*+
 */
public abstract class SpaceObject {
	protected XYR pos, vel;
	public SpaceObject() {
		pos = new XYR();
		vel = new XYR();
	}
	public XYR pos() {
		return pos;
	}
	public XYR vel() {
		return vel;
	}
	public void update() {
		pos.inc(vel);
	}
	public abstract void paint(Graphics2D g2D);
}
