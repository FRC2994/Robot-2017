package ca.team2994.frc.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants
{
	private static final String CONSTANTS_FILE_NAME = 			"/home/lvuser/constants.properties";
	
	private static Properties defaults = new Properties();
	private static Properties constants = new Properties();
	
	// CAN
	public static final String CAN_RIGHT_FRONT_DRIVE =			"CAN_RIGHT_FRONT_DRIVE";
	public static final String CAN_RIGHT_REAR_DRIVE =			"CAN_RIGHT_REAR_DRIVE";
	public static final String CAN_LEFT_FRONT_DRIVE = 			"CAN_LEFT_FRONT_DRIVE";
	public static final String CAN_LEFT_REAR_DRIVE =			"CAN_LEFT_REAR_DRIVE";
	public static final String CAN_SHOOTER = 					"CAN_SHOOTER";
	
	// PWM
	public static final String PWM_INDEXER = "PWM_INDEXER";
	
	// DIO
	public static final String DIO_RIGHT_ENCODER_A =			"DIO_RIGHT_ENCODER_A";
	public static final String DIO_RIGHT_ENCODER_B =			"DIO_RIGHT_ENCODER_B";
	public static final String DIO_LEFT_ENCODER_A =				"DIO_LEFT_ENCODER_A";
	public static final String DIO_LEFT_ENCODER_B =				"DIO_LEFT_ENCODER_B";
	
	public static final String DIO_SHOOTER_ENCODER_A =			"DIO_SHOOTER_ENCODER_A";
	public static final String DIO_SHOOTER_ENCODER_B =			"DIO_SHOOTER_ENCODER_B";
	public static final String SHOOTER_ENCODER_CALIBRATION = 	"SHOOTER_ENCODER_CALIBRATION";
	
	// Analog IO
	public static final String AIO_GYRO_SENSOR =				"AIO_GYRO_SENSOR";

	// USB
	public static final String USB_DRIVE_STICK =				"USB_RIGHT_STICK";
	public static final String USB_CONTROL_GAMEPAD =			"USB_CONTROL_GAMEPAD";
	
	// Solenoids
	public static final String PCM_SHIFTER_A = 					"PCM_SHIFTER_A";
	public static final String PCM_SHIFTER_B = 					"PCM_SHIFTER_B";
	
	//PID
	public static final String SHOOTER_PID_P =					"SHOOTER_PID_P";
	public static final String SHOOTER_PID_I =					"SHOOTER_PID_I";
	public static final String SHOOTER_PID_D =					"SHOOTER_PID_D";
	public static final String SHOOTER_PID_E =					"SHOOTER_PID_E";
	
	public static final String GYRO_PID_P = 					"GYRO_PID_P";
	public static final String GYRO_PID_I = 					"GYRO_PID_I";
	public static final String GYRO_PID_D = 					"GYRO_PID_D";
	public static final String GYRO_PID_E = 					"GYRO_PID_E";
	public static final String GYRO_PID_MAX = 					"GYRO_PID_MAX";
	
	public static final String ENCODER_PID_P = 					"ENCODER_PID_P";
	public static final String ENCODER_PID_I = 					"ENCODER_PID_I";
	public static final String ENCODER_PID_D = 					"ENCODER_PID_D";
	public static final String ENCODER_PID_E = 					"ENCODER_PID_E";
	public static final String ENCODER_PID_MAX = 				"ENCODER_PID_MAX";
	

	public static final String CALIBRATION_FILE_LOC =			"CALIBRATION_FILE_LOC";
	public static final String CALIBRATION_BUTTON = 			"CALIBRATION_BUTTON";
	
	//Compressor channel
	public static final String COMPRESSOR_CHANNEL =				"COMPRESSOR_CHANNEL";
	

	//Double Solenoid Channels
	public static final String SOLENOID_SHIFTER_CHANNEL1 =		"SOLENOID_SHIFTER_CHANNEL1";
	public static final String SOLENOID_SHIFTER_CHANNEL2 =		"SOLENOID_SHIFTER_CHANNEL2";
	
	// Shooter
	public static final String INDEXER_SPEED = "INDEXER_SPEED";

	//Gamepad Buttons
	

	//Joystick Buttons
	public static final String JOYSTICK_CALIBRATE  =			"JOYSTICK_CALIBRATE";

	static {
		// CAN
		defaults.put(CAN_RIGHT_FRONT_DRIVE, "1");
		defaults.put(CAN_RIGHT_REAR_DRIVE, "2");
		defaults.put(CAN_LEFT_FRONT_DRIVE, "3");
		defaults.put(CAN_LEFT_REAR_DRIVE, "4");
		defaults.put(CAN_SHOOTER, "1");

		// PWM
		defaults.put(PWM_INDEXER, "0");

		// DIO
		defaults.put(DIO_RIGHT_ENCODER_A, "0");
		defaults.put(DIO_RIGHT_ENCODER_B, "1");
		defaults.put(DIO_LEFT_ENCODER_A, "2");
		defaults.put(DIO_LEFT_ENCODER_B, "3");

		defaults.put(DIO_SHOOTER_ENCODER_A, "4");
		defaults.put(DIO_SHOOTER_ENCODER_B, "5");
		defaults.put(SHOOTER_ENCODER_CALIBRATION, "0.0078125");

		// Analog IO
		defaults.put(AIO_GYRO_SENSOR, "0");

		// USB
		defaults.put(USB_DRIVE_STICK, "0");
		defaults.put(USB_CONTROL_GAMEPAD, "1");

		// Solenoid
		defaults.put(PCM_SHIFTER_A, "0");
		defaults.put(PCM_SHIFTER_B, "1");
		
		//PID
		defaults.put(SHOOTER_PID_P, "0");
		defaults.put(SHOOTER_PID_I, "0");
		defaults.put(SHOOTER_PID_D, "0");
		// The forklift PID works at encoder-value-scale so we don't need as much accuracy.
		defaults.put(SHOOTER_PID_E, "5");

		defaults.put(GYRO_PID_P, "0.05");
		defaults.put(GYRO_PID_I, "0.01");
		defaults.put(GYRO_PID_D, "0.01");
		defaults.put(GYRO_PID_E, "5.0");
		defaults.put(GYRO_PID_MAX, "0.4");

		defaults.put(ENCODER_PID_P, "0.5"); //2.16 (Ryan and Jacks value)
		defaults.put(ENCODER_PID_I, "0.0"); 
		defaults.put(ENCODER_PID_D, "0.0");
		defaults.put(ENCODER_PID_E, "0.1");
		defaults.put(ENCODER_PID_MAX, "0.2"); // 0.4 (Ryan and Jacks value) Too much jerk

		defaults.put(CALIBRATION_FILE_LOC, "/home/lvuser/calibration.txt");
		defaults.put(CALIBRATION_BUTTON, "2");
		
		//Compressor Channel
		defaults.put(COMPRESSOR_CHANNEL, "1");
		
		// Double Solenoid Channels
		defaults.put(SOLENOID_SHIFTER_CHANNEL1, "0");
		defaults.put(SOLENOID_SHIFTER_CHANNEL2, "1");
		
		// Shooter
		defaults.put(INDEXER_SPEED, "0.5");
		
		// Joystick buttons
		defaults.put(JOYSTICK_CALIBRATE, "1");

		constants.putAll(defaults);
	}
	
	/**
	 * Returns constant as a String
	 * @param constant name
	 * @return
	 */
	public static String getConstant(String name) {
		return constants.getProperty(name);
	}

	public static void setConstant(String name, String value) {
		constants.setProperty(name, value);
	}

	/**
	 * Returns constant as an int
	 * @param constant name
	 * @return
	 */
	public static int getConstantAsInt(String name) {
		return Integer.parseInt(constants.getProperty(name));
	}
	
	/**
	 * Returns constant as a double
	 * @param constant name
	 * @return
	 */
	public static double getConstantAsDouble(String name) {
		return Double.parseDouble(constants.getProperty(name));
	}

	public static void readConstantPropertiesFromFile() {
		Properties defaultsFromFile = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(CONSTANTS_FILE_NAME);			
			defaultsFromFile.load(in);
		} catch (IOException e) {
			System.out.println("Warning: Unable to load properties file " + CONSTANTS_FILE_NAME);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				System.out.println("Error: Unable to close properties file " + CONSTANTS_FILE_NAME);
				e.printStackTrace();
			}			
		}
		
		constants.putAll(defaultsFromFile);
	}
}
