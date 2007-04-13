
public class MathCalc {

	public static int power(int operand, int power) {
		
		int result = 1;
		
		for (int i = 0; i < power; i++) {
			result *= operand;
		}
		
		return result;
	}
	
	public static int colorCombine (int rgb1, int rgb2, short ratio1, short ratio2) {
		
		int r1 = rgb1 / 0x10000;
		int g1 = rgb1 / 0x100 - r1 * 0x100;
		int b1 = rgb1 - r1 * 0x10000 - g1 * 0x100;
		
		int r2 = rgb2 / 0x10000;
		int g2 = rgb2 / 0x100 - r2 * 0x100;
		int b2 = rgb2 - r2 * 0x10000 - g2 * 0x100;
		
		int r = r1 * ratio1 / (ratio1 + ratio2) + r2 * ratio2 / (ratio1 + ratio2);
		int g = g1 * ratio1 / (ratio1 + ratio2) + g2 * ratio2 / (ratio1 + ratio2);
		int b = b1 * ratio1 / (ratio1 + ratio2) + b2 * ratio2 / (ratio1 + ratio2);
		
		return r * 0x10000 + g * 0x100 + b;
	}
	
	public static int getRandom(int range) {
		int tmpRandom;

		tmpRandom = GlobalVars.RANDOMGENERATOR.nextInt() % range;
		if (tmpRandom < 0) {
			tmpRandom *= -1;
		}
		return tmpRandom;
	}
}
