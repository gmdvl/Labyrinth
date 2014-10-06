package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Demo extends JFrame {
	private static final Random rand = new Random(); 
			
	public JPanel contentPane, mazePane;
	public static Point player;
	private boolean available;
	static Point goal;
	private GridLayout layout; 
	
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

	private int timm = 1;
	private Timer timer = new Timer();
	
	public Demo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.white);		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		mazePane = new JPanel();
		mazePane.setBackground(Color.black);
		mazePane.setBorder(new LineBorder(Color.black));
		mazePane.setPreferredSize(new Dimension(600, 600));
		contentPane.add(mazePane, BorderLayout.CENTER);
		
		layout = new GridLayout(31, 31);
		mazePane.setLayout(layout);
		
		for(int i = 0; i < 31; i++) {
			for(int j = 0; j < 31; j++) {
				JPanel item = new JPanel();
				item.setBackground(Color.black);
				mazePane.add(item);
			}
		}
		
		createPlayer();
		createGoal();
		
		contentPane.setFocusable(true);
		contentPane.requestFocusInWindow();
		
		final walls wall;	
		
		wall = new walls(mazePane);
		
		contentPane.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			
			public void keyReleased(KeyEvent e) {}
			
			@SuppressWarnings("deprecation")
			public void keyPressed(KeyEvent e) {
				if(available) {
				
				switch (e.getKeyCode()) {
					case KeyEvent.VK_A:
						wall.update(player, new Point(player.x - 1, player.y));
						break;
					case KeyEvent.VK_D:
						wall.update(player, new Point(player.x + 1, player.y));
						break;
					case KeyEvent.VK_W:
						wall.update(player, new Point(player.x, player.y - 1));
						break;
					case KeyEvent.VK_S:
						wall.update(player, new Point(player.x, player.y + 1));
						break;
					case KeyEvent.VK_ESCAPE:
						System.exit(0);
						break;
				}

				if((winner_yet(player, goal))) {
					wall.stop();
					timer.cancel();
					JOptionPane.showMessageDialog(null, "You Won!!!");
					createPlayer();
					createGoal();
					wall.refresh();
				}
				}
			}
		});
		
		wall.start();
		TimerTask timertask = new TimerTask() {
			public void run() {
				if(timm % 30 == 0) {
					available = false;
					wall.refresh();
				}
				available = true;
				timm++;
			}
		};
		
		timer.scheduleAtFixedRate(timertask, 0, 100);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private walls walls;
	
	private void createPlayer() {
		player = new Point(rand.nextInt(layout.getColumns()), rand.nextInt(layout.getRows()));
	}
	
	private void createGoal() {
		goal = new Point();
		
		int rows = layout.getRows(), cols = layout.getColumns();
		int x, y;
		
		do{
			x = rand.nextInt(cols);
			y = rand.nextInt(rows);
		}while(x == player.x || y == player.y);
		
		goal.x = x;
		goal.y = y;
	}

	private boolean winner_yet(Point p, Point q) {
		return (p.x == q.x) && (p.y == q.y);
	}
}
