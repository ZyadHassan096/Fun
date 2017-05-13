package first;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManagment {
		private String UserName;
		static int position=0;
		boolean teacher;
	public String getUserName()
		{
			return UserName;
		}
	public void updatePassword(String Email,String pass)	
		{	ArrayList<TeacherAccount> tacc=	loadTeacherData();
			ArrayList<StudentAccount> sacc=loadStudentData();
			CheckEmail(Email);
			if(teacher)
			{
				tacc.get(position).getInformation().setPassword(pass);
				File f=new File("TeacherData.txt");
				f.delete();
				try {
					f.createNewFile();
					PrintWriter pw=new PrintWriter(f);
					for(int i=0;i<tacc.size();i++)
					{
						pw.println(tacc.get(i).getInformation().getName()+"\t"+tacc.get(i).getInformation().getEmail()+"\t"+tacc.get(i).getInformation().getPassword()+"\t"+tacc.get(i).getInformation().getGender());
					}
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				sacc.get(position).getInformation().setPassword(pass);
				File f=new File("StudentData.txt");
				f.delete();
				try {
					f.createNewFile();
					PrintWriter pw=new PrintWriter(f);
					for(int i=0;i<sacc.size();i++)
					{
						pw.println(sacc.get(i).getInformation().getName()+"\t"+sacc.get(i).getInformation().getEmail()+"\t"+sacc.get(i).getInformation().getPassword()+"\t"+sacc.get(i).getInformation().getGender());
					}
					pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
			
		}
	public boolean Validate (Information info)
	{
		
		if(!info.getEmail().contains("@")||(info.getPassword().length()<8)||!info.getEmail().contains(".com"))
			return false;
		else
			return true;
	}
	
	public boolean CheckTeacher (LoginInfo log)
	{
		ArrayList<TeacherAccount> ta=loadTeacherData();
		
		for(int i=0;i<ta.size();i++)
		{
			if(ta.get(i).getInformation().getEmail().equals(log.getEmail())&&ta.get(i).getInformation().getPassword().equals(log.getPassword()))
			{	
				UserName=ta.get(i).getInformation().getName();
				return true;
			}
		}
		return false;	
	}
	public boolean CheckStudent(LoginInfo log)
	{	ArrayList<StudentAccount> ta=loadStudentData();
		for(int i=0;i<ta.size();i++)
		{
			if(ta.get(i).getInformation().getEmail().equals(log.getEmail())&&ta.get(i).getInformation().getPassword().equals(log.getPassword()))
					{	UserName=ta.get(i).getInformation().getName();
						return true;
					}
		}
		return false;
	}
	
	public boolean CheckEmail (String loginEmail){
		ArrayList<StudentAccount> ta=loadStudentData();
		ArrayList<TeacherAccount> tac=loadTeacherData();
		for(int i=0;i<ta.size();i++)
		{
			if(ta.get(i).getInformation().getEmail().equals(loginEmail))
			{	teacher=false;
				position=i;
				return true;
			}
		}
		for(int i=0;i<tac.size();i++)
		{
			if(tac.get(i).getInformation().getEmail().equals(loginEmail))
					{	teacher=true;
						position=i;
						return true;
					}
		}
		return false;
	
	}
	
	public ArrayList<StudentAccount> loadStudentData()
	{

		ArrayList<StudentAccount> listOfStudent=new ArrayList<StudentAccount>();
		
		File f=new File("StudentData.txt");
		try {
			Scanner s=new Scanner (f);
			while(s.hasNextLine())
			{	Information info=new Information();
			StudentAccount tacc=new StudentAccount();
				
				String line=s.nextLine();
				String arr[]=line.split("\t");
				info.setName(arr[0]); 
				info.setEmail(arr[1]);
				info.setPassword(arr[2]);
				info.setGender(arr[3].charAt(0));
				tacc.setInformation(info);
				tacc.setCoins((Integer.parseInt(arr[4])));
				listOfStudent.add(tacc);
				
			}
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listOfStudent;
		
	}
	
	public ArrayList<TeacherAccount> loadTeacherData()
	{	
		ArrayList<TeacherAccount> listOfTeacher=new ArrayList<TeacherAccount>();
		
		
		File f=new File("TeacherData.txt");
		try {
			Scanner s=new Scanner (f);
			while(s.hasNextLine())
			{	Information info=new Information();
			TeacherAccount tacc=new TeacherAccount();
				
				String line=s.nextLine();
				String arr[]=line.split("\t");
				info.setName(arr[0]); 
				info.setEmail(arr[1]);
				info.setPassword(arr[2]);
				info.setGender(arr[3].charAt(0));
				tacc.setInformation(info);
				listOfTeacher.add(tacc);
				
			}
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listOfTeacher;
	}
	public void addTeacher(TeacherAccount teacher) {
		// TODO Auto-generated method stub
		String textWrite = "" ;
		
		ArrayList<TeacherAccount> allTeachers = loadTeacherData() ;
		
		for (int i=0 ; i<allTeachers.size() ; i++)
		{
			textWrite+=allTeachers.get(i).getInformation().getName() + "\t" +allTeachers.get(i).getInformation().getEmail() + "\t" 
					+allTeachers.get(i).getInformation().getPassword() + "\t" + allTeachers.get(i).getInformation().getGender() + "\r\n" ;

		}
		
		textWrite+=teacher.getInformation().getName() + "\t" +teacher.getInformation().getEmail() + "\t" +teacher.getInformation().getPassword() 
				+ "\t" + teacher.getInformation().getGender() + "\r\n" ;
		
		writeFile(textWrite, "TeacherData");
		createFile(teacher.getInformation().getEmail());
	}
	
	public void addStudent(StudentAccount stud) {
		// TODO Auto-generated method stub
		String textWrite = "" ;
		
		ArrayList<StudentAccount> allStud = loadStudentData() ;
		
		for (int i=0 ; i<allStud.size() ; i++)
		{
			textWrite+=allStud.get(i).getInformation().getName() + "\t" +allStud.get(i).getInformation().getEmail() + "\t" 
					+allStud.get(i).getInformation().getPassword() + "\t" + allStud.get(i).getInformation().getGender() 
					+ "\t" + allStud.get(i).getCoins() + "\r\n" ;

		}
		
		textWrite+=stud.getInformation().getName() + "\t" +stud.getInformation().getEmail() + "\t" +stud.getInformation().getPassword() 
				+ "\t" + stud.getInformation().getGender() + "\t200" + "\r\n"  ;
		
		writeFile(textWrite, "StudentData");
		createFile(stud.getInformation().getEmail());

	}
		
	public ArrayList<Achievement> loadAchievements (String email)
	{
		ArrayList<Achievement> ach = new ArrayList<Achievement>() ;
		
		String text = "" ;
		try {
			System.out.println("email: "+email);
			text = readFile(email + ".txt") ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		if (text.equals("0"))
		{
			System.out.println("********************************************");
			return ach ;
		}
		else
		{
			Scanner input = new Scanner(text);

			while (input.hasNext())
			{
				String name = input.next() ;
				int score = input.nextInt() ;
				Achievement a = new Achievement(score, name) ;
				ach.add(a) ;
			}
		
			return ach ;
		}
	}
	
	public void updateStudents(String email , int coins , int scoreIndex , int score) {
		
		ArrayList<StudentAccount> allStud = loadStudentData() ;
		String text = "" ;
		
		for (int i=0 ; i<allStud.size() ; i++)
		{
			System.out.println(email);
			if (allStud.get(i).info.getEmail().equals(email)) 
			{
				text+=allStud.get(i).getInformation().getName() + "\t" +allStud.get(i).getInformation().getEmail() + "\t"  +allStud.get(i).getInformation().getPassword()
						+ "\t" + allStud.get(i).getInformation().getGender() + "\t" + coins + "\r\n" ;
			}
			else
				text+=allStud.get(i).getInformation().getName() + "\t" +allStud.get(i).getInformation().getEmail() + "\t"  +allStud.get(i).getInformation().getPassword()
					+ "\t" + allStud.get(i).getInformation().getGender() + "\t" + allStud.get(i).getCoins() + "\r\n" ;
		}
		
		System.out.println(text);
		
		writeFile(text,"StudentData");
		
		
		ArrayList<Achievement> ach = loadAchievements(email) ;
		ach.get(scoreIndex).setScore(score);
		
		text = "" ;
		
		for (int i=0 ; i<ach.size() ; i++)
		{
			text += ach.get(i).getAchievement() + "\t" + ach.get(i).getScore() + "\r\n" ;
		}
		writeFile(text, email);
		
	}
	
	/**************    READERS && WRITERS  **************************/
	public static void createFile (String fileName )
	{
		try{
		    PrintWriter writer = new PrintWriter(fileName + ".txt", "UTF-8");
		    writer.println("");
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}
	
	
	public static String readFile(String path) throws IOException {
		String content = null;
		File file = new File(path);
		
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return content;
	}
	
	public static void writeFile(String text , String name) {
		BufferedWriter writer = null;
		try {
			
			String fileName = name+".txt" ;
			File logFile = new File(fileName);
		
			
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(text);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}

	}	
		
	
}
