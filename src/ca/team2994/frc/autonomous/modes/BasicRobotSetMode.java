package ca.team2994.frc.autonomous.modes;

import ca.team2994.frc.autonomous.AutoBuilder;
import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.commands.DriveStraight;
import ca.team2994.frc.autonomous.commands.DriveTurn;
import ca.team2994.frc.autonomous.commands.GearShift;
import ca.team2994.frc.autonomous.commands.StartIndex;
import ca.team2994.frc.autonomous.commands.StartShooterMotor;
import ca.team2994.frc.autonomous.commands.Wait;

public class BasicRobotSetMode extends AutoMode {
	//TODO: Test this on practice field. These are bogus values.

	@Override
	protected AutoCommand[] initializeCommands() {
		// This assumes that we start in the staging zone parallel to the closest scoring platform
		AutoBuilder builder = new AutoBuilder();

		// Shift to low gear
		builder.add(new GearShift(false));
//		builder.add(new SetBrakeCoast(BrakeCoastStatus.COAST));
//		builder.add(new MoveGearMech(Position.DOWN));
//		builder.add(new DriveStraight(-32));
//		builder.add(new DriveStraight(1));
//		builder.add(new Wait(5));

//		builder.add(new DriveTurn(-120));

//		builder.add(new DriveTurn(90));
		
		// One gear middle
//		builder.add(new DriveStraight(-73, 0.3));
		
		// Left gear one
		builder.add(new DriveStraight(-71, 0.70));
		builder.add(new DriveTurn(64));
		builder.add(new DriveStraight(-52, 0.5));
		builder.add(new StartShooterMotor());
		builder.add(new Wait(1));
		builder.add(new DriveStraight(12, 0.75));
		builder.add(new DriveTurn(-19));
		builder.add(new StartIndex());
		
		return builder.toArray();
	}
}
