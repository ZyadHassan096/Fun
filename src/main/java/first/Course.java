package first;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Course {
	public String Name ;
	public String Course_ID ;
	public String Teacher_Name ;
	Course (){};
	public Course (String n , String C_ID , String T_Name)
	{
		Name=n ; Course_ID = C_ID ; Teacher_Name=T_Name ;
	}
	public boolean InsertInDB()
	{
		String s = "";
		DataManagment dm = new DataManagment() ;
		String CourseText = "" ;
		try {
			 CourseText = dm.readFile("Courses.txt") ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner input = new Scanner (CourseText);
		
		while (input.hasNext())//check if there is same ID 
		{
			String CourseName = input.next() ;
			String CourseID = input.next() ;
			if (CourseID.equals(Course_ID)) 
			{
				System.out.println("There is Same ID");
				return false;
			}
			String TeacherName = input.next() ;
			s +=  CourseName + "\t" + CourseID + "\t" + TeacherName +"\r\n" ;
		}
		s += Name + "\t" + Course_ID + "\t" + Teacher_Name +"\r\n" ;
		dm.writeFile(s, "Courses");//add course data to courses file 
		dm.writeFile("", Name);//create empty file with name of new course
		return true ;
	}
	
	
}
