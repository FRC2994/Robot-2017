package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.AIO_GYRO_SENSOR;
import static ca.team2994.frc.utils.Constants.CAN_LEFT_FRONT_DRIVE;
import static ca.team2994.frc.utils.Constants.CAN_LEFT_REAR_DRIVE;
import static ca.team2994.frc.utils.Constants.CAN_RIGHT_FRONT_DRIVE;
import static ca.team2994.frc.utils.Constants.CAN_RIGHT_REAR_DRIVE;
import static ca.team2994.frc.utils.Constants.PCM_CAN;
import static ca.team2994.frc.utils.Constants.DIO_LEFT_ENCODER_A;
import static ca.team2994.frc.utils.Constants.DIO_LEFT_ENCODER_B;
import static ca.team2994.frc.utils.Constants.DIO_RIGHT_ENCODER_A;
import static ca.team2994.frc.utils.Constants.DIO_RIGHT_ENCODER_B;
import static ca.team2994.frc.utils.Constants.ENCODER_PID_D;
import static ca.team2994.frc.utils.Constants.ENCODER_PID_E;
import static ca.team2994.frc.utils.Constants.ENCODER_PID_I;
import static ca.team2994.frc.utils.Constants.ENCODER_PID_P;
import static ca.team2994.frc.utils.Constants.GYRO_PID_D;
import static ca.team2994.frc.utils.Constants.GYRO_PID_E;
import static ca.team2994.frc.utils.Constants.GYRO_PID_I;
import static ca.team2994.frc.utils.Constants.GYRO_PID_P;
import static ca.team2994.frc.utils.Constants.SOLENOID_SHIFTER_CHANNEL1;
import static ca.team2994.frc.utils.Constants.SOLENOID_SHIFTER_CHANNEL2;
import static ca.team2994.frc.utils.Constants.getConstantAsDouble;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.driveJoystick;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import ca.team2994.frc.controls.ButtonEntry;
import ca.team2994.frc.controls.EJoystick;
import ca.team2994.frc.controls.SimGyro;
import ca.team2994.frc.utils.SimPID;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain extends Subsystem {
	CANTalon leftFrontDrive = new CANTalon(getConstantAsInt(CAN_RIGHT_FRONT_DRIVE));
	CANTalon leftRearDrive = new CANTalon(getConstantAsInt(CAN_RIGHT_REAR_DRIVE));
	CANTalon rightFrontDrive = new CANTalon(getConstantAsInt(CAN_LEFT_FRONT_DRIVE));
	CANTalon rightRearDrive = new CANTalon(getConstantAsInt(CAN_LEFT_REAR_DRIVE));
	
	Encoder rightDriveEncoder = new Encoder(getConstantAsInt(DIO_RIGHT_ENCODER_A), getConstantAsInt(DIO_RIGHT_ENCODER_B), true);
	Encoder leftDriveEncoder = new Encoder(getConstantAsInt(DIO_LEFT_ENCODER_A), getConstantAsInt(DIO_LEFT_ENCODER_B), true);

	AnalogGyro gyro = new SimGyro(getConstantAsInt(AIO_GYRO_SENSOR));
	
	RobotDrive robotDrive;
	
	SimPID gyroPID = new SimPID(
						getConstantAsDouble(GYRO_PID_P),
						getConstantAsDouble(GYRO_PID_I),
						getConstantAsDouble(GYRO_PID_D),
						getConstantAsDouble(GYRO_PID_E));

	SimPID autoDrivePID = new SimPID(
						getConstantAsDouble(ENCODER_PID_P),
						getConstantAsDouble(ENCODER_PID_I),
						getConstantAsDouble(ENCODER_PID_D),
						getConstantAsDouble(ENCODER_PID_E));
	
	DoubleSolenoid gearShiftSolenoid = new DoubleSolenoid(getConstantAsInt(PCM_CAN), 
											getConstantAsInt(SOLENOID_SHIFTER_CHANNEL1),
											getConstantAsInt(SOLENOID_SHIFTER_CHANNEL2));
	public static DriveTrain instance;
	
	public static DriveTrain getInstance() {
		return instance;
	}
	
	public DriveTrain() {
		// Set the rear drives to follow the left and right front drives
		leftRearDrive.changeControlMode(TalonControlMode.Follower);
		leftRearDrive.set(leftFrontDrive.getDeviceID());
		rightRearDrive.changeControlMode(TalonControlMode.Follower);
		rightRearDrive.set(rightFrontDrive.getDeviceID());

		robotDrive = new RobotDrive(leftFrontDrive, rightFrontDrive);

		rightDriveEncoder.setDistancePerPulse(0.00981770833);
		leftDriveEncoder.setDistancePerPulse(0.00981770833);
		rightDriveEncoder.setReverseDirection(true);

		gyro.initGyro();
		
		// Set low gear by default
		setLowGear();
		
		
		instance = this;
	}
	
	public void reset() {
		autoDrivePID.setDesiredValue(0);
		// Reset the encoder PID to a reasonable state.
		autoDrivePID.resetErrorSum();
		autoDrivePID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know both the distances are zero from the
		// above reset).
		autoDrivePID.calcPID(0);

		// Reset the gyro PID to a reasonable state.
		gyroPID.resetErrorSum();
		gyroPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know the gyro angle is zero from the above
		// reset).
		gyroPID.calcPID(0);
		
		this.resetEncoders();
		this.resetGyro();
	}
	
	public void driveWithCurve(double speed, double turn) {
		robotDrive.drive(speed, turn);
	}
	
	public void arcadeDrive(EJoystick driveJoystick) {
		robotDrive.arcadeDrive(driveJoystick);
	}
	
	public void resetEncoders() {
		leftDriveEncoder.reset();
		rightDriveEncoder.reset();
	}
	
	public enum BrakeCoastStatus {
		BRAKE,
		COAST
	}
	
	public void setBrakeCoast(BrakeCoastStatus brakeOrCoast) {
		leftRearDrive.enableBrakeMode(brakeOrCoast == BrakeCoastStatus.BRAKE ? true : false);
	}
	
	public void setLowGear() {
		gearShiftSolenoid.set(Value.kReverse);
	}
	
	public void setHighGear() {
		gearShiftSolenoid.set(Value.kForward);
	}
	
	public double getHeading() {
		return gyro.getAngle();
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
	public SimPID getAutoDrivePID() {
		return autoDrivePID;
	}
	
	public SimPID getTurnPID() {
		return gyroPID;
	}
	
	public int getLeftEncoderValue() {
		return leftDriveEncoder.get();
	}

	public int getRightEncoderValue() {
		return rightDriveEncoder.get();
	}
	
	public double getDistance() {
		return rightDriveEncoder.getDistance();
	}
	
	@Override
	public void initTeleop() {
		// Set low gear & brake mode by default
		setBrakeCoast(BrakeCoastStatus.BRAKE);
		setLowGear();
		reset();

		robotDrive.setSafetyEnabled(false);
		driveJoystick.enableButton(7);
	}

	@Override
	public void tickTeleop() {
		if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
			setHighGear();
		}
		else if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_OPENED) {
			setLowGear();
		}
		System.out.println("Encoder: " + getRightEncoderValue() + ", " + getLeftEncoderValue());

		robotDrive.arcadeDrive(driveJoystick);
	}

	@Override
	public void tickTesting() {
		
	}

	@Override
	public void initTesting() {
		
	}

	public void setMotors(double leftMotors, double rightMotors) {
		robotDrive.setLeftRightMotorOutputs(leftMotors, rightMotors);
	}
}
