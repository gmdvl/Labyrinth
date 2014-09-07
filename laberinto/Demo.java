package laberinto;

import laberinto.walls;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class Demo extends JFrame {
	private static final Random rand = new Random(); 
			
	public static JPanel contentPane, player, goal;
	private int[][] table;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo frame = new Demo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static int dimension = 20/2,timm=1;//test original size 20
	Timer timer =  new Timer();
	public Demo() {		
		setSize(1024/2, 768/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);		
		contentPane.setLayout(null);
		contentPane.setSize(this.getHeight(), this.getWidth());
		setContentPane(contentPane);
		
		
		
		createPlayer(contentPane, dimension, dimension);
		createGoal(contentPane, player, dimension, dimension);
		//wallMaker(contentPane, dimension, dimension);
		TimerTask timertask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(timm%10==0)
				{
					/*for(int i=contentPane.countComponents()-1;i>2;i++)
						contentPane.remove(wall);*/
					//wallMaker(contentPane, dimension, dimension);
				}
			}
		};
		//timer.scheduleAtFixedRate(timertask, 0, 1000);
		contentPane.setFocusable(true);
		contentPane.requestFocusInWindow();
		
		contentPane.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyPressed(KeyEvent e) {//problema al moverse era fijo 20 cambiado a el size del player
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					player.setLocation(player.getX() - player.size().width, player.getY());
					contentPane.repaint();
					break;
				case KeyEvent.VK_D:
					player.setLocation(player.getX() + player.size().width, player.getY());
					contentPane.repaint();
					break;
				case KeyEvent.VK_W:
					player.setLocation(player.getX(), player.getY() - player.size().height);
					break;
				case KeyEvent.VK_S:
					player.setLocation(player.getX(), player.getY() + player.size().height);
					contentPane.repaint();
					break;
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;
				}

				if((winner_yet(player, goal)))
					JOptionPane.showMessageDialog(null, "You Won!!!");
			}
		});
		
		
		(new walls()).start();
		
	}

	
	private void createPlayer(JPanel board, int height, int width) {
		int rows = board.getHeight() / height, cols = board.getWidth() / width;
		
		player = new JPanel();
		player.setBackground(Color.cyan);
		player.setBounds(rand.nextInt(rows) * height, rand.nextInt(cols) * width, height, width);
		board.add(player);
	}
	
	private void createGoal(JPanel board, JPanel point, int height, int width) {
		int rows = board.getWidth() / width, cols = board.getHeight() / height;
		int pos_x, pos_y;
		
		do {
			pos_x = rand.nextInt(cols) * width;
			pos_y = rand.nextInt(rows) * height;
		}while(pos_x == point.getX() || pos_y == point.getY());
		
		goal = new JPanel();
		goal.setBackground(Color.red);
		goal.setBounds(pos_x, pos_y, height, width);
		board.add(goal);
	}

	private boolean winner_yet(JPanel x, JPanel y)
	{
		timer.cancel();
		return x.getLocation().equals(y.getLocation());
	}
}
