public final class Constants {
	private Constants() {}
	
	public static final double GRAVITATIONAL = 6.67408e-11;
	
	public static class Time {
		private Time() {}
		public static final double SECONDS_PER_FRAME = 30;
	}
	public static class Units {
		private Units() {}
		public static final double KM_TO_M = 1000;
	}
	public static class Mass {
		private Mass() {}
		//kg
		public static final double SUN = 1.989e30;
		public static final double EARTH = 5.972e24;
	}
	public static class Radius {
		private Radius() {}
		//km
		public static final double SUN = adjust(696000);
		public static final double EARTH = adjust(6371);
		public static final double adjust(double value) {
			return Math.pow(value, 0.9);
		}
	}
	public static class Distance {
		private Distance() {}
		
		public static final double SUN_EARTH = adjust(152000000);
		public static final double adjust(double value) {
			System.out.println("Original Value: " + value);
			double adjusted = Math.pow(value, 0.8);
			System.out.println("Adjusted Value: " + adjusted);
			return adjusted;
		}
	}
	
}
