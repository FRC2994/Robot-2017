
package ca.team2994.robot2017;

import java.util.ArrayList;
import java.util.List;

import ca.team2994.frc.autonomous.AutoMode;
import ca.team2994.frc.autonomous.modes.BasicRobotSetMode;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private Shooter shooter;
	private Gear gear;
	private Pickup pickup;
	private DriveTrain driveTrain;
	private Climber climber;
	
	private List<Subsystem> subsystems;

	private static Robot instance;
	
	public Robot() {
		instance = this;
	}
	
	public static Robot getInstance() {
		return instance;
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		Subsystems.initialize();
		
		subsystems = new ArrayList<Subsystem>();
//
		this.shooter = new Shooter();
		subsystems.add(shooter); 

		this.gear = new Gear();
		subsystems.add(gear);

		this.climber = new Climber();
		subsystems.add(climber);
//		
		this.driveTrain = new DriveTrain();
		subsystems.add(driveTrain);
//		
		this.pickup = new Pickup();
		
		subsystems.add(pickup);
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
//		shooter.stopLoading();
	}

	@Override
	public void disabledPeriodic() {

	}

	AutoMode mode;
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		mode = new BasicRobotSetMode();
		driveTrain.robotDrive.setSafetyEnabled(false);
		driveTrain.setLowGear();
		mode.initialize();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		mode.tick();
	}

	@Override
	public void teleopInit() {
		for (Subsystem subsystem : subsystems) {
			subsystem.initTeleop();
		}
		
		Subsystems.driveJoystick.enableButton(3);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Subsystems.driveJoystick.update();
		Subsystems.controlGamepad.update();

		for (Subsystem subsystem : subsystems) {
			subsystem.tickTeleop();
		}
	}

	@Override
	public void testInit() {
		for (Subsystem subsystem : subsystems) {
			subsystem.initTesting();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		Subsystems.compressor.start();
		Subsystems.driveJoystick.update();
		Subsystems.controlGamepad.update();

		for (Subsystem subsystem : subsystems) {
			subsystem.tickTesting();
		}
	}
}
