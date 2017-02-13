package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.Subsystems;

public class BasicDriveTurn implements AutoCommand {
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		Subsystems.gyroSensor.reset(0);
	}

	/* (non-Javadoc)
	 * @see ca.team2994.frc.autonomous.AutoCommand#tick()
	 */
	@Override
	public boolean tick() {
		// TODO Auto-generated method stub
		System.out.println(Subsystems.gyroSensor.getAngle());
		if (Subsystems.gyroSensor.getAngle() != 90) {
			Subsystems.robotDrive.setLeftRightMotorOutputs(1.0, -1.0);
			System.out.println("Turned");
			return true;
		}
		return false;
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		Subsystems.robotDrive.drive(0, 0);
	}
	

}
