package ca.team2994.frc.autonomous.modes;

import ca.team2994.frc.autonomous.AutoBuilder;
import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.commands.DriveStraight;
import ca.team2994.frc.autonomous.commands.DriveTurn;
import ca.team2994.frc.autonomous.commands.GearShift;
import ca.team2994.frc.autonomous.commands.GeneralAutoConstants;
import ca.team2994.frc.autonomous.commands.Wait;

public class DeliverMiddleGearGoToLine extends AutoMode implements GeneralAutoConstants {
	private static int INCHES_FROM_STARTB_TO_PEG = 17;
	private static int BACKWARDS_INCHES_FROM_PEG = 8;
	private static int ANGLE_TO_BASELINE = 50;
	private static int DISTANCE_TO_BASELINE_FROM_ANGLED_TURN = 8;
	
	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();

		// Shift to low gear
		builder.add(new GearShift(false));
		builder.add(new DriveStraight(-INCHES_FROM_STARTB_TO_PEG));
		builder.add(new Wait(SECONDS_TO_WAIT_FOR_PILOT));
		builder.add(new DriveStraight(BACKWARDS_INCHES_FROM_PEG));
		builder.add(new DriveTurn(ANGLE_TO_BASELINE));
		builder.add(new DriveStraight(DISTANCE_TO_BASELINE_FROM_ANGLED_TURN));

		return builder.toArray();
	}
}
