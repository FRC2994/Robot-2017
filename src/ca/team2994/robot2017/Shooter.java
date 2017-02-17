package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.CAN_SHOOTER;
import static ca.team2994.frc.utils.Constants.DIO_SHOOTER_ENCODER_A;
import static ca.team2994.frc.utils.Constants.DIO_SHOOTER_ENCODER_B;
import static ca.team2994.frc.utils.Constants.INDEXER_SPEED;
import static ca.team2994.frc.utils.Constants.PWM_INDEXER;
import static ca.team2994.frc.utils.Constants.SHOOTER_ENCODER_CALIBRATION;
import static ca.team2994.frc.utils.Constants.SHOOTER_PID_D;
import static ca.team2994.frc.utils.Constants.SHOOTER_PID_E;
import static ca.team2994.frc.utils.Constants.SHOOTER_PID_I;
import static ca.team2994.frc.utils.Constants.SHOOTER_PID_P;
import static ca.team2994.frc.utils.Constants.getConstantAsDouble;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.driveJoystick;

import com.ctre.CANTalon;

import ca.team2994.frc.controls.ButtonEntry;
import ca.team2994.frc.controls.EEncoder;
import ca.team2994.frc.utils.SimPID;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends Subsystem {
	double previousRotations;
	long previousTime;
	int p, i, d, eps;
	
	CANTalon shooter = new CANTalon(getConstantAsInt(CAN_SHOOTER));
	Victor indexer = new Victor(getConstantAsInt(PWM_INDEXER));
	
	Encoder shooterEncoder = new EEncoder(getConstantAsInt(DIO_SHOOTER_ENCODER_A), getConstantAsInt(DIO_SHOOTER_ENCODER_B));
	
	SimPID shooterPID = new SimPID(
									getConstantAsDouble(SHOOTER_PID_P),
									getConstantAsDouble(SHOOTER_PID_I),
									getConstantAsDouble(SHOOTER_PID_D),
									getConstantAsDouble(SHOOTER_PID_E)
									);

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

	@Override
	public void initTeleop() {
		load();
		setShooterSpeed(0);
	}

	public void resetPID(int p, int i, int d, int eps) {
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
		indexer.set(getConstantAsDouble(INDEXER_SPEED));
	}
	
	public void stopLoading() {
		indexer.set(0);
	}
	
	public void setShooterSpeed(int rpm) {
		// Set up the desired number of units.
		shooterPID.setDesiredValue(rpm);
	}

	@Override
	public void tickTeleop() {
		
	}
	
	@Override
	void initTesting() {
		Subsystems.driveJoystick.enableButton(3);
		Subsystems.driveJoystick.enableButton(4);
		Subsystems.driveJoystick.enableButton(5);
		Subsystems.driveJoystick.enableButton(6);
		Subsystems.driveJoystick.enableButton(7);
		Subsystems.driveJoystick.enableButton(10);
		Subsystems.driveJoystick.enableButton(11);
	}
	
	public void tickTesting() {
		System.out.println(calculateSpeed());
		double speed = calculateSpeed();
		double outputValue = shooterPID.calcPID(speed);
		int target = (int)(300*driveJoystick.getY());

		setShooterSpeed(target);
		
		shooter.set(outputValue);
		
		System.out.println("Speed: " + speed + ", output: " + outputValue + ", target: " + target);

		if (driveJoystick.getRawButton(2)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				eps += 0.1;
				System.out.println("p value = " + p);
			}
			
			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				eps -= 0.1;
				System.out.println("p value = " + p);
			}
			
			if (driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED) {
				eps += 0.01;
				System.out.println("p value = " + p);
			}
			
			if (driveJoystick.getEvent(10) == ButtonEntry.EVENT_CLOSED) {
				eps -= 0.01;
				System.out.println("p value = " + p);
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
		}
	}
}