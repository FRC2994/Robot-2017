package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.*;
import static ca.team2994.robot2017.Subsystems.*;

public class Shooter {
	double previousRotations;
	long previousTime;
	
	public Shooter() {
		// Reset the encoder PID to a reasonable state.
		shooterPID.resetErrorSum();
		shooterPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know both the distances are zero from the
		// above reset).
		shooterPID.calcPID(0);
		shooterEncoder.reset();
		shooterEncoder.setDistancePerPulse(getConstantAsDouble(SHOOTER_ENCODER_CALIBRATION));
		shooterEncoder.setReverseDirection(true);
		// Initialize values... pretend there was a tick before this one.
		previousRotations = shooterEncoder.getDistance();
		previousTime = System.currentTimeMillis();
	}

	public double convertMillisToMinutes(long millis){
		return (millis / 1000.0) / 60.0;
	}
	public double calculateSpeed(){
		double rotations = shooterEncoder.getDistance();
		long time = System.currentTimeMillis();
		double minutes = convertMillisToMinutes(time - previousTime);
		double rotationDifference = rotations - previousRotations;
		double speed = rotationDifference/minutes;
		previousRotations = rotations;
		previousTime = time;
		return speed;
	}
	public void load() {
		Subsystems.indexer.set(getConstantAsDouble(INDEXER_SPEED));
	}
	
	public void stopLoading() {
		Subsystems.indexer.set(0);
	}
	
	public void setShooterSpeed(int rpm) {
		// Set up the desired number of units.
		shooterPID.setDesiredValue(rpm);
	}
	
	public void tick() {
		System.out.println(calculateSpeed());
	}
}