package org.yaolabs.epilepsy.inferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class EEG_GoldStdVariables {
	  static Map<OWLNamedIndividual,ArrayList<OWLNamedIndividual>> GoldStd_NormalPatterns_PropMap = new HashMap<OWLNamedIndividual,ArrayList<OWLNamedIndividual>>();
	  static ArrayList<OWLNamedIndividual> NormalPatternPropList = new ArrayList<OWLNamedIndividual>();
	  
	  public static Map<OWLNamedIndividual, ArrayList<OWLNamedIndividual>> getGoldStd_NormalPatterns_PropMap() {
		return GoldStd_NormalPatterns_PropMap;
	}



	public static void main(String[] args)
	
	{

	ArrayList<OWLNamedIndividual> AlphaRhythm = new ArrayList<OWLNamedIndividual>();
	

	
	OWLOntologyManager man = OWLManager.createOWLOntologyManager();
    String base = "http://www.owl-ontologies.com/unnamed.owl";

    try {
		OWLOntology ont = man.createOntology(IRI.create(base));
	} catch (OWLOntologyCreationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    OWLDataFactory dataFactory = man.getOWLDataFactory();
	
    //Reference Properties with URIs
    
    //Assert the top level
    OWLNamedIndividual Alpha_Rhythms = dataFactory.getOWLNamedIndividual(IRI.create(base
            + "#Alpha_Rhythms"));
	
    //Assert Properties of this top level
    OWLNamedIndividual Posterior = dataFactory.getOWLNamedIndividual(IRI.create(base
            + "#Posterior"));
    OWLNamedIndividual Occipital = dataFactory.getOWLNamedIndividual(IRI.create(base
            + "#Occipital"));
    OWLNamedIndividual Attention = dataFactory.getOWLNamedIndividual(IRI.create(base
            + "#Attention"));
    OWLNamedIndividual Awake = dataFactory.getOWLNamedIndividual(IRI.create(base
            + "#Awake"));
    OWLNamedIndividual Amp_less_50uV = dataFactory.getOWLNamedIndividual(IRI.create(base
            + "#_<50uV"));
    OWLNamedIndividual Assymetry = dataFactory.getOWLNamedIndividual(IRI.create(base
            + "#Asymmetry"));
    
    //Gold standard for AlphaRhythm
	  AlphaRhythm.add(Posterior);
	  AlphaRhythm.add(Occipital);
	  AlphaRhythm.add(Attention);
	  AlphaRhythm.add(Awake);
	  AlphaRhythm.add(Amp_less_50uV);
	  AlphaRhythm.add(Assymetry);
	  
	
	  GoldStd_NormalPatterns_PropMap.put(Alpha_Rhythms, AlphaRhythm);
	}
}
