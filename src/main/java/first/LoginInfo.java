package first;


public class LoginInfo {
	private String email;
	private String password;
	public LoginInfo()
	{
		email="";
		password="";
	}
	public LoginInfo(String em,String pass)
	{
		this.email=em;
		this.password=pass;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email2) {
		this.email = email2 ;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
