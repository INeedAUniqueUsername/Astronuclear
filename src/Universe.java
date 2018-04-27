import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Universe {
	List<SpaceObject> objects;
	List<SpaceObject> create;
	List<SpaceObject> destroy;
	public Universe() { 
		objects = new ArrayList<>();
		create = new ArrayList<>();
		destroy = new ArrayList<>();
	}
	public List<SpaceObject> getObjects() {
		return objects;
	}
	public void createObjects(SpaceObject... o) {
		create.addAll(Arrays.asList(o));
	}
	public void destroyObjects(SpaceObject... o) {
		destroy.addAll(Arrays.asList(o));
	}
	public void update() {
		objects.addAll(create);
		create.clear();
		
		objects.forEach(o -> o.update());
		
		objects.removeAll(destroy);
		destroy.clear();
	}
	public void paint(Graphics2D g2D) {
		objects.forEach(o -> o.paint(g2D));
		
		objects.forEach(o -> {
			objects.forEach(o2 -> g2D.drawLine((int) o.pos().x(), (int) o.pos.y(), (int) o2.pos().x(), (int) o2.pos().y()));
		});
	}
}
