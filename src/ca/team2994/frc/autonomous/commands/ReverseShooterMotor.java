package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.Shooter;

public class ReverseShooterMotor implements AutoCommand {
	@Override
	public void initialize() {
		Shooter.getInstance().shootAtPercentageVoltage(-0.2);
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() { }
}
