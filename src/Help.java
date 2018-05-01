import java.awt.geom.Point2D;

public class Help {
	public final static double modRange(double input, double range) {
		double result = input % range;
		while(result < 0) {
			result = result + range;
		}
		return result;
	}
	public final static double modRangeDegrees(double input) {
		return modRange(input, 360);
	}
	public final static double getAngleDiff(double angle1, double angle2) {
		return Math.min(modRangeDegrees(angle1 - angle2), modRangeDegrees(angle2 - angle1));
	}
	public final static double calcFinalDistance(double x, double speed, double decel) {
		double decelTime = speed / decel;
		return x + x * decelTime + ((x > 0) ? -1 : 1) * 0.5 * decel * Math.pow(decelTime, 2);
	}
	public static final XY calcFireSolutionTargetPosDiff(Cartesian pos_diff, Cartesian vel_diff, double weapon_speed) {
		//Here is our initial estimate. If the target is moving, then by the time the shot reaches the target's original position, the target will be somnewhere else
		double time_to_hit_estimate = pos_diff.magnitude() / weapon_speed;
		XY pos_diff_future = calcFuturePos(pos_diff, vel_diff, time_to_hit_estimate);
		
		//System.out.println("Try 0");
		//System.out.println("Time to Hit: " + time_to_hit_estimate);
		
		double time_to_hit_old = 0;
		for(int i = 1; i < 10; i++) {
			double time_to_hit = pos_diff_future.magnitude() / weapon_speed;
			pos_diff_future = calcFuturePos(pos_diff, vel_diff, time_to_hit);
			
			//System.out.println("Try " + i);
			//System.out.println("Time to Hit: " + time_to_hit);
			
			if(Math.abs(time_to_hit - time_to_hit_old) < 1) {
				break;
			}
			time_to_hit_old = time_to_hit;
		}
		return pos_diff_future;
	}
	public static final double calcFireSolution(Cartesian pos_diff, Cartesian vel_diff, double weapon_speed) {
		return calcFireSolutionTargetPosDiff(pos_diff, vel_diff, weapon_speed).angle();
	}
	public static final XY calcFuturePos(Cartesian origin, Cartesian vel, double time) {
		double angle = vel.angle();
		double speed = Math.sqrt(Math.pow(vel.x(), 2) + Math.pow(vel.y(), 2));
		return origin.polarOffset(angle, speed * time);
	}
	public static final XY calcFuturePosWithDeceleration(Cartesian pos, Cartesian vel, double decel) {
		double decelTime = vel.magnitude() / decel;
		return new XY(pos.x() + vel.x() * decelTime + ((vel.x() > 0) ? -1 : 1) * 0.5 * decel * Math.pow(decelTime, 2),
				pos.y() + vel.y() * decelTime + ((vel.y() > 0) ? -1 : 1) * 0.5 * decel * Math.pow(decelTime, 2));
	}
}
