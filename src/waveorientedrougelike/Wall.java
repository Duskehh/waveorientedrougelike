package waveorientedrougelike;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wall extends Entity {

	public Wall(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void drawWall(Graphics2D g2d) {
		this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
		g2d.setColor(Color.BLACK);
		g2d.fill(rect);
	}
}
