/**
 * The LoginProxy class implements the GetUIData interface and implements the Proxy design pattern by preventing users 
 * from using the main UI until they provide valid credentials to the login window
 * 
 * @author Saaketh Chenna
 */
package cryptoTrader.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginProxy extends JFrame implements ActionListener, KeyListener, GetUIData {
	private JFrame frame;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel passwordLabel;
	private JPasswordField passwordText;
	private JButton loginButton;
	
	private static String INVALID_CREDENTIALS = "The credentials provided are invalid. Press 'Close' to terminate application.";
	
	/**
	 * Creates a login window where a user is able to provide their
	 * username and password
	 */
	public void login() {
		JPanel panel = new JPanel();
		frame = new JFrame();
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		
		//copied code for location
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
		
		//copied code for location
		
		panel.setLayout(null);
		
		userLabel = new JLabel("Username");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		userText.addKeyListener(this);
		panel.add(userText);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 50, 80, 25);
		panel.add(passwordLabel);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(100, 50, 165, 25);
		passwordText.addKeyListener(this);
		panel.add(passwordText);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		loginButton.addActionListener(this);
		panel.add(loginButton);
		
		addKeyListener(this);
	
		frame.setVisible(true);
		
	}
	
	/**
	 * Checks to see if the credentials match any in the database
	 * 
	 * @param userText
	 * @param passwordText
	 * @return true if credentials have a match in the database, false otherwise
	 * @throws FileNotFoundException
	 */
	public boolean validateCredentials(String userText, String passwordText) throws FileNotFoundException {
		File file = new File("src/main/resources/Database");
		
		Scanner sc = new Scanner(file);
		
		String username = sc.nextLine();
		String password = sc.nextLine();
		
		if(username.equals(userText) && password.equals(passwordText)) {
			return true;
		}
		else {
			return false;
		}
		
		
		
	}
	
	/**
	 * Listens for when the user press the login button
	 */
	public void actionPerformed(ActionEvent e) {
		String username = userText.getText();
		String password = passwordText.getText();
		
		try {
			if(validateCredentials(username, password)) {
				frame.dispose();
				JFrame mainFrame = MainUI.getInstance();
				mainFrame.setSize(900, 600);
				mainFrame.pack();
				mainFrame.setVisible(true);
				
			}
			else {
				JOptionPane.showOptionDialog(null, INVALID_CREDENTIALS , "Login Failed", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, new String[]{"Close"}, "default");	
				System.exit(0);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * Listens for when the user clicks enter on their keyboard
	 */
	public void keyPressed(KeyEvent e) {
	    if (e.getKeyCode()==KeyEvent.VK_ENTER){
	    	actionPerformed(null);
	    }

}
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
