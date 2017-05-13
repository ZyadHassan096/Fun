package first;

import java.util.ArrayList;

public class TeacherAccount  {
	private Information info;
	public TeacherAccount() {
		info=null;
		
	}
	public TeacherAccount(Information info2) {
		// TODO Auto-generated constructor stub
		info = info2 ;
	}
	
	public void setInformation(Information info) {
		this.info=info;
		
	}

	
	public Information getInformation() {
		// TODO Auto-generated method stub
		return info;
	}


}
