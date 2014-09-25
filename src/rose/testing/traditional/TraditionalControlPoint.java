package rose.testing.traditional;

import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

import rose.testing.semantic.SemanticGUI;

public class TraditionalControlPoint extends ControlPoint implements NotifyListener, EventListener, SearchResponseListener {
	private TraditionalGUI tGUI;
	private SemanticGUI sGUI;
	private int LCDCount = 0, LEDCount = 0;

	public TraditionalControlPoint(TraditionalGUI gui) {
		// TODO Auto-generated constructor stub
		addNotifyListener(this);
		addSearchResponseListener(this);
		addEventListener(this);
		setSSDPPort(8016);
		this.tGUI = gui;
	}

	@Override
	public void deviceSearchResponseReceived(SSDPPacket ssdpPacket) {
		// TODO Auto-generated method stub
		if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LCD:1") && LCDCount == 0) {
			System.out.println("[TraditionalControlPoint] Find LCD");
			LCDCount = LCDCount + 1;
			if (tGUI != null) {
				tGUI.MSTraditionalLCDResult.setText("Search Susses");
			}
		} else if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LED:1") && LEDCount == 0) {
			System.out.println("[TraditionalControlPoint] Find LED");
			LEDCount = LEDCount + 1;
			if (tGUI != null) {
				tGUI.MSSemanticLEDResult.setText("Search Susses");
			}
		} else if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LCD:1") && LCDCount == 1) {
			System.out.println("[TraditionalControlPoint] Find TV");
			if (tGUI != null) {
				tGUI.MSTraditionalTVResult.setText("Search Susses");
			}
		} else if (ssdpPacket.getST().equals("urn:schemas-upnp-org:device:LED:1") && LEDCount == 1) {
			System.out.println("[TraditionalControlPoint] Find Light");
			if (tGUI != null) {
				tGUI.MSSemanticLightResult.setText("Search Susses");
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

	public void searchDevice() {
		search("urn:schemas-upnp-org:device:LCD:1");
		search("urn:schemas-upnp-org:device:TV:1");
		search("urn:schemas-upnp-org:device:LED:1");
		search("urn:schemas-upnp-org:device:Light:1");
	}

	public void invokeDevice() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:TV:1") == null) {

				}
				System.out.println("[TraditionalControlPoint] invoke TV");
				if (tGUI != null) {
					tGUI.NOTraditionalTVResult.setText("Invoke Susses");
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:LCD:1") == null) {

				}
				System.out.println("[TraditionalControlPoint] invoke LCD");
				if (tGUI != null) {
					tGUI.NOTraditionalLCDResult.setText("Invoke Susses");
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:LED:1") == null) {

				}
				System.out.println("[TraditionalControlPoint] invoke LED");
				if (tGUI != null) {
					tGUI.NOSemanticLEDResult.setText("Invoke Susses");
				}
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (getDevice("urn:schemas-upnp-org:device:Light:1") == null) {

				}
				System.out.println("[TraditionalControlPoint] invoke Light");
				if (tGUI != null) {
					tGUI.NOSemanticLightResult.setText("Invoke Susses");
				}
			}
		}).start();
	}

}
