package weapon;

import main.GamePanel;

public class WeaponCheckTile {
	GamePanel gp;
	public WeaponCheckTile(GamePanel gp) {
		this.gp=gp;
	}
	
	boolean checkTile(Weapon wp) {
		int col=(int)(wp.worldX+gp.tileSize/2)/gp.tileSize;
		int row=(int)(wp.worldY+gp.tileSize/2)/gp.tileSize;
		int nextTile=gp.tileM.currentMap[col][row];
		if(gp.tileM.tileA[nextTile].id!="deepWater" && gp.tileM.tileA[nextTile].colision==true) {
			return true;
		}
		return false;
		
	}
}
