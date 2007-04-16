import javax.microedition.lcdui.Graphics;
//
public class Can {

		private MathFloat length;
		private MathFloat thickness; 
		private short angle; // angle * 15 = echter Winkel | 0 = verticalUp
		private short posX;
		private short posY;
		
		public Can(short angle, short posX, short posY, short size) {
//			System.out.println("--- Element Can LOADED ---");
			
			this.angle = angle;
			this.posX = posX;
			this.posY = posY;
			
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

		public void draw(Graphics g) {
			// System.out.println("--- ID: "+ id +" | Element Draw BEGINN ---");
			// System.out.println("--- ID: "+ id +" | editChild: "+ editChild +" ---");
		
			short tmpPosX = posX;
			short tmpPosY = posY;
			
			boolean drawHorizontal = false;
			
			if (angle >= 28 || angle <= 4 || (angle >= 12 && angle <= 20)) {
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
			
			
			// Draw Henkel
			
			
			for(int i=0;i<thickness.getInt()/6;i++){
				
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
				
			g.setColor(MathCalc.colorCombine(GlobalVars.COLOR_CAN_OUTER, GlobalVars.COLOR_CAN_INNER, n, (short)(colorSteps - n)));
					
				
			g.drawLine(tmpPosX2+thickness.getInt(), tmpPosY2 -(tmpPosY2-tmpPosY)-thickness.getInt()/3-i, tmpPosX+thickness.getInt()+thickness.getInt()/3, tmpPosY2 -(tmpPosY2-tmpPosY)-thickness.getInt()/3-i);
			g.drawLine(tmpPosX2+thickness.getInt(), tmpPosY2 -(tmpPosY2-tmpPosY)-thickness.getInt()/2-thickness.getInt()/3+i, tmpPosX+thickness.getInt()+thickness.getInt()/3, tmpPosY2 -(tmpPosY2-tmpPosY)-thickness.getInt()/2-thickness.getInt()/3+i);
			
			g.drawLine(tmpPosX+thickness.getInt()+thickness.getInt()/3+i, tmpPosY2 -(tmpPosY2-tmpPosY)-thickness.getInt()/2-thickness.getInt()/3,tmpPosX+thickness.getInt()+thickness.getInt()/3+i, tmpPosY2 -(tmpPosY2-tmpPosY)-thickness.getInt()/3);
			//g.drawLine(tmpPosX+i, tmpPosY -(tmpPosY-tmpPosY2)/2, tmpPosX-thickness.getInt()/2+i, tmpPosY2-thickness.getInt()/3);
			//g.drawLine(tmpPosX+i, tmpPosY -(tmpPosY-tmpPosY2)/2, tmpPosX-thickness.getInt()/2+i, tmpPosY2-thickness.getInt()/3);
			}
			
			// Draw Gie�kopf
			
			
			///////////////////////// Mein Dreieck ////////////////////////
			//
			//g.fillTriangle(tmpPosX-thickness.getInt()/2+thickness.getInt()/5,tmpPosY2-thickness.getInt()/3+thickness.getInt()/4,tmpPosX-thickness.getInt()/2-thickness.getInt()/5,tmpPosY2-thickness.getInt()/3+thickness.getInt()/4,tmpPosX-thickness.getInt()/2,tmpPosY2-thickness.getInt()/2+thickness.getInt()/8 );
			//
			///////////////////////////////////////////////////////////////
			///////////////////////// Dreieckslinien //////////////////////
			//g.drawLine(tmpPosX-thickness.getInt()/2+thickness.getInt()/5 , tmpPosY2-thickness.getInt()/3+thickness.getInt()/3, tmpPosX-thickness.getInt()/2-thickness.getInt()/5,tmpPosY2-thickness.getInt()/3+thickness.getInt()/4);
			//g.drawLine(tmpPosX-thickness.getInt()/2+thickness.getInt()/5 , tmpPosY2-thickness.getInt()/3+thickness.getInt()/4,tmpPosX-thickness.getInt()/2,tmpPosY2-thickness.getInt()/2+thickness.getInt()/8  );
			///////////////////////////////////////////////////////////////
			
			
			// Draw Gie�arm
			
			for (int i = 0; i< (thickness.getInt()/5); i++)
			{
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
			
			if (!drawHorizontal) {
				//g.drawLine(tmpPosX2, tmpPosY2 - i-(tmpPosY2-tmpPosY)/2, tmpPosX + i-(tmpPosX2-tmpPosX)/2, tmpPosY);
				
			}
			else {
				g.drawLine(tmpPosX+i, tmpPosY -(tmpPosY-tmpPosY2)/2+thickness.getInt()/4, tmpPosX-thickness.getInt()/2+i, tmpPosY2-thickness.getInt()/3+thickness.getInt()/4);
			}
			}
			
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
			
			
			
			// System.out.println("--- ID: "+ id +" | Element Draw END ---");
		}
		
		private short calcX2(short tmpX) {
			short tmpAngle = (short)(angle - 8);
			if (tmpAngle < 0) {
				tmpAngle += 32;
			}
			
			MathFloat tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
			tmpPos.multiply(length);
			return (short)(tmpX + tmpPos.getShort());
		}
		
		private short calcY2(short tmpY) {
			MathFloat tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[angle].value);
			tmpPos.multiply(length);
			return (short)(tmpY - tmpPos.getShort());
		}


}
