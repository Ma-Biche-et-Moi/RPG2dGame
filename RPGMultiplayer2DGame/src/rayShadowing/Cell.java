package rayShadowing;

public class Cell {
	
	public boolean exist;
	public boolean[] edgeExist = new boolean[4];
	public int[] edgeId = new int[4];
	
	public Cell(boolean exist) {
		
		this.exist = exist;
		
		for (int i = 0; i < 4; i++) {
			edgeExist[i] = false;
			edgeId[i] = 0;
		}
	}
}
