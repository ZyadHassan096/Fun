package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.swing.JOptionPane;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import first.Achievement;
import first.Course;
import first.DataManagment;
import first.Game;
import first.Information;
import first.LoginInfo;
import first.MCQ_Question;
import first.StudentAccount;
import first.TF_Question;
import first.TeacherAccount;
import first.UserData;

@RestController
public class RigesterationController {
	public LoginInfo Current_User = new LoginInfo() ;
	public String GamePlaying = "" ;
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/SignUp/{email}/{UName}/{Pass}/{gender}/{UserType}")
	@ResponseBody
	public void SignUp(@PathVariable String email , @PathVariable String UName , 
					   @PathVariable String Pass , @PathVariable char gender , @PathVariable String UserType )
	{
		Information info = new Information(); 
		info.setEmail(email);
		info.setName(UName);
		info.setPassword(Pass);
		info.setGender(gender);
		
		DataManagment dm = new DataManagment() ;
		boolean valid = dm.Validate(info ) ;
		boolean check = false ;
		// check if he is a teacher 
		if (UserType.equals("t"))
			check = true ;
		
		String str = "Invalid Data, Please Try Again" ;
		boolean exist=dm.CheckEmail(email);
		if(exist)
		{
			str = "this Email already exists ! ";
			valid=false;
		}
		if (valid)
		{
			if (check)// true mean Teacher
			{
				TeacherAccount teacher = new TeacherAccount(info) ;
				dm.addTeacher (teacher);
				System.out.println("Account Created Successfully"  );
			}
			else // student
			{
				StudentAccount student= new StudentAccount(info);
				dm.addStudent(student);
				System.out.println("Account Created Successfully"  );
			}
		}
		else 
		{	
			System.out.println("not valid");
			System.out.println(str );
		}
		Current_User.setEmail(email);
		Current_User.setPassword(Pass);
		}
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/LogIn/{email}/{password}")
	@ResponseBody
	public ArrayList<UserData> LogIn(@PathVariable String email , @PathVariable String password)
	{
		LoginInfo loginfo=new LoginInfo(email,password);
		DataManagment dm=new DataManagment();
		boolean t= dm.CheckTeacher(loginfo);
		boolean s=dm.CheckStudent(loginfo);
		String userName=dm.getUserName();
		String text ="" ;
		ArrayList<UserData> data = new ArrayList<UserData>() ;
		try {
			System.out.println("email: "+email);
			text = dm.readFile(email + ".txt") ;
			} catch (IOException e) {System.out.println("Inavlid email or Pass");}
		
		Current_User.setEmail(email);
		Current_User.setPassword(password);
		
		if(t) // teacher
		{	
			if (text.length()==2)
			{
				UserData empty = new UserData("","","","t") ;
				data.add(empty) ;
				return data ;
			}
				Scanner input = new Scanner(text);
				while (input.hasNext())
				{
					String scinceCategory = input.next() ;
					String schema = input.next() ;
					String name = input.next() ;
					UserData temp = new UserData(name, scinceCategory, schema , "t") ;
					data.add(temp);
				}
			
		}
		else if(s)
		{
			System.out.println(text.length());
			if (text.length()==2)
			{
				UserData empty = new UserData("",0,"s") ;
				data.add(empty) ;
				return data ;
			}
			Scanner input = new Scanner(text);
			while (input.hasNext())
			{
				String name = input.next() ;
				int score = input.nextInt();
				UserData temp = new UserData(name, score , "s") ;
				data.add(temp);
			}
		}
		else
		{
			System.out.println("wrong Email or password please check your data !");
		}
		return data ;
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/CreateCourse/{CourseName}/{CourseID}/{TeacherName}")
	@ResponseBody
	public boolean CreateCourse(@PathVariable String CourseName ,@PathVariable String CourseID ,@PathVariable String TeacherName )
	{
		boolean flag=true ;
		Course course = new Course(CourseName , CourseID , TeacherName) ;
		flag=course.InsertInDB();
		return flag ;
	}
	
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/ShowCourses")
	@ResponseBody
	public ArrayList<Course> ShowCourses() throws IOException
	{
		ArrayList<Course> courses = new ArrayList<Course>() ;
		DataManagment dm=new DataManagment();
		String text="" ;
		text = dm.readFile("Courses.txt") ;
		Scanner input = new Scanner(text);
		while (input.hasNext())
		{
			String CourseName = input.next() ;
			String CourseID = input.next() ;
			String TeacherName = input.next() ;
			Course temp = new Course(CourseName, CourseID, TeacherName) ;
			courses.add(temp);
		}
		System.out.println(courses.get(0).Name);
		return courses ;
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/ShowGames/{CourseName}")
	@ResponseBody
	public ArrayList<Game> ShowGames(@PathVariable String CourseName) throws IOException
	{
		ArrayList<Game> games = new ArrayList<Game>() ;
		DataManagment dm=new DataManagment();
		String text="" ;
		text = dm.readFile(CourseName+".txt") ;
		System.out.println(text);
		Scanner input = new Scanner(text);
		while (input.hasNext())
		{
			String schema = input.next() ;
			String GameName = input.next() ;
			Game temp = new Game(GameName, schema) ;
			games.add(temp);
		}
		return games ;
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/AddGame/{GameName}/{Schema}/{TeacherEmail}/{CourseName}")
	@ResponseBody
	public boolean AddGame(@PathVariable String GameName , @PathVariable String Schema ,
						@PathVariable String CourseName , @PathVariable String TeacherEmail) throws IOException
	{
		boolean x=true ;//to check existance .. True mean there is no same Game Name
		String textWrite = "" ;
		DataManagment dm=new DataManagment() ;
		
		//Add CourseName , Schema , GameName in GamesFile
		textWrite = "" ;
		textWrite=dm.readFile("games"+".txt");
		Scanner input = new Scanner(textWrite);
		while (input.hasNext())
		{
			String Cname = input.next() ;
			String schema = input.next() ;
			String Gname = input.next() ;
			if (GameName.equals(Gname))
			{
				x=false ;
				return x;
			}
		}
		textWrite+="\n"+CourseName + "\t" + Schema + "\t" +GameName  ;
		dm.writeFile(textWrite, "games");
		
		//Create File with Game Name
		dm.createFile(GameName);
		
		//Write Schema & GameName In Course File
		textWrite=dm.readFile(CourseName+".txt");
		textWrite+="\n"+Schema + "\t" +GameName  ;
		dm.writeFile(textWrite, CourseName);
		
		//Add CourseName , Schema , GameName in TeacherFile
		textWrite = "" ;
		textWrite=dm.readFile(TeacherEmail+".txt");
		if (textWrite.length()==0)
		{
			textWrite+=CourseName + "\t" + Schema + "\t" +GameName  ;
		}
		else
			{textWrite+="\n"+CourseName + "\t" + Schema + "\t" +GameName  ;}
		
		dm.writeFile(textWrite, TeacherEmail);
		
		return x;
				
	}
	
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/AddTF_Question/{Question}/{Answer}/{GameName}")
	@ResponseBody
	public void AddTF_Question(@PathVariable String Question , @PathVariable String Answer ,
							   @PathVariable String GameName) throws IOException
	{
		DataManagment dm =new DataManagment() ;
		String textWrite = "" ;
		textWrite=dm.readFile(GameName+".txt");
		textWrite+=Question + "\t" + Answer + "\r\n"  ;
		dm.writeFile(textWrite, GameName);
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/AddMCQ_Question/{Question}/{FirstChoice}/{SecondChoice}/{ThirdChoice}/{FourthChoice}/{Answer}/{GameName}")
	@ResponseBody
	public void AddMCQ_Question(@PathVariable String Question ,@PathVariable String FirstChoice ,@PathVariable String SecondChoice ,
								@PathVariable String ThirdChoice , @PathVariable String FourthChoice , @PathVariable String Answer ,
							    @PathVariable String GameName) throws IOException
	{
		DataManagment dm =new DataManagment() ;
		String textWrite = "" ;
		textWrite=dm.readFile(GameName+".txt");
		textWrite+=Question + "\r\n" + FirstChoice + "\r\n" +SecondChoice + "\r\n" +ThirdChoice + "\r\n" +FourthChoice+ "\r\n"  + Answer + "\r\n"  ;
		dm.writeFile(textWrite, GameName);
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/GetSchema/{GameName}")
	@ResponseBody
	public String GetSchema (@PathVariable String GameName) throws IOException
	{
		String text = "" ;
		DataManagment dm=new DataManagment() ;
		
		//check game schema in GamesFile
		text = "" ;
		text=dm.readFile("games"+".txt");
		Scanner input = new Scanner(text);
		String GameSchema="";
		while (input.hasNext())
		{
			String Cname = input.next() ;
			String schema = input.next() ;
			String Gname = input.next() ;
			if (GameName.equals(Gname))
			{
				GameSchema=schema ;
				return GameSchema ;
			}
		}
		
		System.out.println("There is no Game With this name ");
		return GameSchema ;
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/Play_MCQGame/{GameName}")
	@ResponseBody
	public ArrayList<MCQ_Question> Play_MCQGame(@PathVariable String GameName ) throws IOException
	{
		GamePlaying = GameName ;
		ArrayList<MCQ_Question> Questions = new ArrayList<MCQ_Question>() ;
		
		DataManagment dm = new DataManagment() ;
		String text = "" ;
		text=dm.readFile(GameName+ ".txt") ;
		Scanner input = new Scanner(text) ;
		while (input.hasNext())
		{
			String question = input.next() ;
			String firstChoice =input.next();
			String secondChoice =input.next();
			String thirdChoice =input.next();
			String fourthChoice =input.next();
			String ans = input.next();
			MCQ_Question temp = new MCQ_Question(question , firstChoice , secondChoice , thirdChoice , fourthChoice , ans) ;
			Questions.add(temp) ;
		} 
		return Questions ;
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/Play_TFGame/{GameName}")
	@ResponseBody
	public ArrayList<TF_Question> Play_TFGame(@PathVariable String GameName ) throws IOException
	{
		GamePlaying = GameName ;
		ArrayList<TF_Question> Questions = new ArrayList<TF_Question>() ;
		
		DataManagment dm = new DataManagment() ;
		String text = "" ;
		text=dm.readFile(GameName+ ".txt") ;
		Scanner input = new Scanner(text) ;
		while (input.hasNext())
		{
			String question = input.next() ;
			String ans = input.next();
			TF_Question temp = new TF_Question(question , ans) ;
			Questions.add(temp) ;
		} 
		return Questions ;
	}
	
	@CrossOrigin(origins = "http://localhost:8060")
	@GetMapping("/SaveScore/{Score}")
	@ResponseBody
	public boolean SaveScore (@PathVariable String Score) throws IOException
	{
		String GameName = GamePlaying ;
		int AddedScore = Integer.parseInt(Score) ;
		
		DataManagment dm = new DataManagment() ;
		boolean isStudent = dm.CheckStudent(Current_User);
		if (isStudent==true)
		{
			
			boolean GameExist =false ; //check he play this game before
			String text = dm.readFile(Current_User.getEmail() + ".txt")  , textWrite="";
			Scanner input = new Scanner(text) ;
			while (input.hasNext())
			{
				String GName = input.next() ;
				String score = input.next();
				if (GName.equals(GameName)) //he played it before
				{
					GameExist = true ;
					int oldScore = Integer.parseInt(score) ;
					int newScore = oldScore + AddedScore ;
					score = Integer.toString(newScore) ;
				}
				textWrite += GName + "\t" + score + "\r\n" ;
			} 
			if (GameExist==false)//first time play it 
			{
				String GName = GameName ;
				String score = Integer.toString(AddedScore);
				textWrite += GName + "\t" + score + "\r\n" ;
			}
			dm.writeFile(textWrite, Current_User.getEmail());
			System.out.println("Done");
			return true ;
		}
		return false ;
	}
	
	
}
