package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.CLIMBER_SPEED;
import static ca.team2994.frc.utils.Constants.PWM_CLIMBER;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.driveJoystick;

import ca.team2994.frc.controls.ButtonEntry;
import edu.wpi.first.wpilibj.Victor;

public class Climber extends Subsystem {
	Victor climber = new Victor(getConstantAsInt(PWM_CLIMBER));
	
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

	@Override
	void initTesting() {
		// TODO Auto-generated method stub
		
	}
	
}
