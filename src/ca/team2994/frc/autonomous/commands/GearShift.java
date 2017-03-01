package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.DriveTrain;

public class GearShift implements AutoCommand {

	private final boolean highGear;
	private DriveTrain driveTrain = DriveTrain.getInstance();
	
	public GearShift(boolean highGear) {
		this.highGear = highGear;
	}
	
	@Override
	public void initialize() {
		if (highGear) {
			driveTrain.setHighGear();
		}
		else {
			driveTrain.setLowGear();
		}
	}

	@Override
	public boolean tick() {
		// Doesn't need a tick
		return false;
	}

	@Override
	public void cleanup() {
		// No cleanup necessary
		System.out.println("End of cleanup");
	}

}
