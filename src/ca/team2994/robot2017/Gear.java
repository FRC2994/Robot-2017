package ca.team2994.robot2017;

import static ca.team2994.robot2017.Subsystems.driveJoystick;
import static ca.team2994.frc.utils.Constants.*;

import ca.team2994.frc.controls.ButtonEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Gear extends Subsystem {
	DoubleSolenoid solenoid = new DoubleSolenoid(
											getConstantAsInt(PCM_CAN),
											getConstantAsInt(SOLENOID_GEAR_CHANNEL1),
											getConstantAsInt(SOLENOID_GEAR_CHANNEL2));
	public static Gear instance;
	
	public enum Position {
		DOWN,
		UP
	}
	
	public static Gear getInstance() {
		return instance;
	}
	
	public Gear() {
		resetSolenoid();
		
		driveJoystick.enableButton(6);
		instance = this;
	}
	
	public void resetSolenoid() {
		setGearDirection(Position.UP);
	}

	public void setGearDirection(Position pos) {
		// Forward = down
		// Reverse = up
		if (pos == Position.DOWN) {
			solenoid.set(Value.kReverse);
		}
		else if (pos == Position.UP) {
			solenoid.set(Value.kForward);
		}
	}

	@Override
	public void initTeleop() {
		setGearDirection(Position.UP);
	}

	@Override
	public void tickTeleop() {
		if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
			// If the button is pressed we bring down the gear mech
			setGearDirection(Position.DOWN);
		}
		else if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_OPENED) {
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
