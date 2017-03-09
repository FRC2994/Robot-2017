package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.Shooter;

public class Shoot implements AutoCommand {
	private Shooter shooter;
	
	public Shoot() {
		this.shooter = Shooter.getInstance();
	}
	
	@Override
	public void initialize() {
		shooter.shootAtPercentageVoltage(0.75);
	}

	@Override
	public boolean tick() {
		return true;
	}

	@Override
	public void cleanup() {

	}

}
