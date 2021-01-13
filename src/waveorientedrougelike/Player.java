package waveorientedrougelike;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player extends Entity {
	private BufferedImage playerImg;

	int vx;
	int vy;
	float velocity = 0.0f;
	float dmg = 0.0f;
	float firerate = 0.0f;
	int homing = 0;
	float spread = 0.0f;
	float bulletcount = 0.0f;
	float range = 0.0f;

	public Player(int x, int y, int w, int h) {
		super(x, y, w, h);
		try {
			playerImg = ImageIO.read(getClass().getResource("Player.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		// import img
	}

	public void drawPlayer(Graphics2D g2d) {
//        ImageIcon ic = new ImageIcon("C:/Benutzer/eclipse-workspace/WoR/res/texturen/Player");
		// Image i = ic.getImage();
		// g2d.drawImage(i, x, y, null);
		// this.rect = new Rectangle(x - w / 2, y - h / 2, w, h);
		g2d.setColor(Color.RED);
		// g2d.fill(rect);
		// draw image
		g2d.drawImage(playerImg, (int) x, (int) y, w, h, null);
	}

	public void move() {
		int px = (int) x;
		int py = (int) y;
		x += vx;
		this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
		if (checkCollisionWall()) {
			x = px;
		}
		y += vy;
		this.rect = new Rectangle((int) x - w / 2, (int) y - h / 2, w, h);
		if (checkCollisionWall()) {
			y = py;
		}
//        if(checkCollisionEnemy()) {
//            x = px ;
//          }
//         if(checkCollisionEnemy()) {
//             y = py ;
//           }
	}

	public boolean checkCollisionWall() {

		for (Wall wall : WORPanel.w1) {

			if (rect.intersects(wall.rect)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkCollisionEnemy() {
		for (Enemy enemy : WORPanel.enemies) {

			if (rect.intersects(enemy.rect)) {
				return true;
			}
		}
		return false;
	}

}
