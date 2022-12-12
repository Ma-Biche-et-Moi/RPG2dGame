package tile;

import java.awt.Graphics2D;
//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public randomMap rMap;
	public tile[] tileA;
	public tileType[] tileTypeA;
	public int mapTileNum[][];
	public int mapTileTest[][];
	public int currentMap[][];
	int screenXSize;
	int screenYSize;	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		this.rMap= new randomMap(gp);
		tileA = new tile[50];
		tileTypeA =new tileType[10];
		this.screenXSize=gp.screenWidth /2-(gp.tileSize/2);
		this.screenYSize = gp.screenHeight/2;
		loadType();
		loadAsset();
		//loadMap();
	}
	public void loadType() {
		tileTypeA[0]=setupType("grass",1,false,20,110);
		tileTypeA[1]=setupType("wall",1,false,50,0);
		tileTypeA[2]=setupType("water",0.5,false,20,15);
		tileTypeA[3]=setupType("tree",1,true,25,15);
		tileTypeA[4]=setupType("sand",0.75,false,40,10);
		tileTypeA[5]=setupType("road",1.25,false,10,5);
		tileTypeA[6]=setupType("deepWater",1,true,15,5);
	}
	public void loadMap() {
		currentMap=new int [50][50];
		currentMap =rMap.randMap(50,50);
		
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
/*		try {

		InputStream is =getClass().getResourceAsStream("/level/world01.txt");
		BufferedReader br = new  BufferedReader(new InputStreamReader(is));

		
		int worldcol=0;
		int worldRow=0;
		while (worldcol< gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			String line=br.readLine();
			while (worldcol <gp.maxWorldCol) {
					 String number[] =line.split(" ");
					 int num =Integer.parseInt(number[worldcol]);
					 mapTileNum[worldcol][worldRow] =num;
					 worldcol++;
			}
				 if(worldcol== gp.maxWorldCol) {
					 worldcol	=0;
						worldRow++;
					 }
			 
		}
		 br.close();
		}catch(Exception e) {}*/
		//	loadAsset();
		}
	public void loadAsset() {
					  //setup(type de la tile,nom du png,nom de la class,liste des classe voisine de la tile)
			tileA[0] =setup(0,"grass","grass",new String[]{});
			tileA[1] =setup(0,"wall","wall",new String[]{});
			tileA[2] =setup(0,"water0","water",new String[]{});
			tileA[3] =setup(0,"sand","sand",new String[]{});
			tileA[4] =setup(0,"tree","tree",new String[]{});
			tileA[5] =setup(0,"road00","road",new String[]{});
			tileA[6] =setup(0,"deepWater","deepWater",new String[]{});
			tileA[7] =setup(1,"water1","water",new String[]{"water","","","water","","water","","water"});
			tileA[8] =setup(1,"water2","water",new String[]{"grass","grass","grass","grass","water","water","water","grass"});

	}
	public void draw(Graphics2D g2) {
		int worldCol=0;
		int worldRow=0;
		
		while (worldCol< gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			int tileNum =currentMap[worldCol][worldRow];
			int worldX =worldCol*gp.tileSize;
			int worldY =worldRow*gp.tileSize;
			int screenX =worldX -gp.player.worldX+ gp.player.screenX;
			int screenY =worldY -gp.player.worldY+ gp.player.screenY;
			if (tileNum==-1) {
				tileNum=0;
			}
			if(tileA[tileNum]!=null) {
			//if(worldX + gp.finalTileSize> gp.player.worldX-screenXSize && worldX - gp.finalTileSize<gp.player.worldX +screenXSize && 
			//		worldY + gp.finalTileSize> gp.player.worldY-screenYSize && worldY -	 gp.finalTileSize<gp.player.worldY +screenYSize) {
			g2.drawImage(tileA[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			worldCol++;
			if(worldCol==gp.maxWorldCol) {
				worldCol=0;
				worldRow++;
			}
			}
		}
		tile setup(int num,String path,String id,String[] side) {
			//loadType();
			tile tempTile =new tile();
			tempTile.id=id;
			tempTile.side=side;
			tempTile.type=num;
			try {
				System.out.println("/tiles/"+path+".png");
				tempTile.image =ImageIO.read(getClass().getResourceAsStream("/tiles/"+path+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i=0;i<tileTypeA.length;i++) {
				if(tileTypeA[i] != null) {
				if (tileTypeA[i].name==id) {
					tempTile.speedCoef=tileTypeA[i].speedCoef;
					tempTile.colision=tileTypeA[i].colision;
				}				
			}}
			return tempTile;
			
		}
		tileType setupType(String name,double speedCoef,boolean colision,int ratio,int spawnRate) {
			tileType tempType = new tileType();
			tempType.speedCoef=speedCoef;
			tempType.name=name;
			tempType.colision=colision;
			tempType.ratio=ratio;
			tempType.spawnRate=spawnRate;
			return tempType;
			
		}
	}

