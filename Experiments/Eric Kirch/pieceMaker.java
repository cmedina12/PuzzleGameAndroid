public class pieceMaker
{
    //ONLY IN JAVA FOR NOW, WILL PORT ONCE WE KNOW WHAT WE'RE USING FOR THIS
	public static int width;//Image width
	public static int height;//Image height
	public static int pieceAmount;//Would set this value depending on difficulty, 3x3 would be 3, 5x5 would be 5, etc.

	public Piece pieceList[];

	public pieceMaker()
	{
		width = 0;
		height = 0;
		pieceAmount = 0;
	}

	public pieceMaker(int h, int w, int pieces)
	{
		width = w;
		height = h;
		pieceAmount = pieces;
		pieceList = new Piece[pieces*pieces];//Makes array that will contain the amount of pieces
	}

	private class Piece
	{
		public int pieceID;//ID for piece
		private int width1;//Left
		private int width2;//Right
		private int height1;//Top
		private int height2;//Bottom
		//Image data goes here

		public Piece(int id, int l, int r, int t, int b)
		{
			pieceID=id;
			width1=w;
			width2=r;
			height1=t;
			height2=b;
		}
	}

	public void fill()
	{
		//Method to fill pieceList with the necesarry data
	}


}