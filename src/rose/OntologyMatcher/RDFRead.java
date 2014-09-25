package rose.OntologyMatcher;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class RDFRead {
	public RDFRead() {
		// TODO Auto-generated constructor stub
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		m.read("http://192.168.1.239/ont/owl3.owl");
		
		String sourceUri = "http://schemas.upnp.org/device/";
		String relationShip = "http://schemas.upnp.org/device/";

		OntProperty isA = m.createTransitiveProperty(relationShip + "is-a");
		
		OntClass tv = m.createClass(sourceUri + "TV");
		OntClass pdp = m.createClass(sourceUri + "PDP");
		OntClass lcd = m.createClass(sourceUri + "LCD");
		OntClass led = m.createClass(sourceUri + "LED");
		OntClass ccfl = m.createClass(sourceUri + "CCFL");

		OntClass light = m.createClass(sourceUri + "Light");
		OntClass LED = m.createClass(sourceUri + "LED");// LED燈
		OntClass CFLs = m.createClass(sourceUri + "CFLs");// 節能燈泡
		
		System.out.println("if CCFL is a TV ?");
		if (match(m, isA, ccfl, tv)) {
			System.out.println("CCFL is a TV\n");
		} else {
			System.out.println("CCFL is not a TV\n");
		}
		System.out.println("if CCFL is a PDP ?");
		if (match(m, isA, ccfl, pdp)) {
			System.out.println("CCFL is a PDP\n");
		} else {
			System.out.println("CCFL is not a PDP\n");
		}
		
		m.read("http://192.168.1.239/ont/owl2.owl");
		try {
			OutputStreamWriter osw = new OutputStreamWriter(System.out, "Big5");
			m.write(osw, "RDF/XML-ABBREV");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean match(OntModel m, OntProperty p, OntClass child, OntClass parent) {

		StmtIterator stmt = m.listStatements(child, p, parent);
		if (stmt.hasNext()) {
			return true;
		}
		return false;

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RDFRead r = new RDFRead();
	}

}
