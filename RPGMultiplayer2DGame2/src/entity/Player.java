package entity;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import weapon.LauchedKnife;

public class Player extends Entity{

		GamePanel gp;
		KeyHandler keyH;
		
		public int screenX;
		public int screenY;

		public int fire=0;
		int  prevWorldX;
		int prevWorldY;
		public float posLineX; 
		public float posLineY;
		public Player(GamePanel gp){
			super(gp);
			this.gp=gp;
			this.keyH=gp.keyH;
			this.screenX = gp.screenWidth/2-(gp.tileSize/2);
			this.screenY = gp.screenHeight/2;
			setDefaultValues();
			solidArea.x=8;
			solidArea.y=16;
			solidArea.height=(int) (gp.tileSize*0.75);
			solidArea.width=(int) (gp.tileSize*0.75);
		}
		public void setDefaultValues() {
			speed=4;
			DefaultSpeed=4;
			direction="up";
			worldX=(gp.maxWorldCol/2)*gp.tileSize;
			worldY=(gp.maxWorldRow/2)*gp.tileSize;
			solidArea =new Rectangle();
			prevWorldX=worldX;
			prevWorldY=worldY;
			getPlayerImage();
			
		}

		public void getPlayerImage() {
			
			up1 = setup("/player/boy_up_1");
			up2 = setup("/player/boy_up_2");
			down1 = setup("/player/boy_down_1");
			down2 = setup("/player/boy_down_2");
			left1 = setup("/player/boy_left_1");
			left2 = setup("/player/boy_left_2");
			right1 = setup("/player/boy_right_1");
			right2 = setup("/player/boy_right_2");
		}
		public void update() {

			Point p = MouseInfo.getPointerInfo().getLocation();
			Dimension a =Toolkit.getDefaultToolkit().getScreenSize();
			double cox=Math.abs(p.x-(a.getWidth()/2));
			double coy=Math.abs(p.y-(a.getHeight()/2));
			rotation=(int) Math.toDegrees(Math.atan(cox/coy)); 

			posLineX= (float) Math.abs((Math.sin(Math.toRadians(rotation))*lenght));
			posLineY= (float) Math.abs((Math.cos(Math.toRadians(rotation))*lenght));
			rotate=(float) Math.toDegrees(Math.atan(posLineX/posLineY));
			if(p.x>=(a.getWidth()/2) && p.y>=(a.getHeight()/2)) {
				zone="db";
			}
			else if(p.x<=(a.getWidth()/2) && p.y>=(a.getHeight()/2)) {
				zone="gb";
			}
			else if(p.x<=(a.getWidth()/2) && p.y<=(a.getHeight()/2)) {
				zone="gh";
			}
			else {
				zone="dh";
			}

		/*	switch(zone) {
			case "db":
				rotate=(rotate)+90;
			break;
			case "gb":
				rotate+=180;
			break;
			case "gh":
				rotate=(rotate)+270;
			break;
			}*/
			if(keyH.upPressed==true) {
				direction="up";
			//	worldY-=speed;

			}
			if(keyH.downPressed==true) {
				//worldY+=speed;
				direction="down";
			}
			if(keyH.leftPressed==true) {
				//worldX-=speed;
				direction="left";
			}
			if(keyH.rightPressed==true) {
				//worldX+=speed;
				direction="right";
			}
			if(worldX-screenX <=0 && worldX<prevWorldX) {
				screenX-=speed;
				prevWorldX=worldX;
			}
			else if(worldX >=gp.worldWidth-(gp.screenWidth/2)-(gp.tileSize/2)  && worldX>prevWorldX) {
				screenX+=speed;
				prevWorldX=worldX;
			}
			else if(screenX<gp.screenWidth/2-(gp.tileSize/2)&& worldX <gp.worldWidth-gp.screenWidth/2 && worldX>prevWorldX) {
				screenX+=speed;
				prevWorldX=worldX;
			}
			else if(screenX>gp.screenWidth/2-(gp.tileSize/2) && worldX<prevWorldX) {
				screenX-=speed;
				prevWorldX=worldX;
			}
			
			if(worldY-screenY <=0 && worldY<prevWorldY) {
				screenY-=speed;
				prevWorldY=worldY;
			}
			else if(worldY >=(gp.worldHeight-(gp.screenHeight/2)-gp.tileSize)  && worldY>prevWorldY) {
				screenY+=speed;
				prevWorldY=worldY;
			}
			else if(screenY<(gp.screenHeight/2)-(gp.tileSize/2) && worldY < gp.worldHeight/2 && worldY>prevWorldY) {
				screenY+=speed;
				prevWorldY=worldY;
			}
			else if(screenY>gp.screenHeight/2 &&  worldY > gp.worldHeight/2 && worldY<prevWorldY) {
				screenY-=speed;
				prevWorldY=worldY;
			}
			

			
			collisionOn =false;
			gp.cChecker.checkTile(this);
			
			if (collisionOn == false) {
				if (keyH.rightPressed==true ||keyH.leftPressed==true ||keyH.downPressed==true || keyH.upPressed==true) {


					
					switch (direction) {
					case "up": 
						worldY-=speed;
					//	worldX+=tempSpeedX;
					break;
					case "down": 
						worldY+=speed;
					//	worldX-=tempSpeedX;
					break;
					case "left":
					//	worldY-=tempSpeedX;
						worldX-=speed;
						
					break;
					case "right":
					//	worldY+=tempSpeedX;
						worldX+=speed;
					break;
					}
					spriteCounter++;
					if (spriteCounter > 12) {
						if (spriteNum == 1) {
							spriteNum = 2;
						}
						else if (spriteNum == 2) {
							spriteNum = 1;
						}
						spriteCounter = 0;
					}
				}
				}
				if(keyH.firstWeapon==true && fire==0) {
					fire=1;
				int tempWorldX=worldX;//-gp.tileSize/2;
				int tempWorldY=worldY;//-gp.tileSize/2;
					LauchedKnife tempWeapon=new LauchedKnife(gp, "my", rotate, zone, tempWorldX,tempWorldY);
					gp.weaponList.add(tempWeapon);
				}
				
			}
			
			

		
		int a=0;
		int lenght=50;
		public void draw(Graphics2D g2) {
			
			if (a==0) {
				getPlayerImage();
				a=1;
				boolean ok =false;
					for(int i=(int)(0.25*gp.maxWorldCol);i<(0.75*gp.maxWorldCol);i++) {
						for(int j=(int)(0.25*gp.maxWorldRow);j<(0.75*gp.maxWorldRow);j++) {
							if(ok==false) {
								if(gp.tileM.currentMap[i][j] != -1) {
								if(gp.tileM.tileA[gp.tileM.currentMap[i][j]].id == "road") {
									worldX=gp.tileSize*i;
									worldY=gp.tileSize*j;
									ok=true;
								}}
						}}
					}
			}
			BufferedImage image = null;
			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				break;
			}

			switch(direction) {
			case "up":
				if(zone=="dh") {
					posLineY=screenY+gp.tileSize/2-posLineY;
					posLineX=screenX+gp.tileSize/2+posLineX;
					}
				else if(zone=="gh") {
					posLineY=screenY+gp.tileSize/2-posLineY;
					posLineX=screenX+gp.tileSize/2-posLineX;
					}
				else if(zone=="gb") {
					posLineY=screenY+gp.tileSize/2;
					posLineX=screenX+gp.tileSize/2-lenght;
				}
				else {
					posLineY=screenY+gp.tileSize/2;
					posLineX=screenX+gp.tileSize/2+lenght;
				}
			break;
			case "down":
				if(zone=="db") {
					posLineY=screenY+gp.tileSize/2+posLineY;
					posLineX=screenX+gp.tileSize/2+posLineX;
					}
				else if(zone=="gb") {
					posLineY=screenY+gp.tileSize/2+posLineY;
					posLineX=screenX+gp.tileSize/2-posLineX;
					}
				else if(zone=="gh") {
					posLineY=screenY+gp.tileSize/2;
					posLineX=screenX+gp.tileSize/2-lenght;
				}
				else {
					posLineY=screenY+gp.tileSize/2;
					posLineX=screenX+gp.tileSize/2+lenght;
				}
			break;
			case "left":
				if(zone=="gb") {
					posLineY=screenY+gp.tileSize/2+posLineY;
					posLineX=screenX+gp.tileSize/2-posLineX;
					}
				else if(zone=="gh") {
					posLineY=screenY+gp.tileSize/2-posLineY;
					posLineX=screenX+gp.tileSize/2-posLineX;
					}
				else if(zone=="db") {
					posLineX=screenX+gp.tileSize/2;
					posLineY=screenY+gp.tileSize/2+lenght;
				}
				else {
					posLineX=screenX+gp.tileSize/2;
					posLineY=screenY+gp.tileSize/2-lenght;
				}
			break;
			case "right":
				if(zone=="dh") {
					posLineY=screenY+gp.tileSize/2-posLineY;
					posLineX=screenX+gp.tileSize/2+posLineX;
					}
				else if(zone=="db") {
					posLineY=screenY+gp.tileSize/2+posLineY;
					posLineX=screenX+gp.tileSize/2+posLineX;
					}
				else if(zone=="gb") {
					posLineX=screenX+gp.tileSize/2;
					posLineY=screenY+gp.tileSize/2+lenght;
				}
				else {
					posLineX=screenX+gp.tileSize/2;
					posLineY=screenY+gp.tileSize/2-lenght;
				}
			break;
			}
			g2.drawLine(screenX+gp.tileSize/2, screenY+gp.tileSize/2,(int) posLineX,(int) posLineY);
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
		}
		
	
}
