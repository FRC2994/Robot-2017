package ca.team2994.frc.autonomous;

import java.util.ArrayList;
import java.util.List;

import ca.team2994.frc.autonomous.modes.BasicRobotSetMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import static ca.team2994.frc.utils.Constants.*;

public class AutoModeSelector {
	private static List<Class<? extends AutoMode>> modes = new ArrayList<>();
	
	private AnalogInput autoSelector = new AnalogInput(getConstantAsInt(AIO_AUTO_SELECT));
	
	private static int NUM_POSSIBLE_MODES = 12;
	
	static {
		//TODO: Add all autonomous modes here.

		modes.add(BasicRobotSetMode.class);
	}
	
	public AutoMode selectMode() {
		double voltage = autoSelector.getVoltage();
		int modeNo = (int)(voltage/NUM_POSSIBLE_MODES);

		if (modeNo > modes.size()-1) {
			modeNo = 0;
		}

		try {
			return modes.get(modeNo).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("Cannot create class.");
			return new BasicRobotSetMode();
		}
	}
}
