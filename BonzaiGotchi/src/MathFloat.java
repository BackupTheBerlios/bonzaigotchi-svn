// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-17 updated by chappy
//            added getShort()
// 2006-12-19 updated by chappy
//            made it actually running ;)

// fixed to 3 decimals

/**
 * Stores and operates a float variable.
 */
public class MathFloat {

	public long value;
	
	/**
	 * Stores a int value into a long value.
	 * @param tmpValue
	 */
	public MathFloat (int tmpValue) {
		value = tmpValue;
	}
	
	/**
	 * Adds operand to value.
	 * @param operand
	 */
	public void add(int operand) {
		value += operand * 1000;
	}
	/**
	 * Adds and Mathfloat element to value.
	 * @param operand Mathfloat
	 */
	public void add(MathFloat operand) {
		value += operand.value;
	}
	
	/**
	 * Subtracts operand from value.
	 * @param operand
	 */
	public void subtract(int operand) {
		value -= operand * 1000;
	}
	
	/**
	 * Subtracts and Mathfloat element from value.
	 * @param operand Mathfloat
	 */
	public void subtract(MathFloat operand) {
		value -= operand.value;
	}
	
	/**
	 * Multiplied with operand.
	 * @param operand
	 */
	public void multiply(int operand) {
		value *= operand;
	}
	
	/**
	 * Multiplied with Mathfloat element.
	 * @param operand
	 */
	public void multiply(MathFloat operand) {
		value = value * operand.value / 1000;
	}
	
	/**
	 * Divided by operand.
	 * @param operand
	 */
	public void divide(int operand) {
		value /= operand;
	}
	
	/**
	 * Divided by Mathfloat element.
	 * @param operand
	 */
	public void divide(MathFloat operand) {
		value *= 1000;
		value /= operand.value;
	}
	
	/**
	 * Returns an int from value.
	 * @return
	 */
	public int getInt() {
		return (int)(value / 1000);
	}
	
	/**
	 * Returns a short from value.
	 * @return
	 */
	public short getShort() {
		return (short)(value / 1000);
	}
	
	// Statische Funktionen
	
	/**
	 * Multiplies a Mathfloat element with an integer.
	 * @param operand1
	 * @param operand2
	 * @return a new Mathfloat element.
	 */
	public static MathFloat multiply (MathFloat operand1, int operand2) {
		return new MathFloat ((int)(operand1.value * operand2));
	}
	/**
	 * Multiplies two Mathfloat elements
	 * @param operand1
	 * @param operand2* 
	 * @return a new Mathfloat element.
	 */
	public static MathFloat multiply (MathFloat operand1, MathFloat operand2) {
		return new MathFloat ((int)(operand1.value * operand2.value / 1000));
	}
	/**
	 * Divides two Mathfloat elements
	 * @param operand1
	 * @param operand2
	 * @return a new Mathfloat element.
	 */
	public static MathFloat divide (MathFloat operand1, MathFloat operand2) {
		return new MathFloat ((int)((operand1.value * 1000) / operand2.value));
	}
	
	/**
	 * Divides two integer
	 * @param operand1
	 * @param operand2
	 * @return a new Mathfloat element.
	 */
	public static MathFloat divide (int operand1, int operand2) {
		return new MathFloat (operand1 * 1000 / operand2);
	}
	
	/**
	 * Divides a Mathfloat element with an integer.
	 * @param operand1
	 * @param operand2
	 * @return
	 */
	public static MathFloat divide (int operand1, MathFloat operand2) {
		MathFloat tmpMathFloat = new MathFloat (operand1 * 1000);
		tmpMathFloat.divide(operand2);
		return tmpMathFloat;
	}
}
