package ca.team2994.frc.autonomous.modes;

import ca.team2994.frc.autonomous.AutoBuilder;
import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.commands.DriveStraight;
import ca.team2994.frc.autonomous.commands.DriveTurn;
import ca.team2994.frc.autonomous.commands.ReverseShooterMotor;
import ca.team2994.frc.autonomous.commands.StartIndex;
import ca.team2994.frc.autonomous.commands.StartShooterMotor;
import ca.team2994.frc.autonomous.commands.Wait;
import ca.team2994.robot2017.Shooter;

// Turn left
public class AutoLeftShot extends AutoMode implements AutoConstants {
	@Override
	protected AutoCommand[] initializeCommands() {
		AutoBuilder builder = new AutoBuilder();
		builder.add(new DriveStraight(-FORWARD_TO_TURN_LR, 0.70));
		builder.add(new DriveTurn(ABSANG_TO_GEAR));
		builder.add(new DriveStraight(-FORWARD_TO_IMPALE_LR_GEAR, 0.5));
		builder.add(new ReverseShooterMotor());
		builder.add(new Wait(GEAR_WAIT/(double)2));
		builder.add(new StartShooterMotor());
		builder.add(new Wait(GEAR_WAIT/(double)2));
		builder.add(new DriveStraight(GEAR_BACKUP, 0.75));
		builder.add(new DriveTurn(-ABSANG_BOILER_LINEUP));
		builder.add(new StartIndex());

		return builder.toArray();
	}
}
