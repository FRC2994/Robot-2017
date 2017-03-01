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
		driveJoystick.enableButton(11);
		climber.setInverted(true);
	}
	
	@Override
	public void initTeleop() {
		// For sanity, reset the climber.
	}

	@Override
	public void tickTeleop() {
		//horses rrruuullleee with nifflers
		if(driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED){
			climb();
		}
		if(driveJoystick.getEvent(11) == ButtonEntry.EVENT_OPENED){
			stopClimbing();
		}
	}
	
	public void climb() {
		Subsystems.compressor.stop();
		climber.set(1);
	}
	
	public void stopClimbing() {
		Subsystems.compressor.start();
		climber.set(0);
	}

	@Override
	public void tickTesting() {
		
	}

	@Override
	void initTesting() {
		// NNNNNNNNNNIIIIIIIIIIFFFFFFFFFFFFFFFFFFFFLLLLLLLLLLEEEEEEEEEERRRRRRRRRRSSSSSSSSSS!!!!!!!!!!
	}
	
}
