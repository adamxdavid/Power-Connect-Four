/**
 *  Manages data structure and operations
 *  for the power connect 4 boardgame.
 *  @author Adam David
 */	
public class PowerConnectFour {
	
	/**
	 *  The grid to contain tokens. Cells can be empty.
	 * 	underlying array of columns for storage
	 */

	private Column<Token>[] grid;

	/**
	 *  The fixed number of columns the game grid should have.
	 */
	private static final int NUM_COLS = 7;

	/**
	 *  The minimum number of rows of the grid for display.
	 */
	private static final int MIN_ROWS = 6;

	/**
	 * playerOne is always the first to make a move when the game starts.
	 */
	private static final Token playerOne = Token.RED;

	/**
	 * playerTwo is always the second to make a move when the game starts.
	 */
	private static final Token playerTwo = Token.YELLOW;

	/**
	 * The character used to represent empty cells when the grid is displayed.
	 */
	private static final Character empty = Character.valueOf('-');
	
	/**
	 * When grid is displayed, the top row of the grid should always be empty.
	 */  
	private static final int MARGIN_ROWS = 1;
	
	/**
	 *  rows stores how many rows are to be displayed excluding margin.
	 */
	private int rows;

	/**
	 *  whosTurn stores the current token to be deployed.
	 */
	private Token whosTurn;
	
	/**
	 *  A default constructer. Creates underlying data structure.
	 */
	@SuppressWarnings("unchecked")	
	public PowerConnectFour() {
		// Constructor with no arguments.
		
		// A grid with NUM_COLS columns should be created.  The initial capacity of 
		// each column should be DEFAULT_CAPACITY defined in our Column class. 
		// All columns are empty initially(size 0).
		
		// Initialize game settings.
		
		//array of columns created
		grid = (Column<Token>[]) new Column[NUM_COLS];
		//adding 7 columns to grid
		for (int i = 0; i < 7; ++i) {
			grid[i] = new Column<Token>();
		}
		//the game will start with 6 rows, this can change later.
		this.rows = MIN_ROWS;
		this.whosTurn = playerOne;
		
	}


	/**
	 *  A getter method for number of columns.
	 *  @return will return the number of columns.
	 */
	public int sizeCol() { 
		// Return number of columns of the grid
		// Set to be a constant number.
		
		// O(1)
		return NUM_COLS; //default return, make sure to remove/change
	}
	
	/**
	 *  Calculates how many rows need to be displayed in GUI.
	 *  @return number of rows
	 */
	public int sizeRow() { 
		// Return number of rows _for DISPLAY_ of the grid
		
		// 	return of this method is used by GUI to set the appropriate display 
		//	range for the grid.
		//  Our rules for display:
		//   - always show at least MIN_ROWS for each column;
		//	 - if the number of pieces of any column reaches or grows beyond MIN_ROWS,
		//		make sure the display covers the "tallest" column and leaves one "margin"
		//		row at the top of the grid.
		
		// O(1)

		//get each columns size, rows must be 6 (if size is 0-5), or 1 greater than the biggest size (6+)
		int size;
		int max = 0;

		//getting longest column (store into max)
		for (int i = 0; i < NUM_COLS; ++i) {
			size = grid[i].size();
			if (size > max) {
				max = size;
			}
		}
		if (0 <= max && max <= 5) {
			this.rows = 6;
		}
		//rows will be 1 more than the longest column
		else if (max >= 6) {
			this.rows = max + 1;
		}

		return this.rows;
	}
	
	/**
	 *  A getter method for a constant.
	 *  @return symbol '-''
	 */
	public Character getEmptySymbol(){
		// Return the character defined for empty cells for display.
		// Set this to be a constant.
		
		// O(1)
		return empty; //default return, make sure to remove/change
	}
		

	/**
	 *  Calculates how many rows need to be displayed.
	 *  @param col index for column
	 *  @param row index for row
	 *  @return number of rows
	 */
	public Token get(int col, int row) {
		// Return token at the given column and row of the grid.

		// For an invalid row/col index (out of the range of current display), 
		// throw an IndexOutOfBoundsException.
		// Use this code to produce the correct error message for
		// the exception:
		//	  "Col " + col + ", Row "+ row + " out of bounds!"

		// Return null if the cell at the given col and row is empty
		// O(1)
		Token x;
		if (col < 0 || col > 6) {
			throw new IndexOutOfBoundsException("Col " + col + ", Row "+ row + " out of bounds!");
		}
		if (row < 0 || row >= sizeRow()) {
			throw new IndexOutOfBoundsException("Col " + col + ", Row "+ row + " out of bounds!");
		}
		//System.out.println(col +" "+ row);
		try {
			x = this.grid[col].get(row);
		}
		catch (IndexOutOfBoundsException e) {
			return null;
		}
		
		return x;
	}

	/**
	 *  Getter method for a column in the grid.
	 *  @param col index of column
	 *  @return column
	 */
	public Column<Token> getColumn(int col){
		// Return column at the given index
		
		// For an invalid column index, throw an IndexOutOfBoundsException.
		// Use this code to produce the correct error message for
		// the exception:
		//	  "Col " + col + " out of bounds!"
		
		// O(1)

		if (col < 0 || col > 6) {
			throw new IndexOutOfBoundsException("Col " + col + " out of bounds!");
		}

			
		return this.grid[col];
	}
	
	/**
	 *  Getter method for current player.
	 *  @return token red or yellow
	 */
	public Token currentPlayer(){
		// Return the player that can make the next move.
		// O(1)

		return whosTurn; 

	}

	/**
	 *  Drops a token in data structure.
	 *  @param col index of the column
	 *  @return boolean if it was sucessful or not
	 */
	public boolean drop(int col){
		// Current player drop a token at the given column.
		// Return true if the move can be made; return false 
		// if the move cannot be made for any reason.  Switch 
		// to the other player only if the move can be made successfully.
		
		// when a column grows, the display settings may need to be changed.
		
		// Amortized O(1)

		//when not 0-6, move is invalid, otherwise valid
		if (col < 0 || col > 6) {
			return false;
		}

		//rows to be displayed are auto managed in sizeRow()
		this.grid[col].add(whosTurn);
		if (whosTurn == Token.RED) {
			whosTurn = Token.YELLOW;
		}
		else if (whosTurn == Token.YELLOW) {
			whosTurn = Token.RED;
		}
		
		return true;
	}
	


	/**
	 *  Power drops a token in data structure.
	 *  @param col index of the column
	 *  @param row index of the row
	 *  @return boolean if it was sucessful or not
	 */
	public boolean powerDrop(int col, int row){
		// Current player drop/insert a token at the given column and row.
		// Note: no floating tokens allowed.
		
		// Return true if the move can be made; return false  if the move 
		// cannot be made for any reason.  Switch  to the other player only 
		// if the move is made successfully.

		// when a column grows, the display settings may need to be changed.

		// O(N) where N is the number of tokens in the involved column

		if (col < 0 || col > 6) {
			return false;
		}
		if (row > this.grid[col].size() || row < 0) {
			return false;
		}

		this.grid[col].add(row, whosTurn);

		if (whosTurn == Token.RED) {
			whosTurn = Token.YELLOW;
		}
		else if (whosTurn == Token.YELLOW) {
			whosTurn = Token.RED;
		}

		return true;
	}
	
	/**
	 *  Pops a token object in the underlying data structure.
	 *  @param col index of the column
	 *  @return boolean if it was sucessful or not
	 */
	public boolean pop(int col){
		// Current player pop a token from the given column.
		// Return true if the move can be made; return false 
		// if the move cannot be made for any reason.  Switch 
		// to the other player only if the move is made successfully.
		
		// when a column shrinks, the display settings may need to be changed.

		// O(N) where N is the number of tokens in the involved column

		//only 7 collumns 0-6
		if (col < 0 || col > 6) {
			return false;
		}
		//there has to be something to pop
		if (this.grid[col].size() == 0) {
			return false;
		}
		//gridrow[col][0] must be same token as current turn
		if (this.grid[col].get(0) != whosTurn) {
			return false;
		}

		this.grid[col].delete(0);

		//swap turns
		if (whosTurn == Token.RED) {
			whosTurn = Token.YELLOW;
		}
		else if (whosTurn == Token.YELLOW) {
			whosTurn = Token.RED;
		}
		return true;

	}

	/**
	 *  Power pops a token in data structure.
	 *  @param col index of the column
	 *  @param row index of the row
	 *  @return boolean if it was sucessful or not
	 */
	public boolean powerPop(int col, int row){
		// Current player pop/remove a token from the given column and row.

		// Note: tokens above the removed one need to be shifted to make sure
		// there are no floating tokens in grid.		
		// Return true if the move can be made; return false  if the move 
		// cannot be made for any reason.  Switch  to the other player only 
		// if the move is made successfully.

		// when a column shrinks, the display settings may need to be changed.

		// O(N) where N is the number of tokens in the involved column

		//only 7 collumns 0-6
		if (col < 0 || col > 6) {
			return false;
		}
		//there has to be something to pop
		if (this.grid[col].size() <= row || row < 0) {
			return false;
		}
		if (this.grid[col].get(row) != whosTurn) {
			return false;
		}
		this.grid[col].delete(row);

		//swap turns
		if (whosTurn == Token.RED) {
			whosTurn = Token.YELLOW;
		}
		else if (whosTurn == Token.YELLOW) {
			whosTurn = Token.RED;
		}


		return true;
	}
	

	/**
	 *  Checks for horizontal 4 in row.
	 *  @param col index of the column
	 *  @param row index of the row
	 *  @param player the player to check 4 ina row for
	 *  @return int the amt of tokens in a row
	 */
	public int countRow(int col, int row, Token player){
		// Count and return the number of consecutive tokens for the given player
		// in a row such that one of those tokens is at the given row and col 
		// of the grid.
		
		// Return 0 if out of bounds
		
		// O(1)
		int count = 0;

		//only 7 collumns 0-6
		if (col < 0 || col > 6) {
			return 0;
		}
		//does this column go that high, also prevents out of bounds
		if (row > this.grid[col].size() || row < 0) {
			return 0;
		}
		//is it null, does it not equal the right token.
		if (this.grid[col].get(row) == null || this.grid[col].get(row) != player) {
			return 0;
		}

		//counting from beginning, col = 0
		if (col == 0) {
			//iterate through columns, keep row the same
			for (int i = 0; i < 7; ++i) {
				//checks that get is out of bounds or not
				if (this.grid[i].size() <= row) {
					break;
				}
				if (this.grid[i].get(row) == player) {
					++count;
				}
				else {
					break;
				}
			}
		}

		//both ways count
		else if (col>0 && col<6) {
			for (int i = col; i < 7;++i) {
				//checks that get is out of bounds or not
				if (this.grid[i].size() <= row) {
					break;
				}
				if (this.grid[i].get(row) == player) {
					++count;
				}
				else {
					break;
				}				
			}
			for (int i = col-1; i >= 0;--i) {
				//checks that get is out of bounds or not
				if (this.grid[i].size() <= row) {
					break;
				}
				if (this.grid[i].get(row) == player) {
					++count;
				}
				else {
					break;
				}
			}
		}
		//counting from top
		else if (col == 6){
			for (int i = 6; i >= 0;--i) {
				//checks that get is out of bounds or not
				if (this.grid[i].size() <= row) {
					break;
				}
				if (this.grid[i].get(row) == player) {
					++count;
				}
				else {
					break;
				}

			}
		}

		return count;
	}
		
	/**
	 *  Checks for vertical 4 in row.
	 *  @param col index of the column
	 *  @param row index of the row
	 *  @param player the player to check 4 ina row for
	 *  @return int the amt of tokens in a row
	 */
	public int countCol(int col, int row, Token player){
		// Count and return the number of consecutive tokens for the given player
		// in a column such that one of those tokens is at the given row and col 
		// of the grid.
		
		// Return 0 if out of bounds
		
		// O(N) where N is the number of tokens in the involved column

		int count = 0;
		if (col < 0 || col > 6 || row < 0) {
			return 0;
		}
		if (this.grid[col].size() <= row) {
			return 0;
		}

		if (row == 0) {
			//iterate through columns, keep row the same
			for (int i = 0; i < this.grid[col].size(); ++i) {
				if (this.grid[col].get(i) == player) {
					++count;
				}
				else {
					break;
				}
			}
		}
		//both ways count
		else if (row>0 && row<this.grid[col].size()-1) {
			for (int i = row; i < this.grid[col].size(); ++i) {
				if (this.grid[col].get(i) == player) {
					++count;
				}
				else {
					break;
				}
			}
			for (int i = row-1; i >= 0; --i) {
				if (this.grid[col].get(i) == player) {
					++count;
				}
				else {
					break;
				}
			}
		}
		else if (row == this.grid[col].size()-1){
			for (int i = row; i >= 0; --i) {
				if (this.grid[col].get(i) == player) {
					++count;
				}
				else {
					break;
				}
			}
		}		

		return count;
	}
	
	/**
	 *  Checks for diagonal 4 in row.
	 *  @param col index of the column
	 *  @param row index of the row
	 *  @param player the player to check 4 ina row for
	 *  @return int the amt of tokens in a row
	 */
	public int countMajorDiagonal(int col, int row, Token player){
		// Count and return the number of consecutive tokens for the given player
		// in a major diagonal such that one of those tokens is at the given row and col 
		// of the grid.  A major diagonal line covering (col, row) can extend diagonally 
		// down and to the right as well as up and to the left from the given 
		// location (col, row).  
		
		// Return 0 if out of bounds
		// O(1)

		int count = 0;
		int rowInc = row;
		if (col < 0 || col > 6 || row < 0) {
			return 0;
		}
		//starting from bottom right, going upward-left
		if (col == 6) {
			for (int i = 6; i >= 0; --i ) {
				//because of the nature of movement, impossible to have 4 in row in corner
				if (rowInc < 0) {
					break;
				}
				//check column is long enough for row before get method
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					++rowInc;
				}
				else {
					break;
				}
				
			}
		}
		//middle route
		else if (col != 0 && col != 6) {
			for (int i = col; i >= 0; --i) {
				//because of the nature of movement, impossible to have 4 in row in corner
				if (rowInc < 0) {
					break;
				}
				//check column is long enough for row before get method
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					++rowInc;
				}
				else {
					break;
				}
			}
			//Erases past changes
			rowInc = row - 1;
			for (int i = col + 1; i <= 6; ++i ) {
				if (rowInc < 0) {
					break;
				}
				//check column is long enough for row before get method
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					--rowInc;
				}
				else {
					break;
				}
			}
		}

		//starting from left route
		else if (col == 0) {
			for (int i = 0; i <= 6; ++i) {
				//because of the nature of movement, impossible to have 4 in row in corner
				if (rowInc < 0) {
					break;
				}
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					--rowInc;
				}
				else {
					break;
				}
			}
		}
			
		
		return count;
	}

	/**
	 *  Checks for diagonal 4 in row.
	 *  @param col index of the column
	 *  @param row index of the row
	 *  @param player the player to check 4 ina row for
	 *  @return int the amt of tokens in a row
	 */
	public int countMinorDiagonal(int col, int row, Token player){
		// Count and return the number of consecutive tokens for the given player
		// in a minor diagonal such that one of those tokens is at the given row and col 
		// of the grid.  A minor diagonal line covering (col, row) can extend diagonally 
		// up and to the right as well as down and to the left from the given 
		// location (col, row).  
		
		// Return 0 if out of bounds
		// O(1)

		int count = 0;
		int rowInc = row;
		if (col < 0 || col > 6 || row < 0) {
			return 0;
		}
		//starting from bottom left, going top right
		if (col == 0) {
			for (int i = 0; i <= 6; ++i ) {
				if (rowInc < 0) {
					break;
				}
				//check column is long enough for row before get method
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					++rowInc;
				}
				else {
					break;
				}
				
			}
		}
		//middle route
		else if (col != 0 && col != 6) {
			for (int i = col; i < 7; ++i) {
				if (rowInc < 0) {
					break;
				}
				//check column is long enough for row before get method
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					++rowInc;
				}
				else {
					break;
				}
			}
			//Erases past changes
			rowInc = row - 1;
			for (int i = col - 1; i >=0; --i) {
				if (rowInc < 0) {
					break;
				}
				
				//check column is long enough for row before get method
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					--rowInc;
				}
				else {
					break;
				}
			}
		}

		//starting from right route
		else if (col == 6) {
			for (int i = 6; i >= 0; --i) {
				//because of the nature of movement, impossible to have 4 in row in corner
				if (rowInc < 0) {
					break;
				}
				if (this.grid[i].size() <= rowInc) {
					break;
				}
				if (this.grid[i].get(rowInc) == player) {
					++count;
					--rowInc;
				}
				else {
					break;
				}
			}
		}

		return count;
	}
	
	/**
	 * The method that checks whether the specified player has four connected tokens
	 * horizontally, vertically, or diagonally.  It relies on the methods of countRow(),
	 * countCol(), countMajorDiagonal(), and countMinorDiagonal() to work correctly.
	 *
	 * @param player the token to be checked
	 * @return whether the given player has four tokens connected
	 */
	public boolean hasFourConnected(Token player){
		// Check whether the specified player has four tokens either in a row,
		// in a column, or in a diagonal line (major or minor). Return true if 
		// so; return false otherwise.	
		
		for (int j = 0; j<sizeCol(); j++){
			for (int i = 0; i<sizeRow(); i++){
				if (countRow(j, i, player)>=4 || countCol(j, i, player)>=4
					|| countMajorDiagonal(j, i, player)>=4 
					|| countMinorDiagonal(j, i, player)>=4 )
					return true;
			}
		}
		return false;
		
	}

	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//******************************************************
	
	/**
	 * Main tests if the data structure works correctly with all the varius functions or not.
	 * @param args takes in commandline arguements
	 */
	public static void main(String[] args) {
	
		// init with an empty grid
		PowerConnectFour myGame = new PowerConnectFour();
		if (myGame.sizeCol() == NUM_COLS && myGame.sizeRow() == MIN_ROWS
			&& myGame.getColumn(2).size() == 0 && myGame.currentPlayer() == Token.RED
			&& myGame.get(0,0) == null){
			System.out.println("Yay 1!");		
		}
		
		// drop
		if (!myGame.drop(10) && myGame.drop(2) && myGame.getColumn(2).size() == 1 && 
			myGame.get(2,0) == Token.RED && myGame.currentPlayer() == Token.YELLOW ){
			System.out.println("Yay 2!");					
		}
		
		// drop, pop, column growing/shrinking, board display changed
		boolean ok = true;
		for (int i=0; i<5; i++){
			ok = ok && myGame.drop(2); 	//take turns to drop to column 2 for five times
		}
		//System.out.println("===Current Grid===");		
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		if (ok && myGame.getColumn(2).size() == 6 && myGame.sizeRow() == 7
			&& myGame.pop(2) && myGame.sizeRow() == 6 && myGame.get(2,1) == Token.RED){
			System.out.println("Yay 3!");							
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		
		// power drop
		if (!myGame.powerDrop(3,1) && myGame.powerDrop(3,0) && myGame.powerDrop(2,2)
			&& myGame.getColumn(2).size() == 6 && myGame.get(2,2) == Token.RED
			&& myGame.get(2,3) == Token.YELLOW && myGame.getColumn(3).size() == 1){
			System.out.println("Yay 4!");							
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		
		//power pop
		if (!myGame.powerPop(2,1) && myGame.powerPop(2,3) 
			&& myGame.getColumn(2).size() == 5 && myGame.get(2,3).getSymbol()=='R'){
			System.out.println("Yay 5!");									
		}
		//PowerConnectFourGUI.displayGrid(myGame); //uncomment to check the grid display
		//PowerConnectFourGUI.reportcurrentPlayer(myGame);
		// expected display:
		//|   || 0 || 1 || 2 || 3 || 4 || 5 || 6 |
		//| 5 || - || - || - || - || - || - || - |
		//| 4 || - || - || Y || - || - || - || - |
		//| 3 || - || - || R || - || - || - || - |
		//| 2 || - || - || R || - || - || - || - |
		//| 1 || - || - || R || - || - || - || - |
		//| 0 || - || - || Y || Y || - || - || - |
		//Player R's turn

		//counting
		if (myGame.countRow(3,0,Token.YELLOW) == 2 && myGame.countRow(3,0,Token.RED) == 0
			&& myGame.countCol(2,3,Token.RED) == 3 && myGame.drop(3) /*one more R*/
			&& myGame.countMajorDiagonal(3,1,Token.RED) == 2 /* (3,1) and (2,2) */
			&& myGame.countMinorDiagonal(2,0,Token.YELLOW) == 1){
			System.out.println("Yay 6!");												
		}
			
	
	}
}