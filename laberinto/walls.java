package laberinto;

import java.awt.Color;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import laberinto.Demo;

public class walls extends Thread {

	JPanel contentPane = Demo.contentPane;
	int dimension =Demo.dimension,i=0;
	boolean fin=true;
	public void para(){fin=false;}
	public void run() {
		//contentPane.removeAll();
		//while (fin) {
			wallMaker(contentPane, dimension, dimension);
		//System.out.println("start ok");
		//}

	}
	
	private void wallMaker(JPanel board, int height, int width) {
		int rows = board.getWidth() / width, cols = board.getHeight() / height;
		int pos_x = 0, pos_y = 0;
		Random rand=new Random();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				
				boolean ans = (rand.nextInt(2) == 1)? true : false;
				
				if(ans) {
					if(validate(Demo.player, pos_x, pos_y) && validate(Demo.goal, pos_x, pos_y)) {
						final JPanel wall = new JPanel();
						wall.setName("wall");
						wall.setBackground(Color.BLACK);
						wall.setBounds(pos_x, pos_y, height, width);
						wall.setBorder(BorderFactory.createLineBorder(Color.darkGray));
						board.add(wall);
					}
				}
				
				pos_x += width;
			}
			
			pos_x = 0;
			pos_y += height;
		}
	}

	public void refresh()
	{
		for(int i=contentPane.getComponentCount()-1;i>1;i--)
		{
			contentPane.remove(i);
		}
		wallMaker(contentPane, dimension, dimension);
	}
	
	private boolean validate(JPanel item, int x, int y) {		
		return item.getX() != x || item.getY() != y;
	}
	
}
