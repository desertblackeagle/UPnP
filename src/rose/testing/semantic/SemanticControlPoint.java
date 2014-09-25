package rose.testing.semantic;

import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

public class SemanticControlPoint extends rose.semanticControlPoint.SemanticControlPoint implements NotifyListener, EventListener, SearchResponseListener {
	private SemanticGUI sGUI;
	private int LCDCount = 0, LEDCount = 0;

	public SemanticControlPoint(SemanticGUI gui) {
		// TODO Auto-generated constructor stub
		addNotifyListener(this);
		addSearchResponseListener(this);
		addEventListener(this);
		setSSDPPort(8015);
		sGUI = gui;
	}

	@Override
	public void deviceSearchResponseReceived(SSDPPacket ssdpPacket) {
		// TODO Auto-generated method stub
		if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LCD:1") && LCDCount == 0) {
			System.out.println("[SemanticControlPoint] Find LCD");
			LCDCount = LCDCount + 1;
			if (sGUI != null) {
				sGUI.MSTraditionalLCDResult.setText("Search Susses");
			}
		} else if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LED:1") && LEDCount == 0) {
			System.out.println("[SemanticControlPoint] Find LED");
			LEDCount = LEDCount + 1;
			if (sGUI != null) {
				sGUI.MSSemanticLEDResult.setText("Search Susses");
			}
		} else if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LCD:1") && LCDCount == 1) {
			System.out.println("[SemanticControlPoint] Find TV");
			if (sGUI != null) {
				sGUI.MSTraditionalTVResult.setText("Search Susses");
			}
		} else if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LED:1") && LEDCount == 1) {
			System.out.println("[SemanticControlPoint] Find Light");
			if (sGUI != null) {
				sGUI.MSSemanticLightResult.setText("Search Susses");
			}
		}
	}

	@Override
	public void eventNotifyReceived(String uuid, long seq, String varName, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceNotifyReceived(SSDPPacket ssdpPacket) {
		// TODO Auto-generated method stub

	}

	public void searchTraditional() {
		search("urn:schemas-upnp-org:device:LCD:1");
		search("urn:schemas-upnp-org:device:TV:1");
	}

	public void searchSemantic() {
		search("urn:schemas-upnp-org:device:LED:1");
		search("urn:schemas-upnp-org:device:Light:1");
	}

	public void invokeTraditional() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:LCD:1") == null) {

				}
				System.out.println("[SemanticControlPoint] invoke LCD");
				if (sGUI != null) {
					sGUI.NOTraditionalLCDResult.setText("Invoke Susses");
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:TV:1") == null) {

				}
				System.out.println("[SemanticControlPoint] invoke TV");
				if (sGUI != null) {
					sGUI.NOTraditionalTVResult.setText("Invoke Susses");
				}
			}
		}).start();
	}

	public void invokeSemantic() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:LED:1") == null) {

				}
				System.out.println("[SemanticControlPoint] invoke LED");
				if (sGUI != null) {
					sGUI.NOSemanticLEDResult.setText("Invoke Susses");
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:Light:1") == null) {

				}
				System.out.println("[SemanticControlPoint] invoke Light");
				if (sGUI != null) {
					sGUI.NOSemanticLightResult.setText("Invoke Susses");
				}
			}
		}).start();
	}
}
