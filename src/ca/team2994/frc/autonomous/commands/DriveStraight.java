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
	private double maxSpeed;
	private DriveTrain driveTrain = DriveTrain.getInstance();

	public DriveStraight(double distance) {
		this(distance, Constants.getConstantAsDouble(Constants.ENCODER_PID_MAX));
	}
	
	public DriveStraight(double distance, double maxSpeed) {
		this.distance = distance;
		this.maxSpeed = maxSpeed;
	}

	@Override
	public void initialize() {
		// Reset the encoders (encoder.get(Distance|)() == 0)
		driveTrain.reset();
		drivePID = driveTrain.getAutoDrivePID();
		drivePID.setDesiredValue(distance);
		System.out.println("DriveStraight Init");
		
		prevEncoder = driveTrain.getRightEncoderValue();
		prevTime = System.currentTimeMillis();
	}
	
	int prevEncoder;
	long prevTime;
	
	@Override
	public boolean tick() {
		if (!drivePID.isDone()) {
			double driveVal = drivePID.calcPID(driveTrain.getDistance());
			// TODO: Read this from the constants file as "encoderPIDMax"
			double limitVal = SimLib.limitValue(driveVal, maxSpeed);

			System.out.println("Limitval: " + limitVal + ", encoder dist: " + driveTrain.getDistance());
			driveTrain.setMotors(limitVal, limitVal);

			return true;
		}
		System.out.println("Drive PID Done");
		return false;
		
	}

	@Override
	public void cleanup() {
		driveTrain.setMotors(0.0, 0.0);	
	}

}
