package rose.semanticDevice.multiDevice;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.cybergarage.upnp.device.InvalidDescriptionException;

public class SemanticDeviceGUI extends JFrame {
	private JButton startDevice, stopDevice, chooseFile;
	private JTextField descriptionPath;
	private MultiDevice md;
	private JFileChooser fileChooser;

	public SemanticDeviceGUI() {
		// TODO Auto-generated constructor stub
		setSize(400, 500);
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
			} else if (arg0.getActionCommand().equals("選擇檔案")) {
				System.out.println("choose fileing");
				fileChooser.showOpenDialog(null);
				System.out.println("choosing : " + fileChooser.getSelectedFile());
				descriptionPath.setText(fileChooser.getSelectedFile().toString().replace("\\", "/"));
			}
		}
	};

	private void init() {
		chooseFile = new JButton("選擇檔案");
		chooseFile.setBounds(0, 170, 100, 100);
		startDevice = new JButton("start");
		startDevice.setBounds(0, 0, 100, 100);
		stopDevice = new JButton("stop");
		stopDevice.setBounds(110, 0, 100, 100);
		add(startDevice);
		add(stopDevice);
		add(chooseFile);
		startDevice.addActionListener(listener);
		stopDevice.addActionListener(listener);
		chooseFile.addActionListener(listener);
		descriptionPath = new JTextField();
		descriptionPath.setBounds(0, 110, 300, 50);
		add(descriptionPath);
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("c:/upnp"));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SemanticDeviceGUI m = new SemanticDeviceGUI();
	}

}
