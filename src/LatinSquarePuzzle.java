/*
 * Kurt Kangas
 * CIS 2212 Java Software Development I
 * Latin Square Puzzle
 */
import java.util.Scanner;

@SuppressWarnings("unused")
public class LatinSquarePuzzle {
	private Scanner input;

	// Feel free to change the colors.
	private final int DIM = 4;	// 4x4 Latin Square
	private final String TEXTCOLOR_RESET = "\u001B[0m";
	private final String VARCOLOR = "\u001B[45;1;15m";
	private final String VARCOLOR1 = "\u001B[31m\u001B[42m";  // Color code for the variables in the square.

	// The puzzle must be solved with only the digits in this string.
	private String legalDigits = "2 6 7 8";	 


	// TODO: Declare and initialize all of your collections here.  Make them private.
	private char[][]square = { {'7','A','B','2'},
			{'C','2','8','D'},
			{'E','7','2','F'},
			{'2','G','H','8'} };
	private char[][]originalSquare;
	private int [] RowAnswers = {60, 13, 52, 11};
	private int [] ColAnswers = {48, 17, 43, 12};

	private char[][] MathOperators = {{ 'x'},
			{'+'},
			{'-'},
			{'='}};

	//** 3x3 Test, Stuff will need to be uncommented out in order for this to work. Check comments for stuff needed to comment out.
	// Uncomment out Lines - Line 195
	// Comment out Lines - Line 192
	// Lines - 277 & 318 are up to you for styling.
	/*

	private char[][]square = { {'1','A','3'},
								{'2','3','B'},
								{'C','1','2'} };
	private char[][]originalSquare;
	// Answers for Latin Square Example - A = 2 , B = 1, C = 3
	private int [] RowAnswers = {5, 7, 5};
	private int [] ColAnswers = {5, 7, 5};

	private String legalDigits = "1 2 3";	

	private char[][] MathOperators = {{ 'x'},
			{'+'},
			{'='}};
	 */

	/**
	 * Construct a LatinSquarePuzzle object
	 */
	public LatinSquarePuzzle() {
		input = new Scanner(System.in);
	}

	/**
	 * This method controls the game.
	 */
	public void go() {
		displayTheRules();

		// TODO: Call the other game methods until either the user solves the puzzle
		//		or they give up.


		boolean again = false;
		do {
			copySquare();
			displayTheSquare();
			System.out.println("\n");
			System.out.println("Enter your solution for each variable.");
			inputTheSolution();
			System.out.println("\nHere's your solution:");
			displayTheSquare();
			boolean isValid = checkTheSquare();
			if (isValid == true)
			{		
				System.out.println("\nThat's correct!");
				again = false;

			}else {
				System.out.println("\n\nThat's incorrect. Do you want to try again? (Y/N)");
				String Again = input.nextLine();
				if (Again.charAt(0) == 'Y' || Again.charAt(0) == 'y'){
					square = originalSquare;
					again  = true;
					displayTheRules();
				}else {
					again = false;	
				}
			}	
		}while(again);	
	}

	/**
	 * Display the rules and the legal digits.
	 */
	private void displayTheRules() {
		System.out.println(TEXTCOLOR_RESET + "Solve the Latin Square Puzzle!");
		System.out.print("The legal digits for this puzzle are: " + legalDigits);
		System.out.println("\nEach digit can be used only 1 time in each column and each row.");	
	}

	/**
	 * Display the square
	 */
	private void displayTheSquare() {
		// TODO: Write the code that displays the square.
		//		This part is challenging.  It involves several loops and decisions.
		//		Don't try to solve it all at once.  Write - test - write - test - write ...
		for (int r = 0; r < square.length; r++)
		{
			// Prints the lines of characters
			for (int c = 0; c < square[0].length; c++)
			{
				if (square[r][c] >= 'A' && square[r][c] <= 'Z')
				{
					System.out.print(VARCOLOR);
					System.out.print(square[r][c]);
					System.out.print(TEXTCOLOR_RESET);
					System.out.print(" ");
				}
				else
				{
					System.out.print(square[r][c]);
					System.out.print(" ");
				}
				System.out.print(MathOperators[c]);
				System.out.print(" ");
			}
			System.out.print(RowAnswers[r]);
			System.out.println();
			// Prints the lines of Math operators
			for (int i = 0; i < square.length; i++)
			{
				System.out.print(MathOperators[r]);
				System.out.print("   ");
			}
			System.out.println();
		}
		for (int r = 0; r < square.length; r++)
		{
			System.out.print(ColAnswers[r]);
			System.out.print("  ");
		}
	}

	/**
	 * Input the digits from the user to solve the puzzle.
	 */
	private void inputTheSolution() {
		// TODO: This part is easier.  Loop through the square.
		//		When you find a letter in the square, ask the user to enter a digit.
		//		Replace the letter in the square with the digit.

		for (int r = 0; r < originalSquare.length; r++)
		{
			String INPUT_SQUARE = "";
			for (int c = 0; c < originalSquare[0].length; c++)
			{
				if (((originalSquare[r][c] - '0') >= 0) && ((originalSquare[r][c] - '0') <= 9))
				{

				}
				else
				{
					System.out.printf("%c: ", originalSquare[r][c]);
					INPUT_SQUARE = input.nextLine();
					square[r][c] = INPUT_SQUARE.charAt(0);
				}
			}
			//System.out.println();
		}
	}

	/**
	 * Copy the original square into a temporary work space so we can go back to the original.
	 */
	private void copySquare() {
		// TODO: Copy the original square to a temporary square.
		//		1. Instantiate a new 2D array.
		//		2. Loop through the rows.
		//		3. Use the Array clone method to create a copy of the columns.
		//			temp[row] = original[row].clone();
		originalSquare = new char[4][2];

		//** This will be used for whatever size you want, example this is a 3 x 3
		//originalSquare = new char[3][3];

		for (int r = 0; r < square.length; r++)
		{
			originalSquare[r] = square[r].clone();
			for (int c = 0; c < square[0].length; c++)
			{
				originalSquare[c] = square[c].clone();
			}
		}
	}

	/**
	 * See if the puzzle is solved.
	 * @return true if the solution is correct, otherwise false.
	 */
	private boolean checkTheSquare() {
		// Note:  This method returns false as soon as a wrong answer is discovered.

		// TODO: Check for a valid solution.  This is also challenging.  
		//		1. Is each digit legal?
		//		2. Does each digit appear only once in each row and each column?
		//		3. Does the result of the math equation for each row and column match the correct answer?

		// Check the square
		int count = 0;
		boolean GoForward = false;
		boolean GoForwardToCols = GoForward;
		boolean ReturnResult = GoForwardToCols;

		for (int r = 0; r < square.length; r++)
		{
			count = r;
			if (count != 0)
			{
				count = count + count - 1;
			}
			for (int c = 0; c < square[0].length; c++)
			{
				if ( ((square[r][c] - '0') >= 0) && ((square[r][c] - '0') <= 9) )
				{		
					if (legalDigits.charAt(count) == ' ')
					{
						count++;
					}
					if ((square[r][c] - '0') == (legalDigits.charAt(count) - '0'))
					{
						GoForward = true;
					}
				}
			}
		}
		// Check Rows
		if (GoForward = true)
		{
			int max = square.length;
			int min = 0;
			int counter = 0;
			String Lines = "";
			for (int r = 0; r < square.length; r++)
			{
				for (int c = 0; c < square[0].length; c++)
				{
					Lines += square[r][c];
				}
			}
			for (int r = 0; r < square.length; r++)
			{
				int Value = 0;
				for (int c = 0; c < square[0].length; c++)
				{
					counter++;
					if (counter >= max)
					{
						min = 0;

						//** IF CHANGING THE SQUARES MATH OPERATORS YOU WILL NEED TO DELETE THE CORRESPONDING LINE & LINE BELOW (min++).

						Value = (Lines.charAt((counter - max)) - '0');  					// Initial value (Always keep)
						min++;
						Value = Value * (Lines.charAt((counter - (max - min))) - '0');		// Multiplcation
						min++;
						Value = Value + (Lines.charAt((counter - (max - min))) - '0');		// Addition
						min++;
						Value = Value - (Lines.charAt(counter - (max - min)) - '0');		// Subtraction
					}
				}
				if (Value == RowAnswers[r])
				{	
					GoForwardToCols = true;
				}
			}
		}
		// Check Cols
		if (GoForwardToCols = true)
		{
			int max = square.length;
			int min = 0;
			int counter = 0;
			String Lines = "";
			for (int r = 0; r < square.length; r++)
			{
				for (int c = 0; c < square[0].length; c++)
				{
					Lines += square[r][c];
				}
			}
			for (int r = 0; r < square.length; r++)
			{
				int Value = 0;
				for (int c = 0; c < square[0].length; c++)
				{
					counter++;
					if (counter >= max)
					{
						min = 0;

						//** IF CHANGING THE SQUARES MATH OPERATORS YOU WILL NEED TO DELETE THE CORRESPONDING LINE & LINE BELOW (min++).

						Value = (Lines.charAt((counter - max)) - '0');  					// Initial value (Always keep)
						min+=max;
						Value = Value * (Lines.charAt((counter - (max - min))) - '0');		// Multiplcation
						min+=max;
						Value = Value + (Lines.charAt((counter - (max - min))) - '0');		// Addition
						min+=max;
						Value = Value - (Lines.charAt(counter - (max - min)) - '0');		// Subtraction
						c = square[0].length;
					}
				}
				if (Value == ColAnswers[r])
				{	
					ReturnResult = true;
				}
				else
				{
					GoForward = false;  // This may be wrong?
				}
			}

		}
		return ReturnResult;// Just to make the compiler happy.  Fix this later.
	}
}
