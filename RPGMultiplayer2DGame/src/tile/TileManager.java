package tile;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import processing.core.*;
import rayCasting.Boundary;
import rayCasting.Particle;
import rayCasting.Ray;

public class TileManager extends PApplet {
	
	GamePanel gp;
	float scl = (float) 0.1;
	
	public Tile[] tile;
	public int mapTileNum[][];
	int lastDigit = 0;
	int waterStartI = 0;
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[99];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		setMap();
	}
	
	public void setMap() {
		
		int[][] tempTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		for (int i = 0; i < gp.maxWorldCol; i++) {
			for (int j = 0; j < gp.maxWorldRow; j++) {
				
				double v = noise(i * scl, j * scl);
				
				if (v < 0.3) {
				    //water
					tempTileNum[i][j] = 0;
				} else if (v < 0.60) {
				    //grass
					tempTileNum[i][j] = 1;
				} else {
				    //forest
					tempTileNum[i][j] = 2;
				}
			}
		}
		
		for (int i = 0; i < gp.maxWorldCol; i++) {
			for (int j = 0; j < gp.maxWorldRow; j++) {
				
				if (tempTileNum[i][j] == 0) {
					mapTileNum[i][j] = checkWaterConnection(i, j, tempTileNum);
				} else if (tempTileNum[i][j] == 1) {
					Random random = new Random();
					int r = random.nextInt(100);
					if (r <= 80) {
						mapTileNum[i][j] = 0;
					} else {
						mapTileNum[i][j] = 1;
					}
				} else if (tempTileNum[i][j] == 2) {
					mapTileNum[i][j] = 2;
				}
				
			}
		}
		
		loadMap("/structures/mapBrick.txt", 4, 6, 12, 12);
		
	}
	
	public int checkWaterConnection(int i, int j, int[][] tempTileNum) {
		
		boolean N, NE, E, SE, S, SO, O, NO;
		
		if (j != 0) {
			if (tempTileNum[i][j-1] != 0) {N = true;} else {N = false;}
		} else {N = false;}
		
		if (i != gp.maxWorldCol-1 && j != 0) {
			if (tempTileNum[i+1][j-1] != 0) {NE = true;} else {NE = false;}
		} else {NE = false;}
		
		if (i != gp.maxWorldCol-1) {
			if (tempTileNum[i+1][j] != 0) {E = true;} else {E = false;}
		} else {E = false;}
		
		if (i != gp.maxWorldCol-1 && j != gp.maxWorldRow-1) {
			if (tempTileNum[i+1][j+1] != 0) {SE = true;} else {SE = false;}
		} else {SE = false;}
		
		if (j != gp.maxWorldRow-1) {
			if (tempTileNum[i][j+1] != 0) {S = true;} else {S = false;}
		} else {S = false;}
		
		if (i != 0 && j != gp.maxWorldRow-1) {
			if (tempTileNum[i-1][j+1] != 0) {SO = true;} else {SO = false;}
		} else {SO = false;}
		
		if (i != 0) {
			if (tempTileNum[i-1][j] != 0) {O = true;} else {O = false;}
		} else {O = false;}
		
		if (i != 0 && j != 0) {
			if (tempTileNum[i-1][j-1] != 0) {NO = true;} else {NO = false;}
		} else {NO = false;}
		
		if (N && E && S && O) {return 0;}
		if (N && !E && !S && O && NO) {return waterStartI+2;}
		if (N && !E && !S && !O) {return waterStartI+3;}
		if (N && E && !S && !O) {return waterStartI+4;}
		if (!N && !E && !S && O) {return waterStartI+5;}
		if (!N && E && !S && !O) {return waterStartI+6;}
		if (!N && !E && S && O) {return waterStartI+7;}
		if (!N && !E && S && !O) {return waterStartI+8;}
		if (!N && E && SE && S && !O) {return waterStartI+9;}
		if (!N && !E && SE && !S && !O) {return waterStartI+10;}
		if (!N && !E && !S && SO && !O) {return waterStartI+11;}
		if (!N && NE && !E && !S && !O) {return waterStartI+12;}
		if (!N && !E && !S && !O && NO) {return waterStartI+13;}
		if (N && E && O && !S) {return waterStartI+14;}
		if (N && E && !O && S) {return waterStartI+15;}
		if (!N && E && O && S) {return waterStartI+16;}
		if (N && !E && O && S) {return waterStartI+17;}
		if (!N && !E && S && !O && NO) {return waterStartI+18;}
		if (!N && !E && S && !O && NE) {return waterStartI+19;}
		if (!N && !E && !S && O && NE) {return waterStartI+20;}
		if (!N && !E && !S && O && SE) {return waterStartI+21;}
		if (N && E && S && O) {return waterStartI+29;}
		if (N && E && S && O) {return waterStartI+29;}
		if (N && E && S && O) {return waterStartI+29;}
		if (N && E && S && O) {return waterStartI+29;}
		
		Random random = new Random();
		int r = random.nextInt(100);
		if (r <= 20) {
			return waterStartI+1;
		}
		return waterStartI;
		
	}
	
	public void getTileImage() {
		
		setup(0, "grass00", false, "grass", false);
		setup(1, "grass01", false, "grass", false);
		setup(2, "tree", true, "tree", true);
		setup(3, "road00", false, "road", false);
		setup(4, "road02", false, "grass", false);
		setup(5, "road04", false, "grass", false);
		setup(6, "road05", false, "grass", false);
		setup(7, "road07", false, "grass", false);
		setup(8, "road09", false, "grass", false);
		setup(9, "road10", false, "grass", false);
		setup(10, "road11", false, "grass", false);
		setup(11, "road12", false, "grass", false);
		setup(12, "hut", false, "hut", false);
		setupWater();
	}
	
	public void setupWater() {
		
		waterStartI = lastDigit;
		int index = 0;
		for (int i = waterStartI; i < waterStartI+29; i++) {
			String name = "water" + str(index);
			setup(i, name, true, "water", false);
			index += 1;
		}
	}
	
	public int[] checkSpawn() {
		
		int[] coordinates = new int[2];
		coordinates[0] = -1;
		coordinates[1] = -1;
		int x = 15;
		int y = 15;
		
		while (coordinates[0] == -1) {
			
			if (tile[mapTileNum[x-1][y-1]].id == "grass" && 
				tile[mapTileNum[x][y-1]].id == "grass" && 
				tile[mapTileNum[x+1][y-1]].id == "grass" &&
				
				tile[mapTileNum[x-1][y]].id == "grass" && 
				tile[mapTileNum[x][y]].id == "grass" && 
				tile[mapTileNum[x+1][y]].id == "grass" &&
		
				tile[mapTileNum[x-1][y+1]].id == "grass" && 
				tile[mapTileNum[x][y+1]].id == "grass" && 
				tile[mapTileNum[x+1][y+1]].id == "grass") {
				
				coordinates[0] = x;
				coordinates[1] = y;
			}
			
			if (x >= gp.maxWorldCol) {
				x = 15;
				y++;
			}
			
			x++;
		}
		
		int i = coordinates[0];
		int j = coordinates[1];
		mapTileNum[i-1][j-1] = 8; mapTileNum[i][j-1] = 7; mapTileNum[i+1][j-1] = 9;
		mapTileNum[i-1][j] = 6; mapTileNum[i][j] = 3; mapTileNum[i+1][j] = 5;
		mapTileNum[i-1][j+1] = 10; mapTileNum[i][j+1] = 4; mapTileNum[i+1][j+1] = 11;
		
		return coordinates;
	}
	
	public void setup(int index, String imageName, boolean collision, String id, boolean opaque) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			tile[index].id = id;
			tile[index].opaque = opaque;
			lastDigit += 1;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String mapPath, int x, int y, int width, int height) {
		
		try {
			InputStream is = getClass().getResourceAsStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = x;
			int row = y;
			
			while (col < x+width && row < y+height) {
				
				String line = br.readLine();
				
				while (col < (x+width)) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col-x]);
					
					if (num != 99) {
						mapTileNum[col][row] = num;
					}
					col++;
				}
				if (col == x+width) {
					col = x;
					row++;
				}
			}
			br.close();
			
			
		} catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if (worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
					worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.8));
				
				Boundary[] walls = new Boundary[4];
				
				walls[0] = new Boundary(worldCol, worldRow, worldCol+1, worldRow, "horizontal", false);
				walls[1] = new Boundary(worldCol, worldRow+1, worldCol+1, worldRow+1, "horizontal", false);
				walls[2] = new Boundary(worldCol, worldRow, worldCol, worldRow+1, "vertical", false);
				walls[3] = new Boundary(worldCol+1, worldRow, worldCol+1, worldRow+1, "vertical", false);
				
				int rayI = 0;
				for (Ray ray : gp.particle.rays) {
					for (int i = 0; i < 4; i++) {
						Point pt = ray.cast(walls[i]);
						if (pt != null) {
							final float d = (float) Math.sqrt((pt.x-gp.screenWidth/2)*(pt.x-gp.screenWidth/2) + (pt.y-gp.screenHeight/2)*(pt.y-gp.screenHeight/2));
							if (d <= gp.particle.rayDistances[rayI]) {
								g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1));
							}
						}
					}
					rayI++;
				}
				
				if (tile[tileNum].id == "tree") {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1));
				}
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			worldCol++;
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	

}
