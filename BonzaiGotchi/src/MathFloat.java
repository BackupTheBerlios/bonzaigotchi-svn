// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-17 updated by chappy
//            added getShort()
// 2006-12-19 updated by chappy
//            made it actually running ;)

public class MathFloat {

	public int value;
	public byte decimal;
	
	public MathFloat (int tmpValue, byte tmpDecimal) {
		value = tmpValue;
		decimal = tmpDecimal;
	}
	
	public void add(int operand) {
//		System.out.println("--- MathFloat ADD: value|decimal|operand: " + value + "|" + decimal + "|" + operand + " ---");
//		System.out.println("--- MathFloat ADD: power: " + MathCalc.power(10, decimal) + " ---");
		value += operand * MathCalc.power(10, decimal);
//		System.out.println("--- MathFloat ADD: value: " + value + " ---");
	}
	
	public void add(MathFloat operand) {
		if (operand.decimal > decimal) {
			value *= MathCalc.power(10, (operand.decimal - decimal));
			value += operand.value;
			value /= MathCalc.power(10, (operand.decimal - decimal));
		}
		else {
			value += operand.value * MathCalc.power(10, (decimal - operand.decimal));
		}
	}
	
	public void subtract(int operand) {
		value -= operand * MathCalc.power(10, decimal);
	}
	
	public void subtract(MathFloat operand) {
		if (operand.decimal > decimal) {
			value *= MathCalc.power(10, (operand.decimal - decimal));
			value -= operand.value;
			value /= MathCalc.power(10, (operand.decimal - decimal));
		}
		else {
			value -= operand.value * MathCalc.power(10, (decimal - operand.decimal));
		}
	}
	
	public void multiply(int operand) {
		value *= operand * MathCalc.power(10, decimal) / MathCalc.power(10, decimal);
	}
	
	public void multiply(MathFloat operand) {
		if (operand.decimal > decimal) {
//			System.out.println("--- MathFloat MULTIPLY: value|decimal|operandValue|operandDecimal: " + value + "|" + decimal + "|" + operand.value + "|" + operand.decimal + " ---");
			value *= MathCalc.power(10, (operand.decimal - decimal));
			value *= operand.value;
			value /= MathCalc.power(10, operand.decimal);
			value /= MathCalc.power(10, (operand.decimal - decimal));
		}
		else {
//			System.out.println("--- MathFloat xMULTIPLY: value|decimal|operandValue|operandDecimal: " + value + "|" + decimal + "|" + operand.value + "|" + operand.decimal + " ---");
			value *= operand.value * MathCalc.power(10, (decimal - operand.decimal)) / MathCalc.power(10, decimal);
		}
	}
	
	public void divide(int operand) {
		value *= MathCalc.power(10, decimal) / operand * MathCalc.power(10, decimal);
	}
	
	public void divide(MathFloat operand) {
		if (operand.decimal > decimal) {
			value *= MathCalc.power(10, (operand.decimal - decimal));
			value *= MathCalc.power(10, operand.decimal);
			value /= operand.value;
			value /= MathCalc.power(10, (operand.decimal - decimal));
		}
		else {
			value /= operand.value * MathCalc.power(10, (decimal - operand.decimal));
		}
	}
	
	public int getInt() {
		return value / MathCalc.power(10, decimal);
	}
	
	public short getShort() {
		return (short)(value / MathCalc.power(10, decimal));
	}
	
	/*
	// Statische Funktionen
	public static MathFloat multiply (MathFloat operand1, MathFloat operand2) {
		operand1.multiply(operand2);
		return operand1;
	}

	*/
}
