package rose.semanticDevice.Light;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.cybergarage.upnp.device.InvalidDescriptionException;

import rose.semanticDevice.multiDevice.MultiDevice;

public class SemanticDeviceGUI extends JFrame {
	private JButton startDevice, stopDevice;
	private JTextField descriptionPath;
	private MultiDevice md;

	public SemanticDeviceGUI() {
		// TODO Auto-generated constructor stub
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);

		init();
		revalidate();
		repaint();
	}

	ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getActionCommand().equals("start")) {
				if (!descriptionPath.getText().equals("")) {
					try {
						md = new MultiDevice(descriptionPath.getText());
						md.start();
						System.out.println("device start");
					} catch (InvalidDescriptionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.println("please input description path");
				}
			} else if (arg0.getActionCommand().equals("stop")) {
				if (md != null) {
					md.stop();
					System.out.println("device stop");
				} else {
					System.out.println("Device not start");
				}
			}
		}
	};

	private void init() {
		startDevice = new JButton("start");
		startDevice.setBounds(0, 0, 100, 100);
		stopDevice = new JButton("stop");
		stopDevice.setBounds(110, 0, 100, 100);
		add(startDevice);
		add(stopDevice);
		startDevice.addActionListener(listener);
		stopDevice.addActionListener(listener);
		descriptionPath = new JTextField();
		descriptionPath.setBounds(0, 110, 300, 50);
		add(descriptionPath);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SemanticDeviceGUI m = new SemanticDeviceGUI();
	}

}
