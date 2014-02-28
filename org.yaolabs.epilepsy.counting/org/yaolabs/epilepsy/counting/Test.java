package org.yaolabs.epilepsy.counting;
import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.yaolabs.epilepsy.counting.CountAlgoCall;

public class Test extends CountAlgoCall{
	
	public static void main (String args[])
	{
		
		public static OWLOntology OWLFileLoad(String FilePath){
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			 File file = new File(FilePath);
			  OWLOntology OwlFile = null;
			try {
				OwlFile = manager.loadOntologyFromOntologyDocument(file);
			} catch (OWLOntologyCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  System.out.println("Loaded ontology: " + OwlFile);
			return OwlFile;
		}

		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");
		
			CountAlgoCall someobj = new CountAlgoCall(EEGOwl);
	}
	
	public Test(OWLOntology EEGOwl) {
		super(EEGOwl);
		// TODO Auto-generated constructor stub
	}

	
		
		
		
	
}
