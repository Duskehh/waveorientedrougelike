package waveorientedrougelike;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class PaintTestFrame extends JFrame {

	static final long serialVersionUID = 32423450;
	private static final Timer timer = new Timer();
	private WORPanel panel;

	// States

	public PaintTestFrame() {
		setSize(800, 800);
		setVisible(true);
		setTitle("WoR");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		panel = new WORPanel();
		add(panel);
		revalidate();
		panel.requestFocus();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				panel.update();
			}
		}, 1000 / 60, 1000 / 60);
	}

	public static void main(String[] args) {
		new PaintTestFrame();

	}

}