package castle;

public class Role {

	private int HP;
	private int ATK;
	private Room currentRoom;
	private boolean isAlive = true;

	public Role(int hP, int aTK ,Room currentroom) {
		super();
		HP = hP;
		ATK = aTK;
		currentRoom = currentroom;
	}
	
	public void decreaseHP(int num){
		HP -= num;
		if(HP<=0){
			isAlive = false;
		}
	}
	
	public void increaseHP(int num){
		HP += num;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public int getHP(){
		return HP;
	}
	
	public int getATK(){
		return ATK;
	}
	
	public void increaseATK(int num){
		ATK += num;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
