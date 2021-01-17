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
	// private BufferedImage playerImg;
	private int iconIndex = 0;
	private Image[] playerIcons = new Image[5];
	int vx;
	int vy;
	float velocity = 0.0f;
	float dmg = 0.0f;
	float firerate = 0.0f;
	int homing = 0;
	float spread = 0.0f;
	float bulletcount = 0.0f;
	float range = 0.0f;
	int playerSpeed = 5;
	public int health = 0;
	private Enemy enemy;
	private float timerin = 0.00f;

	public Player(int x, int y, int w, int h, int health) {
		super(x, y, w, h);
		this.health = health;
		// this.enemy = enemy;
		importPlayertextures();
		// playerImg = ImageIO.read(getClass().getResource("Standing.gif"));

	}
	// import img

	public void drawPlayer(Graphics2D g2d) {
//        ImageIcon ic = new ImageIcon("C:/Benutzer/eclipse-workspace/WoR/res/texturen/Player");
		// Image i = ic.getImage();
		// g2d.drawImage(i, x, y, null);
		// this.rect = new Rectangle(x - w / 2, y - h / 2, w, h);
		// g2d.setColor(Color.RED);
		// g2d.fill(rect);
		// draw image
		// g2d.drawImage(playerImg, (int) x, (int) y, w, h, null);
		g2d.drawImage(playerIcons[iconIndex], (int) x, (int) y, w, h, null);
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
				timerin += 0.02;
				if (rect.intersects(enemy.rect)) {
				if(timerin >= 1) {
					health -= enemy.damage;
					timerin = 0;
					
					}
				return true;
			}
		}
		return false;
		
	}

	private void importPlayertextures() {
		playerIcons[0] = new ImageIcon(getClass().getResource("Standing.gif")).getImage(); // Standing.gif
		playerIcons[1] = new ImageIcon(getClass().getResource("walkingLeft.gif")).getImage(); // Left
		playerIcons[2] = new ImageIcon(getClass().getResource("walkingRight.gif")).getImage(); // rigth
		playerIcons[3] = new ImageIcon(getClass().getResource("walkingLeft.gif")).getImage(); // up
		playerIcons[4] = new ImageIcon(getClass().getResource("walkingRight.gif")).getImage(); // down

	}

	public void setMovementDirection(Direction direction) {
		switch (direction) {
		case left:
			iconIndex = 1;
			vx = -playerSpeed;
			break;
		case right:
			iconIndex = 2;
			vx = playerSpeed;
			break;
		case up:
			iconIndex = 3;
			vy = -playerSpeed;
			break;
		case down:
			iconIndex = 4;
			vy = playerSpeed;
			break;

		default:
			break;
		}
	}

	public void resetMovementDirection(Direction direction) {
		switch (direction) {
		case left:

			vx = 0;
			break;
		case right:

			vx = 0;
			break;
		case up:

			vy = 0;
			break;
		case down:

			vy = 0;
			break;

		default:
			break;

		}
		if (vx == 0 && vy == 0) {
			iconIndex = 0;

		}
	}
	
	

	

}
