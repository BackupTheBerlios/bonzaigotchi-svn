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

	private int id;

	private MathFloat length;
	private MathFloat thickness; 
	private short angle; // angle * 15 = echter Winkel | 0 = verticalUp
	private short posX;
	private short posY;
	private int water; 
	private short health = 100; // tempValue
	private int demand;
	private MathFloat waterRequest;

	private int childWaterRequest = 0;
	private byte childWaterDivider = 100;
	
	private Element childLeft;
	private Element childCenter;
	private Element childRight;
	
	private Element parent = null;
	
	public Element(Element inputParent, FileIO data) {
	
		id = ++GlobalVars.COUNTERELEMENT;
		System.out.println("--- Element Constructor LOADED | "+ id +" ---");
		
		parent = inputParent;
		
		// Load Vars
		length = new MathFloat((int)data.readDataLong());
		thickness = new MathFloat((int)data.readDataLong());
		angle = data.readDataShort();
		posX = data.readDataShort();
		posY = data.readDataShort();
		water = data.readDataInt();
		health = data.readDataShort();
		
		// Check if I have children
		boolean tmpChildLeft = data.readDataBoolean();
		boolean tmpChildCenter = data.readDataBoolean();
		boolean tmpChildRight = data.readDataBoolean();
		
		if (tmpChildLeft) {
			// System.out.println("--- Element Constructor ChildLeft ---");
			childLeft = new Element(this, data);
		}
		if (tmpChildCenter) {
			// System.out.println("--- Element Constructor ChildCenter ---");
			childCenter = new Element(this, data);
			
		}
		if (tmpChildRight) {
			// System.out.println("--- Element Constructor ChildRight ---");
			childRight = new Element(this, data);
		}
				
	}
	
	public Element(Element inputParent, short tmpAngle, short tmpPosX, short tmpPosY, int tmpWater) {
	
		id = ++GlobalVars.COUNTERELEMENT;
		System.out.println("--- Element Constructor SPAWNED | "+ id +" ---");
		
		parent = inputParent;
		
		angle = tmpAngle;
		posX = tmpPosX;
		posY = tmpPosY;
		water = tmpWater;
		
		length = new MathFloat(GlobalVars.SPAWN_LENGTH_INIT);
		thickness = new MathFloat(GlobalVars.SPAWN_THICKNESS_INIT);
		
	}
	
	public int getChildWaterRequest() {
		// System.out.println("--- ID: "+ id +" | Element WaterRequest BEGINN ---");
		
		int tmpChildWaterRequest = 0;
		
		// System.out.println("--- ID: "+ id +" | Water: " + water + " ---");
		demand = calcDemand();
		// System.out.println("--- ID: "+ id +" | Demand: " + demand + " ---");
		short waterPercentage = (short)(water * 100 / (demand * 100)); // water x 100 (to get percentage) / (demand x [maximum intervalls the capacity could last]
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
/*		
		int i = 0;
		while (GlobalVars.REQUEST_WATER_THRESHOLD[i] <= waterPercentage) {
			if (++i == GlobalVars.REQUEST_WATER_THRESHOLD.length) { // will be neede if (water = 100%)
				break;
			}
		}
		i--;
*/		
		
		waterRequest = new MathFloat(demand*1000);
		waterRequest.multiply(GlobalVars.REQUEST_WATER_FACTOR[n]);
		// System.out.println("--- ID: "+ id +" | WaterRequest|Factor: " + waterRequest.getInt() + "|" + GlobalVars.REQUEST_WATER_FACTOR[n].value + " ---");
		// System.out.println("--- ID: "+ id +" | Element WaterRequest END ---");
		return childWaterRequest + waterRequest.getInt();
	}

	public boolean grow (int supply) {
		// System.out.println("--- ID: "+ id +" | Element Grow BEGINN ---");
		// System.out.println("--- ID: "+ id +" | Supply: " + supply + "---");
		// Usage
		
		int supplyTaken = 0;
		short waterPercentage = (short)(water * 100 / (demand * 100));
		
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
		System.out.println("--- ID: "+ id +" | waterPercentage: " + waterPercentage + " ---");
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
		
		System.out.println("--- ID: "+ id +" | Health|HEALTH_INC: " + health + "|" + tmpHealthInc + " ---");
		
		// grow
		
		if (childLeft != null || childCenter != null || childRight != null) {
			
			if (waterPercentage >= GlobalVars.GROWTH_WATER_MIN && health >= GlobalVars.GROWTH_HEALTH_MIN) {
				thickness.add(GlobalVars.GROWTH_THICKNESS_ONLY_INC);
				water -= demand;
				// System.out.println("--- ID: "+ id +" | Thickness: " + thickness.getInt() + " ---");
			} else {
				water -= demand / 2;
				if (water < 0) {
					water = 0;
					health--;
				}
			}
			
			// Let my children have the rest of the supply but share it brotherly
			if (childLeft != null) {
				supplyTaken = supply * childWaterDivider / 100;
				supply -= supplyTaken;
				if (!childLeft.grow(supplyTaken)) { childKill (childLeft); }
			}
			if (childCenter != null) {
				if (childLeft != null) {
					if (!childCenter.grow(supply)) { childKill (childCenter); }
				}
				else {
					supplyTaken = supply * childWaterDivider / 100;
					supply -= supplyTaken;
					if (!childCenter.grow(supplyTaken)) { childKill (childCenter); }
				}
			}
			if (childRight != null) {
				if (!childRight.grow(supply)) { childKill (childRight); }
			}
		}
		else {
			if (waterPercentage >= GlobalVars.GROWTH_WATER_MIN && health >= GlobalVars.GROWTH_HEALTH_MIN) {
				length.add(GlobalVars.GROWTH_LENGTH_INC);
				thickness.add(GlobalVars.GROWTH_THICKNESS_INC);
				water -= demand;
				// System.out.println("--- ID: "+ id +" | Length|Thickness: " + length.getInt() + "|" + thickness.getInt() + " ---");
			} else {
				water -= demand / 2;
			}
		}
	
		if (childLeft == null && childCenter == null && childRight == null && length.getInt() > GlobalVars.SPAWN_LENGTH_MIN && water > GlobalVars.SPAWN_WATER_MIN && getRandom(GlobalVars.SPAWN_CHANCE) == 3) {
			short tmpRandom = (short)getRandom(3);
			
			// System.out.println("--- ID: "+ id +" | SpawnRandom: " + tmpRandom + " ---");
			
			switch (tmpRandom + 3) {
				case 0:
					childLeft = new Element(this, calcAngle((short)20), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					water -= GlobalVars.SPAWN_WATER_CHILD;
					break;
					
				case 1:
					childCenter = new Element(this, calcAngle((short)22), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					water -= GlobalVars.SPAWN_WATER_CHILD;
					break;
					
				case 2:
					childRight = new Element(this, calcAngle((short)1), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					water -= GlobalVars.SPAWN_WATER_CHILD;
					break;
					
				case 3:
					childLeft = new Element(this, calcAngle((short)20), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					childCenter = new Element(this, calcAngle((short)22), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					water -= GlobalVars.SPAWN_WATER_CHILD * 2;
					break;
					
				case 4:
					childLeft = new Element(this, calcAngle((short)20), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					childRight = new Element(this, calcAngle((short)1), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					water -= GlobalVars.SPAWN_WATER_CHILD * 2;
					break;
					
				case 5:
					childCenter = new Element(this, calcAngle((short)22), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					childRight = new Element(this, calcAngle((short)1), calcX2(posX), calcY2(posY), GlobalVars.SPAWN_WATER_CHILD);
					water -= GlobalVars.SPAWN_WATER_CHILD * 2;
					break;
			}
		}
		
		if (health < -100) { return false; }
		else { return true; }
		// System.out.println("--- ID: "+ id +" | Element Grow END ---");
	}
		
	

	public void draw(Graphics g) {
		// System.out.println("--- ID: "+ id +" | Element Draw BEGINN ---");
		// System.out.println("--- ID: "+ id +" | editChild: "+ editChild +" ---");
		if ( GlobalVars.PAINTSTATUS == 1 ||
			(GlobalVars.PAINTSTATUS == 2 && (GlobalVars.ELEMENTEDIT.equals(this) || GlobalVars.ELEMENTEDIT.equals(parent))) ||
			(GlobalVars.PAINTSTATUS == 3 && GlobalVars.ELEMENTEDIT.equals(this))) {
			// System.out.println("--- ID|PAINTSTATUS: "+ id +" | "+ GlobalVars.PAINTSTATUS +" ---");
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
			
			int innerColor = 0;
			int outerColor = 0;
			
			switch (GlobalVars.PAINTSTATUS) {
				case 1:
					if (health <= GlobalVars.GROWTH_HEALTH_MIN) {
						if (health <= GlobalVars.GROWTH_HEALTH_DEATH) {
							innerColor = GlobalVars.COLOR_ELEMENT_DEAD;
							outerColor = GlobalVars.COLOR_ELEMENT_DEAD;
						}
						else {
							short steps = (short)((GlobalVars.GROWTH_HEALTH_MIN - GlobalVars.GROWTH_HEALTH_DEATH) / 10) +1; 
							short ratio = (short)((health - GlobalVars.GROWTH_HEALTH_DEATH) / 10);
							innerColor = MathCalc.colorCombine(GlobalVars.COLOR_ELEMENT_INNER, GlobalVars.COLOR_ELEMENT_DRY, ratio, (short)(steps - ratio));
							innerColor = MathCalc.colorCombine(GlobalVars.COLOR_ELEMENT_OUTER, GlobalVars.COLOR_ELEMENT_DRY, ratio, (short)(steps - ratio));
							// System.out.println("--- ID: "+ id +" | GROW - STEPS|RATIO|HEALTH: "+ steps + " | "+ ratio +" | "+ health +" ---");
						}
					} else {
						innerColor = GlobalVars.COLOR_ELEMENT_INNER;
						outerColor = GlobalVars.COLOR_ELEMENT_OUTER;
					}
					break;
				case 2:
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
			
				g.setColor(MathCalc.colorCombine(outerColor, innerColor, n, (short)(colorSteps - n)));

				
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

	private int getRandom(int range) {
		int tmpRandom;

		tmpRandom = GlobalVars.RANDOMGENERATOR.nextInt() % range;
		if (tmpRandom < 0) {
			tmpRandom *= -1;
		}
		return tmpRandom;
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

	private short calcAngle(short inputAngle) {
		short tmpAngle = (short)(getRandom(4) + angle + inputAngle);
		
		while (tmpAngle > 23) {
			tmpAngle -= 24;
		}
		return tmpAngle;
	}
	
	private int calcDemand() {
		int tmpDemand = (int)(((thickness.value * length.value / 1000000) -4) / 4);
		if (tmpDemand < 0) {
			tmpDemand = 0;
		}
		else if (tmpDemand > 29) {
			tmpDemand = 29;
		}
		return GlobalVars.GROWTH_WATER_DEMAND[tmpDemand];
	}
	
	// kill specific child
	public void childKill(Element childToKill) {
		if (childLeft.equals(childToKill)) {
			childLeft.childKill();
			childLeft = null;
		}
		if (childCenter.equals(childToKill)) {
			childCenter.childKill();
			childCenter = null;
		}
		if (childRight.equals(childToKill)) {
			childRight.childKill();
			childRight = null;
		}
	}
	
	// kill all of them
	public void childKill() {
		if (childLeft != null) {
			childLeft.childKill();
			childLeft = null;
		}
		if (childCenter != null) {
			childCenter.childKill();
			childCenter = null;
		}
		if (childRight != null) {
			childRight.childKill();
			childRight = null;
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
		data.writeData(posX);
		data.writeData(posY);
		data.writeData(water);
		data.writeData(health);

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
