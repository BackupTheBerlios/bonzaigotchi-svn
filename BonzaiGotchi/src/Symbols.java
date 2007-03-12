import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Symbols {

	private final int LINE = 1;
	private final int CIRCLE = 2;
	private final int RECT = 3;
	private final int ROUNDRECT = 4;
	Image arrow = null;
	Image question = null;
	Image tree = null;
	Image watercan = null;
	Image scissors = null;
	//private String[][] icons = { { "1:015,005,015,015", "1:010,005,005,015","2:010,005,015,015,000,360" }, {"1:005,005,015,015", "1:010,005,005,015","3:010,005,015,015" },{"4:010,005,015,015,005,005" } };

	private String[][] icons = { { "1:005,015,020,013", "1:008,002,017,020","2:016,010,004,004,000,190","2:015,017,004,004,100,180" }};
	
	
	
	public Symbols() {
	}

	public void draw(Graphics g) {
		System.out.println("Willkommen in der Symbols");
		//System.out.println((icons[0][0].substring(2, 5)));
		//System.out.println(Integer.parseInt(icons[0][0].substring(0, icons[0][0].indexOf(":"))));
		/*for (int i = 0; i < icons.length; i++) { // icon
			for (int n = 0; n < icons[i].length; n++) { // element of icon
				switch (Integer.parseInt(icons[i][n].substring(0, icons[i][n].indexOf(":")))) { // icon Type
					case LINE:
					
						g.drawLine(Integer.parseInt(icons[i][n].substring(2, 5))+i*35,Integer.parseInt(icons[i][n].substring(6, 9)),Integer.parseInt(icons[i][n].substring(10, 13))+i*35,Integer.parseInt(icons[i][n].substring(14, 17)));
						
						break;
					case CIRCLE:
						
						g.drawArc(Integer.parseInt(icons[i][n].substring(2, 5))+i*35,Integer.parseInt(icons[i][n].substring(6, 9)), Integer.parseInt(icons[i][n].substring(10, 13)),Integer.parseInt(icons[i][n].substring(14, 17)),Integer.parseInt(icons[i][n].substring(18, 21)),Integer.parseInt(icons[i][n].substring(22, 25)));
						
						break;
					case RECT:
						
						g.drawRect(Integer.parseInt(icons[i][n].substring(2, 5))+i*35,Integer.parseInt(icons[i][n].substring(6, 9)), Integer.parseInt(icons[i][n].substring(10, 13)),Integer.parseInt(icons[i][n].substring(14, 17)));
						
						break;
					
					case ROUNDRECT:
						
						g.drawRoundRect(Integer.parseInt(icons[i][n].substring(2, 5))+i*35,Integer.parseInt(icons[i][n].substring(6, 9)), Integer.parseInt(icons[i][n].substring(10, 13)),Integer.parseInt(icons[i][n].substring(14, 17)),Integer.parseInt(icons[i][n].substring(18, 21)),Integer.parseInt(icons[i][n].substring(22, 25)));
						
						break;	
				}
			} // for element of icon
		} // for icon*/
		
		 try {
				arrow = Image.createImage("/arrow20.png");
				question = Image.createImage("/question20.png");
				watercan = Image.createImage("/watercan20.png");
				scissors = Image.createImage("/scissors20.png");
				tree = Image.createImage("/tree20.png");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		g.drawImage(arrow, 10, 30, Graphics.TOP|Graphics.LEFT); 
		g.drawImage(question, 30,30, Graphics.TOP|Graphics.LEFT); 
		g.drawImage(watercan, 50,30, Graphics.TOP|Graphics.LEFT);
		g.drawImage(scissors, 70,30, Graphics.TOP|Graphics.LEFT);
		g.drawImage(tree, 90,30, Graphics.TOP|Graphics.LEFT);
		
		try {
			arrow = Image.createImage("/arrow20black.png");
			question = Image.createImage("/question20black.png");
			watercan = Image.createImage("/watercan20black.png");
			scissors = Image.createImage("/scissors20black.png");
			tree = Image.createImage("/tree20black.png");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		g.drawImage(arrow, 10, 50, Graphics.TOP|Graphics.LEFT); 
		g.drawImage(question, 30,50, Graphics.TOP|Graphics.LEFT); 
		g.drawImage(watercan, 50,50, Graphics.TOP|Graphics.LEFT);
		g.drawImage(scissors, 70,50, Graphics.TOP|Graphics.LEFT);
		g.drawImage(tree, 90,50, Graphics.TOP|Graphics.LEFT);
	} // draw
}
