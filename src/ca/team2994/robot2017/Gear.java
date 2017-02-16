package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.COMPRESSOR_CHANNEL;
import static ca.team2994.frc.utils.Constants.SOLENOID_GEAR_CHANNEL1;
import static ca.team2994.frc.utils.Constants.SOLENOID_GEAR_CHANNEL2;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.driveJoystick;
import static ca.team2994.robot2017.Subsystems.gearDown;

import ca.team2994.frc.controls.ButtonEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Gear extends Subsystem {
	DoubleSolenoid solenoid = new DoubleSolenoid(
											getConstantAsInt(COMPRESSOR_CHANNEL),
											getConstantAsInt(SOLENOID_GEAR_CHANNEL1),
											getConstantAsInt(SOLENOID_GEAR_CHANNEL2));
	
	public enum Position {
		DOWN,
		UP
	}
	
	public Gear() {
		resetSolenoid();
		
		driveJoystick.enableButton(x);
	}
	
	public void resetSolenoid() {
		setGearDirection(Position.UP);
	}

	public void setGearDirection(Position pos) {
		// Forward = down
		// Reverse = up
		if (pos == Position.DOWN) {
			solenoid.set(Value.kForward);
		}
		else if (pos == Position.UP) {
			solenoid.set(Value.kReverse);
		}
	}

	@Override
	public void initTeleop() {
		setGearDirection(Position.UP);
	}

	@Override
	public void tickTeleop() {
		if (driveJoystick.getEvent(x) == ButtonEntry.EVENT_CLOSED) {
			// If the button is pressed we bring down the gear mech
			setGearDirection(Position.DOWN);
		}
		else if (driveJoystick.getEvent(x) == ButtonEntry.EVENT_OPENED) {
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
