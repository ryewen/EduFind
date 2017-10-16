package teachnet.info.object.rateLesson;

public class RateScore {
	
	public static final String[] STD_SCORES_0 = {"17.50", "14", "10.50", "7", "3.50"};
	
	public static final String[] STD_SCORES_1 = {"10.50", "8.40", "6.30", "4.20", "2.10"};
	
	public static final String[] STD_SCORES_2 = {"14", "11.20", "8.40", "5.60", "2.80"};
	
	private static final int SCORES_LENGTH = 6;
	
	private String[] scores = new String[SCORES_LENGTH];
	
	/*
	private String score0; //17.50 14 10.50 7 3.50
	
	private String score1; //10.50 8.40 6.30 4.20 2.10 
	
	private String score2; //17.50 14 10.50 7 3.50
	
	private String score3; //10.50 8.40 6.30 4.20 2.10 
	
	private String score4; //14 11.20 8.40 5.60 2.80 
	
	private String score5; //50-95
	*/
	
	private String words;
	
	public RateScore() {
		scores[0] = "17.50";
		scores[1] = "10.50";
		scores[2] = "17.50";
		scores[3] = "10.50";
		scores[4] = "14";
		scores[5] = "95";
		words = "good teacher";
	}
	
	public RateScore(String[] scores, String words) {
		this.scores = scores;
		this.words = words;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(! (o instanceof RateScore)) return false;
		RateScore rateScore = (RateScore) o;
		for(int i = 0; i < SCORES_LENGTH; i ++) {
			if(! rateScore.getScores()[i].equals(getScores()[i])) return false;
		}
		if(! rateScore.getWords().equals(getWords())) return false;
		return true;
	}
	
	public String addedScores() {
		String addedScores = "";
		for(String score : scores) {
			addedScores += score + "|";
		}
		return addedScores;
	}
	
	public String addedOrders() {
		String orders = "";
		for(int i = 0; i < scores.length; i ++) {
			if(i == 0 || i == 2) {
				orders += "0" + getOrder(scores[i], STD_SCORES_0) + "|";
			}
			if(i == 1 || i == 3) {
				orders += "0" + getOrder(scores[i], STD_SCORES_1) + "|";
			}
			if(i == 4) {
				orders += "0" + getOrder(scores[i], STD_SCORES_2) + "|";
			}
			if(i == 5) {
				orders += "|";
			}
		}
		return orders;
	}
	
	private int getOrder(String score, String[] std_scores) {
		for(int i = 0; i < std_scores.length; i ++) {
			if(score.equals(std_scores[i])) return i + 1;
		}
		return 1;
	}
	
	public void setScores(String[] scores) {
		this.scores = scores;
	}
	
	public String[] getScores() {
		return scores;
	}
	
	public void setWords(String words) {
		this.words = words;
	}
	
	public String getWords() {
		return words;
	}
}
