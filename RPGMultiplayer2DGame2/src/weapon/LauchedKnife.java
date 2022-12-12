package weapon;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;



import main.GamePanel;

public class LauchedKnife extends Weapon{
	public LauchedKnife(GamePanel gp,String owner,float rotation,String zone,int worldX,int worldY) {
		
		this.gp=gp;
		speed=10;
		damage=20;
		range=10*gp.tileSize;
		this.owner=owner;
		this.worldX=worldX;
		this.worldY=worldY;
		speedX=(float) (Math.sin(Math.toRadians(rotation))*speed);
		speedY=(float) (Math.cos(Math.toRadians(rotation))*speed);
		
		switch(zone) {
		case "dh":
			speedY*=-1;
			rotate=rotation;
		break;
		case "gb":
			speedX*=-1;
			rotate=rotation+180;
		break;
		case "gh":
			speedX*=-1;
			speedY*=-1;
			rotate=rotation+270;
		break;
		default:
			rotate=rotation+90;
		break;
		}


		switch(gp.player.direction) {
		case "up":
			if(zone=="db") {
				speedX=speed;
				speedY=0;
				rotate=90;
				}
			else if(zone=="gb" ) {
				speedX=speed*-1;
				speedY=0;
				rotate=270;
				
			}
		break;
		case "down":
			if(zone=="dh") {
				speedX=speed;
				speedY=0;
				rotate=90;
				}
			else if(zone=="gh" ) {
				speedX=speed*-1;
				speedY=0;
				rotate=270;
				
			}
		break;
		case "left":
			if(zone=="db") {
				speedX=0;
				speedY=speed;
				rotate=180;
				}
			else if(zone=="dh" ) {
				speedX=0;
				speedY=speed*-1;
				rotate=0;
			}
		break;
		case "right":
			if(zone=="gb") {
				speedX=0;
				speedY=speed;
				rotate=180;
				}
			else if(zone=="gh" ) {
				speedX=0;
				speedY=speed*-1;
				rotate=0;
				
			}
		break;
		}

		image=setup("/weapon/knifeLauched");
	}
}
