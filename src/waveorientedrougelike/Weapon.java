package waveorientedrougelike;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Weapon extends Entity {
	
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public float velocity = 0.0f;
	float dmg = 0.0f;
	float firerate = 0.0f;
	int homing = 0;
	float spread = 0.0f;
	float bulletcount = 0.0f;
	float range = 0.0f;

		
	public Weapon(int x, int y, int w, int h, float velocity, float dmg , float firerate, int homing, float spread, float bulletcount, float range) {
		super(x, y, w, h);
		this.velocity = velocity;
		this.dmg = dmg;
		this.firerate = firerate;
		this.homing = homing;
		this.spread = spread;
		this.bulletcount = bulletcount;
		this.range = range;
	}
		
	public void shoot(int targetx, int targety) {
		bullets.add(new Bullet((int) x, (int) y, 10, 10, targetx, targety, velocity));		
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateBullets(Graphics2D g2d) {
        for (int i = 0; i< bullets.size(); i++) {
            bullets.get(i).drawBullet(g2d);
            
        }
        
    }
	
	public void drawWeapon(Graphics2D g2d) {
		this.rect = new Rectangle((int) x - w / 2,(int) y - h / 2, w, h);
		g2d.setColor(Color.GREEN);
        updateBullets(g2d);
	}
	
}
