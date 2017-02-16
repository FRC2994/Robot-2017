package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.*;
import static ca.team2994.robot2017.Subsystems.*;

import ca.team2994.frc.controls.ButtonEntry;
import ca.team2994.frc.utils.SimPID;

public class Shooter {
	double previousRotations;
	long previousTime;
	double p, i, d, eps;
	
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
	
	public void resetPID(double p, double i, double d, double eps) {
		shooterPID = new SimPID(p, i, d, eps);
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
	
	int tickCheck = 0;
	
	public void tick() {
		int setpoint = (int)(driveJoystick.getY()*300);
		setShooterSpeed(setpoint);
		double value = shooterPID.calcPID(calculateSpeed());
		tickCheck++;
		if (tickCheck % 5 == 0) {
//			System.out.println(tickCheck);
			System.out.println("PID value: " + value + " <-> Speed: " + calculateSpeed() + " <-> Setpoint: " + setpoint);
		}
		if (value > 30) {
			shooter.set(value);
		}

		if (driveJoystick.getRawButton(2)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				eps += 5;
				System.out.println("eps value = " + eps);
			}
			
			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				eps -= 5;
				System.out.println("eps value = " + eps);
			}
			
			if (driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED) {
				eps += 1;
				System.out.println("eps value = " + eps);
			}
			
			if (driveJoystick.getEvent(10) == ButtonEntry.EVENT_CLOSED) {
				eps -= 1;
				System.out.println("eps value = " + eps);
			}
		}
	
		if (driveJoystick.getRawButton(4)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				p += 0.1;
				System.out.println("p value = " + p);
			}
			
			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				p -= 0.1;
				System.out.println("p value = " + p);
			}
			
			if (driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED) {
				p += 0.01;
				System.out.println("p value = " + p);
			}
			
			if (driveJoystick.getEvent(10) == ButtonEntry.EVENT_CLOSED) {
				p -= 0.01;
				System.out.println("p value = " + p);
			}
		}
		
		if (driveJoystick.getRawButton(3)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				i += 0.1;
				System.out.println("i value = " + i);
			}
			
			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				i -= 0.1;
				System.out.println("i value = " + i);
			}
			
			if (driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED) {
				i += 0.01;
				System.out.println("i value = " + i);
			}
			
			if (driveJoystick.getEvent(10) == ButtonEntry.EVENT_CLOSED) {
				i -= 0.01;
				System.out.println("i value = " + i);
			}
		}
		
		if (driveJoystick.getRawButton(5)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				d += 0.1;
				System.out.println("d value = " + d);
			}
			
			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				d -= 0.1;
				System.out.println("d value = " + d);
			}
			
			if (driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED) {
				d += 0.01;
				System.out.println("d value = " + d);
			}
			
			if (driveJoystick.getEvent(10) == ButtonEntry.EVENT_CLOSED) {
				d -= 0.01;
				System.out.println("d value = " + d);
			}
		}
		
		if (driveJoystick.getEvent(8) == ButtonEntry.EVENT_CLOSED) {
			resetPID(p, i, d, eps);
			System.out.println("p = " + p + ", i = " + i + ", d = " + d + ", eps = " + eps);
		}
		
	/*	if (driveJoystick.getEvent(9) == ButtonEntry.EVENT_CLOSED) {
			p = 0.5;
			i = 0.5;
			d = 0.5;
			eps = 5;
			resetPID(p, i, d, eps);
			System.out.println("p = " + p + ", i = " + i + ", d = " + d + ", eps = " + eps);
		}*/
		
		if (driveJoystick.getEvent(1) == ButtonEntry.EVENT_CLOSED) {
			System.out.println(calculateSpeed());
		}
	}
}