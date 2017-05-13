package first;

public class MCQ_Question {
	public String Question ;
	public String FirstChoice ;
	public String SecondChoice ;
	public String ThirdChoice ;
	public String FourthChoice ;
	public String Answer ;
	
	public MCQ_Question() {}
	
	public MCQ_Question(String question, String firstChoice, String secondChoice, String thirdChoice,
			String fourthChoice, String answer) {
		Question = question;
		FirstChoice = firstChoice;
		SecondChoice = secondChoice;
		ThirdChoice = thirdChoice;
		FourthChoice = fourthChoice;
		Answer = answer;
	}

}
