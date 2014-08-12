package rose.traditionalDevice.myRemoteController;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.ActionList;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.ServiceList;
import org.cybergarage.upnp.ServiceStateTable;
import org.cybergarage.upnp.StateVariable;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

public class UPnPDevicePrint extends ControlPoint implements NotifyListener, EventListener, SearchResponseListener {

	public UPnPDevicePrint() {
		addNotifyListener(this);
		addSearchResponseListener(this);
		addEventListener(this);
		search();
		printDevice();
	}

// //////////////////////////////////////////////
// Listener
// //////////////////////////////////////////////

	public void deviceNotifyReceived(SSDPPacket packet) {
//	System.out.println("deviceNotifyReceived " + packet);
	}

	public void deviceSearchResponseReceived(SSDPPacket packet) {
//	System.out.println("deviceSearchResponseReceived " + packet.getST());
	}

	public void eventNotifyReceived(String uuid, long seq, String name, String value) {
//	System.out.println(uuid + " \n" + seq + " \n" + name + " \n" + value);
	}

	private void printDevice() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DeviceList dl = getDeviceList();
					for (int i = 0; i < dl.size(); i++) {
						printDevice((Device) dl.get(i), 0);
//						ServiceList sl = ((Device) dl.get(i)).getServiceList();
//						for (int x = 0; x < sl.size(); x++) {
//							System.out.println("S : " + ((Service) sl.get(x)).getServiceType());
//						}
					}
					System.out.println();
				}
			}
		}).start();
	}

	private void printDevice(Device d, int stair) {
		for (int j = 0; j < stair; j++) {
			System.out.print("\t");
		}
		System.out.println(d.getFriendlyName());
		printService(d.getServiceList(), stair);
		DeviceList dl = d.getDeviceList();
		stair++;
		for (int i = 0; i < dl.size(); i++) {
			Device d1 = (Device) dl.get(i);
			printDevice(d1, stair);
		}
	}

	private void printService(ServiceList sl, int stair) {
		for (int i = 0; i < sl.size(); i++) {
			for (int j = 0; j < stair; j++) {
				System.out.print("\t");
			}
			System.out.println("Service : " + ((Service) sl.get(i)).getServiceType());
			int tmp = stair + 1;
			System.out.println("URL root : " + ((Service) sl.get(i)).getRootDevice().getLocation());
			System.out.println("URL : " + ((Service) sl.get(i)).getSCPDURL());
			printAction(((Service) sl.get(i)).getActionList(), tmp);
		}
	}

	private void printAction(ActionList al, int stair) {

		for (int i = 0; i < al.size(); i++) {
			for (int j = 0; j < stair; j++) {
				System.out.print("\t");
			}
			System.out.println("Action : " + ((Action) al.get(i)).getName());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UPnPDevicePrint().start();
	}

}
