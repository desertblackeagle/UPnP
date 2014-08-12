package rose.semanticDevice;

import java.io.File;
import java.io.InputStream;

import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.ServiceList;
import org.cybergarage.upnp.device.InvalidDescriptionException;
import org.cybergarage.upnp.ssdp.SSDPPacket;
import org.cybergarage.xml.Node;

import rose.ontologybroker.OntologyBrokerMatch;

public class SemanticDevice extends Device {
	private OntologyBrokerMatch ontologyBrokerMatch;

	public SemanticDevice(Node root, Node device) {
		super(root, device);
		ontologyBrokerInit();
	}

	public SemanticDevice() {
		super();
		ontologyBrokerInit();
	}

	public SemanticDevice(Node device) {
		super(device);
		ontologyBrokerInit();
	}

	public SemanticDevice(File descriptionFile) throws InvalidDescriptionException {
		super(descriptionFile);
		ontologyBrokerInit();
	}

	public SemanticDevice(InputStream input) throws InvalidDescriptionException {
		super();
		ontologyBrokerInit();
	}

	public SemanticDevice(String descriptionFileName) throws InvalidDescriptionException {
		super(descriptionFileName);
		ontologyBrokerInit();
	}

	private void ontologyBrokerInit() {
//		System.out.println("OntologyBrokerInit init");
		ontologyBrokerMatch = new OntologyBrokerMatch();

	}

	public void semanticDeviceStart() {
		ontologyBrokerMatch.start();
		System.out.println("OntologyBrokerInit start");
	}

	public void semanticDeviceStop() {
		ontologyBrokerMatch.stop();
		System.out.println("OntologyBrokerInit stop");
	}

//	public boolean isDevice(String name) {
////		System.out.println("[SemanticDevice][isDevice]");
//		System.out.println("[SemanticDevice]name : "+name);
//		if (super.isDevice(name)) {
//			if(name.equals("urn:schemas-upnp-org:device:ontologybroker:1")){
//				System.out.println("abcdabcdabcd");
//			}
//			return true;
//		}else{
//			if(name.equals("urn:schemas-upnp-org:device:ontologybroker:1")){
//				System.out.println("123456789123456789");
//			}
//			String deviceTypeToURL = transformURNToURL(name);
//			String originDeviceTypeURL = transformURNToURL(getDeviceType());
////			System.out.println("[Debug][SemanticDevice] deviceTypeToURL " + deviceTypeToURL + " and origin " + originDeviceTypeURL);
//			if (ontologyBrokerMatch.invokeMatch(deviceTypeToURL, originDeviceTypeURL).equals("true")) {
//				return true;
//			} else {
//				return false;
//			}
//		}
////		return super.isDevice(name);
//	}

	public boolean deviceSearchResponse(SSDPPacket ssdpPacket) {
		boolean deviceNotFind = false;
		if (super.deviceSearchResponse(ssdpPacket) == deviceNotFind) {

			String stToURL = transformURNToURL(ssdpPacket.getST());
			String originDeviceTypeURL = transformURNToURL(getDeviceType());
//			System.out.println("[Debug][SemanticDevice] stURL " + stToURL + " and origin " + originDeviceTypeURL);

			if (ontologyBrokerMatch.invokeMatch(stToURL, originDeviceTypeURL).equals("true")) {
//				System.out.println("[Debug][SemanticDevice]" + originDeviceTypeURL + " is send search response");
				String devUSN = getUDN();
				String devType = getDeviceType();
				devUSN = getUDN() + "::" + devType;
				postSearchResponse(ssdpPacket, devType, devUSN);

				ServiceList serviceList = getServiceList();
				int serviceCnt = serviceList.size();
				for (int n = 0; n < serviceCnt; n++) {
					Service service = serviceList.getService(n);
					service.serviceSearchResponse(ssdpPacket);
				}

				DeviceList childDeviceList = getDeviceList();
				int childDeviceCnt = childDeviceList.size();
				for (int n = 0; n < childDeviceCnt; n++) {
					Device childDevice = childDeviceList.getDevice(n);
					childDevice.deviceSearchResponse(ssdpPacket);
				}
				return true;
			} else {
				return false;
			}
		} else {
			return deviceNotFind;
		}
	}

	private String transformURNToURL(String URN) {
		String http = "";
		http = URN.replace(":", "/");
		http = http.replace("-", ".");
		http = http.replace("urn/", "http://");
		return http;
	}
}
