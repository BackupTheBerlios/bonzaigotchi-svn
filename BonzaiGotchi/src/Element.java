// BonzaiGotchi
// 2006-12-14 class created by chappy
// 2006-12-18 updated by Sven
//            updated childWaterRequest()
// 2006-12-19 updated by chappy
//            updated Constructor [Spawned]
//            updated draw()
//            added writeData()
//            updated childWaterRequest()
//            updated grow()
//            updated by Sven
//            added automated Spawning
// 2006-12-20 updated by chappy
//            updated arrayLoops (HEALTH_INC;WATER_FACTOR)

import javax.microedition.lcdui.Graphics;

public class Element {

//	private int id;

	private MathFloat length;
	private MathFloat thickness; 
	private short angle; // angle * 11.25 = echter Winkel | 0 = verticalUp
	private short posX;
	private short posY;
	private int water; 
	private short health = 100;
	private int demand;
	private short waterPercentage;
	private MathFloat waterRequest;
	private boolean growStop = false;
	private int color = -1;
	private boolean colorNoAdaption = false;

	private int childWaterRequest = 0;
	private byte childWaterDivider = 100;
	
	private Element childLeft;
	private Element childCenter;
	private Element childRight;
	
	private Element parent = null;
	
	public Element(Element parent, FileIO data, short posX, short posY) {
	
//		id = ++GlobalVars.COUNTERELEMENT;
//		System.out.println("--- Element Constructor LOADED | "+ id +" ---");
		
		this.parent = parent;
		
		// Load Vars
		length = new MathFloat((int)data.readDataLong());
		thickness = new MathFloat((int)data.readDataLong());
		angle = data.readDataShort();
		this.posX = posX;
		this.posY = posY;
		water = data.readDataInt();
		health = data.readDataShort();
		growStop = data.readDataBoolean();
		color = data.readDataInt();
		colorNoAdaption = data.readDataBoolean();
		
		// Check if I have children
		boolean tmpChildLeft = data.readDataBoolean();
		boolean tmpChildCenter = data.readDataBoolean();
		boolean tmpChildRight = data.readDataBoolean();
		
		if (tmpChildLeft) {
			// System.out.println("--- Element Constructor ChildLeft ---");
			childLeft = new Element(this, data, calcX2(posX), calcY2(posY));
		}
		if (tmpChildCenter) {
			// System.out.println("--- Element Constructor ChildCenter ---");
			childCenter = new Element(this, data, calcX2(posX), calcY2(posY));
			
		}
		if (tmpChildRight) {
			// System.out.println("--- Element Constructor ChildRight ---");
			childRight = new Element(this, data, calcX2(posX), calcY2(posY));
		}
				
	}
	
//	public Element(Element parent, short angle, short posX, short posY, int water) {
//		element(parent, angle, posX, posY, water);
//	}
	
	public Element(Element parent, short angle, short posX, short posY, int water, int color) {
		this(parent, angle, posX, posY, water);
		this.color = color;
	}
	
	public Element(Element parent, short angle, short posX, short posY, int water) {
	
//		id = ++GlobalVars.COUNTERELEMENT;
//		System.out.println("--- Element Constructor SPAWNED | "+ id +" ---");
		
		this.parent = parent;
		
		this.angle = angle;
		this.posX = posX;
		this.posY = posY;
		this.water = water;
		
		length = new MathFloat(GlobalVars.SPAWN_LENGTH_INIT);
		thickness = new MathFloat(GlobalVars.SPAWN_THICKNESS_INIT);
		
	}
	

	
	public int getChildWaterRequest() {
		// System.out.println("--- ID: "+ id +" | Element WaterRequest BEGINN ---");
		
		int tmpChildWaterRequest = 0;
		
		// System.out.println("--- ID: "+ id +" | Water: " + water + " ---");
		demand = calcDemand();
		// System.out.println("--- ID: "+ id +" | Demand: " + demand + " ---");
		waterPercentage = (short)(water * 100 / (demand * 100)); // water x 100 (to get percentage) / (demand x [maximum intervalls the capacity could last]
		// System.out.println("--- ID: "+ id +" | WaterPercentage: " + waterPercentage + " ---");
		
		childWaterRequest = 0;
		
		if (childLeft != null || childCenter != null || childRight != null) {
			
			if (childLeft != null) {
				childWaterRequest = childLeft.getChildWaterRequest();
				// System.out.println("--- ID: "+ id +" | ChildLeft WaterRequest: " + childWaterRequest + " ---");
			}
			if (childCenter != null) {
				if (childLeft != null) {
					tmpChildWaterRequest = childCenter.getChildWaterRequest();
					// System.out.println("--- ID: "+ id +" | ChildCenter TmpWaterRequest: " + tmpChildWaterRequest + " ---");
				}
				else {
					childWaterRequest = childCenter.getChildWaterRequest();
					// System.out.println("--- ID: "+ id +" | ChildCenter WaterRequest: " + childWaterRequest + " ---");
				}
			}
			if (childRight != null) {
				if (childLeft != null || childCenter != null) {
					tmpChildWaterRequest = childRight.getChildWaterRequest();
					// System.out.println("--- ID: "+ id +" | ChildRight TmpWaterRequest: " + tmpChildWaterRequest + " ---");
				}
				else {
					childWaterRequest = childRight.getChildWaterRequest();
					// System.out.println("--- ID: "+ id +" | ChildRight WaterRequest: " + childWaterRequest + " ---");
				}
			}
			
			
			// System.out.println("--- ID: "+ id +" | WaterRequest: " + childWaterRequest + " ---");
			// System.out.println("--- ID: "+ id +" | TmpWaterRequest: " + tmpChildWaterRequest + " ---");
			childWaterDivider = (byte)(childWaterRequest * 100 / (childWaterRequest + tmpChildWaterRequest));
			// System.out.println("--- ID: "+ id +" | ChildWaterDivider: " + childWaterDivider + " ---");
			childWaterRequest += tmpChildWaterRequest;
					
		}
		
		int n = 0;
		for(int i = 0; i < GlobalVars.REQUEST_WATER_THRESHOLD.length; i++) {
			if (GlobalVars.REQUEST_WATER_THRESHOLD[i] <= waterPercentage) {
				n = i;
			}
		}	
		
		waterRequest = new MathFloat(demand*1000);
		waterRequest.multiply(GlobalVars.REQUEST_WATER_FACTOR[n]);
		// System.out.println("--- ID: "+ id +" | WaterRequest|Factor: " + waterRequest.getInt() + "|" + GlobalVars.REQUEST_WATER_FACTOR[n].value + " ---");
		// System.out.println("--- ID: "+ id +" | Element WaterRequest END ---");
		return childWaterRequest + waterRequest.getInt();
	}

	public boolean grow (int supply, int colorAdaption) {
		// System.out.println("--- ID: "+ id +" | Element Grow BEGINN ---");
		// System.out.println("--- ID: "+ id +" | Supply: " + supply + "---");
		// Usage
		
		if (colorAdaption >= 0 && !colorNoAdaption) {
			if (color == -1) color = GlobalVars.COLOR_ELEMENT_INNER;
			color = MathCalc.colorCombine(color, colorAdaption, (short)16, (short)1);
		}
		
		int supplyTaken = 0;
		
		if ((childLeft != null || childCenter != null || childRight != null) && childWaterRequest/2 > supply && waterPercentage > GlobalVars.WATER_SOCIAL_THRESHOLD) {
			// System.out.println("--- ID: "+ id +" | SOCIAL ACT ---");
			waterRequest.divide(2);
			supplyTaken = Math.min(waterRequest.getInt(), supply);
		}
		else {
			supplyTaken = Math.min(waterRequest.getInt(), supply);
		}
		
		// System.out.println("--- ID: "+ id +" | SupplyTaken: " + supplyTaken + " ---");
		
		supply -= supplyTaken;
		water += supplyTaken;
		
		// System.out.println("--- ID: "+ id +" | water: " + water + " ---");
		
		// Health
		waterPercentage = (short)(water * 100 / (demand * 100));
//		System.out.println("--- ID: "+ id +" | waterPercentage: " + waterPercentage + " ---");
		short tmpHealthInc = 0;
		
		for(int i = 0; i < GlobalVars.HEALTH_WATER_THRESHOLD.length; i++) {
			if (GlobalVars.HEALTH_WATER_THRESHOLD[i] <= waterPercentage) {
				tmpHealthInc = GlobalVars.HEALTH_WATER_INC[i];
			}
		}
		
		health += tmpHealthInc;
		if (health > 100) {
			health = 100;
		}
		
//		System.out.println("--- ID: "+ id +" | Health|HEALTH_INC: " + health + "|" + tmpHealthInc + " ---");
		
		// grow
		
		if (growStop || childLeft != null || childCenter != null || childRight != null) {
			
			if (waterPercentage >= GlobalVars.GROWTH_WATER_MIN && health >= GlobalVars.GROWTH_HEALTH_MIN) {
				thickness.add(GlobalVars.GROWTH_THICKNESS_ONLY_INC);
				water -= demand;
				// System.out.println("--- ID: "+ id +" | Thickness: " + thickness.getInt() + " ---");
			} else {
				water -= MathFloat.divide(demand, GlobalVars.GROWTH_WATER_DEMAND_NO_GROWTH_FACTOR).getInt();
				if (water < 0) {
					water = 0;
					health--;
				}
			}
			
			// Let my children have the rest of the supply but share it brotherly
//			System.out.println("--- ID: "+ id +" | Element.grow: Children ---");
			if (childLeft != null) {
				supplyTaken = supply * childWaterDivider / 100;
				supply -= supplyTaken;
				if (!childLeft.grow(supplyTaken, color)) { 
//					System.out.println("--- ID: "+ id +" | Element.grow: Left ---");
					childKill (childLeft);
					}
			}
			if (childCenter != null) {
				if (childLeft != null) {
					if (!childCenter.grow(supply, color)) { 
//						System.out.println("--- ID: "+ id +" | Element.grow: Center_Left ---");
						childKill (childCenter);
					}
				}
				else {
					supplyTaken = supply * childWaterDivider / 100;
					supply -= supplyTaken;
					if (!childCenter.grow(supplyTaken, color)) {
//						System.out.println("--- ID: "+ id +" | Element.grow: Center_NoLeft ---");
						childKill (childCenter);
					}
				}
			}
			if (childRight != null) {
				if (!childRight.grow(supply, color)) { 
//					System.out.println("--- ID: "+ id +" | Element.grow: Right ---");
					childKill (childRight);
				}
			}
		}
		else {
			if (waterPercentage >= GlobalVars.GROWTH_WATER_MIN && health >= GlobalVars.GROWTH_HEALTH_MIN) {
				length.add(GlobalVars.GROWTH_LENGTH_INC);
				thickness.add(GlobalVars.GROWTH_THICKNESS_INC);
				water -= demand;
				// System.out.println("--- ID: "+ id +" | Length|Thickness: " + length.getInt() + "|" + thickness.getInt() + " ---");
			} else {
				water -= MathFloat.divide(demand, GlobalVars.GROWTH_WATER_DEMAND_NO_GROWTH_FACTOR).getInt();
			}
		}
	
		if (!growStop && childLeft == null && childCenter == null && childRight == null && length.getInt() > GlobalVars.SPAWN_LENGTH_MIN && waterPercentage > GlobalVars.GROWTH_WATER_MIN && MathCalc.getRandom(GlobalVars.SPAWN_CHANCE) == 3) {
			short tmpRandom = (short)MathCalc.getRandom(3);
			
			// System.out.println("--- ID: "+ id +" | SpawnRandom: " + tmpRandom + " ---");
			
			switch (tmpRandom + 3) {
				case 0:
					childLeft   = new Element(this, calcAngleRandom((short)27, (short)4), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					water -= GlobalVars.SPAWN_WATER_CHILD;
					break;
					
				case 1:
					childCenter = new Element(this, calcAngleRandom((short)31, (short)3), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					water -= GlobalVars.SPAWN_WATER_CHILD;
					break;
					
				case 2:
					childRight  = new Element(this, calcAngleRandom((short)2,  (short)4), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					water -= GlobalVars.SPAWN_WATER_CHILD;
					break;
					
				case 3:
					childLeft   = new Element(this, calcAngleRandom((short)27, (short)4), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					childCenter = new Element(this, calcAngleRandom((short)31, (short)3), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					water -= GlobalVars.SPAWN_WATER_CHILD * 2;
					break;
					
				case 4:
					childLeft   = new Element(this, calcAngleRandom((short)27, (short)4), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					childRight  = new Element(this, calcAngleRandom((short)2,  (short)4), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					water -= GlobalVars.SPAWN_WATER_CHILD * 2;
					break;
					
				case 5:
					childCenter = new Element(this, calcAngleRandom((short)31, (short)3), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					childRight  = new Element(this, calcAngleRandom((short)2,  (short)4), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD, color);
					water -= GlobalVars.SPAWN_WATER_CHILD * 2;
					break;
			}
		}
		
		if (health < GlobalVars.GROWTH_HEALTH_DEATH) { return false; }
		else { return true; }
		// System.out.println("--- ID: "+ id +" | Element Grow END ---");
	}
		
	

	public void draw(Graphics g) {
		// System.out.println("--- ID: "+ id +" | Element Draw BEGINN ---");
		// System.out.println("--- ID: "+ id +" | editChild: "+ editChild +" ---");
		
		if ( GlobalVars.PAINTSTATUS == GlobalVars.PAINTSTATUS_NORMAL ||
			(GlobalVars.PAINTSTATUS == GlobalVars.PAINTSTATUS_EDIT && (GlobalVars.ELEMENTEDIT.equals(this) || GlobalVars.ELEMENTEDIT.equals(parent))) ||
			 GlobalVars.PAINTSTATUS == GlobalVars.PAINTSTATUS_LEAF) {
			// System.out.println("--- ID|PAINTSTATUS: "+ id +" | "+ GlobalVars.PAINTSTATUS +" ---");
			
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
			
			int innerColor = 0;
			int outerColor = 0;
			
			if (color >= 0) {
				innerColor = color;
				outerColor = MathCalc.colorCombine(color, 0x000000, (short)4, (short)1);
			}
			else {
				innerColor = GlobalVars.COLOR_ELEMENT_INNER;
				outerColor = GlobalVars.COLOR_ELEMENT_OUTER;
			}
			
			switch (GlobalVars.PAINTSTATUS) { 
				case GlobalVars.PAINTSTATUS_NORMAL:
				case GlobalVars.PAINTSTATUS_LEAF:
					if (health <= GlobalVars.COLOR_ELEMENT_DRY_THRESHOLD) {
						if (health <= GlobalVars.GROWTH_HEALTH_DEATH) {
							innerColor = GlobalVars.COLOR_ELEMENT_DEAD;
							outerColor = GlobalVars.COLOR_ELEMENT_DEAD;
						}
						else {
							short steps = (short)((GlobalVars.COLOR_ELEMENT_DRY_THRESHOLD - GlobalVars.GROWTH_HEALTH_DEATH) / 10) +1; 
							short ratio = (short)((health - GlobalVars.GROWTH_HEALTH_DEATH) / 10);
							innerColor = MathCalc.colorCombine(innerColor, GlobalVars.COLOR_ELEMENT_DRY, ratio, (short)(steps - ratio));
							innerColor = MathCalc.colorCombine(outerColor, GlobalVars.COLOR_ELEMENT_DRY, ratio, (short)(steps - ratio));
							// System.out.println("--- ID: "+ id +" | GROW - STEPS|RATIO|HEALTH: "+ steps + " | "+ ratio +" | "+ health +" ---");
						}
					}
					break;
					
				case GlobalVars.PAINTSTATUS_EDIT:
					if (GlobalVars.ELEMENTEDIT.equals(this)) {
						innerColor = GlobalVars.COLOR_ELEMENT_EDIT_INNER;
						outerColor = GlobalVars.COLOR_ELEMENT_EDIT_OUTER;
					}
					else {
						switch (parent.getRelative(this)) {
							case 1:
								innerColor = GlobalVars.COLOR_ELEMENT_EDIT_INNER_CHILD_LEFT;
								outerColor = GlobalVars.COLOR_ELEMENT_EDIT_OUTER;
								break;
							case 2:
								innerColor = GlobalVars.COLOR_ELEMENT_EDIT_INNER_CHILD_CENTER;
								outerColor = GlobalVars.COLOR_ELEMENT_EDIT_OUTER;
								break;
							case 3:
								innerColor = GlobalVars.COLOR_ELEMENT_EDIT_INNER_CHILD_RIGHT;
								outerColor = GlobalVars.COLOR_ELEMENT_EDIT_OUTER;
								break;
						}
					}
					break;
			}
			
			if (GlobalVars.PAINTSTATUS != GlobalVars.PAINTSTATUS_LEAF) {
				// draw the Branch
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
						if (thickness.getInt() % 2 == 0) { n = 0; }
						else                             { n = 1; }
						nIncrement = true;
					}
							
					g.setColor(MathCalc.colorCombine(outerColor, innerColor, n, (short)(colorSteps - n)));
					
					if (nIncrement) { n++; }
					else            { n--; }
					
					if (drawHorizontal) { g.drawLine(tmpPosX + i, tmpPosY, tmpPosX2 + i, tmpPosY2); }
					else                { g.drawLine(tmpPosX, tmpPosY + i, tmpPosX2, tmpPosY2 + i); }
					
					// System.out.println("--- LOOP END ---");			
				}
			}
			else {
				// draw the leafs
				if (length.getInt() >= GlobalVars.LEAF_LENGTH_MIN && thickness.getInt() < GlobalVars.LEAF_THICKNESS_MAX) {
					
					boolean first = true;
					MathFloat leafLength = new MathFloat(0);
					short leafAngle = calcAngle(angle, (short)-10);
					short tmpLeafAngle = 0;
					short leafPosX = 0;
					short leafPosY = 0;
					g.setColor(outerColor);

					for (int i = 0; i < GlobalVars.LEAF.length; i += 3) {
						if (GlobalVars.LEAF[i] == -1) {
							if (first) {
								first = false;
								i = 0;
								if (drawHorizontal) tmpPosX2 += thickness.getInt();
								else tmpPosY2 += thickness.getInt();
								leafAngle = calcAngle(angle, (short)10);
							}
							else break;
						}
							if (GlobalVars.LEAF[i] == 0) {
								leafPosX = tmpPosX2;
								leafPosY = tmpPosY2;
							}
							tmpLeafAngle = calcAngle(leafAngle, (short)GlobalVars.LEAF[i+1]);
							leafLength.value = GlobalVars.LEAF[i+2];
							g.drawLine(leafPosX, leafPosY, leafPosX = calcX2(leafPosX, tmpLeafAngle, leafLength), leafPosY = calcY2(leafPosY, tmpLeafAngle, leafLength));
					}
				}				
			}
				
		} // if paintstatus 1 || 2
		else if (GlobalVars.PAINTSTATUS == 3 && GlobalVars.ELEMENTEDIT.equals(this)) {
//			System.out.println("--- ID: "+ id +" | Element Draw.PS3 ---");
			
			short tmpAngle = calcAngle(angle, (short)-8);

			MathFloat tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
			tmpPos.multiply(GlobalVars.EDITEXACTPOS);
			int tmpPosCenterX = posX + tmpPos.getShort();
			
			tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[angle].value);
			tmpPos.multiply(GlobalVars.EDITEXACTPOS);
			int tmpPosCenterY = posY - tmpPos.getShort();
			
			tmpAngle = calcAngle(tmpAngle, (short)-8);
			
			tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
			tmpPos.multiply(GlobalVars.EDITEXACTLENGTH + thickness.getInt() / 2);
			int tmpPosX = tmpPosCenterX + tmpPos.getShort();
			
			tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
			tmpPos.multiply((GlobalVars.EDITEXACTLENGTH + thickness.getInt() / 2) * -1);
			int tmpPosX2 = tmpPosCenterX + tmpPos.getShort();
			
			tmpAngle = calcAngle(angle, (short)-8);
			
			tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
			tmpPos.multiply(GlobalVars.EDITEXACTLENGTH + thickness.getInt() / 2);
			int tmpPosY = tmpPosCenterY - tmpPos.getShort();
			
			tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
			tmpPos.multiply((GlobalVars.EDITEXACTLENGTH + thickness.getInt() / 2) * -1 );
			int tmpPosY2 = tmpPosCenterY - tmpPos.getShort();
			
			g.setColor(GlobalVars.COLOR_ELEMENT_EDIT_INNER);
			g.drawLine(tmpPosX, tmpPosY, tmpPosX2, tmpPosY2);
			
		}

				
		// so now my children shoud paint themselves
		if (childLeft != null) {
			childLeft.draw(g);
		}
		if (childCenter != null) {
			childCenter.draw(g);
		}
		if (childRight != null) {
			childRight.draw(g);
		}
		
		// System.out.println("--- ID: "+ id +" | Element Draw END ---");
	}

	private short calcX2(short tmpX) {
		return calcX2(tmpX, angle, length);
	}
	
	private short calcX2(short tmpX, short tmpAngle, MathFloat tmpLength) {
		tmpAngle = calcAngle(tmpAngle, (short)-8);
		MathFloat tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
		tmpPos.multiply(tmpLength);
		return (short)(tmpX + tmpPos.getShort());
	}
	
	private short calcY2(short tmpY) {
		
		return calcY2(tmpY, angle, length);
	}
	
	private short calcY2(short tmpY, short tmpAngle, MathFloat tmpLength) {
		MathFloat tmpPos = new MathFloat((int)GlobalVars.COSINUS_TABLE[tmpAngle].value);
		tmpPos.multiply(tmpLength);
		return (short)(tmpY - tmpPos.getShort());
	}	

	private short calcAngleRandom(short inputAngle, short range) {
		short tmpAngle = (short)(MathCalc.getRandom(range) + angle + inputAngle);
		
		while (tmpAngle > 31) {
			tmpAngle -= 32;
		}
		return tmpAngle;
	}
	
	private short calcAngle(short tmpAngle, short degree) {
		tmpAngle += degree;
		
		if (tmpAngle > 31) tmpAngle -= 32;
		else if (tmpAngle < 0) tmpAngle += 32;

		return tmpAngle;
	}
	
	private int calcDemand() {
		// tmpDemand = index Value
		int tmpDemand = (int)(((thickness.value * length.value / 1000000) -4) / 4);
		if (tmpDemand < 0) {
			tmpDemand = 0;
		}
		else if (tmpDemand >= GlobalVars.GROWTH_WATER_DEMAND.length) {
			tmpDemand = GlobalVars.GROWTH_WATER_DEMAND.length - 1;
		}
		
		// tmpDemand = real Value
		tmpDemand = GlobalVars.GROWTH_WATER_DEMAND[tmpDemand];
		if (growStop || childLeft != null || childCenter != null || childRight != null) {
			tmpDemand = MathFloat.divide(tmpDemand, GlobalVars.GROWTH_WATER_DEMAND_NO_GROWTH_FACTOR).getInt();
		}
		return tmpDemand;
	}
	
	public void setColor(int color) {
		this.color = color;
		colorNoAdaption = true;
	}
	
	// kill specific child
	public void childKill(Element childToKill) {
//		System.out.println("--- ID: "+ id +" | Element.childKill ---");
		if (childLeft != null && childLeft.equals(childToKill)) {
//			System.out.println("--- ID: "+ id +" | Element.childKill: Left ---");
			childLeft.childKill();
			childLeft = null;
//			System.out.println("--- ID: "+ id +" | Element.childKill: Left_killed ---");
		}
		if (childCenter != null && childCenter.equals(childToKill)) {
//			System.out.println("--- ID: "+ id +" | Element.childKill: Center ---");
			childCenter.childKill();
			childCenter = null;
//			System.out.println("--- ID: "+ id +" | Element.childKill: Center_killed ---");
		}
		if (childRight != null && childRight.equals(childToKill)) {
//			System.out.println("--- ID: "+ id +" | Element.childKill: Right ---");
			childRight.childKill();
			childRight = null;
//			System.out.println("--- ID: "+ id +" | Element.childKill: Right_killed ---");
		}
	}
	
	// kill all of them
	public void childKill() {
		// System.out.println("--- ID: "+ id +" | Element.childKillAll ---");
		if (childLeft != null) {
//			System.out.println("--- ID: "+ id +" | Element.childKillAll: Left ---");
			childLeft.childKill();
			childLeft = null;			
		}
		if (childCenter != null) {
//			System.out.println("--- ID: "+ id +" | Element.childKillAll: Center ---");
			childCenter.childKill();
			childCenter = null;
		}
		if (childRight != null) {
//			System.out.println("--- ID: "+ id +" | Element.childKillAll: Right ---");
			childRight.childKill();
			childRight = null;			
		}
	}
	
	public void cut(int cutPos, boolean seal) {
		childKill();
		length = new MathFloat(cutPos * 1000);
		if (seal) {
			growStop = true;
		}
	}
	
	public Element getRelative(byte relative) {
		
		switch (relative) {
			case 1:
				return childLeft;
			case 2:
				return childCenter;
			case 3:
				return childRight;
			case 4:
				return parent;
		}
		return null;
	}
	
	public byte getRelative(Element relative) {
		if (childLeft != null && childLeft.equals(relative)) {
			return 1;
		}
		else if (childCenter != null && childCenter.equals(relative)) {
			return 2;
		}
		else if (childRight != null && childRight.equals(relative)) {
			return 3;
		}
		else if (parent.equals(relative)) {
			return 4;
		}
		else {
			return 0;
		}
	}
	
	public int getLength() {
		return length.getInt();
	}
	
	public void writeData(FileIO data) {

		boolean tmpChildLeft = false;
		boolean tmpChildCenter = false;
		boolean tmpChildRight = false;

		if (childLeft != null) {
			tmpChildLeft = true;
		}
		if (childCenter != null) {
			tmpChildCenter = true;
		}
		if (childRight != null) {
			tmpChildRight = true;
		}

		
		// write Vars
		data.writeData(length.value);
		data.writeData(thickness.value);
		
		data.writeData(angle);
//		data.writeData(posX);
//		data.writeData(posY);
		data.writeData(water);
		data.writeData(health);
		data.writeData(growStop);
		data.writeData(color);
		data.writeData(colorNoAdaption);

		data.writeData(tmpChildLeft);
		data.writeData(tmpChildCenter);
		data.writeData(tmpChildRight);
		
		if (tmpChildLeft) {
			childLeft.writeData(data);
			
		}
		if (tmpChildCenter) {
			childCenter.writeData(data);
			
		}
		if (tmpChildRight) {
			childRight.writeData(data);
			
		}
	}
}
