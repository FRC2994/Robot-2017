package ca.team2994.frc.autonomous.commands;

import ca.team2994.frc.autonomous.AutoCommand;
import ca.team2994.robot2017.Gear;
import ca.team2994.robot2017.Gear.Position;

public class MoveGearMech implements AutoCommand {
	Position position;
	
	public MoveGearMech(Position position) {
		this.position = position;
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		Gear.getInstance().setGearDirection(position);
	}

	@Override
	public boolean tick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
