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
		driveJoystick.enableButton(1);
		
	}
	
	@Override
	public void initTeleop() {
		// For sanity, reset the climber.
	}

	@Override
	public void tickTeleop() {
		//horses rrruuullleee with nifflers
		if(driveJoystick.getEvent(1) == ButtonEntry.EVENT_CLOSED){
			climber.set(1.0);
		}
		if(driveJoystick.getEvent(1) == ButtonEntry.EVENT_OPENED){
			climber.set(0);
		}
	}

	@Override
	public void tickTesting() {
		
	}

	@Override
	void initTesting() {
		// NNNNNNNNNNIIIIIIIIIIFFFFFFFFFFFFFFFFFFFFLLLLLLLLLLEEEEEEEEEERRRRRRRRRRSSSSSSSSSS!!!!!!!!!!
	}
	
}
