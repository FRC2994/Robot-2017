package ca.team2994.robot2017;

import static ca.team2994.robot2017.Subsystems.climber;
import static ca.team2994.robot2017.Subsystems.driveJoystick;
import static ca.team2994.frc.utils.Constants.*;

import ca.team2994.frc.controls.ButtonEntry;

public class Climber implements Subsystem {
	public Climber() {
		driveJoystick.enableButton(x);
	}
	
	@Override
	public void initTeleop() {
		// For sanity, reset the climber.
		climber.set(0);
	}

	@Override
	public void tickTeleop() {
		if (driveJoystick.getEvent(x) == ButtonEntry.EVENT_CLOSED) {
			climber.set(getConstantAsInt(CLIMBER_SPEED));
		}
		else if (driveJoystick.getEvent(x) == ButtonEntry.EVENT_OPENED) {
			climber.set(0.0);
		}
	}

	@Override
	public void tickTesting() {
		// TODO Auto-generated method stub
		
	}
	
}
