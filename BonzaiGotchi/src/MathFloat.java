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
	
	/*
	// Statische Funktionen
	public static MathFloat multiply (MathFloat operand1, MathFloat operand2) {
		operand1.multiply(operand2);
		return operand1;
	}

	*/
}
