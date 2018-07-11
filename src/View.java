import static main.Constants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import main.Constants;
public class View extends JPanel implements KeyListener, MouseListener {
	private Universe universe;
	private XY pov_pos;
	private XY pov_vel;
	private SpaceObject pov_object;
	private double km_per_pixel;
	private double km_per_pixel_inc;
	private boolean[] pressed;
	
	private int tick = 0;
	
	private Body sun, mercury, venus, earth, moon, mars, jupiter, saturn, uranus, neptune, pluto;
	private Body[] solarsystem;
	
	private final Font font = new Font("Monospaced", 0, 12);
	public View() {
		pov_pos = new XY();
		pov_vel = new XY();
		universe = new Universe();
		sun = new Body(universe, Radius.SUN, Mass.SUN);
		mercury = new Body(	universe,	Radius.MERCURY, Mass.MERCURY,	new XYR(Separation.Sun.MERCURY, 0));
		venus = new Body(	universe,	Radius.VENUS,	Mass.VENUS,		new XYR(Separation.Sun.VENUS, 0));
		earth = new Body(	universe,	Radius.EARTH,	Mass.EARTH,		new XYR(Separation.Sun.EARTH, 0));
		moon = new Body(	universe,	Radius.MOON,	Mass.MOON,		new XYR(earth.pos().polarOffset(0, Separation.Earth.MOON)));
		mars = new Body(	universe,	Radius.MARS,	Mass.MARS,		new XYR(Separation.Sun.MARS, 0));
		jupiter = new Body(	universe,	Radius.JUPITER, Mass.JUPITER,	new XYR(Separation.Sun.JUPITER, 0));
		saturn = new Body(	universe,	Radius.SATURN,	Mass.SATURN,	new XYR(Separation.Sun.SATURN, 0));
		uranus = new Body(	universe,	Radius.URANUS,	Mass.URANUS,	new XYR(Separation.Sun.URANUS, 0));
		neptune = new Body(	universe,	Radius.NEPTUNE, Mass.NEPTUNE,	new XYR(Separation.Sun.NEPTUNE, 0));
		pluto = new Body(	universe,	Radius.PLUTO,	Mass.PLUTO,		new XYR(Separation.Sun.PLUTO, 0));
		
		System.out.println(earth.pos.x());
		solarsystem = new Body[] { sun, mercury, venus, earth, moon, mars, jupiter, saturn, uranus, neptune, pluto };
		universe.createObjects(solarsystem);
		universe.update();
		for(Body b : solarsystem) {
			b.initializeOrbits();
		}
		addKeyListener(this);
		addMouseListener(this);
		km_per_pixel = Constants.Radius.SUN / 10;
		km_per_pixel_inc = 0;
		pressed = new boolean[100];
	}
	public void paintComponent(Graphics g) {
		update();
		
		Graphics2D g2D = (Graphics2D) g;
		paintBackground(g2D);
		
		AffineTransform gTransform = g2D.getTransform();
		transformGraphics(g2D);
		paintUniverse(g2D);
		g2D.setTransform(gTransform);
		
		paintDisplay(g2D);
		
		repaint();
	}
	private void paintBackground(Graphics2D g2D) {
		g2D.setBackground(Color.BLACK);
		g2D.clearRect(0, 0, getWidth(), getHeight());
	}
	private void paintDisplay(Graphics2D g2D) {
		g2D.setColor(new Color(255, 255, 255, 256 * 3 / 4));
		
		g2D.drawLine(Window.SCREEN_CENTER_X - 16, Window.SCREEN_CENTER_Y, Window.SCREEN_CENTER_X + 16, Window.SCREEN_CENTER_Y);
		g2D.drawLine(Window.SCREEN_CENTER_X, Window.SCREEN_CENTER_Y - 16, Window.SCREEN_CENTER_X, Window.SCREEN_CENTER_Y + 16);
		
		g2D.draw(new Rectangle(16, 16, 128, 1));
		g2D.drawString("Zoom Scale: " + new DecimalFormat("#.##").format(km_per_pixel * 128) + "km", 16, 32);
		g2D.drawString("Zoom Speed: " + new DecimalFormat("#.##").format(km_per_pixel_inc * 30) + "km/frame", 16, 48);
		
		paintClickBox(g2D, sun, "Sun");
		paintClickBox(g2D, mercury, "Mercury");
		paintClickBox(g2D, venus, "Venus");
		paintClickBox(g2D, earth, "Earth");
		paintClickBox(g2D, moon, "Moon");
		paintClickBox(g2D, mars, "Mars");
		paintClickBox(g2D, jupiter, "Jupiter");
		paintClickBox(g2D, saturn, "Saturn");
		paintClickBox(g2D, uranus, "Uranus");
		paintClickBox(g2D, neptune, "Neptune");
		paintClickBox(g2D, pluto, "Pluto");
		
		//g2D.drawLine((int) (earth_draw_pos.x() - earth_draw_radius), (int) (earth_draw_pos.y() - earth_draw_radius), (int) (earth_draw_pos.x() + earth_draw_radius), (int) (earth_draw_pos.y() + earth_draw_radius));
		
	}
	private void paintClickBox(Graphics2D g2D, Body b, String name) {
		AffineTransform gTransform = g2D.getTransform();
		transformGraphics(g2D);
		g2D.scale(1, -1);
		g2D.scale(km_per_pixel, km_per_pixel);

		XY earth_pos_draw = new XY(b.pos());
		earth_pos_draw.scale(1 / km_per_pixel);
		//double earth_draw_radius = earth.getRadius() / km_per_pixel;
		AffineTransform flip = new AffineTransform();
		flip.scale(1, -1);
		
		//int boxSize = (int) Math.max(16, 6 * b.getRadius() / km_per_pixel);
		float fontSize = (float) Math.max(16, b.getRadius() / km_per_pixel);
		g2D.setFont(font.deriveFont(fontSize).deriveFont(flip));
		int stringWidth = g2D.getFontMetrics().stringWidth(name);
		//g2D.drawRect((int) (earth_pos_draw.x() - boxSize/2), (int) (earth_pos_draw.y() - boxSize/2),  (int) (boxSize), (int) (boxSize));
		Rectangle rect = getClickBox(b);
		g2D.draw(rect);
		int boxSize = rect.height;
		g2D.drawString(name, (int) earth_pos_draw.x() - stringWidth/2, (int) earth_pos_draw.y() + boxSize * 3 / 5);
		g2D.setTransform(gTransform);
	}
	private Rectangle getClickBox(Body b) {
		XY pos = new XY(b.pos());
		pos.scale(1 / km_per_pixel);
		int boxSize = (int) Math.max(16, 2 * b.getRadius() / km_per_pixel);
		return new Rectangle((int) pos.x() - boxSize/2, (int) (pos.y() - boxSize/2), boxSize, boxSize);
	}
	private void transformGraphics(Graphics2D g2D) {
		g2D.scale(1 / km_per_pixel, 1 / km_per_pixel);
		
		double translateX = Window.SCREEN_CENTER_X - (pov_pos.x());
		double translateY = Window.SCREEN_CENTER_Y + (pov_pos.y());
		g2D.translate(translateX * km_per_pixel, translateY * km_per_pixel);
	}
	private void paintUniverse(Graphics2D g2D) {
		AffineTransform gTransform = g2D.getTransform();
		g2D.scale(1, -1);
		g2D.setColor(Color.WHITE);
		for(Body b : solarsystem) {
			g2D.drawLine((int) sun.pos().x(), (int) sun.pos().y(), (int) b.pos().x(), (int) b.pos().y());
		}
		
		universe.paint(g2D);
		//Revert to previous transform
		g2D.setTransform(gTransform);
	}
	public void update() {
		tick++;
		universe.update();
		updatePOV();
		/*
 		if(pov_object != null) {
			/*
			Universe parallel = universe.createParallel();
			SpaceObject pov_parallel = parallel.getObjects().get(universe.getObjects().indexOf(pov_object));
			*//*
 			Universe parallel = new Universe();
 			SpaceObject pov_parallel = pov_object.parallel(parallel);
 			parallel.createObjects(sun.parallel(parallel), pov_parallel);
			for(int i = 0; i < 100; i++) {
				parallel.update();
				universe.addMarkers(new XY(pov_parallel.pos()));
			}
		}
		*/
	}
	public void updatePOV() {
		boolean pov_decel = true;
		double pov_acceleration = 0.1; //0.1
		if(isPressed(KeyEvent.VK_LEFT)) {
			pov_vel.incX(-pov_acceleration);
			pov_decel = false;
		}
		if(isPressed(KeyEvent.VK_RIGHT)) {
			pov_vel.incX(pov_acceleration);
			pov_decel = false;
		}
		if(isPressed(KeyEvent.VK_UP)) {
			pov_vel.incY(pov_acceleration);
			pov_decel = false;
		}
		if(isPressed(KeyEvent.VK_DOWN)) {
			pov_vel.incY(-pov_acceleration);
			pov_decel = false;
		}
		if(pov_decel) {
			pov_vel.scale(0.9);
		}
		
		if(isPressed(KeyEvent.VK_D)) {
			earth.pos().set(earth.pos.polarOffset(earth.pos.angleFrom(sun.pos), 1000));
		}
		if(isPressed(KeyEvent.VK_A)) {
			earth.pos().set(earth.pos.polarOffset(earth.pos.angleTo(sun.pos), 1000));
		}
		
		if(isPressed(KeyEvent.VK_M)) {
			System.out.println("aa");
			universe.createObjects(new Ship(universe, mars, new XYR(earth.pos.polarOffset(0, earth.getRadius() * 3))));
		}
		
		boolean inc_decel = true;
		double km_per_pixel_accel = 0.1; //0.001
		
		if(isPressed(KeyEvent.VK_X)) {
			km_per_pixel_inc -= km_per_pixel_accel;
			inc_decel = false;
		}
		if(isPressed(KeyEvent.VK_Z)) {
			km_per_pixel_inc += km_per_pixel_accel;
			inc_decel = false;
		}
		if(inc_decel) {
			km_per_pixel_inc *= 0.9;	
		}
		
		km_per_pixel_inc = Math.signum(km_per_pixel_inc) * Math.min(Math.abs(km_per_pixel_inc), km_per_pixel / 50);
		
		double km_per_pixel_change =  km_per_pixel_inc;
		double km_per_pixel_limit = 0.5;
		
		//Account for zoom change when panning the POV
		pov_pos.scale(km_per_pixel);
		pov_vel.scale(km_per_pixel);
		if(km_per_pixel_inc > 0 || (km_per_pixel_change < 0 && km_per_pixel + km_per_pixel_change > km_per_pixel_limit)) {
			km_per_pixel += km_per_pixel_change;
		} else if(km_per_pixel_inc != 0) {
			km_per_pixel_inc = 0;
			km_per_pixel = km_per_pixel_limit;
		}
		pov_pos.scale(1 / km_per_pixel);
		pov_vel.scale(1 / km_per_pixel);
		
		pov_pos.inc(pov_vel);
		
		if(pov_object != null) {
			pov_pos = new XY(pov_object.pos());
			pov_pos.scale(1 / km_per_pixel);
			pov_vel = new XY(0, 0);
		}
	}
	private boolean isPressed(int key) {
		return pressed[key];
	}
	public void keyPressed(KeyEvent e) {
		pressed[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
		pressed[e.getKeyCode()] = false;
	}
	public void keyTyped(KeyEvent arg0) { }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		int y_adjust = 30;
		XY click = new XY(e.getX(), getHeight() - e.getY() + y_adjust).difference(Window.SCREEN_CENTER).sum(pov_pos);
		//click = click.product(km_per_pixel);
		//universe.addMarkers(new XY(click.x(), click.y()));
		for(Body b : solarsystem) {
			if(getClickBox(b).contains(new Point2D.Double(click.x(), click.y()))) {
				pov_object = b;
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}