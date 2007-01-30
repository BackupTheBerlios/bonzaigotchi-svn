import javax.microedition.lcdui.Graphics;

public class Can {

		private MathFloat length;
		private MathFloat thickness; 
		private short angle; // angle * 15 = echter Winkel | 0 = verticalUp
		private short posX;
		private short posY;
	
		private short size;
		
		private Can child;
		
		public Can(short tmpAngle, short tmpPosX, short tmpPosY, short size) {
			System.out.println("--- Element Can LOADED ---");
			
			angle = tmpAngle;
			posX = tmpPosX;
			posY = tmpPosY;
			
			setSize (size);
		}
				
		public void setSize (short size)  {
			
			length = new MathFloat(GlobalVars.CAN_LENGTH_INIT);
			length.multiply(size);
			thickness = new MathFloat(GlobalVars.CAN_THICKNESS_INIT);
			thickness.multiply(size);
		}
		
		public void setPos (short x, short y) {
			posX = x;
			posY = y;
		}

		public void draw(Graphics g, boolean edit, boolean editChild) {
			// System.out.println("--- ID: "+ id +" | Element Draw BEGINN ---");
			// System.out.println("--- ID: "+ id +" | editChild: "+ editChild +" ---");
			if (!edit || edit && GlobalVars.ELEMENTEDIT.equals(this) || editChild) {

				short tmpPosX = posX;
				short tmpPosY = posY;
				
				boolean drawHorizontal = false;
				
				if (angle >= 21 || angle <= 3 || (angle >= 9 && angle <= 15)) {
					drawHorizontal = true;
					tmpPosX -= thickness.getShort() / 2;
				}
				else {
					tmpPosY -= thickness.getShort() / 2;
				}
				
				// System.out.println("--- DrawHorizontal: " + drawHorizontal + " ---");
				
				// System.out.println("--- ThicknessEven: " + thicknessEven + " ---");
				
				short tmpPosX2 = calcX2(tmpPosX);
				short tmpPosY2 = calcY2(tmpPosY);
				
				// System.out.println("--- PosX|Y: " + tmpPosX + "|" + tmpPosY + " : " + tmpPosX2 + "|" + tmpPosY2 + " ---");
				
				short colorSteps = (short)(thickness.getShort() / 2 + thickness.getShort() % 2 - 1);
				// System.out.println("--- ColorSteps: " + colorSteps + " ---");
						
				short n = colorSteps;
				if (colorSteps < 1) {
					colorSteps = 1;
				}
				boolean nIncrement = false;
				
				for (int i = 0; i < thickness.getInt(); i++) {
					// System.out.println("--- LOOP BEGINN ---");
					
					if (n < 0) {
						if (thickness.getInt() % 2 == 0) {
							n = 0;
						}
						else {
							n = 1;
						}
						nIncrement = true;
					}
					
					// System.out.println("--- N: " + n + " ---");

					g.setColor(MathCalc.colorCombine(GlobalVars.COLOR_CAN_OUTER, GlobalVars.COLOR_CAN_INNER, n, (short)(colorSteps - n)));
										
					if (nIncrement) {
						n++;
					}
					else {
						n--;
					}
					
					if (drawHorizontal) {
						g.drawLine(tmpPosX + i, tmpPosY, tmpPosX2 + i, tmpPosY2);
					}
					else {
						g.drawLine(tmpPosX, tmpPosY + i, tmpPosX2, tmpPosY2 + i);
					}
					
					// System.out.println("--- LOOP END ---");			
				}
				
			}
					
			// System.out.println("--- ID: "+ id +" | Element Draw END ---");
		}
		
		public short calcX2(short tmpX) {
			short tmpAngle = (short)(angle - 6);
			if (tmpAngle < 0) {
				tmpAngle += 24;
			}
			
			MathFloat tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
			tmpPos.multiply(length);
			return (short)(tmpX + tmpPos.getShort());
		}
		
		public short calcY2(short tmpY) {
			MathFloat tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[angle].value);
			tmpPos.multiply(length);
			return (short)(tmpY - tmpPos.getShort());
		}


}
