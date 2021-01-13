package waveorientedrougelike;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bullet extends Entity {

	float angle;
	float velocity;

	public Bullet(int x, int y, int w, int h, int targetx, int targety, float velocity) {

		super(x, y, w, h);
		this.angle = (float) Math.toDegrees(Math.atan2((targetx - x) * -1, targety - y)) + 90;
		this.velocity = velocity;
	}

	public void drawBullet(Graphics2D g2d) {
		this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
		g2d.setColor(Color.BLUE);
		g2d.fill(rect);
	}

	// public void deleteBullet(Graphics2D g2d) {

	// }

	public boolean checkCollisionWall() {

		for (Wall wall : WORPanel.w1) {

			if (rect.intersects(wall.rect)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkCollisionEnemies() {

		for (Enemy enemy : WORPanel.enemies) {

			if (rect.intersects(enemy.rect)) {
				return true;
			}
		}
		return false;
	}

	public void bulletMove() {
		x += Math.cos(Math.toRadians(angle)) * velocity;
		y += Math.sin(Math.toRadians(angle)) * velocity;
		this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);

	}
}
