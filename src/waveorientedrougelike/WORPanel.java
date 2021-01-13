package waveorientedrougelike;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class WORPanel extends JPanel {

	static final long serialVersionUID = 32423450;

	public Player player;
	int enemyCount = 5;
	public static ArrayList<Wall> w1 = new ArrayList<Wall>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	private final ArrayList<Shape> shapes;

	Random rand = new Random();
	boolean isMenu = false;
	boolean isSettings = false;
	boolean isGame = false;
	boolean isChoosingWeapon = true;;

	private Shape settings = new Rectangle2D.Double(200, 240, 175, 50);
	private Shape choosepistol = new Rectangle2D.Double(400, 200, 200, 200);
	private Shape chooseshotgun = new Rectangle2D.Double(400, 350, 200, 200);
	private Shape choosesniper = new Rectangle2D.Double(400, 500, 200, 200);
	private Shape chooselmg = new Rectangle2D.Double(400, 650, 200, 200);
	Weapon curW;
	Weapon shotgun;
	Weapon pistol;
	Weapon sniper;
	Weapon lmg;

	public WORPanel() {
		shapes = new ArrayList<>();
		shapes.add(settings);
		w1.add(new Wall(20, 20, 40, 1600));
		w1.add(new Wall(20, 20, 1600, 40));
		w1.add(new Wall(20, 750, 1600, 40));
		w1.add(new Wall(770, 700, 40, 1600));
		player = new Player(50, 50, 20, 20);
		shotgun = new Weapon((int) player.x, (int) player.y, 20, 20, 10, 10, 1, 1, 1, 1, 1);
		pistol = new Weapon((int) player.x, (int) player.y, 10, 10, 20, 20, 60, 1, 1, 1, 1);
		sniper = new Weapon((int) player.x, (int) player.y, 40, 1, 30, 100, 1000, 1, 1, 1, 1);
		lmg = new Weapon((int) player.x, (int) player.y, 30, 30, 6, 40, 30, 1, 1, 1, 1);

		setFocusable(true);
		addKeyListener(new KL());
		addMouseListener(new ML());

		for (int i = 0; i < enemyCount; i++) { // REMOVING ENEMY SPAWNS IN WALL AND IN PLAYER
			enemies.add(new Enemy(rand.nextInt(800), rand.nextInt(600), 20, 20, player, 200));
			if (enemies.get(i).checkCollisionWall() || enemies.get(i).playerInReach()) {
				enemies.remove(i);
				i--;
			}
		}
	}

	public void updateEnemies(Graphics2D g2d) {
		for (Enemy enemy : enemies) {
			enemy.drawEnemy(g2d);
		}
	}

	public void update() {

		if (isGame) {
			player.move();
			for (Weapon curW : weapons) {
				curW.setPosition((int) player.x, (int) player.y);
			}
			for (Enemy enemy : enemies) {
				enemy.roam();
			}
			for (int i = 0; i < curW.bullets.size(); i++) {
				curW.bullets.get(i).bulletMove();
			}
			collisionEnemyBullet();
		}
		
	}

	public int gameState() {
		int gState = 0;
		if (isGame) {
			gState = 0;
		}
		if (isMenu) {
			gState = 1;
		}
		if (isSettings) {
			gState = 2;
		}
		if (isChoosingWeapon) {
			gState = 3;
		}
		return gState;
	}

	public void collisionEnemyBullet() {
		if (enemies.size() > 0) {
			for (int k = 0; k < enemies.size(); k++) {
				for (int i = 0; i < curW.bullets.size(); i++) {
					if (enemies.get(k).rect.intersects(curW.bullets.get(i).rect)) {
						enemies.get(k).hit(curW.dmg);
						System.out.println(curW.dmg);
						curW.bullets.remove(i);
						break;
					}
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.fillRect(0, 0, 800, 800);
		if (isGame) {
			isMenu = false;
			for (Weapon curW : weapons) {
				curW.drawWeapon(g2d);
			}
			for (Wall wall : w1) {
				wall.drawWall(g2d);
				for (int i = 0; i < enemies.size(); i++) {
					Enemy tempEnemy = enemies.get(i);
					tempEnemy.drawEnemy(g2d);
				}
			}
			player.drawPlayer(g2d);
		}

		if (isMenu) {
			isGame = false;
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", Font.PLAIN, 40));
			g2d.drawString("Press ESC to Continue", getWidth() / 4, getHeight() / 2);
			g2d.create(300, 300, 90, 1000);
			g2d.drawString("Settings", (getWidth() / 4), (getHeight() / 2) - 100);
			g2d.create(300, 300, 90, 1000);
			for (Shape s : shapes) {
				g2d.draw(s);
			}

		}
		if (isSettings) {
			isGame = false;
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", Font.PLAIN, 40));
			g2d.drawString("Oder?", getWidth() / 4, getHeight() / 2);
			g2d.create(300, 300, 90, 1000);
			g2d.drawString("Erfolg", (getWidth() / 4), (getHeight() / 2) - 100);
			g2d.create(300, 300, 90, 1000);

		}
		if (isChoosingWeapon) {
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", Font.PLAIN, 40));
			g2d.drawString("Shotgun", 400, 350);
			g2d.drawString("Pistol", 400, 200);
			g2d.drawString("Sniper", 400, 500);
			g2d.drawString("Light Machine Gun", 400, 650);
		}

		repaint();
		Toolkit.getDefaultToolkit().sync();
	}

	class KL implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				player.vx = 5;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				player.vx = -5;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				player.vy = -5;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				player.vy = 5;
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

				switch (gameState()) {
				case 0:
					isMenu = true;
					isGame = false;
					isSettings = false;
					break;

				case 1:
					isGame = true;
					isMenu = false;
					isSettings = false;
					break;

				case 2:
					isMenu = true;
					isSettings = false;
					isGame = false;
					break;

				case 3:

				default:
					isMenu = false;
					isGame = true;
					isSettings = false;
					break;
				}

			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				player.vx = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				player.vx = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_W) {
				player.vy = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				player.vy = 0;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	class ML implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point mouseposition = e.getPoint();
			if (isChoosingWeapon && chooseshotgun.contains(e.getPoint())) {
				weapons.add(shotgun);
				curW = shotgun;
				isChoosingWeapon = false;
				isGame = true;
			}
			if (isChoosingWeapon && choosepistol.contains(e.getPoint())) {
				weapons.add(pistol);
				curW = pistol;
				isChoosingWeapon = false;
				isGame = true;
			}
			if (isChoosingWeapon && choosesniper.contains(e.getPoint())) {
				weapons.add(sniper);
				curW = sniper;
				isChoosingWeapon = false;
				isGame = true;
			}
			if (isChoosingWeapon && chooselmg.contains(e.getPoint())) {
				weapons.add(lmg);
				curW = lmg;
				isChoosingWeapon = false;
				isGame = true;
			}
			if (isGame) {
				curW.shoot(mouseposition.x, mouseposition.y);
			}
			if (!isGame && isMenu && settings.contains(e.getPoint())) {
				isSettings = true;
				isMenu = false;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
