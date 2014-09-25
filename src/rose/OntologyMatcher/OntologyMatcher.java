package rose.OntologyMatcher;

import java.io.File;

import org.cybergarage.net.HostInterface;
import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.ServiceList;
import org.cybergarage.upnp.StateVariable;
import org.cybergarage.upnp.control.ActionListener;
import org.cybergarage.upnp.control.QueryListener;
import org.cybergarage.upnp.device.InvalidDescriptionException;

public class OntologyMatcher extends Device implements ActionListener, QueryListener {
	private final static String DESCRIPTION_FILE_NAME = "C:/Users/helloR/Dropbox/JavaWorspace/UpnpTest/src/rose/ontologybroker/description/description.xml";
	private StateVariable matchVar;
	private SemanticMatch semanticMatch;

	public OntologyMatcher() throws InvalidDescriptionException {
		// TODO Auto-generated constructor stub
		super(new File(DESCRIPTION_FILE_NAME));
		semanticMatch = new SemanticMatch();
		setSSDPBindAddress(HostInterface.getInetAddress(HostInterface.IPV4_BITMASK, null));
		setHTTPBindAddress(HostInterface.getInetAddress(HostInterface.IPV4_BITMASK, null));
		Action getMatchAction = getAction("Match");
		getMatchAction.setActionListener(this);

		ServiceList serviceList = getServiceList();

		Service service = serviceList.getService(0);
//		System.out.println(serviceList.getService(0).getServiceID());
		service.setQueryListener(this);

		matchVar = getStateVariable("Matched");
		setLeaseTime(5);
	}

	@Override
	public boolean actionControlReceived(Action action) {
		// TODO Auto-generated method stub
		String actionName = action.getName();

		boolean ret = false;
		if (actionName.equals("Match") == true) {
			Argument rdfArg = action.getArgument("RDF");
			String temp = rdfArg.getValue();

			Argument rdf2Arg = action.getArgument("RDF2");
			String temp2 = rdf2Arg.getValue();

			Argument resultArg = action.getArgument("MatchResult");

			if (semanticMatch.match(temp, temp2)) {
				resultArg.setValue("true");
				matchVar.setValue("true");
				System.out.println("match result : " + temp + " is a " + temp2);
			} else {
				resultArg.setValue("false");
				matchVar.setValue("false");
				System.out.println("match result : " + temp + " is not a " + temp2);
			}
			ret = true;
		}
		return ret;
	}

	@Override
	public boolean queryControlReceived(StateVariable stateVar) {
		// TODO Auto-generated method stub
		return true;
	}

	public static void main(String[] args) {
		OntologyMatcher ob;
		try {
			ob = new OntologyMatcher();
			ob.start();
		} catch (InvalidDescriptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
