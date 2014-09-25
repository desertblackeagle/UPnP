package rose.traditionalDevice.myRemoteController;

import java.io.IOException;

import org.cybergarage.net.HostInterface;
import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.ServiceList;
import org.cybergarage.upnp.StateVariable;
import org.cybergarage.upnp.control.ActionListener;
import org.cybergarage.upnp.control.QueryListener;
import org.cybergarage.upnp.device.InvalidDescriptionException;
import org.cybergarage.upnp.ssdp.SSDPPacket;

public class MSearchDump extends Device implements ActionListener, QueryListener{
	private final static String PRESENTATION_URI = "/presentation";
	
	private final static String DEVICE_DESCRIPTION = 
		"<?xml version=\"1.0\" ?>  \n" +
		"<root xmlns=\"urn:schemas-upnp-org:device-1-0\"> \n" +
		" 	<specVersion> \n" +
		" 		<major>1</major>  \n" +
		" 		<minor>0</minor>  \n" +
		" 	</specVersion> \n" +
		" 	<device> \n" +
		" 		<deviceType>urn:schemas-upnp-org:device:LCD:1</deviceType>  \n" +
		" 		<friendlyName>CyberGarage Semantic LCD Device</friendlyName>  \n" +
		" 		<manufacturer>CyberGarage</manufacturer>  \n" +
		" 		<manufacturerURL>http://www.cybergarage.org</manufacturerURL>  \n" +
		" 		<modelDescription>CyberUPnP LCD Device</modelDescription>  \n" +
		" 		<modelName>Display</modelName>  \n" +
		" 		<modelNumber>1.0</modelNumber>  \n" +
		" 		<modelURL>http://www.cybergarage.org</modelURL>  \n" +
		" 		<serialNumber>1234567835</serialNumber>  \n" +
		" 		<UDN>uuid:cybergarageLCDDevice</UDN>  \n" +
		" 		<UPC>123456789024</UPC>  \n" +
		" 		<iconList> \n" +
		" 			<icon> \n" +
		" 				<mimetype>image/gif</mimetype>  \n" +
		" 				<width>48</width>  \n" +
		" 				<height>32</height>  \n" +
		" 				<depth>8</depth>  \n" +
		" 				<url>icon.gif</url>  \n" +
		" 			</icon> \n" +
		" 		</iconList> \n" +
		" 		<serviceList> \n" +
		" 			<service> \n" +
		" 				<serviceType>urn:schemas-upnp-org:service:print:1</serviceType>  \n" +
		" 				<serviceId>urn:upnp-org:serviceId:print:1</serviceId>  \n" +
		" 				<SCPDURL>/service/display/description.xml</SCPDURL>  \n" +
		" 				<controlURL>/service/display/control</controlURL>  \n" +
		" 				<eventSubURL>/service/display/eventSub</eventSubURL>  \n" +
		" 			</service> \n" +
		" 		</serviceList> \n" +
		" 		<presentationURL>http://www.cybergarage.org</presentationURL>  \n" +
		" 	</device> \n" +
		"</root>";

	private final static String SERVICE_DESCRIPTION =
		"<?xml version=\"1.0\"?> \n" +
		"<scpd xmlns=\"urn:schemas-upnp-org:service-1-0\" > \n" +
		" 	<specVersion> \n" +
		" 		<major>1</major> \n" +
		" 		<minor>0</minor> \n" +
		" 	</specVersion> \n" +
		" 	<actionList> \n" +
		" 		<action> \n" +
		" 			<name>Print</name> \n" +
		" 			<argumentList> \n" +
		" 				<argument> \n" +
		" 					<name>Msg</name> \n" +
		" 					<relatedStateVariable>Msg</relatedStateVariable> \n" +
		" 					<direction>in</direction> \n" +
		" 				</argument> \n" +
		" 				<argument> \n" +
		" 					<name>Result</name> \n" +
		" 					<relatedStateVariable>Result</relatedStateVariable> \n" +
		" 					<direction>out</direction> \n" +
		" 				</argument> \n" +
		" 			</argumentList> \n" +
		" 		</action> \n" +
		" 	</actionList> \n" +
		" 	<serviceStateTable> \n" +
		" 		<stateVariable sendEvents=\"yes\"> \n" +
		" 			<name>Msg</name> \n" +
		" 			<dataType>string</dataType> \n" +
		" 		</stateVariable> \n" +
		" 		<stateVariable sendEvents=\"yes\"> \n" +
		" 			<name>Result</name> \n" +
		" 			<dataType>string</dataType> \n" +
		" 		</stateVariable> \n" +
		" 	</serviceStateTable> \n" +
		"</scpd>";
	public MSearchDump()throws InvalidDescriptionException, IOException {
		// TODO Auto-generated constructor stub
		super();
		loadDescription(DEVICE_DESCRIPTION);
		setSSDPBindAddress(
				HostInterface.getInetAddress(HostInterface.IPV4_BITMASK, null)
		);
		setHTTPBindAddress(
				HostInterface.getInetAddress(HostInterface.IPV4_BITMASK, null)
		);
		Service timeService = getService("urn:schemas-upnp-org:service:print:1");
		timeService.loadSCPD(SERVICE_DESCRIPTION);

		Action getTimeAction = getAction("Print");
		getTimeAction.setActionListener(this);
		
		ServiceList serviceList = getServiceList();
		Service service = serviceList.getService(0);
		service.setQueryListener(this);
		
		setLeaseTime(100);
	}

	public boolean deviceSearchResponse(SSDPPacket ssdpPacket) {
		System.out.println(new String(ssdpPacket.getData()));
		return super.deviceSearchResponse(ssdpPacket);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MSearchDump msd;
		try {
			msd = new MSearchDump();
			msd.start();
		} catch (InvalidDescriptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean queryControlReceived(StateVariable stateVar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean actionControlReceived(Action action) {
		// TODO Auto-generated method stub
		return false;
	}

}
