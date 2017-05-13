package first;


public class StudentAccount  {
	Information info;
	private int coins;
	public  StudentAccount() {
		info=new Information();
		coins=200;
	}
	public StudentAccount(Information info2) {
		// TODO Auto-generated constructor stub
	
		
		info = info2;
	}
	
	public void setInformation(Information info) {
		this.info=info;
		
	}

	
	public Information getInformation() {
		// TODO Auto-generated method stub
		return info;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

}
