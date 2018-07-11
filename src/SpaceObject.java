import java.awt.Color;
import java.awt.Graphics2D;

/*
((2*(a-1))+(3*b))
2a1-*3b*+
 */
public class SpaceObject {
	XYR pos, vel;
	public SpaceObject() {
		pos(new XYR());
		vel(new XYR());
	}
	public SpaceObject(XYR pos) {
		this();
		pos(pos);
		
	}
	public SpaceObject(XYR pos, XYR vel) {
		this();
		pos(pos);
		vel(vel);
	}
	public SpaceObject(SpaceObject source) {
		pos(new XYR(source.pos));
		vel(new XYR(source.vel));
	}
	public XYR pos()			{ return pos; }
	public void pos(XYR pos)	{ this.pos = pos; }
	public XYR vel() 			{ return vel; }
	public void vel(XYR vel)	{ this.vel = vel; }
	public void update() {
		pos.inc(vel);
	}
	public void paint(Graphics2D g2D) {}
	public SpaceObject clone() {
		return new SpaceObject(this);
	}
	public SpaceObject parallel(Universe universe) {
		return new SpaceObject(this);
	}
	
}
