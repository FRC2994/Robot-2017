package ca.team2994.robot2017;

import java.util.*;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class Shooter {
	CANTalon shootMotor;
	Victor loadMotor;
	Encoder shootEncoder;
	
	public Shooter() {
		shootMotor = new CANTalon(0);
		loadMotor = new Victor(1);
		shootEncoder = new Encoder(2);
	}
	
	public load() {
		loadMotor.set(0.5);
	}
}