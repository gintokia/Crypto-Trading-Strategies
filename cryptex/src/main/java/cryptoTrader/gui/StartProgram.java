/**
 * 
 * StartProgram is the class used to run the application
 * 
 * @author Saaketh Chenna
 */
package cryptoTrader.gui;

public class StartProgram {
	
	public static void main(String[] args) {
		
		GetUIData startProgram = new LoginProxy();
		startProgram.login();
	}

}
