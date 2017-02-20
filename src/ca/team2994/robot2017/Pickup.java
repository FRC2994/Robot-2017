package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.PWM_PICKUP;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.*;

import ca.team2994.frc.controls.ButtonEntry;
import edu.wpi.first.wpilibj.Victor;

public class Pickup extends Subsystem {
	Victor pickup = new Victor(getConstantAsInt(PWM_PICKUP));
	
	public Pickup() {
		driveJoystick.enableButton(2);
	}
	
	
	
	
	@Override
	public void initTeleop() {
		
	}

	@Override
	public void tickTeleop() {
		if(driveJoystick.getEvent(2) == ButtonEntry.EVENT_CLOSED){
			pickup.set(1.0);
		}
		if(driveJoystick.getEvent(2) == ButtonEntry.EVENT_OPENED){
			pickup.set(0);
		}
	}
	
	@Override
	public void initTesting() {
		
	}

	@Override
	public void tickTesting() {
		
	}
}