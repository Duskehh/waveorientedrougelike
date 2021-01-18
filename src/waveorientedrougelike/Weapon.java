package waveorientedrougelike;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Weapon extends Entity implements Runnable {

	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public float velocity = 0.0f;
	float dmg = 0.0f;
	float firerate = 0.0f;
	int homing = 0;
	float spread = 0.0f;
	float bulletcount = 0.0f;
	float range = 0.0f;
	float timerin = 0.0f;
	boolean shooting = false;
	public static int targetx, targety;

	public Weapon(int x, int y, int w, int h, float velocity, float dmg, float firerate, int homing, float spread,
			float bulletcount, float range) {
		super(x, y, w, h);
		this.velocity = velocity;
		this.dmg = dmg;
		this.firerate = firerate;
		this.homing = homing;
		this.spread = spread;
		this.bulletcount = bulletcount;
		this.range = range;
		new Thread(this).start();
	}

	public void startshooting(int targetx, int targety) {
		this.targetx = targetx;
		this.targety = targety;
		shooting = true;
	}

	public void stopshooting() {
		shooting = false;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void updateBullets(Graphics2D g2d) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).drawBullet(g2d);

		}
	}

	public void drawWeapon(Graphics2D g2d) {
		this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
		g2d.setColor(Color.GREEN);
		updateBullets(g2d);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (shooting) {
				try {
					Thread.sleep((long) (100));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bullets.add(new Bullet((int) x, (int) y, 10, 10, targetx, targety, velocity));
				
				
				
			}
		}
	}
}
