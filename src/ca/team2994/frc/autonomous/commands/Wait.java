package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import edu.wpi.first.wpilibj.Timer;

// A simple command to wait for a couple seconds.
public class Wait implements AutoCommand {
	private int seconds;
	
	public Wait(int seconds) {
		this.seconds = seconds;
	}
	
	@Override
	public void initialize() {
		Timer.delay(seconds);
	}

	@Override
	public boolean tick() {
		return false;
	}

	@Override
	public void cleanup() {
	}
	
}
