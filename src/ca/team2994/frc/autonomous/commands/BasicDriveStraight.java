package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.Subsystems;

public class BasicDriveStraight implements AutoCommand {
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		Subsystems.rightDriveEncoder.reset();
		Subsystems.leftDriveEncoder.reset();
	}

	@Override
	public boolean tick() {
		// TODO Auto-generated method stub
		if (Subsystems.rightDriveEncoder.getDistance() < 5) {
			Subsystems.robotDrive.drive(0.2, 0);
			return true;
		}
		return false;
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		Subsystems.robotDrive.drive(0, 0);
	}
	
}
