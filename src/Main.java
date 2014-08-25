/**
 * Created with IntelliJ IDEA.
 * User: Mangos
 * Date: 8/24/14
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {

	//weight
	private static final double W_USEFUL			= 0.84 + 0.1 + 3 * 0.15 + 4 * 0.005;

	private static final double W_S4_TOTAL			= W_USEFUL + 0.05 + 2 * (0.5625) + 2 * (1.125) + 0.5;
	private static final double W_S4_DRY			= W_USEFUL + 0.05 + 2 * (0.5625 - 0.49) + 2 * (1.125 - 1) + 0.5;

	private static final double W_S3_TOTAL			= W_S4_TOTAL + 0.05 + (0.5625) + (2.25) + 0.5;
	private static final double W_S3_DRY			= W_S4_TOTAL + 0.05 + (0.5625 - 0.49) + (2.25 - 2) + 0.5;

	private static final double W_S2_TOTAL			= W_S3_TOTAL + 0.05 + 0.15 + 3 * (0.5625) + 3 * (2.25) + 3 * 1.25;
	private static final double W_S2_DRY			= W_S3_TOTAL + 0.05 + 0.15 + 3 * (0.5625 - 0.49) + 3 * (2.25 - 2) + 3 * 1.25;

	private static final double W_S1_TOTAL			= W_S2_TOTAL + 3 * 0.05 + 15 * (4.5) + 3 * 1.5 + 3 * 1.25;
	private static final double W_S1_DRY			= W_S2_TOTAL + 3 * 0.05 + 15 * (4.5 - 4)  + 3 * 1.5 + 3 * 1.25;


	public static final void main(final String[] _args) {
		System.out.println(W_USEFUL);
//		System.out.println(W_S1_TOTAL);
//		System.out.println(W_S1_DRY);
//		System.out.println(W_S1_TOTAL / W_S1_DRY);

//		final double[] thrusts = { 215000, 215000, 215000, 315000, 315000, 315000 };
//		final double[] isps = { 350, 350, 350, 240, 240, 240 };

//		System.out.println("Average Isp = " + Calculations.calcAverageIsp(thrusts, isps));
//
//		System.out.println("Znum = " + Calculations.calcZnum(3300, 350, Data.G0_KERBIN));
//		System.out.println("deltaV = " + Calculations.calcDeltaV(390, 9.82, 15.2275, 5.2275));
//		System.out.println("TWR = " + Calculations.calcTWR(0, 0, 0));
	}

}

class Calculations {

	/**
	 * Calculating Tsiolkovsky's number.
	 * @param _deltaV needed delta V in m/s.
	 * @param _isp engine's specific impulse in s.
	 * @param _g0 gravitational acceleration in m/s^2.
	 * @return Tsiolkovsky's number.
	 */
	public static final double calcZnum(final double _deltaV, final double _isp, final double _g0) {
		return Math.exp(_deltaV / (_isp * _g0));
	}

	/**
	 * Calculate delta v using Tsiolkovsky's formula.
	 * @param _isp engine's specific impulse in s.
	 * @param _g0 gravitational acceleration in m/s^2.
	 * @param _mtotal total mass of rocket.
	 * @param _mdry mass of rocket without fuel.
	 * @return delta v.
	 */
	public static final double calcDeltaV(final double _isp, final double _g0, final double _mtotal, final double _mdry) {
		return _isp * _g0 * Math.log(_mtotal / _mdry);
	}

	/**
	 * Calculate thrust to weight ratio.
	 * @param _ft thrust of the engines in N.
	 * @param _mt total mass of the craft in kg.
	 * @param _g0 local gravitational acceleration in m/s^2.
	 * @return twr.
	 */
	public static final double calcTWR(final double _ft, final double _mt, final double _g0) {
		return _ft / _mt * _g0;
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
}

class Data {

	public static final double G0_KERBIN			= 9.82;
	public static final double G0_MUN				= 1.63;

}
