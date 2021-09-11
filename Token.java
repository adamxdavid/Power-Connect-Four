// --------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE
// --------------------------------------------------------
/**
 * An enum class used to define pieces used in Connect Four games.
 *
 * @author Y. Zhong
 */
enum Token {

	/**
	 * Two possible tokens.
	 */
	RED, YELLOW;
	
	/**
	 * The method returns the character used to represent the token 
	 * which is helpful in displaying the grid.
	 *
	 * @return character defined for this token
	 */
	public Character getSymbol(){
		switch(this){
			case RED:
				return 'R';
			
			case YELLOW:
				return 'Y';
				
			default:
				return null;
		
		}
	}
	
	/**
	 * The main method that gives examples of using the Token constants and 
	 * getting the character associated with each token.
	 * 
	 * @param args command line args (ignored)
	 */
	public static void main(String[] args) {
	    System.out.println("RED Token has symbol: " + Token.RED.getSymbol());
    	System.out.println("YELLOW Token has symbol: " + Token.YELLOW.getSymbol());
    }

}