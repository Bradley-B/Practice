package conversion;

import java.util.Scanner;

public class Convert {

	public static void main(String[] args) {
		inputToBinary();
	}
	
	public static void inputToBinary() {
		System.out.println("init");
		Scanner sc = new Scanner(System.in);
		String lastInput = "";

		while(!lastInput.equals("exit")) {
			sc.hasNextLine();
			String input = sc.nextLine();

			for(int i=0;i<input.length();i++) {
				String binary = Integer.toString(input.charAt(i), 2);
				binary = binary.substring(2, binary.length());
				System.out.print(binary+" ");
			}
			lastInput = input;
			System.out.println("");
		}
		sc.close();
	}
}
