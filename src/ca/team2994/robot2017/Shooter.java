package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.CAN_SHOOTER;
import static ca.team2994.frc.utils.Constants.INDEXER_SPEED;
import static ca.team2994.frc.utils.Constants.PWM_INDEXER;
import static ca.team2994.frc.utils.Constants.PWM_AGITATOR;
import static ca.team2994.frc.utils.Constants.getConstantAsDouble;
import static ca.team2994.frc.utils.Constants.getConstantAsInt;
import static ca.team2994.robot2017.Subsystems.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import ca.team2994.frc.controls.ButtonEntry;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends Subsystem {
	public enum Mode {
		PercentageVoltage,
		Speed
	}
	
	double previousRotations;
	long previousTime;
	double p, i, d, eps;
	int target = 0;
	
	private Mode mode = Mode.PercentageVoltage;

	CANTalon shooter = new CANTalon(getConstantAsInt(CAN_SHOOTER));
	Victor indexer = new Victor(getConstantAsInt(PWM_INDEXER));
	Victor agitator = new Victor(getConstantAsInt(PWM_AGITATOR));
	private static Shooter instance;
	
	public static int SHOOT_START_BUTTON = 2;

	public Shooter() {
		indexer.setInverted(true);
//		agitator.setInverted(true);

		shooter.set(0);
		
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
			
		}
		case Speed: {
			shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			shooter.reverseSensor(false);
			shooter.reverseOutput(true);
			shooter.configNominalOutputVoltage(+0.0f, -0.0f);
			shooter.configPeakOutputVoltage(+12.0f, -12.0f);
			shooter.setProfile(0);
			shooter.changeControlMode(TalonControlMode.Speed);
			shooter.configEncoderCodesPerRev(128);
			shooter.setF(0.96);
			shooter.setP(0.6);
			shooter.setI(0.0);
			shooter.setD(0.0);
		}
		}
	}

	@Override
	public void initTeleop() {
	}

	public void load() {
		System.out.println("loading");
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
		}
		else if (driveJoystick.getEvent(1) == ButtonEntry.EVENT_OPENED) {
			stopLoading();
		}

		if (controlGamepad.getEvent(SHOOT_START_BUTTON) == ButtonEntry.EVENT_CLOSED) {
			if (shootToggle) {
				shooter.set(0.75);
			}
			else {
				shooter.set(0);
			}

			shootToggle = !shootToggle;
		}
	}
	
	public void shootAtPercentageVoltage(double percentVoltage) {
		shooter.changeControlMode(TalonControlMode.PercentVbus);
		shooter.set(percentVoltage);
	}
	
	public void stopShooting() {
		shooter.set(0);
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
		return (((val/4)/128)*600);
	}
	
	public void tickTesting() {
		if (ticks % 50 == 0) {
			double errdiff = target != 0 ? ((double)shooter.getClosedLoopError())/(target) : 0;
			System.out.println("Target " + toRPM(target));
			System.out.println("Error " + errdiff);
			System.out.println("Speed " + toRPM(shooter.getSpeed()));
		}

		ticks++;

		if (driveJoystick.getEvent(11) == ButtonEntry.EVENT_CLOSED){
			target += TARGET_INC;
		}
		
		if (driveJoystick.getEvent(10) == ButtonEntry.EVENT_CLOSED){
			target -= TARGET_INC;
		}

		if (driveJoystick.getEvent(1) == ButtonEntry.EVENT_CLOSED){
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
			shooter.setP(p);
			shooter.setI(i);
			shooter.setD(d);
			System.out.printf("p = %f, i = %f, d = %f, f = %f", p, i, d, shooter.getF());
		}

		shooter.set(target);
	}

	public static Shooter getInstance() {
		return instance;
	}
}