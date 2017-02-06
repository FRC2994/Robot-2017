package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.frc.utils.SimLib;
import ca.team2994.robot2017.Subsystems;

public class DriveTurn implements AutoCommand {

	private final int angle;
	
	public DriveTurn(int angle) {
		this.angle = angle;
	}
	
	@Override
	public void initialize() {
		Subsystems.gyroPID.setDesiredValue(angle);
		Subsystems.gyroSensor.reset(0);
		// Reset the gyro PID to a reasonable state.
		Subsystems.gyroPID.resetErrorSum();
		Subsystems.gyroPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know the gyro angle is zero from the above
		// reset).
		Subsystems.gyroPID.calcPID(0);
		System.out.println("DriveTurn Init:" + angle);
	}
	
	@Override
	public boolean tick() {
		if (!Subsystems.gyroPID.isDone()) {
			// Angle needs to be positive
			double driveVal = Subsystems.gyroPID.calcPID(Subsystems.gyroSensor.getAngle());
			//System.out.println(driveVal);
//			double limitVal = SimLib.limitValue(driveVal, Constants.getConstantAsDouble(Constants.GYRO_PID_MAX));
			double limitVal = SimLib.limitValue(driveVal, 0.9);
			System.out.println("gyro.getAngle() = " + Subsystems.gyroSensor.getAngle()+",limitVal = " + limitVal);
			Subsystems.robotDrive.setLeftRightMotorOutputs(limitVal, -limitVal);
			System.out.println("Right: " + Subsystems.rightFrontDrive.get() + ", " + Subsystems.rightRearDrive.get() + " Left: " + Subsystems.leftFrontDrive.get() + ", " + Subsystems.leftRearDrive.get());
			return true;
		}
		return false;
	}

	@Override
	public void cleanup() {
		System.out.println("DriveTurn Cleanup");
		Subsystems.robotDrive.drive(0.0, 0.0);
	}
	
}
