package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.util.Random;

public class Artikl {

	
	private String name;
	private int size;
	private Color col;
	
	
	
	public Artikl(String name, int size) {
		this.name = name;
		this.size = size;
		
		Random rand = new Random();
		col = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
	}
	public String getName() {
		return name;
	}
	public int getSize() {
		return size;
	}
	public Color getCol() {
		return col;
	}
	
	
}
