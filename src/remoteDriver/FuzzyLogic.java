package remoteDriver;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Rule;

public class FuzzyLogic {
	private static String FILEPATH = "./truck.fcl";
	private static String ANGLE_NAME = "angle";
	private static String XPOSITION_NAME = "xposition";
	private static String DIRECTION_NAME = "direction";

	FIS fis;

	public FuzzyLogic() {

	}

	public void initialize() {
		this.fis = FIS.load(FILEPATH, true);
	}

	public double evaluate(double angle, double xposition) {
		this.fis.setVariable(ANGLE_NAME, angle); // Set inputs
		this.fis.setVariable(XPOSITION_NAME, xposition);
		this.fis.evaluate();
		double directionValue = this.fis.getVariable(DIRECTION_NAME).getValue();
		 for( Rule r : fis.getFunctionBlock("truckPark").getFuzzyRuleBlock("No1").getRules() )
		      System.out.println(r);
		return this.normalize(directionValue);
	}

	public double normalize(double angle) {
		return angle / 30;
	}
}
