package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComponent;

public class Slika extends JComponent{
	
	private List<Artikl> artikli;
	private double sizes[];
	
	
	public Slika(List<Artikl> artikli) {
		super();
		this.artikli = artikli;
		sizes = new double[artikli.size()];
		fillSizes();
	}


	private void fillSizes() {
		int sum = 0;
		for(Artikl a : artikli) {
			sum += a.getSize();
			
		}
		for(int i = 0; i<artikli.size(); ++i) {
			sizes[i] = (double)artikli.get(i).getSize()/sum;
		}
		
	}


	public List<Artikl> getArtikli() {
		return artikli;
	}


	@Override
	public void paintComponent(Graphics g) {
		Insets insets = getInsets();
		Graphics2D g2d = (Graphics2D) g;
		System.out.println(getWidth());

		System.out.println(getHeight());
		
		double side = this.getHeight() >= this.getWidth() ? this.getWidth()*0.9 : this.getHeight()*0.9;
		int centerX = this.getWidth()/2;
		System.out.println(centerX);
		int centerY = this.getHeight()/2;
		System.out.println(centerY);
		g2d.fillOval(centerX-(int)side/2, centerY-(int)side/2, (int)side, (int)side);
		
		drawPie(g2d, new Rectangle(centerX-(int)side/2, centerY-(int)side/2, (int)side, (int)side));
	}


	void drawPie(Graphics2D g, Rectangle area) {
	    double total = 0;
	    for (int i = 0; i < artikli.size(); i++) {
	      total += artikli.get(i).getSize();
	    }

	    double curValue = 0.0D;
	    int startAngle = 0;
	    for (int i = 0; i < artikli.size(); i++) {
	      startAngle = (int) (curValue * 360 / total);
	      int arcAngle = (int) (artikli.get(i).getSize() * 360 / total);

	      g.setColor(artikli.get(i).getCol());
	      g.fillArc(area.x, area.y, area.width, area.height, startAngle+90, arcAngle);
	      curValue += artikli.get(i).getSize();
	    }
	}
	
	
	private void paintParts(Graphics2D g2d) {
		double[] deg = new double[artikli.size()];
		
		for(int i = 0; i<artikli.size(); ++i) {
			deg[i] = (double)artikli.get(i).getSize()/360;
		}
		
		
	}
}
