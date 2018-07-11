import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.Constants;

public class Window {
	public static final int SCREEN_WIDTH;
	public static final int SCREEN_HEIGHT;

	static {
		
		//https://stackoverflow.com/questions/32555329/java-get-maximized-state-window-size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Rectangle bounds = gd.getDefaultConfiguration().getBounds();
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());

		Rectangle safeBounds = new Rectangle(bounds);
		safeBounds.x += insets.left;
		safeBounds.y += insets.top;
		safeBounds.width -= (insets.left + insets.right);
		safeBounds.height -= (insets.top + insets.bottom);
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenSize = safeBounds.getBounds();
		SCREEN_WIDTH = (int) screenSize.getWidth();
		SCREEN_HEIGHT = (int) screenSize.getHeight();
	}

	public static final int SCREEN_CENTER_X = SCREEN_WIDTH / 2;
	public static final int SCREEN_CENTER_Y = SCREEN_HEIGHT / 2;
	public static final XY SCREEN_CENTER = new XY(Window.SCREEN_CENTER_X, Window.SCREEN_CENTER_Y);
	
	private JFrame frame;

	public static void main(String[] args) {
		System.out.println(Constants.Mass.EARTH * 1000);
		System.out.println(new BigDecimal(Constants.Mass.EARTH).toPlainString());
		System.out.println(new BigDecimal(Constants.Radius.EARTH).toPlainString());
		System.out.println(new BigDecimal(Constants.Radius.EARTH * Constants.Mass.EARTH).toPlainString());
		/*
		SpaceObject player = StarshipFactory.createPlayership();
		player.setPosR(0);
		generateSprite(player, "Courier v3");
		*/
		/*
		SpaceObject laser = StarshipFactory.createPlayership().getWeapon().get(0).getShotType();
		laser.setPosR(-30);
		generateSprite(laser, "Player Laser");
		*/
		/*
		Starship_NPC enemy = new Starship_NPC();
		enemy.setPos(0, 0, 0);
		enemy.installWeapon(new Weapon(0, 0, 0, 0, 0, 0, 0));
		generateSprite(enemy, "Courier v2 (Hostile)");
		*/
		/*
		Projectile exhaust = new Projectile(0, 0, 0, 5, 10);
		exhaust.setBody(new Body_StarshipExhaust(exhaust));
		generateSprite(exhaust, "Exhaust");
		*/
		/*
		Projectile exhaust2 = new Projectile(0, 0, 45, 5, 10);
		exhaust2.setBody(new Body_StarshipExhaust(exhaust2));
		generateSprite(exhaust2, "Exhaust Enemy");
		*/
		/*
		 * Starship ship = StarshipFactory.createPlayership();
		 * 
		 * for(int i = 0; i < 1; i++) { ship.setPos(0,0,i); ship.updateBody();
		 * Area area = new Area(); for(Polygon p : ship.getBody().getShapes()) {
		 * area.add(new Area(p.getBounds())); } for(Weapon w : ship.getWeapon())
		 * { w.setFireAngle(0); w.update(); w.updateBody(); for(Polygon p :
		 * w.getBody().getShapes()) { area.add(new Area(p.getBounds())); } }
		 * Rectangle bounds = area.getBounds(); System.out.println("X: " +
		 * bounds.getX()); System.out.println("Y: " + bounds.getY());
		 * System.out.println("W: " + bounds.getWidth()); System.out.println(
		 * "H: " + bounds.getHeight()); int scale = 200; int width =
		 * bounds.width * scale; int height = bounds.height * scale;
		 * BufferedImage result = new BufferedImage(width, height,
		 * BufferedImage.TYPE_INT_ARGB); Graphics2D g2D = (Graphics2D)
		 * result.getGraphics(); g2D.setColor(Color.BLACK); g2D.fillRect(0, 0,
		 * width, height); g2D.translate(-bounds.x*scale, -bounds.y*scale);
		 * g2D.scale(scale, scale); ship.draw(g2D); for(Weapon w :
		 * ship.getWeapon().subList(0, 1)) { w.draw(g2D); } try {
		 * ImageIO.write(result, "png", new File("./Output.png")); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */

		/*
		 * try { ImageIO.write(result, "png", new File("./Output.png")); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		//System.exit(0);

		// TODO Auto-generated method stub
		/* Total number of processors or cores available to the JVM */
		System.out.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors());

		/* Total amount of free memory available to the JVM */
		System.out.println("Free memory (mb): " + Runtime.getRuntime().freeMemory() / 1000000);

		/* This will return Long.MAX_VALUE if there is no preset limit */
		long maxMemory = Runtime.getRuntime().maxMemory();
		/* Maximum amount of memory the JVM will attempt to use */
		System.out.println("Maximum memory (mb): " + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory / 1000000));

		/* Total memory currently available to the JVM */
		System.out.println("Total memory available to JVM (mb): " + Runtime.getRuntime().totalMemory() / 1000000);
		/* Get a list of all filesystem roots on this system */
		File[] roots = File.listRoots();

		/* For each filesystem root, print some info */
		for (File root : roots) {
			System.out.println("File system root: " + root.getAbsolutePath());
			System.out.println("Total space (mb): " + root.getTotalSpace() / 1000000);
			System.out.println("Free space (mb): " + root.getFreeSpace() / 1000000);
			System.out.println("Usable space (mb): " + root.getUsableSpace() / 1000000);
		}
		SwingUtilities.invokeLater(() -> { new Window(); });
	}

	public Window() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		//frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		// frame.setExtendedState(frame.getExtendedState() |
		// JFrame.MAXIMIZED_BOTH);
		frame.setBackground(Color.WHITE);
		View view = new View();
		frame.add(view);
		frame.addKeyListener(view);
		
		//frame.setDefaultCloseOperation(DEMO ? JFrame.DO_NOTHING_ON_CLOSE : JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("Astro");
		frame.requestFocus();
		frame.setVisible(true);
	}
}
