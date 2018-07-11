import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import main.Constants;

public class Body extends SpaceObject {
	private Universe universe;
	private double radius;
	private double mass;
	
	public double getRadius() { return radius; }
	public double getMass() { return mass; }
	public Body(Universe universe, double radius, double mass) {
		super();
		this.universe = universe;
		this.radius = radius;
		this.mass = mass;
	}
	public Body(Universe universe, double radius, double mass, XYR pos) {
		super(pos);
		this.universe = universe;
		this.radius = radius;
		this.mass = mass;
	}
	public Body(Body source) {
		super(source);
		this.universe = source.universe;
		this.radius = source.radius;
		this.mass = source.mass;
	}
	public void initializeOrbits() {
		List<SpaceObject> objects = new ArrayList<>(universe.getObjects());
		objects.remove(this);
		for(SpaceObject o : objects) {
			if(o instanceof Body) {
				final double INITIAL_SPEED_ADJUST = 1 / (Math.PI * 10);
				
				Body b = (Body) o;
				XY posDifference = b.pos().difference(pos);
				double distance = posDifference.magnitude() * Constants.Units.KM_TO_M;
				double angle = posDifference.angle() + Math.PI/2;
				double speed = Math.sqrt(Constants.GRAVITATIONAL * b.getMass() / distance) * INITIAL_SPEED_ADJUST * Constants.Time.MULTIPLIER;
				System.out.println("Speed: " + speed);
				if(Double.isNaN(speed)) {
					System.out.println("Pos: " + pos.x() + ", " + pos.y());
					System.out.println("Difference: " + posDifference.x() + ", " + posDifference.y());
					System.out.println("Distance: " + distance);
					System.exit(0);
				}
				vel.inc(new XY().polarOffset(angle, speed));
			}
		}
	}
	public void update() {
		List<SpaceObject> objects = new ArrayList<>(universe.getObjects());
		objects.remove(this);
		for(SpaceObject o : objects) {
			if(o instanceof Body) {
				Body b = (Body) o;
				XY posDifference = pos.difference(b.pos());
				double distance = posDifference.magnitude() * Constants.Units.KM_TO_M;
				double angle = posDifference.angle();
				double acceleration = Constants.GRAVITATIONAL * mass / (distance * distance) * Constants.Time.MULTIPLIER * Constants.Time.MULTIPLIER;
				//System.out.println("Acceleration: " + acceleration);
				b.vel().inc(new XY(acceleration * Math.cos(angle), acceleration * Math.sin(angle)));
			}
		}
		super.update();
	}
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawOval((int) (pos.x() - radius), (int) (pos.y() - radius), (int) (radius*2), (int) (radius*2));
	}
	public Body clone() {
		return new Body(this);
	}
	public Body parallel(Universe universe) {
		Body clone = new Body(this);
		clone.universe = universe;
		return clone;
	}
}
