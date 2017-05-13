package first;

public class UserData {
	public String type ;
	public String name ; // game name for student & teacher
	public String scienceCategory="" , schema=""; // for teacher
	public int score=0 ; //for students
	public UserData(String n,String sc,String s , String t)
	{
		name=n;
		scienceCategory=sc;
		schema=s;
		type=t;
	}
	public UserData(String n,int s , String t)
	{
		name=n;
		score=s ;
		type=t ;
	}

}
