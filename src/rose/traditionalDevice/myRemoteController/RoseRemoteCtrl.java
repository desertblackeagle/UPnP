package rose.traditionalDevice.myRemoteController;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.ArgumentList;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

public class RoseRemoteCtrl extends ControlPoint implements NotifyListener, EventListener, SearchResponseListener {

	private final static String DISPLAY_DEVICE_TYPE = "urn:schemas-upnp-org:device:TV:1";

	public RoseRemoteCtrl() {
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

	public void displayMyPrint() {
//		search("urn:schemas-upnp-org:device:LCD:1", 10);
		Device dev = getDevice(DISPLAY_DEVICE_TYPE);
		if (dev == null) {
			System.out.println("Can't find device");
			return;
		}

		Action getPrintAct = dev.getAction("Print");
		getPrintAct.setArgumentValue("Msg", "I want find TV");
		if (getPrintAct.postControlAction() == false) {
			System.out.println("Can't get print action");
			return;
		} else {
			ArgumentList outArgList = getPrintAct.getOutputArgumentList();
			int nOutArgs = outArgList.size();
			for (int n = 0; n < nOutArgs; n++) {
				Argument outArg = outArgList.getArgument(n);
				String name = outArg.getName();
				String value = outArg.getValue();
				System.out.println("name : " + name + "\nvalue : " + value);
			}
		}
	}

//	public String invokeMatch(String stRDF, String originRDF) {
//		Device dev = getDevice("urn:schemas-upnp-org:device:ontologybroker:1");
//		if (dev == null) {
//			System.out.println("[OntologyBrokerMatch] Can't find ontologybroker");
//			return "Can't find ontologybroker";
//		}
//		String name = "";
//		String value = "";
//		Action getMatchAct = dev.getAction("Match");
//		System.out.println("originRDF : " + originRDF + "\nstRDF : " + stRDF);
//		getMatchAct.setArgumentValue("RDF", stRDF);
//		getMatchAct.setArgumentValue("RDF2", originRDF);
//		if (getMatchAct.postControlAction() == false) {
//			System.out.println("Can't get Match action");
//			return "Can't get Match action";
//		} else {
//			ArgumentList outArgList = getMatchAct.getOutputArgumentList();
//			int nOutArgs = outArgList.size();
//			for (int n = 0; n < nOutArgs; n++) {
//				Argument outArg = outArgList.getArgument(n);
//				name = outArg.getName();
//				value = outArg.getValue();
//				System.out.println("name : " + name + "\nvalue : " + value);
//			}
//			return value;
//		}
//	}
}
