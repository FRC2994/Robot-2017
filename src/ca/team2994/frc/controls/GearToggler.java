package ca.team2994.frc.controls;

import ca.team2994.robot2017.Subsystems;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class GearToggler {
	public static void setLowGear() {
		Subsystems.gearShiftSolenoid.set(Value.kReverse);
	}
	
	public static void setHighGear() {
		Subsystems.gearShiftSolenoid.set(Value.kForward);
	}
	
	public static void setGear(boolean gear) {
		if (gear) setHighGear();
		else setLowGear();
	}
}
