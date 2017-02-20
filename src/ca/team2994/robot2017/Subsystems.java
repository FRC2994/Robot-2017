package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.USB_CONTROL_GAMEPAD;
import static ca.team2994.frc.utils.Constants.USB_DRIVE_STICK;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;

import ca.team2994.frc.controls.EGamepad;
import ca.team2994.frc.controls.EJoystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;


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
//		controlGamepad = new EGamepad(getConstantAsInt(USB_CONTROL_GAMEPAD));

		// Power Panel
		powerPanel = new PowerDistributionPanel();

		//Compressor
		compressor = new Compressor();
	}
}
