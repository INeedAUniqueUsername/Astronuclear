import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;
/*
n = number of nodes
e = number of external nodes
i = number of internal nodes
h = height;

e = i + 1
n = 2e - 1
h <= i
h <= (n - 1) / 2
e <= 2 ^ h
h >= log(2)e
h >= log(2)(n + 1) - 1
 */
public class View extends JPanel implements KeyListener {
	private Universe universe;
	private XY pov_pos;
	private XY pov_vel;
	private double km_per_pixel;
	private double km_per_pixel_inc;
	private boolean[] pressed;
	public View() {
		pov_pos = new XY();
		pov_vel = new XY();
		universe = new Universe();
		Body sun = new Body(universe, (Constants.Radius.SUN), Constants.Mass.SUN);
		Body earth = new Body(universe, Constants.Radius.EARTH, Constants.Mass.EARTH);
		//Mass sun = new Mass(universe, 10, 1);
		//Mass earth = new Mass(universe, 10, 1);
		earth.pos.set(new XY(Constants.Distance.SUN_EARTH, 0));
		System.out.println(earth.pos.x());
		//earth.vel.set(new XY(0, 2));
		universe.createObjects(sun, earth);
		universe.update();
		sun.initializeOrbits();
		earth.initializeOrbits();
		addKeyListener(this);
		km_per_pixel = Constants.Radius.EARTH;
		km_per_pixel_inc = 0;
		pressed = new boolean[100];
	}
	public void paintComponent(Graphics g) {
		universe.update();
		
		boolean pov_decel = true;
		double pov_acceleration = 0.1; //0.1
		if(pressed[KeyEvent.VK_LEFT]) {
			pov_vel.incX(-pov_acceleration);
			pov_decel = false;
		}
		if(pressed[KeyEvent.VK_RIGHT]) {
			pov_vel.incX(pov_acceleration);
			pov_decel = false;
		}
		if(pressed[KeyEvent.VK_UP]) {
			pov_vel.incY(pov_acceleration);
			pov_decel = false;
		}
		if(pressed[KeyEvent.VK_DOWN]) {
			pov_vel.incY(-pov_acceleration);
			pov_decel = false;
		}
		if(pov_decel) {
			pov_vel.scale(0.9);
		}
		
		boolean inc_decel = true;
		double km_per_pixel_accel = 0.1; //0.001
		
		if(pressed[KeyEvent.VK_X]) {
			km_per_pixel_inc -= km_per_pixel_accel;
			inc_decel = false;
		}
		if(pressed[KeyEvent.VK_Z]) {
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
		
		
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setBackground(Color.BLACK);
		g2D.clearRect(0, 0, getWidth(), getHeight());
		g2D.setColor(new Color(255, 255, 255, 256 * 3 / 4));
		g2D.drawLine(Window.SCREEN_CENTER_X - 16, Window.SCREEN_CENTER_Y, Window.SCREEN_CENTER_X + 16, Window.SCREEN_CENTER_Y);
		g2D.drawLine(Window.SCREEN_CENTER_X, Window.SCREEN_CENTER_Y - 16, Window.SCREEN_CENTER_X, Window.SCREEN_CENTER_Y + 16);
		
		g2D.draw(new Rectangle(16, 16, 128, 1));
		g2D.drawString("Zoom Scale: " + new DecimalFormat("#.##").format(km_per_pixel * 128) + "km", 16, 32);
		g2D.drawString("Zoom Speed: " + new DecimalFormat("#.##").format(km_per_pixel_inc * 30) + "km/frame", 16, 48);
		
		AffineTransform gTransform = g2D.getTransform();
		g2D.scale(1 / km_per_pixel, 1 / km_per_pixel);
		
		double translateX = Window.SCREEN_CENTER_X - (pov_pos.x());
		double translateY = Window.SCREEN_CENTER_Y + (pov_pos.y());
		g2D.translate(translateX * km_per_pixel, translateY * km_per_pixel);
		
		paintUniverse(g2D);
		
		g2D.setTransform(gTransform);
		repaint();
	}
	private void paintUniverse(Graphics2D g2D) {
		AffineTransform gTransform = g2D.getTransform();
		g2D.scale(1, -1);
		
		universe.paint(g2D);
		//Revert to previous transform
		g2D.setTransform(gTransform);
	}
	
	public void keyPressed(KeyEvent e) {
		pressed[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
		pressed[e.getKeyCode()] = false;
	}
	public void keyTyped(KeyEvent arg0) { }
}