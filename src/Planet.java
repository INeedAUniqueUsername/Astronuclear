
//Consider splitting the surface of a Planet into triangular tiles to be used for building facilities on
public class Planet extends Body {
	private String name;
	public Planet(Universe universe, double radius, double mass, XYR pos, String name) {
		super(universe, radius, mass, pos);
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
