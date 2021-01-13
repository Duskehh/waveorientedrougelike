package waveorientedrougelike;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends Entity {

	private Player player;
	int vx = 1;
	int vy = 1;
	float angle;
	private float timerin = 0.00f;
	private int health = 0;
	Bullet curB;

	public Enemy(int x, int y, int w, int h, Player player, int health) {
		super(x, y, w, h);
		this.player = player;
		this.health = health;
	}

	public void hit(float damage) {
		health -= (int) damage;
		for (int i = 0; i < WORPanel.enemies.size(); i++) {
			if (WORPanel.enemies.get(i).health <= 0) {
				WORPanel.enemies.remove(i);
			}
		}
	}

	public void drawEnemy(Graphics2D g2d) {
		this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
		g2d.setColor(Color.BLUE);
		g2d.fill(rect);
		g2d.setColor(Color.GREEN);
		g2d.drawString(health + "", x, y);

	}

	public float angleToPlayer() {
		return (float) Math.toDegrees(Math.atan2((player.x - x) * -1, player.y - y)) + 90;

	}

	public void roam() {

		int px = (int) x;
		int py = (int) y;
		if (playerInReach() == false) {
			switch ((int) timerin) {
			case 0:
				x += vx;
				break;
			case 1:
				y -= vy;
				break;
			case 2:
				x -= vx;
				break;
			case 3:
				y += vy;
				break;
			}

			timerin += 0.02;

			if (timerin >= 4) {
				timerin = 0;
			}

			this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
			if (checkCollisionWall()) {
				x = px;
			}
			this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
			if (checkCollisionWall()) {
				y = py;
			}
//	        this.rect = new Rectangle(x - w / 2, y - h / 2, w, h);
//	    	if(checkCollisionPlayer()) {
//	        	 x = px ;
//	        }
//	        this.rect = new Rectangle(x - w / 2, y - h / 2, w, h);
//	        if(checkCollisionPlayer()) {
//	        	y = py ;
//	        	}
		} else {
			x += Math.cos(Math.toRadians(angleToPlayer())) * 4;
			y += Math.sin(Math.toRadians(angleToPlayer())) * 4;
			this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
			if (checkCollisionWall()) {
				x = px;
			}
			this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
			if (checkCollisionWall()) {
				y = py;
			}
			this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
			if (checkCollisionPlayer()) {
				x = px;
			}
			this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
			if (checkCollisionPlayer()) {
				y = py;
			}
		}
	}

	public boolean checkCollisionWall() {

		for (Wall wall : WORPanel.w1) {
			if (rect.intersects(wall.rect)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkCollisionPlayer() {
		if (rect.intersects(player.rect)) {
			return true;
		}
		return false;
	}

	public boolean playerInReach() {

		double kat = (player.x - this.x) * (player.x - this.x);
		double gekat = (player.y - this.y) * (player.y - this.y);
		return Math.sqrt(kat) + Math.sqrt(gekat) < 300;
	}
}
