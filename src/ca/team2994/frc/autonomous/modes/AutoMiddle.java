package ca.team2994.frc.autonomous.modes;

import ca.team2994.frc.autonomous.AutoBuilder;
import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.commands.DriveStraight;
import ca.team2994.frc.autonomous.commands.DriveTurn;
import ca.team2994.frc.autonomous.commands.GearShift;
import ca.team2994.frc.autonomous.commands.GeneralAutoConstants;
import ca.team2994.frc.autonomous.commands.Wait;

public class AutoMiddle extends AutoMode implements AutoConstants {
	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();

		// Shift to low gear
		builder.add(new DriveStraight(-73, 0.3));
		builder.add(new Wait(LONG_GEAR_WAIT));
		builder.add(new DriveStraight(GEAR_BACKUP, 0.3));
		builder.add(new DriveTurn(90)); // Align to go pick up next gear

		return builder.toArray();
	}
}
