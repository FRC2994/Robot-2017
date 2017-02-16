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
	// USB
	public static EJoystick	driveJoystick;
	public static EGamepad controlGamepad;
	
	// Power Panel
	public static PowerDistributionPanel powerPanel;

	public static Compressor compressor;

 	public static DigitalInput[] inputs;

	/**
	 * Initialize all of the subsystems, assumes that the constants file has been read already
	 */
	public static void initialize()
	{
		// USB
		driveJoystick = new EJoystick(getConstantAsInt(USB_DRIVE_STICK));
		controlGamepad = new EGamepad(getConstantAsInt(USB_CONTROL_GAMEPAD));

		// Power Panel
		powerPanel = new PowerDistributionPanel();

		//Compressor
		compressor = new Compressor();
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
