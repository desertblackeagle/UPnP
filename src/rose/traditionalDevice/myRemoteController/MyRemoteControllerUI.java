package rose.traditionalDevice.myRemoteController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MyRemoteControllerUI extends JFrame {
	private RoseRemoteCtrl remoteCtrl;
	private JButton printBtn;

	public MyRemoteControllerUI() {
		// TODO Auto-generated constructor stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		setLayout(null);

		remoteCtrl = new RoseRemoteCtrl();
		System.out.println("remoteCtrl start");
		remoteCtrl.start();
		System.out.println("remoteCtrl end");
		initButton();
		repaint();
		revalidate();
	}

	private void initButton() {
		printBtn = new JButton("print");
		printBtn.setBounds(200, 200, 100, 100);
		printBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				remoteCtrl.displayMyPrint();
//				for (int i = 1; i <= 100; i++) {
//					remoteCtrl.displayMyPrint("urn:schemas-upnp-org:device:TV:" + i);
//				}
//				remoteCtrl.invokeMatch("1","2");
//				remoteCtrl.search("urn:schemas-upnp-org:device:TV:1");
			}
		});
		add(printBtn);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyRemoteControllerUI rmt = new MyRemoteControllerUI();
	}

}