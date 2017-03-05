package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.DriveTrain;

public class SetBrakeCoast implements AutoCommand {
	private DriveTrain.BrakeCoastStatus status;

	public SetBrakeCoast(DriveTrain.BrakeCoastStatus status) {
		this.status = status;
	}
	
	@Override
	public void initialize() {
		DriveTrain.getInstance().setBrakeCoast(status);
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() {
	}

}
