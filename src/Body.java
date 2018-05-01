import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import main.Constants;

public class Body extends SpaceObject {
	private Universe universe;
	private double radius;
	private double mass;
	
	private List<Body> pulled;
	
	public double getRadius() { return radius; }
	public double getMass() { return mass; }
	public Body(Universe universe, double radius, double mass) {
		super();
		this.universe = universe;
		this.radius = radius;
		this.mass = mass;
		pulled = new ArrayList<Body>();
	}
	public Body(Universe universe, double radius, double mass, XYR pos) {
		super(pos);
		this.universe = universe;
		this.radius = radius;
		this.mass = mass;
		pulled = new ArrayList<Body>();
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
				vel.inc(new XY().polarOffset(angle, speed));
			}
		}
	}
	public void update() {
		List<SpaceObject> objects = new ArrayList<>(universe.getObjects());
		objects.remove(this);
		pulled.clear();
		for(SpaceObject o : objects) {
			if(o instanceof Body) {
				Body b = (Body) o;
				XY posDifference = pos.difference(b.pos());
				double distance = posDifference.magnitude() * Constants.Units.KM_TO_M;
				double angle = posDifference.angle();
				double acceleration = Constants.GRAVITATIONAL * mass / (distance * distance) * Constants.Time.MULTIPLIER * Constants.Time.MULTIPLIER;
				//System.out.println("Acceleration: " + acceleration);
				b.vel().inc(new XY(acceleration * Math.cos(angle), acceleration * Math.sin(angle)));
				if(acceleration > 0.1) {
					pulled.add(b);
				}
			}
		}
		super.update();
		universe.addMarkers(new XY(pos));
	}
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.drawOval((int) (pos.x() - radius), (int) (pos.y() - radius), (int) (radius*2), (int) (radius*2));
	}
}
