package waveorientedrougelike;

import java.awt.Rectangle;

public abstract class Entity {

	float x, y;
	int w, h;
	public Rectangle rect;

	public Entity(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rect = new Rectangle(x - w / 2, y - h / 2, w, h);
	}
}
