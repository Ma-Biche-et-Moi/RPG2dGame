package main;

import entity.Entity;

public class collisionCheck {
	GamePanel gp;
	int lastTile;
	public collisionCheck(GamePanel gp) {
		this.gp=gp;
	}
	int a=0;
	public void checkTile(Entity Entity) {
		int EntityLeftWorldX = Entity.worldX +Entity.solidArea.x;
		int EntityRightWorldX =Entity.worldX +Entity.solidArea.x +Entity.solidArea.width;
		int EntityTopWorldY = Entity.worldY +Entity.solidArea.y;
		int EntityBottomWorldY =Entity.worldY +Entity.solidArea.y +Entity.solidArea.height;

		int EntityLeftCol =EntityLeftWorldX/gp.tileSize;
		int EntityRightCol =EntityRightWorldX/gp.tileSize;
		int EntityTopRow =EntityTopWorldY/gp.tileSize;
		int EntityBottomRow =EntityBottomWorldY/gp.tileSize;

		int tileNum1,tileNum2,tileNum3,tileNum4;

		tileNum1= gp.tileM.currentMap[EntityLeftCol][EntityTopRow];
		tileNum2= gp.tileM.currentMap[EntityRightCol][EntityTopRow];
		tileNum3= gp.tileM.currentMap[EntityLeftCol][EntityBottomRow];
		tileNum4= gp.tileM.currentMap[EntityRightCol][EntityBottomRow];
			Entity.speed =Entity.DefaultSpeed*((gp.tileM.tileA[tileNum1].speedCoef+gp.tileM.tileA[tileNum2].speedCoef
					+gp.tileM.tileA[tileNum3].speedCoef+gp.tileM.tileA[tileNum4].speedCoef)/4);
		
		switch(Entity.direction) {
		case "up":
			EntityTopRow =(int) ((EntityTopWorldY	-Entity.speed)/gp.tileSize);
			tileNum1= gp.tileM.currentMap[EntityLeftCol][EntityTopRow];
			tileNum2= gp.tileM.currentMap[EntityRightCol][EntityTopRow];
			if (gp.tileM.tileA[tileNum1].colision ==true || gp.tileM.tileA[tileNum2].colision== true) {
				Entity.collisionOn=true;
			}
			break;
		case "down":
			EntityBottomRow =(int) ((EntityBottomWorldY	+Entity.speed)/gp.tileSize);

			tileNum1= gp.tileM.currentMap[EntityLeftCol][EntityBottomRow];
			tileNum2= gp.tileM.currentMap[EntityRightCol][EntityBottomRow];
			if (gp.tileM.tileA[tileNum1].colision ==true || gp.tileM.tileA[tileNum2].colision== true) {
				Entity.collisionOn=true;
			}
			break;
		case "left":
			EntityLeftCol =(int) ((EntityLeftWorldX	+Entity.speed)/gp.tileSize);
			tileNum1= gp.tileM.currentMap[EntityLeftCol][EntityBottomRow];
			tileNum2= gp.tileM.currentMap[EntityLeftCol][EntityTopRow];
			if (gp.tileM.tileA[tileNum1].colision ==true || gp.tileM.tileA[tileNum2].colision== true) {
				Entity.collisionOn=true;
			}
			break;
		case "right":
			EntityRightCol =(int) ((EntityRightWorldX	+Entity.speed)/gp.tileSize);
			tileNum1= gp.tileM.currentMap[EntityRightCol][EntityBottomRow];
			tileNum2= gp.tileM.currentMap[EntityRightCol][EntityTopRow];
			if (gp.tileM.tileA[tileNum1].colision ==true || gp.tileM.tileA[tileNum2].colision== true) {
				Entity.collisionOn=true;
			}
			break;
		}
		
		
	}
}
