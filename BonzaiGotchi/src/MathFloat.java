// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-17 updated by chappy
//            added getShort()
// 2006-12-19 updated by chappy
//            made it actually running ;)

// fixed to 3 decimals
public class MathFloat {

	public long value;
	
	public MathFloat (int tmpValue) {
		value = tmpValue;
	}
	
	public void add(int operand) {
		value += operand * 1000;
	}
	
	public void add(MathFloat operand) {
		value += operand.value;
	}
	
	public void subtract(int operand) {
		value -= operand * 1000;
	}
	
	public void subtract(MathFloat operand) {
		value -= operand.value;
	}
	
	public void multiply(int operand) {
		value *= operand;
	}
	
	public void multiply(MathFloat operand) {
		value = value * operand.value / 1000;
	}
	
	public void divide(int operand) {
		value /= operand;
	}
	
	public void divide(MathFloat operand) {
		value *= 1000;
		value /= operand.value;
	}
	
	public int getInt() {
		return (int)(value / 1000);
	}
	
	public short getShort() {
		return (short)(value / 1000);
	}
	
	// Statische Funktionen
	public static MathFloat multiply (MathFloat operand1, int operand2) {
		return new MathFloat ((int)(operand1.value * operand2));
	}
	
	public static MathFloat multiply (MathFloat operand1, MathFloat operand2) {
		return new MathFloat ((int)(operand1.value * operand2.value / 1000));
	}

	public static MathFloat divide (MathFloat operand1, MathFloat operand2) {
		return new MathFloat ((int)((operand1.value * 1000) / operand2.value));
	}
	
	public static MathFloat divide (int operand1, int operand2) {
		return new MathFloat (operand1 * 1000 / operand2);
	}
	
	public static MathFloat divide (int operand1, MathFloat operand2) {
		MathFloat tmpMathFloat = new MathFloat (operand1 * 1000);
		tmpMathFloat.divide(operand2);
		return tmpMathFloat;
	}
}
