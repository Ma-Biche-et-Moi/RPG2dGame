package tile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import main.GamePanel;

public class randomMap {
	public int rawMap[][];
	public int finalMap[][];
	int ratioMap[][];
	int scaleXA,scaleYA;
	GamePanel gp;
	public  randomMap(GamePanel gp) {
		this.gp =gp;
	}
	public int[][] randMap(int scaleX,int scaleY) {
		rawMap = new int [scaleX][scaleY];
		finalMap = new int [scaleX][scaleY];
		ratioMap=new int [scaleX][scaleY];
		scaleXA=scaleX;
		scaleYA=scaleY;
		
		for (int i=0;i<scaleY;i++) {
			for(int j=0;j<scaleX;j++) {
				rawMap[j][i]=-1;
				ratioMap[j][i]=-1;
			}
		}
		int col=0;
		int row=0;
		rawMap[col][row] = rand();
		ratioMap[col][row]= 100;
		col++;
		row++;
		int advancement=1;
		while(col<(scaleX) && row<(scaleY)) {
			for(int i=0;i<=advancement;i++) {
				randTile(i,advancement);
				randTile(advancement,i);
			}
			advancement++;
			col++;
			row++;
		}
		col=0;
		row=0;
		advancement=1;
		while(col<(scaleX) && row<(scaleY)) {
			for(int i=0;i<scaleX;i++) {
				for(int j=0;j<scaleY ;j++) {
					upMap(j,i);
				}
			}
			advancement++;
			col++;
			row++;
		}

		for(int i=0;i<scaleX;i++) {
			for(int j=0;j<scaleY ;j++) {
				finalMap[i][j]=applyTexture(j,i);
			}
		}
		return finalMap;
		
	}
	int rand() {
        List<Integer> tileNum = new ArrayList<>();
		for (int i=0;i<gp.tileM.tileTypeA.length;i++) {
			if (gp.tileM.tileTypeA[i] != null) {
				for(int j=0;j<gp.tileM.tileTypeA[i].spawnRate;j++) {
					tileNum.add(i);
				}
				}
			}
		return tileNum.get(ThreadLocalRandom.current().nextInt(tileNum.size()));
	}
	void randTile(int row,int col) {

        List<Integer> list = new ArrayList<>();
        
        if(row> 0 && col>0) {
			if(rawMap[col-1][row]!=-1) {
				for(int z=0;z<ratioMap[col-1][row];z++) {
				list.add(rawMap[col-1][row]);
				}
			}else {
				list.add(rand());
			}
			if(rawMap[col][row-1]!=-1) {
				for(int z=0;z<ratioMap[col][row-1];z++) {
				list.add(rawMap[col][row-1]);
				}
			}else {
				list.add(rand());
			}
			list.add(rand());
			list.add(rand());
		}
        
        else if (row==0 && col>0) {
				list.add(rand());
			
			if(rawMap[col-1][row]!=-1) {
				for(int z=0;z<ratioMap[col-1][row];z++) {
				list.add(rawMap[col-1][row]);
				}
			}else {
				list.add(rand());
			}
			list.add(rand());
			list.add(rand());
			list.add(rand());
        }
        
        else if (row>0 && col==0) {
			if(rawMap[col][row-1]!=-1) {
				for(int z=0;z<ratioMap[col][row-1];z++) {
				list.add(rawMap[col][row-1]);
				}
			}else {
				list.add(rand());
			}

			list.add(rand());
			list.add(rand());
			list.add(rand());
        }
        
        
        
		int tileNum=list.get(ThreadLocalRandom.current().nextInt(list.size()));
		if (col>0 && row>0) {
		if(gp.tileM.tileTypeA[tileNum].name=="road" || gp.tileM.tileTypeA[tileNum].name=="wall") {
			while(rawMap[col][row-1] == tileNum && rawMap[col-1][row]== tileNum && rawMap[col-1][row-1]== tileNum ) {
				tileNum=rand();
			}
			if (col>1 && row>1) {
				while((rawMap[col][row-1] != tileNum && rawMap[col-1][row] == tileNum && rawMap[col-1][row-1]== tileNum &&
					rawMap[col][row-2] == tileNum && rawMap[col-1][row-2] == tileNum )||
					(rawMap[col-1][row] != tileNum && rawMap[col][row-1] == tileNum && rawMap[col-1][row-1]== tileNum &&
					rawMap[col-2][row] == tileNum && rawMap[col-2][row-1] == tileNum )) {
					tileNum=rand();
				}
			}
			}
		}
		int ratio=100;
		
		if(row> 0 && col>0) {
			if(rawMap[col][row-1]==tileNum) {
				if(ratioMap[col][row-1]>=0 &&ratioMap[col][row-1]<ratio) {ratio=ratioMap[col][row-1];}}
			if(rawMap[col-1][row]==tileNum) {
				if(ratioMap[col-1][row]<ratio && ratioMap[col-1][row]>=0) {ratio=ratioMap[col-1][row];}}
		}
		else if (row==0 && col>0) {
			if(rawMap[col-1][row]==tileNum) {
				if(ratioMap[col-1][row]>=0 &&ratioMap[col-1][row]<ratio) {ratio=ratioMap[col-1][row];}}
			
		}
		else if (col==0 && row>0) {
			if(rawMap[col][row-1]==tileNum) {
				if(ratioMap[col][row-1]<ratio && ratioMap[col][row-1]>=0) {ratio=ratioMap[col][row-1];}}
			
		}
		rawMap[col][row]=tileNum;
		ratioMap[col][row]=ratio-gp.tileM.tileTypeA[tileNum].ratio;
		return ;
		
	}
	void upMap(int row,int col) {
		int tileNum=rawMap[col][row];
		if(row>0 && col>0 && col<(scaleXA-1) && row<(scaleYA-1)) {
			if(gp.tileM.tileTypeA[tileNum].name=="road" || gp.tileM.tileTypeA[tileNum].name=="wall" || 
					gp.tileM.tileTypeA[tileNum].name=="water" || gp.tileM.tileTypeA[tileNum].name=="deepWater") {
				while(rawMap[col][row-1] != tileNum && rawMap[col-1][row]!= tileNum && rawMap[col+1][row]!= tileNum &&rawMap[col][row+1]!= tileNum ) {
					tileNum=rand();
				}

			}
			int tempCol=col;
			int tempRow=row;
			if(gp.tileM.tileTypeA[tileNum].name=="road") {
				while((rawMap[col][row-1] == 1 ||rawMap[col][row-1] == tileNum )&& rawMap[col][row+1] != tileNum && rawMap[col-1][row] != tileNum &&
						rawMap[col+1][row] != tileNum &&( gp.tileM.tileTypeA[rawMap[col][row+1]].colision ==true || gp.tileM.tileTypeA[rawMap[col][row+1]].name=="water"  )&& row<scaleYA-2 ) {
					rawMap[col][row+1]=5;
					row++;
					tileNum=5;}
				while(  (rawMap[col][row+1] == 1 || rawMap[col][row+1] == tileNum) && rawMap[col][row-1] != tileNum && rawMap[col-1][row] != tileNum &&
						rawMap[col+1][row] != tileNum &&( gp.tileM.tileTypeA[rawMap[col][row-1]].colision ==true|| gp.tileM.tileTypeA[rawMap[col][row-1]].name=="water"  ) && row>2 ) {
					rawMap[col][row-1]=5;
					row-=1;
					tileNum=5;}
				while( (rawMap[col-1][row] == 1 || rawMap[col-1][row] == tileNum )&& rawMap[col][row+1] != tileNum && rawMap[col][row-1] != tileNum &&
						rawMap[col+1][row] != tileNum && (gp.tileM.tileTypeA[rawMap[col+1][row]].colision ==true || gp.tileM.tileTypeA[rawMap[col+1][row]].name=="water"  )&& col<scaleXA-2) {
					rawMap[col+1][row]=5;
					col++;
					tileNum=5;}
				while( (rawMap[col+1][row] == tileNum || rawMap[col+1][row] == tileNum) && rawMap[col][row+1] != tileNum && rawMap[col-1][row] != tileNum &&
						rawMap[col][row-1] != tileNum && (gp.tileM.tileTypeA[rawMap[col-1][row]].colision ==true|| gp.tileM.tileTypeA[rawMap[col-1][row]].name=="water"  ) && col>2) {
					rawMap[col-1][row]=5;
					col-=1;
					tileNum=5;}
			}
			col=tempCol;
			row=tempRow;
			if(gp.tileM.tileTypeA[tileNum].name=="sand") {
				if(rawMap[col-1][row] == 6) {
					rawMap[col-1][row]=2;
				}
				else if(rawMap[col][row-1] == 6) {
					rawMap[col][row-1]=2;
				}
				else if(rawMap[col][row+1] == 6) {
					rawMap[col][row+1]=2;
				}
				else if(rawMap[col+1][row] == 6) {
					rawMap[col+1][row]=2;
				}
			}
			
		}
	}
	int applyTexture(int row,int col) {
		int tileNum=rawMap[col][row];
		int[] textArray=new int[30];
		int tempTile = -1;
		int a=0;
		for(int i=0;i<textArray.length;i++) {
			textArray[i]=-1;
		}
		for(int i=0;i<gp.tileM.tileA.length;i++) {
			if(gp.tileM.tileA[i]!=null) {
			if(gp.tileM.tileA[i].id==gp.tileM.tileTypeA[tileNum].name ) {
				textArray[a]=i;
				a++;
			}}
		}
		if(row>0 && col>0 && col<(scaleXA-1) && row<(scaleYA-1)) {
			for(int i=0;i<textArray.length;i++) {

				if(textArray[i] != -1 && gp.tileM.tileA[textArray[i]].type==1) {
					int count=0;
					if(gp.tileM.tileTypeA[rawMap[col-1][row]].name==gp.tileM.tileA[textArray[i]].side[0] || ""==gp.tileM.tileA[textArray[i]].side[0]) {
						count++;
					}
					if(gp.tileM.tileTypeA[rawMap[col-1][row-1]].name==gp.tileM.tileA[textArray[i]].side[1] || ""==gp.tileM.tileA[textArray[i]].side[1]) {
						count++;
					}
					if(gp.tileM.tileTypeA[rawMap[col][row-1]].name==gp.tileM.tileA[textArray[i]].side[2] || ""==gp.tileM.tileA[textArray[i]].side[2]) {
						count++;
					}
					if(gp.tileM.tileTypeA[rawMap[col+1][row-1]].name==gp.tileM.tileA[textArray[i]].side[3] || ""==gp.tileM.tileA[textArray[i]].side[3]) {
						count++;
					}
					if(gp.tileM.tileTypeA[rawMap[col+1][row]].name==gp.tileM.tileA[textArray[i]].side[4] || ""==gp.tileM.tileA[textArray[i]].side[4]) {
						count++;
					}
					if(gp.tileM.tileTypeA[rawMap[col+1][row+1]].name==gp.tileM.tileA[textArray[i]].side[5] || ""==gp.tileM.tileA[textArray[i]].side[5]) {
						count++;
					}
					if(gp.tileM.tileTypeA[rawMap[col][row+1]].name==gp.tileM.tileA[textArray[i]].side[6] || ""==gp.tileM.tileA[textArray[i]].side[6]) {
						count++;
					}
					if(gp.tileM.tileTypeA[rawMap[col-1][row+1]].name==gp.tileM.tileA[textArray[i]].side[7] || ""==gp.tileM.tileA[textArray[i]].side[7]) {
						count++;
					}
					if(count==8) {
						tempTile=textArray[i];					
					}
			}}
			
		}
		if(tempTile==-1) {
			for(int i=0;i<textArray.length;i++) {
				
				if(textArray[i]!=-1) {
				if(gp.tileM.tileA[textArray[i]].type==0 && gp.tileM.tileA[textArray[i]].id==gp.tileM.tileTypeA[tileNum].name) {
					tempTile=textArray[i];
				}}
			}
		}
		
		return tempTile;
	}
}

