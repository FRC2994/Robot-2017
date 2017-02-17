package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.utils.SimLib;
import ca.team2994.frc.utils.SimPID;
import ca.team2994.robot2017.DriveTrain;
import ca.team2994.robot2017.Subsystems;

public class DriveTurn implements AutoCommand {

	private final int angle;
	private SimPID gyroPID;
	private DriveTrain driveTrain = DriveTrain.getInstance();
	
	public DriveTurn(int angle) {
		this.angle = angle;
	}
	
	@Override
	public void initialize() {
		gyroPID = driveTrain.getTurnPID();
		gyroPID.setDesiredValue(angle);
		System.out.println("DriveTurn Init:" + angle);
	}
	
	@Override
	public boolean tick() {
		if (!gyroPID.isDone()) {
			double driveVal = gyroPID.calcPID(driveTrain.getHeading());
			double limitVal = SimLib.limitValue(driveVal, 0.9);
			driveTrain.setMotors(limitVal, -limitVal);
			return true;
		}
		return false;
	}

	@Override
	public void cleanup() {
		System.out.println("DriveTurn Cleanup");
		driveTrain.setMotors(0.0, 0.0);
	}
	
}
