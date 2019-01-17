//*********************************************************
// Name: Chang-Syuan Wu		
// Date: Jan 17, 2018
//
// Purpose: 
//		Java program to play the 2048 game
//
// Methods:
//		keyPressed(KeyEvent) - Detects which key is pressed
//		keyTyped(KeyEvent) - Unused abstract method from KeyListener 
//		keyReleased(KeyEvent) - Unused abstract method from KeyListener 
//
//*********************************************************

package game2048;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class GameWindow2048 extends JFrame implements KeyListener{

	// Default serial version UID
	private static final long serialVersionUID = 1L;

	// Set up constants for width and height of frame 
	static final int WIDTH = 550; 
	static final int HEIGHT = 815;
	
	// Declare the panel
	GamePanel2048 panel2048;
	
	// Constructor
	public GameWindow2048(String title) {
		// Set the title of the frame, must be before variable declarations 
		super(title);

		// Set the program to look the same on different platforms
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Declare and initialize the container and panel
		Container container = getContentPane();
		panel2048 = new GamePanel2048(); 
		

		// Set the background color and add the GamePanel to the window 
		panel2048.setBackground(Color.decode("#FAF8EF"));
		panel2048.setOpaque(true);
		setLocationByPlatform(true);
		container.add(panel2048);
		container.validate();
		
		// Allow detection of keyboard input by having the window focused
		addKeyListener(this);
		setFocusable(true);
	}

	public static void main(String[] args) {
		// Instantiate a GameWindow object to display it 
		GameWindow2048 window2048 =  new GameWindow2048("2048"); 
		window2048.setDefaultCloseOperation(EXIT_ON_CLOSE); 

		// Set the location and size of the application window (frame) 
		window2048.setLocation(0,0);
		window2048.setSize(WIDTH, HEIGHT);

		// Disable the window from being resized
		window2048.setResizable(false);

		// Show the window (frame)
		window2048.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// Determine which key the player pressed (keys apart from the WASD and arrow keys are ignored)
		int keyCode = e.getKeyCode();

		switch (keyCode) {

		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			panel2048.move("up");
			break;

		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			panel2048.move("down");
			break;

		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			panel2048.move("left");
			break;

		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			panel2048.move("right");
			break;
		}
	}

	// Abstract methods inherited from KeyListener
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}

}
