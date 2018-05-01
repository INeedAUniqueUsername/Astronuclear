import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends SpaceObject {
	Universe universe;
	private SpaceObject target;
	public Ship(Universe universe, SpaceObject target, XYR xyr) {
		super(xyr);
		this.universe = universe;
		this.target = target;
	}
	public void update() {
		super.update();
		
		XY targetPos = new XY(target.pos());
		double angle = targetPos.difference(pos).angle();
		angle = Help.calcFireSolution(target.pos().difference(pos), target.vel().difference(vel), 1000);
		//XY vel_next = vel.polarOffset(angle, 10);
		vel.inc(new XY().polarOffset(angle, 10));
		
		/*
		double rAccel = Math.PI/12;
		
		//double distance =		pos.polarOffset(pos.r() + vel.r(), 100).distance(target.pos);
		double futureR = 		Help.calcFinalDistance(pos.r(), vel.r(), rAccel);
		double thrust = 10;
		
		double turn = 0;
		
		XY targetPos = new XY(target.pos());
		
		double cwDistance =	pos.polarOffset(futureR - rAccel, 100).distance(targetPos);
		double ccwDistance =	pos.polarOffset(futureR + rAccel, 100).distance(targetPos);
		
		if(cwDistance < ccwDistance) {
			pos.incR(-rAccel);
			double cwDistancePrev;
			do {
				cwDistancePrev = cwDistance;
				cwDistance = pos.polarOffset(futureR - turn, 100).distance(targetPos);
				turn += rAccel;
			} while(cwDistance < cwDistancePrev);
		} else if(ccwDistance < cwDistance) {
			pos.incR(rAccel);
			double ccwDistancePrev;
			do {
				ccwDistancePrev = ccwDistance;
				ccwDistance = pos.polarOffset(futureR + turn, 100).distance(targetPos);
				turn += rAccel;
			} while(ccwDistance < ccwDistancePrev);
		}
		*/
		/*
		XYR vel_next = new XYR(vel.polarOffset(pos.r(), 2));
		if(turn < Math.PI/4) {
			vel = vel_next;
		}
		*/
	}
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.drawPolygon(triangle(600));
		
		XY polar = pos.polarOffset(vel.angle(), 1000);
		g2d.drawLine((int)pos.x(), (int)pos.y(), (int)polar.x(), (int)polar.y());
	}
	private Polygon triangle(double size) {
		
		double r = pos.r();
		Point[] points = { polarOffset(r, size), polarOffset(r + 5 * Math.PI / 4, size), polarOffset(r - 5 * Math.PI / 4, size), polarOffset(r, size) };
		int n = points.length;
		int[] x = {
				points[0].x, points[1].x, points[2].x, points[3].x
		};
		int[] y = {
				points[0].y, points[1].y, points[2].y, points[3].y
		};
		return new Polygon(x, y, n);
	}
	public Point polarOffset(double angle, double magnitude) {
		return new Point((int) (pos.x() + magnitude * Math.cos(angle)), (int) (pos.y() + magnitude * Math.sin(angle)));
	}
}
