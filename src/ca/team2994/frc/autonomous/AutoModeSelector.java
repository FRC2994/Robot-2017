package ca.team2994.frc.autonomous;

import java.util.ArrayList;
import java.util.List;

import ca.team2994.frc.autonomous.modes.BasicRobotSetMode;
import edu.wpi.first.wpilibj.DigitalInput;

public class AutoModeSelector {
	private static List<Class<? extends AutoMode>> modes = new ArrayList<>();
	
	static {
		//TODO: Add all autonomous modes here.

		modes.add(BasicRobotSetMode.class);
	}
	
	public AutoMode selectMode(DigitalInput[] inputs) {
		return null;
	}
}
