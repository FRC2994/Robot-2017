package ca.team2994.robot2017;

import static ca.team2994.robot2017.Subsystems.driveJoystick;

import ca.team2994.frc.controls.ButtonEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Gear extends Subsystem {
	DoubleSolenoid solenoid;// = new DoubleSolenoid(
								//			getConstantAsInt(COMPRESSOR_CHANNEL),
									//		getConstantAsInt(SOLENOID_GEAR_CHANNEL1),
										//	getConstantHANNEL2));AsInt(SOLENOID_GEAR_C
	
	public enum Position {
		DOWN,
		UP
	}
	
	public Gear() {
		resetSolenoid();
		
		driveJoystick.enableButton(0);
	}
	
	public void resetSolenoid() {
		setGearDirection(Position.UP);
	}

	public void setGearDirection(Position pos) {
		// Forward = down
		// Reverse = up
		if (pos == Position.DOWN) {
//			solenoid.set(Value.kForward);
		}
		else if (pos == Position.UP) {
//			solenoid.set(Value.kReverse);
		}
	}

	@Override
	public void initTeleop() {
		setGearDirection(Position.UP);
	}

	@Override
	public void tickTeleop() {
		if (driveJoystick.getEvent(0) == ButtonEntry.EVENT_CLOSED) {
			// If the button is pressed we bring down the gear mech
			setGearDirection(Position.DOWN);
		}
		else if (driveJoystick.getEvent(0) == ButtonEntry.EVENT_OPENED) {
			setGearDirection(Position.UP);
		}
	}

	@Override
	public void tickTesting() {
		
	}

	@Override
	void initTesting() {
		// TODO Auto-generated method stub
		
	}
}
