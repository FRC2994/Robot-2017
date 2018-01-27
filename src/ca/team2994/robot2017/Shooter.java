package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.CAN_SHOOTER;
import static ca.team2994.frc.utils.Constants.INDEXER_SPEED;
import static ca.team2994.frc.utils.Constants.PWM_INDEXER;
import static ca.team2994.frc.utils.Constants.PWM_AGITATOR;
import static ca.team2994.frc.utils.Constants.getConstantAsDouble;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import ca.team2994.frc.controls.ButtonEntry;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends Subsystem {
	public enum Mode {
		PercentageVoltage, Speed
	}

	double previousRotations;
	long previousTime;
	double p, i, d, eps;
	int target = 0;

	private Mode mode = Mode.PercentageVoltage;
	private ControlMode controlMode = ControlMode.PercentOutput;

	TalonSRX shooter = new TalonSRX(getConstantAsInt(CAN_SHOOTER));
	Victor indexer = new Victor(getConstantAsInt(PWM_INDEXER));
	Victor agitator = new Victor(getConstantAsInt(PWM_AGITATOR));
	private static Shooter instance;

	public static int SHOOT_START_BUTTON = 2;

	public Shooter() {
		indexer.setInverted(true);
		// agitator.setInverted(true);

		shooter.set(controlMode, 0);

		driveJoystick.enableButton(1);
		controlGamepad.enableButton(SHOOT_START_BUTTON);
		driveJoystick.enableButton(8);
		driveJoystick.enableButton(9);

		instance = this;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		switch (mode) {
		case PercentageVoltage: {
			controlMode = ControlMode.PercentOutput;
		}
		case Speed: {
			shooter.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
			shooter.setInverted(true);
			shooter.selectProfileSlot(0, 0);
			controlMode = ControlMode.Velocity;
			//shooter.configEncoderCodesPerRev(128);
			// Since the above method was removed, these values are wrong (all calcs happen in native sensor units now)
			shooter.config_kF(0, 0.96, 0);
			shooter.config_kP(0, 0.6, 0);
			shooter.config_kI(0, 0.0, 0);
			shooter.config_kD(0, 0.0, 0);
		}
		}
	}

	@Override
	public void initTeleop() {
		shooter.set(controlMode, 0);
		agitator.set(0);
		indexer.set(0);
	}

	public void load() {
		indexer.set(getConstantAsDouble(INDEXER_SPEED));
		agitator.set(1.0);
	}

	public void stopLoading() {
		indexer.set(0);
		agitator.set(0);
	}

	double speedTarget = 0.0;
	boolean shootToggle = false;

	@Override
	public void tickTeleop() {
		if (driveJoystick.getEvent(1) == ButtonEntry.EVENT_CLOSED) {
			load();
		} else if (driveJoystick.getEvent(1) == ButtonEntry.EVENT_OPENED) {
			stopLoading();
		}

		if (controlGamepad.getEvent(SHOOT_START_BUTTON) == ButtonEntry.EVENT_CLOSED) {
			if (shootToggle) {
				shooter.set(controlMode, 0.75);
			} else {
				shooter.set(controlMode, 0);
			}

			shootToggle = !shootToggle;
		}
	}

	public void shootAtPercentageVoltage(double percentVoltage) {
		controlMode = ControlMode.PercentOutput;
		shooter.set(controlMode, percentVoltage);
	}

	public void stopShooting() {
		shooter.set(controlMode, 0);
	}

	public void shootAtDesiredSpeed(int rpm) {
		
	}

	@Override
	void initTesting() {
		driveJoystick.enableButton(1);
		driveJoystick.enableButton(2);
		driveJoystick.enableButton(3);
		driveJoystick.enableButton(4);
		driveJoystick.enableButton(5);
		driveJoystick.enableButton(6);
		driveJoystick.enableButton(7);
		driveJoystick.enableButton(8);
		driveJoystick.enableButton(9);
		driveJoystick.enableButton(10);
		driveJoystick.enableButton(11);
	}

	int ticks = 0;
	public static final int TARGET_INC = 128;

	public double toRPM(double val) {
		return (((val / 4) / 128) * 600);
	}

	public void tickTesting() {
		if (ticks % 50 == 0) {
			double errdiff = target != 0 ? ((double) shooter.getClosedLoopError(0)) / (target) : 0;
			System.out.println("Target " + toRPM(target));
			System.out.println("Error " + errdiff);
			System.out.println("Speed " + toRPM(shooter.getSelectedSensorVelocity(0)));
		}

		ticks++;

		if (driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED) {
			target += TARGET_INC;
		}

		if (driveJoystick.getEvent(10) == ButtonEntry.EVENT_CLOSED) {
			target -= TARGET_INC;
		}

		if (driveJoystick.getEvent(1) == ButtonEntry.EVENT_CLOSED) {
			target = 0;
		}
		if (driveJoystick.getRawButton(4)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				p += 0.1;
				System.out.println("p value = " + p);
			}

			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				p -= 0.1;
				System.out.println("p value = " + p);
			}
		}

		if (driveJoystick.getRawButton(3)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				i += 0.1;
				System.out.println("i value = " + i);
			}

			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				i -= 0.1;
				System.out.println("i value = " + i);
			}
		}

		if (driveJoystick.getRawButton(5)) {
			if (driveJoystick.getEvent(6) == ButtonEntry.EVENT_CLOSED) {
				d += 0.1;
				System.out.println("d value = " + d);
			}

			if (driveJoystick.getEvent(7) == ButtonEntry.EVENT_CLOSED) {
				d -= 0.1;
				System.out.println("d value = " + d);
			}
		}

		if (driveJoystick.getEvent(8) == ButtonEntry.EVENT_CLOSED) {
			shooter.config_kP(0, p, 0);
			shooter.config_kI(0, i, 0);
			shooter.config_kD(0, d, 0);
			System.out.printf("p = %f, i = %f, d = %f", p, i, d);
		}

		shooter.set(controlMode, target);
	}

	public static Shooter getInstance() {
		return instance;
	}
}