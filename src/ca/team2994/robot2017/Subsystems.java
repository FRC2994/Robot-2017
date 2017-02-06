package ca.team2994.robot2017;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ca.team2994.frc.autonomous.AutoHelper;
import ca.team2994.frc.controls.EEncoder;
import ca.team2994.frc.controls.EGamepad;
import ca.team2994.frc.controls.EJoystick;
import ca.team2994.frc.controls.ERobotDrive;
import ca.team2994.frc.controls.Motor;
import ca.team2994.frc.controls.SimGyro;
import ca.team2994.frc.utils.Constants;
import ca.team2994.frc.utils.SimPID;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;


public class Subsystems {	
	// Motors
	public static Motor rightFrontDrive;
	public static Motor rightRearDrive;
	public static Motor leftFrontDrive;
	public static Motor leftRearDrive;
	public static Motor rightArmMotor;
	public static Motor leftArmMotor;
	public static Motor forkliftMotor;
	public static Motor conveyorMotor;
	
	// Drive
	public static ERobotDrive robotDrive;

	// Encoders
	public static Encoder rightDriveEncoder;
	public static Encoder leftDriveEncoder;
	public static EEncoder shooterEncoder;

	// Sensor
	public static DigitalInput toteDetectionSensor;
	public static SimGyro gyroSensor;
	
	//Solenoid - Gear control
	public static DoubleSolenoid gearShiftSolenoid;
	// USB
	public static EJoystick	driveJoystick;
	public static EGamepad controlGamepad;
	
	// Power Panel
	public static PowerDistributionPanel powerPanel;
	
	// Bling
	public static SerialPort blingPort;
	
	public static Compressor compressor;
 	
 	// PIDs
 	public static SimPID shooterPID;
 	public static SimPID gyroPID;
 	public static SimPID encoderPID;

 	public static DigitalInput[] inputs;

	/**
	 * Initialize all of the subsystems, assumes that the constants file has been read already
	 */
	public static void initialize()
	{
		// Motors
		leftFrontDrive = new Motor(Constants.getConstantAsInt(Constants.PWM_RIGHT_FRONT_DRIVE), Constants.getConstantAsInt(Constants.MOTOR_TYPE_DRIVE));
		leftRearDrive = new Motor(Constants.getConstantAsInt(Constants.PWM_RIGHT_REAR_DRIVE), Constants.getConstantAsInt(Constants.MOTOR_TYPE_DRIVE));
		rightFrontDrive = new Motor(Constants.getConstantAsInt(Constants.PWM_LEFT_FRONT_DRIVE), Constants.getConstantAsInt(Constants.MOTOR_TYPE_DRIVE));
		rightRearDrive = new Motor(Constants.getConstantAsInt(Constants.PWM_LEFT_REAR_DRIVE), Constants.getConstantAsInt(Constants.MOTOR_TYPE_DRIVE));
		rightArmMotor = new Motor(Constants.getConstantAsInt(Constants.PWM_RIGHT_ARM), Constants.getConstantAsInt(Constants.MOTOR_TYPE_ARM));
		leftArmMotor = new Motor(Constants.getConstantAsInt(Constants.PWM_LEFT_ARM), Constants.getConstantAsInt(Constants.MOTOR_TYPE_ARM));
		forkliftMotor = new Motor(Constants.getConstantAsInt(Constants.PWM_FORKLIFT), Constants.getConstantAsInt(Constants.MOTOR_TYPE_FORKLIFT));
		conveyorMotor = new Motor(Constants.getConstantAsInt(Constants.PWM_CONVEYOR), Constants.getConstantAsInt(Constants.MOTOR_TYPE_CONVEYOR));
		
		// Drive
		robotDrive = new ERobotDrive(leftFrontDrive, leftRearDrive, rightFrontDrive, rightRearDrive);
		
		// Encoders
		rightDriveEncoder = new Encoder(Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_A), Constants.getConstantAsInt(Constants.DIO_RIGHT_ENCODER_B), true);
		leftDriveEncoder = new Encoder(Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_A), Constants.getConstantAsInt(Constants.DIO_LEFT_ENCODER_B), true);
		shooterEncoder = new EEncoder(Constants.getConstantAsInt(Constants.DIO_SHOOTER_ENCODER_A), Constants.getConstantAsInt(Constants.DIO_SHOOTER_ENCODER_B));

		// USB
		driveJoystick = new EJoystick(Constants.getConstantAsInt(Constants.USB_DRIVE_STICK));
		controlGamepad = new EGamepad(Constants.getConstantAsInt(Constants.USB_CONTROL_GAMEPAD));

		// Power Panel
		powerPanel = new PowerDistributionPanel();
		
		//Compressor
		compressor = new Compressor();

		
		//Solenoid - Gear shift
		gearShiftSolenoid = new DoubleSolenoid(Constants.getConstantAsInt(Constants.COMPRESSOR_CHANNEL), 
				Constants.getConstantAsInt(Constants.SOLENOID_SHIFTER_CHANNEL1),
				Constants.getConstantAsInt(Constants.SOLENOID_SHIFTER_CHANNEL2));
		
		gyroSensor = new SimGyro(Constants.getConstantAsInt(Constants.AIO_GYRO_SENSOR));
		gyroSensor.initGyro();		
		initPID();
		
		// Set low gear by default
		robotDrive.setLowGear();
	}
	
	/**
	 * Public for testing purposes. Initializes the PID controllers.
	 */
	public static void initPID() {
		//PIDs
		shooterPID = new SimPID(
				Constants.getConstantAsDouble(Constants.SHOOTER_PID_P),
				Constants.getConstantAsDouble(Constants.SHOOTER_PID_I),
				Constants.getConstantAsDouble(Constants.SHOOTER_PID_D),
				Constants.getConstantAsDouble(Constants.SHOOTER_PID_E)
				);

		gyroPID = new SimPID(
				Constants.getConstantAsDouble(Constants.GYRO_PID_P),
				Constants.getConstantAsDouble(Constants.GYRO_PID_I),
				Constants.getConstantAsDouble(Constants.GYRO_PID_D),
				Constants.getConstantAsDouble(Constants.GYRO_PID_E)
				);

		encoderPID = new SimPID(
				Constants.getConstantAsDouble(Constants.ENCODER_PID_P),
				Constants.getConstantAsDouble(Constants.ENCODER_PID_I),
				Constants.getConstantAsDouble(Constants.ENCODER_PID_D),
				Constants.getConstantAsDouble(Constants.ENCODER_PID_E)
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
			List<String> guavaResult = Files.readLines(new File(Constants.getConstant(Constants.CALIBRATION_FILE_LOC)), Charsets.UTF_8);
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
