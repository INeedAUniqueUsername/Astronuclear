import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Universe {
	List<SpaceObject> objects;
	List<XY> markers;
	List<SpaceObject> create;
	List<SpaceObject> destroy;
	int MAX_MARKERS = 1000;
	public Universe() { 
		objects = new ArrayList<>();
		markers = new ArrayList<>();
		create = new ArrayList<>();
		destroy = new ArrayList<>();
	}
	public List<SpaceObject> getObjects() {
		return objects;
	}
	public void addMarkers(XY... xy) {
		markers.addAll(Arrays.asList(xy));
	}
	public List<XY> getMarkers() {
		return markers;
	}
	private void addObjects(SpaceObject... o) {
		objects.addAll(Arrays.asList(o));
	}
	public void createObjects(SpaceObject... o) {
		create.addAll(Arrays.asList(o));
	}
	public void destroyObjects(SpaceObject... o) {
		destroy.addAll(Arrays.asList(o));
	}
	public void update() {
		if(markers.size() > 500) {
			markers = markers.subList(markers.size() - 500, markers.size());
		}
		
		objects.addAll(create);
		create.clear();
		
		objects.forEach(o -> o.update());
		
		objects.removeAll(destroy);
		destroy.clear();
	}
	public void paint(Graphics2D g2D) {
		objects.forEach(o -> o.paint(g2D));
		
		/*
		objects.forEach(o -> {
			objects.forEach(o2 -> g2D.drawLine((int) o.pos().x(), (int) o.pos.y(), (int) o2.pos().x(), (int) o2.pos().y()));
		});
		*/
		g2D.setColor(Color.WHITE);
		markers.forEach(m -> g2D.drawLine((int) m.x(), (int) m.y(), (int) m.x(), (int) m.y()));
	}
	public Universe createParallel() {
		Universe parallel = new Universe();
		List<SpaceObject> objects = new ArrayList<>();
		objects.addAll(create);
		objects.addAll(this.objects);
		objects.forEach(o -> {
			SpaceObject clone = o.parallel(parallel);
			parallel.addObjects(clone);
		});
		return parallel;
	}
}
