package ca.team2994.frc.autonomous.modes;

import ca.team2994.frc.autonomous.AutoBuilder;
import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.commands.DriveStraight;
import ca.team2994.frc.autonomous.commands.DriveTurn;
import ca.team2994.frc.autonomous.commands.Wait;

public class AutoRightNoShot extends AutoMode implements AutoConstants {
	@Override
	protected AutoCommand[] initializeCommands() {
		AutoBuilder builder = new AutoBuilder();
		builder.add(new DriveStraight(-FORWARD_TO_TURN_LR, 0.70));
		builder.add(new DriveTurn(-ABSANG_TO_GEAR));
		builder.add(new DriveStraight(-FORWARD_TO_IMPALE_LR_GEAR, 0.5));
		builder.add(new Wait(LONG_GEAR_WAIT));
		builder.add(new DriveStraight(GEAR_BACKUP, 0.75));
		builder.add(new DriveTurn(ABSANG_TO_GEAR));

		return builder.toArray();
	}
}
