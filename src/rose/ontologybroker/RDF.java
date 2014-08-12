package rose.ontologybroker;

//					TV
//				    / \
//				  PDP LCD
//				       / \
//				    CCFL LED

//				   Light
//				    / \
//				  LED CFLs

import java.io.IOException;
import java.io.OutputStreamWriter;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class RDF {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		OutputStreamWriter osw = new OutputStreamWriter(System.out, "Big5");
		String sourceUri = "http://schemas.upnp.org/device/";
		String relationShip = "http://schemas.upnp.org/device/";
		String display = "http://schemas.upnp.org/device/display/1";
		OntProperty isA = m.createTransitiveProperty(relationShip + "is-a");
		OntClass tv = m.createClass(sourceUri + "TV/1");
		OntClass displayOnt = m.createClass(display);
		OntClass pdp = m.createClass(sourceUri + "PDP");
		OntClass lcd = m.createClass(sourceUri + "LCD/1");
		OntClass led = m.createClass(sourceUri + "LED");
		OntClass ccfl = m.createClass(sourceUri + "CCFL");

		OntClass light = m.createClass(sourceUri + "Light");
		OntClass LED = m.createClass(sourceUri + "LED");// LED燈
		OntClass CFLs = m.createClass(sourceUri + "CFLs");// 節能燈泡
		
		displayOnt.addProperty(isA, displayOnt);
		pdp.addProperty(isA, tv);
		lcd.addProperty(isA, tv);
		ccfl.addProperty(isA, lcd);
		led.addProperty(isA, lcd);

		LED.addProperty(isA, light);
		CFLs.addProperty(isA, light);

		StmtIterator stmt = m.listStatements(null, isA, tv);
		while (stmt.hasNext()) {
			Statement s = stmt.next();
			System.out.println(s);
		}

		System.out.println("\n");
		stmt = m.listStatements(null, isA, light);
		while (stmt.hasNext()) {
			Statement s = stmt.next();
			System.out.println(s);
		}
		System.out.println("\n\n\n\ntest:");
		// test
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

		System.out.println("\n\n\n\n\n\nXML:\n");
		m.write(osw, "RDF/XML-ABBREV");

	}

	public static boolean match(OntModel m, OntProperty p, OntClass child, OntClass parent) {

		StmtIterator stmt = m.listStatements(child, p, parent);
		if (stmt.hasNext()) {
			return true;
		}
		return false;

	}
}
