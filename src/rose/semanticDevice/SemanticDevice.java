package rose.semanticDevice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.cybergarage.http.HTTPRequest;
import org.cybergarage.http.HTTPResponse;
import org.cybergarage.http.HTTPStatus;
import org.cybergarage.net.HostInterface;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.ServiceList;
import org.cybergarage.upnp.UPnP;
import org.cybergarage.upnp.device.InvalidDescriptionException;
import org.cybergarage.upnp.device.NTS;
import org.cybergarage.upnp.ssdp.SSDPNotifyRequest;
import org.cybergarage.upnp.ssdp.SSDPNotifySocket;
import org.cybergarage.upnp.ssdp.SSDPPacket;
import org.cybergarage.util.FileUtil;
import org.cybergarage.xml.Node;
import org.cybergarage.xml.XML;

import rose.RDFMatcher.Matcher;

public class SemanticDevice extends Device {
	private Matcher ontologyBrokerMatch;
	private HashMap<String, String> semanticParent = new HashMap<String, String>();

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
		ontologyBrokerMatch = new Matcher();

	}

	public void semanticDeviceStart() {
		ontologyBrokerMatch.start();
		System.out.println("OntologyBrokerInit start");
	}

	public void semanticDeviceStop() {
		ontologyBrokerMatch.stop();
		System.out.println("OntologyBrokerInit stop");
	}

	// //////////////////////////////////////////////
	// add Parent Device Type
	// //////////////////////////////////////////////

	public void addParentDeviceType(String deviceName, String parentDeviceType) {
		semanticParent.put(deviceName, parentDeviceType);
	}

	// //////////////////////////////////////////////
	// deviceSearchResponse
	// //////////////////////////////////////////////

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

	// //////////////////////////////////////////////
	// httpRequestRecieved
	// //////////////////////////////////////////////

	public void httpRequestRecieved(HTTPRequest httpReq) {
		String uri = httpReq.getURI();
		if (uri.startsWith("/virtual/description") == true && !uri.replace("/virtual/description", "").equals("")) {
//			System.out.println("d : "+getLocationURL(httpReq.getLocalAddress()));
			virtualDescription(uri, httpReq);
		} else {
			super.httpRequestRecieved(httpReq);
		}
	}

	public void virtualDescription(String uri, HTTPRequest httpReq) {
		byte fileByte[] = new byte[0];
		URL u = null;
		try {
			u = new URL(getLocationURL(httpReq.getLocalAddress()));
			BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
			String tmp = "";
			String sumStream = "";
			while ((tmp = br.readLine()) != null) {
				if (tmp.trim().startsWith("<deviceType>")) {
					String deviceName = uri.replace("/virtual/description", "");
					deviceName = deviceName.replace(".xml", "");
//					System.out.println("deviceName : " + deviceName);
					tmp = "<deviceType>" + semanticParent.get(deviceName) + "</deviceType>";
//					System.out.println("deviceType : " + tmp);
				} else if (tmp.trim().startsWith("<UDN>")) {
					String deviceName = uri.replace("/virtual/description", "");
					deviceName = deviceName.replace(".xml", "");
					tmp = tmp.trim().replace("</UDN>", "") + deviceName + "</UDN>";
				}
				sumStream += tmp;
			}
			fileByte = sumStream.getBytes();
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HTTPResponse httpRes = new HTTPResponse();
		if (FileUtil.isXMLFileName(uri) == true) {
			httpRes.setContentType(XML.CONTENT_TYPE);
		}
		httpRes.setStatusCode(HTTPStatus.OK);
		httpRes.setContent(fileByte);
		httpReq.post(httpRes);
	}

	// //////////////////////////////////////////////
	// announce
	// //////////////////////////////////////////////

	@Override
	public void announce(String bindAddr) {
		// TODO Auto-generated method stub
		super.announce(bindAddr);
		for (String deviceName : semanticParent.keySet()) {
			virtualDeviceAnnounce(bindAddr, HostInterface.getHostURL(bindAddr, getHTTPPort(), "/virtual/description" + deviceName + ".xml"), deviceName);
		}
	}

	public void virtualDeviceAnnounce(String bindAddr, String location, String deviceName) {
		SSDPNotifySocket ssdpSock = new SSDPNotifySocket(bindAddr);
		SSDPNotifyRequest ssdpReq = new SSDPNotifyRequest();
		ssdpReq.setServer(UPnP.getServerName());
		ssdpReq.setLeaseTime(getLeaseTime());
		ssdpReq.setLocation(location);
		ssdpReq.setNTS(NTS.ALIVE);

		// uuid:device-UUID(::upnp:rootdevice)*
		if (isRootDevice() == true) {
			String devNT = "upnp:rootdevice";
			String devUSN = "uuid:cybergarage" + deviceName + "Device::upnp:rootdevice";
			ssdpReq.setNT(devNT);
			ssdpReq.setUSN(devUSN);
			ssdpSock.post(ssdpReq);

//			String devUDN = getUDN();
//			ssdpReq.setNT(devUDN);
//			ssdpReq.setUSN(devUDN);
//			ssdpSock.post(ssdpReq);
		}
		ssdpSock.close();
	}
}
