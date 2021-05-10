//Version 0.1: Beginning of the data structure used for player's score, written in Java for now, may be changed later
public class Score
{
	private int time;//In seconds
	private String user;//Username

	public Score()
	{
		time=Integer.MAX_VALUE;
		user="Dummy User";
	}

	public Score(int seconds, String player)
	{
		time=seconds;
		user=player;
	}

	public int getTime()
	{
		return time;
	}

	public String getUser()
	{
		return user;
	}

	@Override
	public int compareTo(Score s)
	{
		return time.compareTo(s.getTime());
	}
}