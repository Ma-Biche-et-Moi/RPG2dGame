package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	
	public int worldX, worldY;
	public int speed;
	public String direction;
	public boolean collisionOn;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}
