package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.PWM_PICKUP;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;

import edu.wpi.first.wpilibj.Victor;

public class Pickup extends Subsystem {
	Victor pickup = new Victor(getConstantAsInt(PWM_PICKUP));
	
	@Override
	public void initTeleop() {
		
	}

	@Override
	public void tickTeleop() {
		
	}
	
	@Override
	public void initTesting() {
		
	}

	@Override
	public void tickTesting() {
		
	}
}
