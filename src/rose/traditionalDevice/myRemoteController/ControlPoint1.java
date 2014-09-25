package rose.traditionalDevice.myRemoteController;

import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

public class ControlPoint1 extends ControlPoint implements NotifyListener, EventListener, SearchResponseListener {
	private final static String DISPLAY_DEVICE_TYPE = "urn:schemas-upnp-org:device:TV:1";

	public ControlPoint1() {
		addNotifyListener(this);
		addSearchResponseListener(this);
		addEventListener(this);
		setSSDPPort(8009);
	}

	// //////////////////////////////////////////////
	// Listener
	// //////////////////////////////////////////////

	public void deviceNotifyReceived(SSDPPacket packet) {
//		System.out.println(packet.getOntologyURL());
//		System.out.println("deviceNotifyReceived " + packet);
	}

	public void deviceSearchResponseReceived(SSDPPacket packet) {
		System.out.println("deviceSearchResponseReceived \n" + new String(packet.getData()));
	}

	public void eventNotifyReceived(String uuid, long seq, String name, String value) {
//		System.out.println(uuid + " \n" + seq + " \n" + name + " \n" + value);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ControlPoint1 c = new ControlPoint1();
		c.start();
		c.search("urn:schemas-upnp-org:device:TV:1");
	}

}
