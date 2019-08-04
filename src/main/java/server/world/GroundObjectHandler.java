package server.world;

public class GroundObjectHandler {
	
	/** Limit amount of placed objects in server to prevent lag, if you want unlimited then use an ArrayList **/
	private int objectLimit = 10;
	private int placedObjects[][] = new int[objectLimit][4];//Id, X, Y, Time
	private boolean addObject = false;

	public GroundObjectHandler() {}
	
	public void process() {
		for (int i = 0; i < objectLimit; i++) {
			if (placedObjects[i][3] == 0) {
				removeObject(i);
			} else {
				placedObjects[i][3]--;
			}
		}
	}
	
	public void addObject() {
		for (int i = 0; i < objectLimit; i++) {
			if (placedObjects[i][0] == 0) {
			
				
			}
		}
	}
	
	public void removeObject(int id) {
	
	}
}