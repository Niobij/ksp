/**
 * Created with IntelliJ IDEA.
 * User: ZOG
 * Date: 8/24/14
 * Time: 6:25 PM
 */
public class Main {

	//weight
	private static final double W_USEFUL			= 0.1 + 0.1 + 0.005 + 0.005 + 0.005 + 0.04 + 0.2 + 3 * 0.15;

	private static final double W_S4_TOTAL 			= W_USEFUL + 0.05 + 0.12 + 0.25 + 2 * (0.0175 + 0.005) + 4 * (0.05 + 0.05 + 0.005) + (0.5625) + 0.5;
	private static final double W_S4_DRY 			= W_S4_TOTAL - 0.49;

	private static final double W_S3_TOTAL			= W_S4_TOTAL + 0.5 + 0.55 + 3 * (0.015 + 0.1 + 0.2 + 0.05) + 4 * 0.005 + 12 * 0.05 + 21 * 0.15 + (0.5625) + (2.25) + 3 * (2.25 + 4.5) + 1.25;
	private static final double W_S3_DRY			= W_S3_TOTAL - 0.49 - 2 - 3 * (2 + 4);

	private static final double W_S2_TOTAL			= W_S3_TOTAL + 0.05 + 0.1;
	private static final double W_S2_DRY			= W_S2_TOTAL - 0;

	private static final double W_S1_TOTAL			= W_S2_TOTAL + 0;
	private static final double W_S1_DRY			= W_S1_TOTAL - 0;

	public static final void main(final String[] _args) {
		final double wTotal     = W_S2_TOTAL;
		final double wDry       = W_S2_DRY;
		final double tdratio    = W_S2_TOTAL / W_S2_DRY;

		System.out.format("Weigth total:\t%.4f t\nWeight dry:\t\t%.4f t\nT/D ratio:\t\t%.4f\n", wTotal, wDry, tdratio);

		final double isp = 350;
        final double deltaV = 1300;
		final double we = Calculations.calcWe(isp);
		final double znum = Calculations.calcZnum(deltaV, we);

		final double needFuel = wDry * znum - wDry;
        final double hasFuel = wTotal - wDry;
        final double remainFuel = needFuel - hasFuel;

		System.out.format("Znum:\t\t\t%.4f\nRemain fuel:\t%.4f t\n", znum, remainFuel);

		
//		System.out.println("deltaV = " + Calculations.calcDeltaV(we, 8.71, 3.71));
		System.out.format("TWR:\t\t\t%.4f\n", Calculations.calcTWR(215000, 33080, Data.G0_DUNA));

//		final double[] thrusts = { 650000, 650000, 2000000 };
//		final double[] isps = { 350, 350, 320 };
//
//		System.out.println("Average Isp = " + Calculations.calcAverageIsp(thrusts, isps));
	}

}

class Calculations {

	/**
	 * Calculating Tsiolkovsky's number.
	 * @param _deltaV needed delta V in m/s.
	 * @param _we engine's effective exhaust velocity (we).
	 * @return Tsiolkovsky's number.
	 */
	public static final double calcZnum(final double _deltaV, final double _we) {
		return Math.exp(_deltaV / _we);
	}

	/**
	 * Calculate delta v using Tsiolkovsky's formula.
	 * @param _we engine's effective exhaust velocity (we).
	 * @param _mtotal total mass of rocket.
	 * @param _mdry mass of rocket without fuel.
	 * @return delta v.
	 */
	public static final double calcDeltaV(final double _we, final double _mtotal, final double _mdry) {
		return _we * Math.log(_mtotal / _mdry);
	}

	/**
	 * Calculate thrust to weight ratio.
	 * @param _ft thrust of the engines in N.
	 * @param _m total mass of the craft in kg.
	 * @param _g0 local gravitational acceleration in m/s^2.
	 * @return twr.
	 */
	public static final double calcTWR(final double _ft, final double _m, final double _g0) {
		return _ft / (_m * _g0);
	}

	/**
	 * Calculate average Isp of multiple different engines. Data in array must be in
	 * proper order.
	 * @param _thrusts array of thrust of every engine.
	 * @param _isps array of specific impulse (Isp) of every engine.
	 * @return average Isp.
	 */
	public static final double calcAverageIsp(final double[] _thrusts, final double[] _isps) {
		if (_thrusts.length == 0 || _isps.length == 0) throw new RuntimeException("Thrusts or isps is empty");
		if (_thrusts.length != _isps.length) throw new RuntimeException("Thrusts and isps must be the same length");

		double result = 0;
		double thrustSum = 0;
		double thrustDivIspSum = 0;

		for (int i = 0; i < _thrusts.length; i++) {
			thrustSum += _thrusts[i];
			thrustDivIspSum += _thrusts[i] / _isps[i];
		}

		result = thrustSum / thrustDivIspSum;
		return result;
	}

	/**
	 * Calculate effective exhaust velocity from specific impulse.
	 * @param _isp specific impulse of engine.
	 * @return effective exhaust velocity (we).
	 */
	public static final double calcWe(final double _isp) {
		return _isp * Data.G0_KERBIN;
	}
}

class Data {

	public static final double G0_KERBIN			= 9.816;
	public static final double G0_MUN				= 1.63;
	public static final double G0_DUNA				= 2.94;

}
