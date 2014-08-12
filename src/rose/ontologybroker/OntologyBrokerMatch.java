package rose.ontologybroker;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.ArgumentList;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;


public class OntologyBrokerMatch extends ControlPoint{
	public OntologyBrokerMatch() {
		search();
	}

	public String invokeMatch(String stRDF, String originRDF) {
		Device ontDev = getDevice("urn:schemas-upnp-org:device:ontologybroker:1");
		if (ontDev == null) {
			System.out.println("[OntologyBrokerMatch] Can't find ontologybroker");
			return "Can't find ontologybroker";
		}
		String name = "";
		String value = "";
		Action getMatchAct = ontDev.getAction("Match");
		System.out.println("originRDF : " + originRDF + "\nstRDF : " + stRDF);
		getMatchAct.setArgumentValue("RDF", originRDF);
		getMatchAct.setArgumentValue("RDF2", stRDF);
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
}
