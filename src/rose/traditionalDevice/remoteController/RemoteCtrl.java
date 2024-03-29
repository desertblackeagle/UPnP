/******************************************************************
 *
 *	CyberUPnP for Java
 *
 *	Copyright (C) Satoshi Konno 2002
 *
 *	File : ClockDevice.java
 *
 ******************************************************************/
package rose.traditionalDevice.remoteController;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.ArgumentList;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.device.NotifyListener;
import org.cybergarage.upnp.device.SearchResponseListener;
import org.cybergarage.upnp.event.EventListener;
import org.cybergarage.upnp.ssdp.SSDPPacket;

public class RemoteCtrl extends ControlPoint implements NotifyListener, EventListener, SearchResponseListener {
	private final static String TV_DEVICE_TYPE = "urn:schemas-upnp-org:device:tv:1";
	private final static String TV_SERVICE_TYPE = "urn:schemas-upnp-org:service:power:1";

	private final static String LIGHT_DEVICE_TYPE = "urn:schemas-upnp-org:device:light:1";
	private final static String LIGHT_SERVICE_TYPE = "urn:schemas-upnp-org:service:power:1";

	private final static String AIRCON_DEVICE_TYPE = "urn:schemas-upnp-org:device:aircon:1";
	private final static String AIRCON_SERVICE_TYPE = "urn:schemas-upnp-org:service:power:1";

	private final static String WASHER_DEVICE_TYPE = "urn:schemas-upnp-org:device:washer:1";
	private final static String WASHER_SERVICE_TYPE = "urn:schemas-upnp-org:service:state:1";

	public RemoteCtrl() {
		addNotifyListener(this);
		addSearchResponseListener(this);
		addEventListener(this);
		search();
	}

	// //////////////////////////////////////////////
	// Listener
	// //////////////////////////////////////////////

	public void deviceNotifyReceived(SSDPPacket packet) {
		System.out.println("deviceNotifyReceived " + packet);
	}

	public void deviceSearchResponseReceived(SSDPPacket packet) {
//		System.out.println("deviceSearchResponseReceived " + packet);
	}

	public void eventNotifyReceived(String uuid, long seq, String name, String value) {
//		System.out.println(uuid + " \n" + seq + " \n" + name + " \n" + value);
	}

	// //////////////////////////////////////////////
	// Power
	// //////////////////////////////////////////////

	public void powerOn(String deviceType) {
		Device dev = getDevice(deviceType);
		if (dev == null) {
			System.out.println("Can't find device");
			return;
		}

		Action getPowerAct = dev.getAction("GetPower");
		if (getPowerAct.postControlAction() == false) {
			System.out.println("Can't get power action");
			return;
		}

		ArgumentList outArgList = getPowerAct.getOutputArgumentList();
		String powerState = outArgList.getArgument(0).getValue();
		String newPowerState = (powerState.compareTo("1") == 0) ? "0" : "1";

		Action setPowerAct = dev.getAction("SetPower");
		setPowerAct.setArgumentValue("Power", newPowerState);
		setPowerAct.postControlAction();
	}

	// //////////////////////////////////////////////
	// TV
	// //////////////////////////////////////////////

	public void tvPowerOn() {
		powerOn(TV_DEVICE_TYPE);
	}

	// //////////////////////////////////////////////
	// Light
	// //////////////////////////////////////////////

	public void lightPowerOn() {
		powerOn(LIGHT_DEVICE_TYPE);
	}

	// //////////////////////////////////////////////
	// Aircon
	// //////////////////////////////////////////////

	public void airconPowerOn() {
		powerOn(AIRCON_DEVICE_TYPE);
	}

	public void airconChangeTemp(String tempOff) {
		Device dev = getDevice(AIRCON_DEVICE_TYPE);
		if (dev == null)
			return;
		Action setTempAct = dev.getAction("SetTemp");
		setTempAct.setArgumentValue("Temp", tempOff);
		setTempAct.postControlAction();
	}

	public void airconTempUp() {
		airconChangeTemp("+1");
	}

	public void airconTempDown() {
		airconChangeTemp("-1");
	}

	// //////////////////////////////////////////////
	// Aircon
	// //////////////////////////////////////////////

	public void setWasherState(String value) {
		Device dev = getDevice(WASHER_DEVICE_TYPE);
		if (dev == null)
			return;
		Action setTempAct = dev.getAction("SetState");
		setTempAct.setArgumentValue("State", value);
		setTempAct.postControlAction();
	}

	public void washerStart() {
		setWasherState("1");
	}

	public void washerStop() {
		setWasherState("0");
	}

}
