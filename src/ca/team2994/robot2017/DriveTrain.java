package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.AIO_GYRO_SENSOR;
import static ca.team2994.frc.utils.Constants.CAN_LEFT_FRONT_DRIVE;
import static ca.team2994.frc.utils.Constants.CAN_LEFT_REAR_DRIVE;
import static ca.team2994.frc.utils.Constants.CAN_RIGHT_FRONT_DRIVE;
import static ca.team2994.frc.utils.Constants.CAN_RIGHT_REAR_DRIVE;
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
import static ca.team2994.frc.utils.Constants.PCM_CAN;
import static ca.team2994.frc.utils.Constants.SOLENOID_SHIFTER_CHANNEL1;
import static ca.team2994.frc.utils.Constants.SOLENOID_SHIFTER_CHANNEL2;
import static ca.team2994.frc.utils.Constants.getConstantAsDouble;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.driveJoystick;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.team2994.frc.controls.ButtonEntry;
import ca.team2994.frc.controls.EJoystick;
import ca.team2994.frc.controls.SimGyro;
import ca.team2994.frc.utils.SimPID;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class DriveTrain extends Subsystem {
	TalonSRX leftFrontDrive = new TalonSRX(getConstantAsInt(CAN_RIGHT_FRONT_DRIVE));
	TalonSRX leftRearDrive = new TalonSRX(getConstantAsInt(CAN_RIGHT_REAR_DRIVE));
	TalonSRX rightFrontDrive = new TalonSRX(getConstantAsInt(CAN_LEFT_FRONT_DRIVE));
	TalonSRX rightRearDrive = new TalonSRX(getConstantAsInt(CAN_LEFT_REAR_DRIVE));
	
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
	
	public class TalonWrapperSpeedController implements SpeedController {
		private TalonSRX talon;
		
		public TalonWrapperSpeedController (TalonSRX talon) {
			this.talon = talon;
		}
		
		@Override
		public void pidWrite(double output) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void set(double speed) {
			talon.set(ControlMode.PercentOutput, speed);
		}

		@Override
		public double get() {
			return talon.getMotorOutputPercent();
		}

		@Override
		public void setInverted(boolean isInverted) {
			talon.setInverted(isInverted);
		}

		@Override
		public boolean getInverted() {
			return talon.getInverted();
		}

		@Override
		public void disable() {
			this.set(0);
		}

		@Override
		public void stopMotor() {
			this.disable();			
		}
	}
	
	public static DriveTrain getInstance() {
		return instance;
	}
	
	public DriveTrain() {
		// Set the rear drives to follow the left and right front drives
		leftRearDrive.set(ControlMode.Follower, leftFrontDrive.getDeviceID());
		rightRearDrive.set(ControlMode.Follower, rightFrontDrive.getDeviceID());

		robotDrive = new RobotDrive(new TalonWrapperSpeedController(leftFrontDrive), new TalonWrapperSpeedController(rightFrontDrive));

		rightDriveEncoder.setDistancePerPulse(0.026);
		leftDriveEncoder.setDistancePerPulse(0.001);
		rightDriveEncoder.setReverseDirection(true);
		leftDriveEncoder.setReverseDirection(true);

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
		leftRearDrive.setNeutralMode(brakeOrCoast == BrakeCoastStatus.BRAKE ? NeutralMode.Brake : NeutralMode.Coast);
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
		
		

		System.out.println("Encoder: " + getLeftEncoderValue() + ", " + getRightEncoderValue() + ", dist = " + getDistance());
		System.out.println("Gyro: " + gyro.getAngle());

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
