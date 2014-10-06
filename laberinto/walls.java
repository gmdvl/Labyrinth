package test;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JPanel;

import test.Demo;

public class walls extends Thread {
	private JPanel board;
	private int rows, columns;
	
	
	public walls(JPanel board) {
		this.board = board;
		
		GridLayout layout = (GridLayout) board.getLayout();
		
		rows = layout.getRows();
		columns = layout.getColumns();
		
		JPanel player = (JPanel) board.getComponent(Demo.player.y * columns + Demo.player.x);
		player.setBackground(Color.blue);
		
		JPanel goal = (JPanel) board.getComponent(Demo.goal.y * columns + Demo.goal.x);
		goal.setBackground(Color.red);
	}
	
	public void run() {
		wallMaker();
	}
	
	private void wallMaker() {
		Random rand = new Random();
		
	/*	MazeGenerator maze = new MazeGenerator(15, 15);
		maze.display();
		boolean[][] table = maze.getTable();*/
		
		/*for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(table[i][j]) {
					Point q = new Point(j, i);
					
					if(validate(Demo.player, q) && validate(Demo.goal, q)) {
						JPanel item = (JPanel) board.getComponent(columns * i + j);
						item.setBackground(Color.white);
					}
				}
			}
		}*/
	}

	public void refresh() {
		for(int i = 0; i < board.getComponentCount(); i++) {
			JPanel item = (JPanel) board.getComponent(i);
				
			Point p = new Point(i % columns, i / columns);
			if(validate(Demo.player, p) && validate(Demo.goal, p))
				item.setBackground(Color.black);
		}
		
		wallMaker();
	}
	
	private boolean validate(Point p, Point q) {
		return p.x != q.x || p.y != q.y;
	}

	public void update(Point p, Point q) {
		int x = q.x, y = q.y;
		
		if(y >= 0 && y < rows) {
			if(x >= 0 && x < columns) {
				JPanel after = (JPanel) board.getComponent(columns * y + x);
				JPanel before = (JPanel) board.getComponent(columns * p.y + p.x);
				
				if(!after.getBackground().equals(Color.black)) {
					before.setBackground(Color.white);
					after.setBackground(Color.blue);
					
					p.x = x;
					p.y = y;
				}
			}
		}
	}
}