package rose.semanticDevice.multiDevice;

import java.io.File;

import org.cybergarage.net.HostInterface;
import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.ServiceList;
import org.cybergarage.upnp.StateVariable;
import org.cybergarage.upnp.control.ActionListener;
import org.cybergarage.upnp.control.QueryListener;
import org.cybergarage.upnp.device.InvalidDescriptionException;

import rose.semanticDevice.SemanticDevice;

public class MultiDevice extends SemanticDevice implements ActionListener, QueryListener {
	private StateVariable printMsg;

	public MultiDevice(String desPath) throws InvalidDescriptionException {
		// TODO Auto-generated constructor stub
		super(new File(desPath));
		setSSDPBindAddress(HostInterface.getInetAddress(HostInterface.IPV4_BITMASK, null));
		setHTTPBindAddress(HostInterface.getInetAddress(HostInterface.IPV4_BITMASK, null));
		semanticDeviceStart();
		
		Action getTimeAction = getAction("Print");
		getTimeAction.setActionListener(this);

		ServiceList serviceList = getServiceList();
		Service service = serviceList.getService(0);
		service.setQueryListener(this);

		printMsg = getStateVariable("Msg");

		setLeaseTime(10);
		for (int i = 2; i <= 100; i++) {
			addParentDeviceType("TV" + i, "urn:schemas-upnp-org:device:TV:" + i);
		}
	}

	@Override
	public boolean actionControlReceived(Action action) {
		String actionName = action.getName();
		if (actionName.equals("Print") == true) {
			Argument msgArg = action.getArgument("Msg");
			String temp = msgArg.getValue();
			Argument resultArg = action.getArgument("Result");
			resultArg.setValue(temp);
			System.out.println("display " + temp);
			return true;
		}
		return false;
	}

	@Override
	public boolean queryControlReceived(StateVariable stateVar) {
		// TODO Auto-generated method stub
		return true;
	}

	public static void main(String[] args) {
		try {
			MultiDevice md1 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description1.xml");
			MultiDevice md2 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description2.xml");
			MultiDevice md3 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description3.xml");
			MultiDevice md4 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description4.xml");
			MultiDevice md5 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description5.xml");
			MultiDevice md6 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description6.xml");
			MultiDevice md7 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description7.xml");
			MultiDevice md8 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description8.xml");
			MultiDevice md9 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description9.xml");
			MultiDevice md10 = new MultiDevice("C:/Users/helloR/Dropbox/JavaWorspace/Semantic-UPnP-multi-notify/src/rose/semanticDevice/multiDevice/description/description10.xml");	
			md1.start();
			md2.start();
			md3.start();
			md4.start();
			md5.start();
			md6.start();
			md7.start();
			md8.start();
			md9.start();
			md10.start();
		} catch (InvalidDescriptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
