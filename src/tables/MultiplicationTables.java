package tables;
/**
 * Practice Main Class to generate multiplication tables of varying length
 * @author Bradley Boxer
 *
 */
public class MultiplicationTables {

	public static void main(String[] args) {
		printTable(100, 20);
	}

	/**
	 * Generates a multiplication table in the console.
	 * @param rowCount the rows in the table
	 * @param colCount the columns in the table
	 */
	
	public static int getNumberAt(int row, int col) {
		return row*col;
	}
	
	public static void printTable(int rowCount, int colCount) {
		int maxOutput = rowCount*colCount;
			for(int row=1;row<rowCount+1;row++) {	
				for(int col=1;col<colCount+1;col++) {
					int output = col*row;
					System.out.print((output)+getSpaces(output, String.valueOf(maxOutput).length()));
				}
				System.out.println("");
			} 
	}
	
	
	/**
	 * Spaces numbers correctly in the console
	 * @param number the number that needs spacing
	 * @param maxNumber the maximum number that will be output in the console
	 * @return a String with the number of spaces provided in the parameter
	 */
	public static String getSpaces(int number, int maxNumber) {
		int spaces;
		String completedString = "";
		
		if(number % 10 == 0) {
			spaces = (maxNumber+1)-(String.valueOf(number).length());
		} else {
			spaces = (maxNumber+1)-(String.valueOf(number-1).length());
		}
		
		for(int i = 0;i<spaces;i++) {
			completedString = completedString + " ";
		}
		return completedString;
	}
}


