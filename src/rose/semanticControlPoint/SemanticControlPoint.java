package rose.semanticControlPoint;

import java.util.ArrayList;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.ArgumentList;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.xml.Node;

public class SemanticControlPoint extends ControlPoint {

	public Device getDevice(String name) {
		Device tmp = null;
		tmp = super.getDevice(name);
		if (tmp == null) {
			if (name.startsWith("urn:schemas-upnp-org:device:ontologybroker")) {
				return null;
			} else {
				return getSemanticDevice(name);
			}
		} else {
			return tmp;
		}
	}

	public String invokeMatch(String RDF, String isaRDF) {
		Device ontDev = getDevice("urn:schemas-upnp-org:device:ontologybroker:1");
		if (ontDev == null) {
			System.out.println("[OntologyBrokerMatch] Can't find ontologybroker");
			return "Can't find ontologybroker";
		}
		String name = "";
		String value = "";
		Action getMatchAct = ontDev.getAction("Match");
		System.out.println("originRDF : " + RDF + "\nstRDF : " + isaRDF);
		getMatchAct.setArgumentValue("RDF", RDF);
		getMatchAct.setArgumentValue("RDF2", isaRDF);
		if (getMatchAct.postControlAction() == false) {
			System.out.println("Can't get Match action");
			return "Can't get Match action";
		} else {
			ArgumentList outArgList = getMatchAct.getOutputArgumentList();
			int nOutArgs = outArgList.size();
			for (int n = 0; n < nOutArgs; n++) {
				Argument outArg = outArgList.getArgument(n);
				name = outArg.getName();
				value = outArg.getValue();
				System.out.println("name : " + name + "\nvalue : " + value);
			}
			return value;
		}
	}

	private String transformURNToURL(String URN) {
		String http = "";
		http = URN.replace(":", "/");
		http = http.replace("-", ".");
		http = http.replace("urn/", "http://");
		return http;
	}

	public Device getSemanticDevice(String name) {
		String deviceType = "";
		DeviceList dl = getDeviceList();
		for (int i = 0; i < dl.size(); i++) {
			Device dev = dl.getDevice(i);
			deviceType = dev.getDeviceType();
			if (invokeMatch(transformURNToURL(deviceType), transformURNToURL(name)).equals("true")) {
				return dev;
			}

			ArrayList<String> deviceAL = dev.getAllDevice();
			for (int j = 0; j < deviceAL.size(); j++) {
				if (invokeMatch(transformURNToURL(deviceAL.get(j)), transformURNToURL(name)).equals("true")) {
					return dev;
				}
			}
		}
		return null;
	}

}
