import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 *  A little ASCII GUI to help you interact with the game.
 *  Use with the command:
 *      java PowerConnectFourGUI 
 *  or 
 * 		java PowerConnectFourGUI Input_File_Name
 *  
 *  @author Y Zhong
 */	
public class PowerConnectFourGUI {

	/**
	 * Two possible input modes.
	 */
	
	enum Mode { KEYBOARD, FILE};
	 
	/**
	 *  The main method that presents the GUI.
	 *  
	 *  @param args command line args: first arg can specify an input file 
	 */
	public static void main(String[] args) {
		int step = 0;
		Scanner scanner=null;
		
		// initialize an empty grid
		PowerConnectFour game = new PowerConnectFour();
		Mode mode = Mode.FILE;
		String next;
		boolean validMove = false;
		
		if(args.length > 1){
			System.out.println("Usage: java PowerConnectFourGUI [Input_File_Name]");
			System.exit(0);
		}
		else if (args.length == 1){
			try{
				// open file for input
				scanner = new Scanner(new File(args[0]));				
			}catch(IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		else{
		
			// no file provided, use keyboard input (standard input)
			scanner = new Scanner(System.in);
			System.out.println("  Supported Moves: \n\tD-Drop, P-Pop, PD-Power Drop, PP-Power Pop, Q-Quit");
			System.out.println("  Example format: 'D 5' - Drop at Column 5");
			System.out.println("  Example format: 'PP 3 0' - Power Pop from Column 3 Row 0");
			mode = Mode.KEYBOARD;
		}

		
		System.out.println("-----------------------------------------------");
		System.out.println("- Starting Game");
		System.out.println("-----------------------------------------------");		
		displayGrid(game);
		Token player = game.currentPlayer();
		reportcurrentPlayer(game);
		
		if (mode==Mode.KEYBOARD)
			System.out.print("Next Move: ");
		else
			enterToContinue();

		while (scanner.hasNext()){
			int col, row;		

			next = scanner.next();
			validMove = false;
			step++;
			switch (next) {
				case "D": //drop
					col = scanner.nextInt();
					next = scanner.nextLine();
					System.out.println("-----------------------------------------------");	
					System.out.format(" %d: Move by player %c : Drop %d: ", step, player.getSymbol(), col);
					validMove = game.drop(col);
					break;
				case "P": //pop
					col = scanner.nextInt();
					next = scanner.nextLine();
					System.out.println("-----------------------------------------------");	
					System.out.format(" %d: Move by player %c : Pop %d: ", step, player.getSymbol(), col);
					validMove = game.pop(col);
					break;
				case "PD": //power drop
					col = scanner.nextInt();
					row = scanner.nextInt();
					next = scanner.nextLine();
					System.out.println("-----------------------------------------------");	
					System.out.format(" %d: Move by player %c : Power Drop Column %d Row %d: ", step, player.getSymbol(), col, row);
					validMove = game.powerDrop(col,row);
					break;
				case "PP": //power pop
					col = scanner.nextInt();
					row = scanner.nextInt();
					next = scanner.nextLine();
					System.out.println("-----------------------------------------------");	
					System.out.format(" %d: Move by player %c : Power Pop Column %d Row %d: ", step, player.getSymbol(), col, row);
					validMove = game.powerPop(col,row);
					break;
				case "Q": //quit
					System.out.println("-----------------------------------------------");	
					System.out.println(" - Ending Game");	
					System.out.println("-----------------------------------------------");	
					System.exit(0);	
					break;				
				default:
					System.out.println("-----------------------------------------------");	
					System.out.format(" %d: Move by player %c : %s ", step, player.getSymbol(), next);

			}
						
			if (validMove)
				System.out.println("Valid Move");
			else
				System.out.println("Invalid Move");	
			System.out.println("-----------------------------------------------");							
			displayGrid(game);

			if (game.hasFourConnected(player)){
				System.out.println("-----------------------------------------------");	
				System.out.format(" Winner: %c!\n", player.getSymbol());
				break;			
			}
			player = game.currentPlayer(); //if valid move, player already switched
			if (game.hasFourConnected(player)){
				System.out.println("-----------------------------------------------");	
				System.out.format(" Winner: %c!\n", player.getSymbol());
				break;			
			}
			reportcurrentPlayer(game);
			if (mode == Mode.FILE)
				enterToContinue();
			else
				System.out.print("Next Move: ");

		
		}
		System.out.println("-----------------------------------------------");	
		System.out.println(" - Ending Game");	
		System.out.println("-----------------------------------------------");	
		
	}
	
	/**
	 * The method that displays the grid of the game.
	 *
	 * @param game the connect four game to be displayed
	 */
	public static void displayGrid(PowerConnectFour game){
		int row = game.sizeRow();
		int col = game.sizeCol();
				
		System.out.format("|   |");
		for (int j=0; j<col; j++){
			System.out.format("| %d |", j);
		}
		System.out.println("");		
		for (int i=row-1; i>=0; i--){
			System.out.format("| %d |", i);
			for (int j=0; j<col; j++){
				Token cell = game.get(j,i);
				if (cell == null)
					System.out.format("| %c |", game.getEmptySymbol());
				else
					System.out.format("| %c |", cell.getSymbol());
			}
			System.out.println("");
		}

	}
	
	/**
	* The method that reports the current player of a game.
	*
	* @param game the connect four game involved
	*/
	public static void reportcurrentPlayer(PowerConnectFour game){
		if (game.currentPlayer()==null)
			System.out.format("Player info missing\n");
		else
			System.out.format("Player %c's turn\n", game.currentPlayer().getSymbol());	
	}
	
	/**
	 * The metod that interacts with the user and returns when user presses enter/return.
	 *
	 */
	public static void enterToContinue() {
		System.out.print("Press enter to continue ...");
		Scanner s = new Scanner(System.in);
		s.nextLine();
	}

}		
