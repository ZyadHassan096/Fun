package first;

import java.util.ArrayList;

import javax.xml.crypto.Data;

public class Achievement {
	private int score;
	private String achievement;
	public Achievement(int S,String achiev)
	{
		score=S;
		achievement=achiev;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score2) {
		score = score2;
	}
	public String getAchievement() {
		return achievement;
	}
	public void setAchievement(String achievement2) {
		achievement = achievement2;
	}
	
	void addAchievement ( String email)
	{
		DataManagment dm = new DataManagment() ;
		ArrayList<Achievement> ach = dm.loadAchievements(email) ;
		ach.add(this) ;
		String text = "" ;
		
		for (int i=0 ; i<ach.size() ; i++)
		{
			text += ach.get(i).getAchievement() + "\t" + ach.get(i).getScore() + "\r\n" ;
		}
		dm.writeFile(text, email);
		
	}
}
