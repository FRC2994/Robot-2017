package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.utils.Constants;
import ca.team2994.frc.utils.SimLib;
import ca.team2994.frc.utils.SimPID;
import ca.team2994.robot2017.DriveTrain;
import ca.team2994.robot2017.Subsystems;

public class DriveStraight implements AutoCommand {
	
	private final double distance;
	private SimPID drivePID;
	private DriveTrain driveTrain = DriveTrain.getInstance();

	public DriveStraight(double distance) {
		this.distance = distance;
	}
	
	@Override
	public void initialize() {
		// Reset the encoders (encoder.get(Distance|)() == 0)
		driveTrain.reset();
		drivePID = driveTrain.getAutoDrivePID();
		drivePID.setDesiredValue(distance);
		System.out.println("DriveStraight Init");
	}
	
	@Override
	public boolean tick() {
		if (!drivePID.isDone()) {
			double driveVal = drivePID.calcPID(driveTrain.getDistance());
			// TODO: Read this from the constants file as "encoderPIDMax"
			double limitVal = SimLib.limitValue(driveVal, Constants.getConstantAsDouble(Constants.ENCODER_PID_MAX));

			driveTrain.setMotors(limitVal, limitVal);

			return true;
		}
		return false;
		
	}

	@Override
	public void cleanup() {
		driveTrain.setMotors(0.0, 0.0);	
	}

}
