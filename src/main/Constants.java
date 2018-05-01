package main;
public enum Constants {;
	public static final double GRAVITATIONAL = 6.67408e-11;
	
	public static enum Time {;
		public static final double MULTIPLIER = 1 / 90d;
	}
	public static enum Units {;
		public static final double KM_TO_M = 1000;
	}
	public static enum Mass {;
		public static final double
			SUN =			1.989e30,
			MERCURY =		3.3e23,
			VENUS =			4.87e24,
			EARTH =			5.972e24,
			MARS =			6.42e23,
			JUPITER =		1.9e27,
			SATURN =		5.69e26,
			URANUS =		8.68e25,
			NEPTUNE =		1.02e26,
			PLUTO =			1.29e22,
			
			MOON =			7.34767309e22;
	}
	public static enum Radius {;
		//km
		public static final double
			SUN =	 		adjust(696000),
			MERCURY =		adjust(4878/2d),
			VENUS =			adjust(12104d),
			EARTH =			adjust(6371d),
			MARS =			adjust(6787/2d),
			JUPITER =		adjust(142796/2d),
			SATURN =		adjust(120660/2d),
			URANUS =		adjust(51118/2d),
			NEPTUNE =		adjust(48600/2d),
			PLUTO =			adjust(2274/2d),

			MOON =			adjust(1737d);
		public static final double adjust(double value) {
			//return Math.pow(value, 0.9);
			//return value / 10;
			return value / 10;
		}
	}
	public static enum Separation {;
		public static enum Sun {;
			//Remember to append d to all the double values to prevent integer overflow
			public static final double
				MERCURY =	adjust(57910000d),
				VENUS = 	adjust(108942000d),
				EARTH = 	adjust(152000000d),
				MARS = 		adjust(227900000d),
				JUPITER =	adjust(778300000d),
				SATURN = 	adjust(1427000000d),
				URANUS = 	adjust(2871000000d),
				NEPTUNE =	adjust(4497100000d),
				PLUTO =		adjust(5913000000d);
		}
		public static enum Earth {;
			public static final double MOON = adjust(384400d);
		}
		public static final double adjust(double value) {
			System.out.println("Original Value: " + value);
			//double adjusted = value;
			//double adjusted = Math.pow(value, 0.8);
			double adjusted = value / 100;
			System.out.println("Adjusted Value: " + adjusted);
			return adjusted;
		}
	}
	
}
