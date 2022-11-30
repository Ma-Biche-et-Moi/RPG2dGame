package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	public final int screenX, screenY;
	
	public Player(GamePanel gp) {
		
		super(gp);
		
		this.gp = gp;
		keyH = gp.keyH;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		setup();
		getPlayerImage();
	}
	
	public void setup() {
		
		int[] coordinates = gp.tileM.checkSpawn();
		worldX = coordinates[0] * gp.tileSize;
		worldY = coordinates[1] * gp.tileSize;
		speed = 6;
		direction = "down";
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
		
		if (keyH.downPressed) {
			direction = "down";
		}
		else if (keyH.upPressed) {
			direction = "up";
		}
		else if (keyH.rightPressed) {
			direction = "right";
		}
		else if (keyH.leftPressed) {
			direction = "left";
		}
		
		if (keyH.downPressed || keyH.upPressed || keyH.leftPressed || keyH.rightPressed) {
			
			if (keyH.upPressed == true) {
				direction = "up";
			}
			if (keyH.downPressed == true) {
				direction = "down";
			}
			if (keyH.leftPressed == true) {
				direction = "left";
			}
			if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			// CHECK SPEED
			if (gp.cChecker.checkTileCollision(this, "road")) {
				speed = 8;
			} else if (gp.cChecker.checkTileCollision(this, "water")) {
				speed = 3;
			} else {
				speed = 6;
			}
			
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			if (collisionOn == false) {
				
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
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
	
	public void draw(Graphics2D g2) {
		
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
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1));
		
		g2.drawImage(image, screenX, screenY, null);
		
	}

}
