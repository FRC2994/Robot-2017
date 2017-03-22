package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.Shooter;

public class StartShooterMotor implements AutoCommand {
	private Shooter shooter;
	
	public StartShooterMotor() {
		this.shooter = Shooter.getInstance();
	}
	
	@Override
	public void initialize() {
		shooter.shootAtPercentageVoltage(0.75);
		System.out.println("Endinit");
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() {
	}

}
