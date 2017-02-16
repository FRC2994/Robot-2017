package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ctre.CANTalon;
import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;

import ca.team2994.frc.autonomous.AutoHelper;
import ca.team2994.frc.controls.EEncoder;
import ca.team2994.frc.controls.EGamepad;
import ca.team2994.frc.controls.EJoystick;
import ca.team2994.frc.controls.GearToggler;
import ca.team2994.frc.controls.SimGyro;
import ca.team2994.frc.utils.SimPID;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Victor;


public class Subsystems {	
	// Motors
	public static CANTalon rightFrontDrive;
	public static CANTalon rightRearDrive;
	public static CANTalon leftFrontDrive;
	public static CANTalon leftRearDrive;
	public static CANTalon shooter;
	public static Victor indexer;
	public static Victor pickup;
	public static Victor climber;
	
	// Drive
	public static RobotDrive robotDrive;

	// Encoders
	public static Encoder rightDriveEncoder;
	public static Encoder leftDriveEncoder;
	public static EEncoder shooterEncoder;

	// Sensor
	public static SimGyro gyroSensor;
	
	//Solenoid
	public static DoubleSolenoid gearShiftSolenoid;
	public static DoubleSolenoid gearDown;

	// USB
	public static EJoystick	driveJoystick;
	public static EGamepad controlGamepad;
	
	// Power Panel
	public static PowerDistributionPanel powerPanel;

	public static Compressor compressor;

 	// PIDs
 	public static SimPID shooterPID;
 	public static SimPID gyroPID;
 	public static SimPID drivePID;

 	public static DigitalInput[] inputs;

	/**
	 * Initialize all of the subsystems, assumes that the constants file has been read already
	 */
	public static void initialize()
	{
		// Motors
		leftFrontDrive = new CANTalon(getConstantAsInt(CAN_RIGHT_FRONT_DRIVE));
		leftRearDrive = new CANTalon(getConstantAsInt(CAN_RIGHT_REAR_DRIVE));
		rightFrontDrive = new CANTalon(getConstantAsInt(CAN_LEFT_FRONT_DRIVE));
		rightRearDrive = new CANTalon(getConstantAsInt(CAN_LEFT_REAR_DRIVE));
		shooter = new CANTalon(getConstantAsInt(CAN_SHOOTER));
		indexer = new Victor(getConstantAsInt(PWM_INDEXER));
		pickup = new Victor(getConstantAsInt(PWM_PICKUP));
		climber = new Victor(getConstantAsInt(PWM_CLIMBER));

		// Drive
		robotDrive = new RobotDrive(leftFrontDrive, leftRearDrive, rightFrontDrive, rightRearDrive);
		
		// Encoders
		rightDriveEncoder = new Encoder(getConstantAsInt(DIO_RIGHT_ENCODER_A), getConstantAsInt(DIO_RIGHT_ENCODER_B), true);
		leftDriveEncoder = new Encoder(getConstantAsInt(DIO_LEFT_ENCODER_A), getConstantAsInt(DIO_LEFT_ENCODER_B), true);
		shooterEncoder = new EEncoder(getConstantAsInt(DIO_SHOOTER_ENCODER_A), getConstantAsInt(DIO_SHOOTER_ENCODER_B));

		// USB
		driveJoystick = new EJoystick(getConstantAsInt(USB_DRIVE_STICK));
		controlGamepad = new EGamepad(getConstantAsInt(USB_CONTROL_GAMEPAD));

		// Power Panel
		powerPanel = new PowerDistributionPanel();
		
		//Compressor
		compressor = new Compressor();

		
		//Solenoid - Gear shift
		gearShiftSolenoid = new DoubleSolenoid(getConstantAsInt(COMPRESSOR_CHANNEL), 
				getConstantAsInt(SOLENOID_SHIFTER_CHANNEL1),
				getConstantAsInt(SOLENOID_SHIFTER_CHANNEL2));
		
		gearDown = new DoubleSolenoid(getConstantAsInt(COMPRESSOR_CHANNEL),
				getConstantAsInt(SOLENOID_GEAR_CHANNEL1),
				getConstantAsInt(SOLENOID_GEAR_CHANNEL2));

		gyroSensor = new SimGyro(getConstantAsInt(AIO_GYRO_SENSOR));
		gyroSensor.initGyro();		
		initPID();

		// Set high gear by default
		GearToggler.setHighGear();
	}
	
	/**
	 * Public for testing purposes. Initializes the PID controllers.
	 */
	public static void initPID() {
		//PIDs
		shooterPID = new SimPID(
				getConstantAsDouble(SHOOTER_PID_P),
				getConstantAsDouble(SHOOTER_PID_I),
				getConstantAsDouble(SHOOTER_PID_D),
				getConstantAsDouble(SHOOTER_PID_E)
				);

		gyroPID = new SimPID(
				getConstantAsDouble(GYRO_PID_P),
				getConstantAsDouble(GYRO_PID_I),
				getConstantAsDouble(GYRO_PID_D),
				getConstantAsDouble(GYRO_PID_E)
				);

		drivePID = new SimPID(
				getConstantAsDouble(ENCODER_PID_P),
				getConstantAsDouble(ENCODER_PID_I),
				getConstantAsDouble(ENCODER_PID_D),
				getConstantAsDouble(ENCODER_PID_E)
				);
	}
	
	/**
	 * Read in the encoder values from the autonomous config file. TODO:
	 * Integrate this with Georges' Constants class.
	 */
	public static void readEncoderValues(double encoderADistancePerPulseOverride, double encoderBDistancePerPulseOverride) {
		double encoderADistancePerPulse = encoderADistancePerPulseOverride;
		double encoderBDistancePerPulse = encoderBDistancePerPulseOverride;
		
		try {
			List<String> guavaResult = Files.readLines(new File(getConstant(CALIBRATION_FILE_LOC)), Charsets.UTF_8);
			Iterable<String> guavaResultFiltered = Iterables.filter(guavaResult, AutoHelper.SKIP_COMMENTS);

			String[] s = Iterables.toArray(AutoHelper.SPLITTER.split(guavaResultFiltered.iterator().next()), String.class);

			if (encoderADistancePerPulse == 0) {
				encoderADistancePerPulse = Double.parseDouble(s[0]);
			}
			if (encoderBDistancePerPulse == 0) {
				encoderBDistancePerPulse = Double.parseDouble(s[1]);
			} 

			System.out.println("EncoderADistancePerPulse:" + encoderADistancePerPulse + ", EncoderBDistancePerPulse:" + encoderBDistancePerPulse);
			leftDriveEncoder.setDistancePerPulse(encoderADistancePerPulse);
			rightDriveEncoder.setDistancePerPulse(encoderBDistancePerPulse);
		} catch (IOException e) {
			System.out.println("Calibration file read error!");
			leftDriveEncoder.setDistancePerPulse(encoderADistancePerPulseOverride);
			rightDriveEncoder.setDistancePerPulse(encoderBDistancePerPulseOverride);
		}
	}
}
