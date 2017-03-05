package ca.team2994.frc.autonomous.modes;

import ca.team2994.frc.autonomous.AutoBuilder;
import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.commands.DriveStraight;
import ca.team2994.frc.autonomous.commands.DriveTurn;
import ca.team2994.frc.autonomous.commands.GearShift;
import ca.team2994.frc.autonomous.commands.SetBrakeCoast;
import ca.team2994.robot2017.DriveTrain.BrakeCoastStatus;

public class BasicRobotSetMode extends AutoMode {
	//TODO: Test this on practice field. These are bogus values.

	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();

		// Shift to low gear
		builder.add(new GearShift(false));
		builder.add(new SetBrakeCoast(BrakeCoastStatus.COAST));
//		builder.add(new MoveGearMech(Position.DOWN));
//		builder.add(new DriveStraight(-32));
//		builder.add(new DriveStraight(1));
//		builder.add(new Wait(5));
		builder.add(new DriveStraight(-26.5, 0.3));
//		builder.add(new DriveTurn(-60));

//		builder.add(new DriveTurn(90));
		
		return builder.toArray();
	}
}
