package ca.team2994.robot2017;

import static ca.team2994.frc.utils.Constants.*;
import static ca.team2994.robot2017.Subsystems.*;

public class Shooter {
	public Shooter() {
		
	}
	
	public void load() {
		Subsystems.indexer.set(getConstantAsDouble(INDEXER_SPEED));
	}
	
	public void setShooterSpeed(int rpm) {
		// Set up the desired number of units.
		shooterPID.setDesiredValue(rpm);
		// Reset the encoder PID to a reasonable state.
		shooterPID.resetErrorSum();
		shooterPID.resetPreviousVal();
		// Used to make sure that the PID doesn't bail out as done
		// right away (we know both the distances are zero from the
		// above reset).
		shooterPID.calcPID(0);
	}
	
	public void tick() {
		Subsystems.shooterPID.calcPID(shooterEncoder.getDistance());
	}
}