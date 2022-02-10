import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class connect4{

	static int column = 3;
	static char AI = 'R';
	static char Player = 'B';
	


	public static void main(String[] args) {
		
		String line1 = ".......";
		String line2 = ".......";
		String line3 = ".......";
		String line4 = ".......";
		String line5 = ".......";
		String line6 = ".......";
		String[] board = new String[6];
		
		board[0] = line1;
		board[1] = line2;
		board[2] = line3;
		board[3] = line4;
		board[4] = line5;
		board[5] = line6;
	
		while(!isWon(board, AI) && !isWon(board, Player))
		{
			column = getMove(board, AI);
	        board = enterMove(board, column + 1, AI);
	        printBoard(board);
	        
	        Scanner input = new Scanner(System.in);	
            int move = input.nextInt();
            board = enterMove(board, move, Player);
            
            printBoard(board);
			
			if(isWon(board, AI))
			{
				System.out.println("AI team won!");
				printBoard(board);
			}
			else if(isWon(board, Player))
			{
				System.out.println("Player team won!");
				printBoard(board);
			}
		}		
	}

	public static String[] enterMove(String[] board, int move, char currPlayer)
	{

		move = move -1;
		for(int x = 5; x > -1; x--)
		{
			if(board[x].charAt(move) == '.')
			{
				StringBuilder temp = new StringBuilder(board[x]);
				temp.setCharAt(move, currPlayer);
				
				board[x] = temp.toString();
				
				break;
			}
		}
			
		return board;
	}
	
	public static boolean isWon(String[] board, char team)
	{
		
		for (int j = 0; j < 6 - 3  ; j++ ){
	        for (int i = 0; i<7; i++){
	            if (board[j].charAt(i) == team && board[j+1].charAt(i) == team && board[j+2].charAt(i) == team && board[j+3].charAt(i) == team){
	                return true;
	            }           
	        }
	    }
	    // verticalCheck
	    for (int i = 0; i<7-3 ; i++ ){
	        for (int j = 0; j < 6; j++){
	            if (board[j].charAt(i) == team && board[j].charAt(i + 1) == team && board[j].charAt(i + 2) == team && board[j].charAt(i + 3) == team){
	                return true;
	            }           
	        }
	    }
	    

	    // ascendingDiagonalCheck 
	    for (int i=3; i<7; i++){
	        for (int j=0; j<6-3; j++){
	            if (board[j].charAt(i) == team && board[j+1].charAt(i - 1) == team && board[j+2].charAt(i - 2) == team && board[j+3].charAt(i - 3) == team)
	                return true;
	        }
	    }
	    // descendingDiagonalCheck
	    for (int i1 = 3; i1<7; i1++)
	    {
	        for (int j=3; j<6; j++)
	        {
	            if (board[j].charAt(i1) == team && board[j-1].charAt(i1 - 1) == team && board[j-2].charAt(i1 - 2) == team && board[j-3].charAt(i1 - 3) == team)
	            {
	                return true;
	            }
	        }
	    }

	    return false;

	}
	
	public static int printBoard(String[] board)
	{
		System.out.println("  1    2    3    4    5    6    7");

		for(int x = 0; x < board.length; x ++)
		{
			for(int y = 0; y < 7; y ++)
			{
				if(board[x].charAt(y) == '.')
					System.out.print("[   ]");

				else if (board[x].charAt(y) == 'B')
					System.out.print("[ X ]");
				else if (board[x].charAt(y) == 'R')
					System.out.print("[ O ]");
			}
			System.out.println();
		}
		
	return 0;
	}
	public static int getMove(String[] board, char toMove)
  {
	  
        minimax(board, 3, true);

        return column;

  }
	
	public static boolean terminate(String[] board) 
	{
		boolean spaceLeft = true;
		
	    for (int x = 0; x < 6; x++)
	    {
	        for (int y = 0; y< 7; y++)
	        {
	            if (board[x].charAt(y) == '.')
	            {
	                spaceLeft = true;
	                break;
	            }
	            else 
	            	spaceLeft = false;
	        }
	    }		
	    
		if(isWon(board, AI) || isWon(board, Player))
			return true;
		

		else if (spaceLeft)
	    	return false;
	    
		return spaceLeft;
	}
	
	
	public static int score(String[] board, char piece)
	{
		int score = 0;
		
		char oPiece;
		if(piece == Player)
			oPiece = AI;
		else
			oPiece = Player;
		
		char center[] = new char[6];

		center[0] = board[5].charAt(3);
		center[1] = board[4].charAt(3);
		center[2] = board[3].charAt(3);


		if(center[0] == piece)
		{
			score += 10;
			if(center[1] == piece)
			{
				score += 10;
				if(center[2] == piece)
					score += 10;
			}	
		}
		
		char window[] = new char[4];
		
		//Horizontal
		for(int x = 0; x < 6; x++)
		{
			char row[] = new char[7];
	
			for(int y = 0; y < 7; y++)
			{
				row[y] = board[x].charAt(y);
	
			}
			for(int i = 0; i < 4; i++)
			{
				
				window[0] = row[i];
				window[1]= row[i + 1];
				window[2] =row[i + 2];
				window[3] = row[i + 3];
													
				if(window[0] == piece && window[1] == piece && window[2] == piece && window[3] == piece)
					score += 1000;
				else if(window[0] == piece && window[1] == piece && window[2] == piece && window[3] == '.')
					score += 10;
				else if(window[0] == piece && window[1] == piece && window[2] == '.' && window[3] == piece)
					score += 10;
				else if(window[0] == piece && window[1] == '.' && window[2] == piece && window[3] == piece)
					score += 10;
				else if(window[0] == '.' && window[1] == piece && window[2] == piece && window[3] == piece)
					score += 10;
				
				else if(window[0] == piece && window[1] == piece && window[2] == '.')
					score += 5;
				
				if(window[0] == oPiece && window[1] == oPiece && window[2] == oPiece && window[3] == '.')
					score = score - 80;
				else if(window[0] == oPiece && window[1] == oPiece && window[2] == '.' && window[3] == oPiece)
					score = score - 80;
				else if(window[0] == oPiece && window[1] == '.' && window[2] == oPiece && window[3] == oPiece)
					score = score - 80;
				else if(window[0] == '.' && window[1] == oPiece && window[2] == oPiece && window[3] == oPiece)
					score = score - 80;

			}
		}
		
		
		//Vertical 

		for(int y = 0; y < 7; y++)
		{

			char col[] = new char[6];
	
			int reverse = 0;
			for(int x = 5; x > -1; x--)
			{

				col[reverse] = board[x].charAt(y);
				reverse++;
			}
			
			for(int i = 0; i < 3; i++)
			{
				window[0] = col[i];
				window[1] = col[i + 1];
				window[2] = col[i + 2];
				window[3] = col[i + 3];
		
				
				if(window[0] == piece && window[1] == piece && window[2] == piece && window[3] == piece)
					score += 1000;
				else if(window[0] == piece && window[1] == piece && window[2] == piece && window[3] == '.')
					score += 10;
				else if(window[0] == piece && window[1] == piece && window[2] == '.' && window[3] == piece)
					score += 10;
				else if(window[0] == piece && window[1] == '.' && window[2] == piece && window[3] == piece)
					score += 10;
				else if(window[0] == '.' && window[1] == piece && window[2] == piece && window[3] == piece)
					score += 10;
				
				else if(window[0] == piece && window[1] == piece && window[2] == '.')
					score += 5;
				
				if(window[0] == oPiece && window[1] == oPiece && window[2] == oPiece && window[3] == '.')
					score = score - 80;
				else if(window[0] == oPiece && window[1] == oPiece && window[2] == '.' && window[3] == oPiece)
					score = score - 80;
				else if(window[0] == oPiece && window[1] == '.' && window[2] == oPiece && window[3] == oPiece)
					score = score - 80;
				else if(window[0] == '.' && window[1] == oPiece && window[2] == oPiece && window[3] == oPiece)
					score = score - 80;
			}
		}
		
		//Positive Slope
		char slope[] = new char[4];
		
		for(int x = 0; x < 3; x ++)
		{
			slope[0] = board[5 - x].charAt(0);		
			slope[1] = board[4 - x].charAt(1);		
			slope[2] = board[3 - x].charAt(2);		
			slope[3] = board[2 - x].charAt(3);		
	
			if(slope[0] == piece && slope[ 1]== piece && slope[2] == piece && slope[3]==piece)
					score += 1000;
			else if(slope[0] == '.' && slope[1]== piece && slope[2]== piece && slope[3]== piece)
					score += 10;
			else if(slope[0] == piece && slope[1]== '.' && slope[2]== piece && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== '.' && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== piece && slope[3]== '.')
				score += 10;
			else if(slope[0]== piece && slope[1] == piece && slope[2] == '.')
					score += 5;
			
			if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == oPiece && slope[3] == '.')
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == '.' && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == '.' && slope[2] == oPiece && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == '.' && slope[1] == oPiece && slope[2] == oPiece && slope[3] == oPiece)
				score = score - 80;
			
			slope[0] = board[5 - x].charAt(1);		
			slope[1] = board[4 - x].charAt(2);		
			slope[2] = board[3 - x].charAt(3);		
			slope[3] = board[2 - x].charAt(4);		
	
			if(slope[0] == piece && slope[ 1]== piece && slope[2] == piece && slope[3]==piece)
				score += 1000;
			else if(slope[0] == '.' && slope[1]== piece && slope[2]== piece && slope[3]== piece)
					score += 10;
			else if(slope[0] == piece && slope[1]== '.' && slope[2]== piece && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== '.' && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== piece && slope[3]== '.')
				score += 10;
			else if(slope[0]== piece && slope[1] == piece && slope[2] == '.')
					score += 5;
			
			if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == oPiece && slope[3] == '.')
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == '.' && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == '.' && slope[2] == oPiece && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == '.' && slope[1] == oPiece && slope[2] == oPiece && slope[3] == oPiece)
			score = score - 80;

			
			slope[0] = board[5 - x].charAt(2);		
			slope[1] = board[4 - x].charAt(3);		
			slope[2] = board[3 - x].charAt(4);		
			slope[3] = board[2 - x].charAt(5);		
	
			if(slope[0] == piece && slope[ 1]== piece && slope[2] == piece && slope[3]==piece)
				score += 1000;
			else if(slope[0] == '.' && slope[1]== piece && slope[2]== piece && slope[3]== piece)
					score += 10;
			else if(slope[0] == piece && slope[1]== '.' && slope[2]== piece && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== '.' && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== piece && slope[3]== '.')
				score += 10;
			else if(slope[0]== piece && slope[1] == piece && slope[2] == '.')
					score += 5;
			
			if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == oPiece && slope[3] == '.')
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == '.' && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == '.' && slope[2] == oPiece && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == '.' && slope[1] == oPiece && slope[2] == oPiece && slope[3] == oPiece)
				score = score - 80;
			
			slope[0] = board[5 - x].charAt(3);		
			slope[1] = board[4 - x].charAt(4);		
			slope[2] = board[3 - x].charAt(5);		
			slope[3] = board[2 - x].charAt(6);		
	
			if(slope[0] == piece && slope[ 1]== piece && slope[2] == piece && slope[3]==piece)
				score += 1000;
			else if(slope[0] == '.' && slope[1]== piece && slope[2]== piece && slope[3]== piece)
					score += 10;
			else if(slope[0] == piece && slope[1]== '.' && slope[2]== piece && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== '.' && slope[3]== piece)
				score += 10;
			else if(slope[0] == piece && slope[1]== piece && slope[2]== piece && slope[3]== '.')
				score += 10;
			else if(slope[0]== piece && slope[1] == piece && slope[2] == '.')
					score += 5;
			
			if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == oPiece && slope[3] == '.')
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == oPiece && slope[2] == '.' && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == oPiece && slope[1] == '.' && slope[2] == oPiece && slope[3] == oPiece)
				score = score - 80;
			else if(slope[0] == '.' && slope[1] == oPiece && slope[2] == oPiece && slope[3] == oPiece)
				score = score - 80;

		}
		
		
		//Negative Slope
		char negSlope[] = new char[4];
		
		for(int x = 0; x < 3; x ++)
		{
			negSlope[0] = board[5 - x].charAt(6);		
			negSlope[1] = board[4 - x].charAt(5);		
			negSlope[2] = board[3 - x].charAt(4);		
			negSlope[3] = board[2 - x].charAt(3);		
	
			if(negSlope[0] == piece && negSlope[ 1]== piece && negSlope[2] == piece && negSlope[3]==piece)
					score += 1000;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== '.')
					score += 10;
			else if(negSlope[0] == '.' && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== piece)
				score += 10;
			else if(negSlope[0] == piece && negSlope[1]== '.' && negSlope[2]== piece && negSlope[3]== piece)
				score += 10;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== '.' && negSlope[3]== piece)
				score += 10;
			else if(negSlope[0]== piece && negSlope[1] == piece && negSlope[2] == '.')
					score += 5;
			
			if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == '.')
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == '.' && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == '.' && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == '.' && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;

			
			negSlope[0] = board[5 - x].charAt(5);		
			negSlope[1] = board[4 - x].charAt(4);		
			negSlope[2] = board[3 - x].charAt(3);		
			negSlope[3] = board[2 - x].charAt(2);		
	
			if(negSlope[0] == piece && negSlope[ 1]== piece && negSlope[2] == piece && negSlope[3]==piece)
				score += 1000;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== '.')
				score += 10;
			else if(negSlope[0] == '.' && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0] == piece && negSlope[1]== '.' && negSlope[2]== piece && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== '.' && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0]== piece && negSlope[1] == piece && negSlope[2] == '.')
				score += 5;
			
			if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == '.')
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == '.' && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == '.' && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == '.' && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;
			
			negSlope[0] = board[5 - x].charAt(4);		
			negSlope[1] = board[4 - x].charAt(3);		
			negSlope[2] = board[3 - x].charAt(2);		
			negSlope[3] = board[2 - x].charAt(1);		
	
			
			if(negSlope[0] == piece && negSlope[ 1]== piece && negSlope[2] == piece && negSlope[3]==piece)
				score += 1000;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== '.')
				score += 10;
			else if(negSlope[0] == '.' && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0] == piece && negSlope[1]== '.' && negSlope[2]== piece && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== '.' && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0]== piece && negSlope[1] == piece && negSlope[2] == '.')
				score += 5;
			
			if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == '.')
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == '.' && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == '.' && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == '.' && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;
			
			
			negSlope[0] = board[5 - x].charAt(3);		
			negSlope[1] = board[4 - x].charAt(2);		
			negSlope[2] = board[3 - x].charAt(1);		
			negSlope[3] = board[2 - x].charAt(0);		
	
			
			if(negSlope[0] == piece && negSlope[ 1]== piece && negSlope[2] == piece && negSlope[3]==piece)
				score += 1000;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== '.')
				score += 10;
			else if(negSlope[0] == '.' && negSlope[1]== piece && negSlope[2]== piece && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0] == piece && negSlope[1]== '.' && negSlope[2]== piece && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0] == piece && negSlope[1]== piece && negSlope[2]== '.' && negSlope[3]== piece)
			score += 10;
			else if(negSlope[0]== piece && negSlope[1] == piece && negSlope[2] == '.')
				score += 5;
			
			if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == '.')
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == oPiece && negSlope[2] == '.' && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == oPiece && negSlope[1] == '.' && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;
			else if(negSlope[0] == '.' && negSlope[1] == oPiece && negSlope[2] == oPiece && negSlope[3] == oPiece)
				score = score - 80;
			
		}
		
		
		return score;
	}
	
	public static boolean[] validDrops(String[] board) {
		boolean locations[] = new boolean[7];
		
		for(int x = 0; x < 7; x++) {
			if(board[0].charAt(x) != '.')
				locations[x] = true;
			else
				locations[x] = false;
		}
		
		return locations;
	}
	

	
	//Red is AI Black is user
	public static int minimax(String[] board, int depth, boolean maximizingPlayer)
	{
		boolean terminal_node = terminate(board);
		
		if(depth == 0 ||  terminal_node)
		{
			if(terminal_node) {
				if (isWon(board, AI))
					return 100000000;
				else if (isWon(board, Player))
					return -100000000;
				else
					return 0;
			}
			else 
				return score(board, AI);
		}
		

		if (maximizingPlayer)
		{			
			
			int best = -1000000;
			column = 0;

			for(int x = 0; x < board.length; x++)
			{
				for(int y = 0; y < 7; y++)
				{
					if(board[x].charAt(y) == '.')
					{
						int newScore;

						//makes move
						StringBuilder temp = new StringBuilder(board[x]);
						temp.setCharAt(y, AI);
						board[x] = temp.toString();
						
						//Call recursively 
						//best = Math.max(best, minimax(board, depth - 1, false));
						
						newScore = 	minimax(board, depth - 1, false);
						if (newScore > best)
						{
							best = newScore;
							column = y;
						}
						
						temp = new StringBuilder(board[x]);
						temp.setCharAt(y, '.');
						board[x] = temp.toString();
					}
				}
			}
			return best;
		}
		

		else
		{			
			int best = 1000000;
			column = 0;

			for(int x = 0; x < board.length; x++)
			{
				for(int y = 0; y < 7; y++)
				{
					if(board[x].charAt(y) == '.')
					{
						//might have to initialize before
						int newScore;
						//makes move
						StringBuilder temp = new StringBuilder(board[x]);
						temp.setCharAt(y, Player);
						board[x] = temp.toString();
						
						//Call recursively 
						//best = Math.min(best, minimax(board, depth - 1, true));
						newScore = 	minimax(board, depth - 1, true);
						if (newScore < best)
						{
							best = newScore;
							column = y;
						}
						
						temp = new StringBuilder(board[x]);
						temp.setCharAt(y, '.');
						board[x] = temp.toString();
					}
				}
			}
			return best;
		}
	}
	
	public int getColumn()
	{
		return column;
	}
	

}