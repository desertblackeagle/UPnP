package rose.ontologybroker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class SemanticMatch {
	private OntModel m = null;
	private OntProperty isA = null;

	public SemanticMatch() {
		// TODO Auto-generated constructor stub
		m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
//		m.read("http://192.168.1.239/ont/owl1.owl");
//		m.read("C:/owl1.owl");
		try {
			m.read(new FileInputStream(new File("C:/owl1.owl")), "Big-5");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String relationShip = "http://schemas.upnp.org/device/";

		isA = m.createTransitiveProperty(relationShip + "is-a");
	}

	public boolean match(String rdf1, String rdf2) {
		if (m != null) {
			OntClass ont1 = m.createClass(rdf1);
			OntClass ont2 = m.createClass(rdf2);
			if (match(m, isA, ont1, ont2)) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("OntModel is null");
		}
		return false;
	}

	private boolean match(OntModel m, OntProperty p, OntClass child, OntClass parent) {
		StmtIterator stmt = m.listStatements(child, p, parent);
		if (stmt.hasNext()) {
			return true;
		}
		return false;
	}

}
