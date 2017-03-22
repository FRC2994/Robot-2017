package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.Shooter;

public class StartIndex implements AutoCommand {
	private Shooter shooter;
	
	public StartIndex() {
		this.shooter = Shooter.getInstance();
	}
	
	@Override
	public void initialize() {
		shooter.load();
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() {
	}

}
