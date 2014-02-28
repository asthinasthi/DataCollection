package org.yaolaps.epilepsy.inferences;
//ALL THE CLASSES UNDER EEG ARE INFERRED HERE.
import java.awt.List;
import java.io.File;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.yaolabs.epilepsy.counting.CountAlgoCall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.yaolabs.epilepsy.counting.CountingAlgorithm;


public class EEG {

	public static void main(String args[])
	{
		 Map<OWLNamedIndividual , ArrayList<OWLNamedIndividual>> Patient_ObjProp_Map = new HashMap<OWLNamedIndividual , ArrayList<OWLNamedIndividual>>();
		 Set<OWLNamedIndividual> Patients = null;			

			//get all FILE INDIVIDUALS , which are the patients.
		 File file = new File("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");
		OWLOntology EEGOwl = OWLFileLoad("/Users/Ani/Dropbox/YaoNotes Project/Research/SoftwareDevelopment/OwlFiles/EEG_Summary_Dev.owl");
			
		CountAlgoCall DataCollected = new CountAlgoCall(EEGOwl);
			
			//Inferring Normal Patterns
			//Alpha Rhythm
		//for every patient check if he/she has Alpha Rhythm

		try {
			DataCollected.CountAllFileIndsinProj();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Patients = DataCollected.getAllFileindividuals();
		
		ObjectPropAssertions OPA = new ObjectPropAssertions();
		OPA.GetPropertiesofFile(EEGOwl, Patients);
		Patient_ObjProp_Map = OPA.getPatient_ObjProp_Map();
		
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
       
        //Object Prop to be asserted
        OWLNamedIndividual Alpha_Rhythms = dataFactory.getOWLNamedIndividual(IRI.create(base
                + "#Alpha_Rhythms"));
        
        OWLClass Complex = dataFactory.getOWLClass(IRI.create(base
                + "#Complex"));
		  OWLClassAssertionAxiom ClassAssertion = dataFactory
	                .getOWLClassAssertionAxiom(Complex, Alpha_Rhythms);
		 man.addAxiom(EEGOwl, ClassAssertion);
      
        
        //create obj property
        OWLObjectProperty hasNormalPattern = dataFactory.getOWLObjectProperty(IRI.create(base
                + "#hasNormalPattern"));
        
        //Gold standard for AlphaRhythm
		  AlphaRhythm.add(Posterior);
		  AlphaRhythm.add(Occipital);
		  AlphaRhythm.add(Attention);
		  AlphaRhythm.add(Awake);
		  AlphaRhythm.add(Amp_less_50uV);
		  AlphaRhythm.add(Assymetry);
		  
		  Map<String,ArrayList<OWLNamedIndividual>> GoldStd_NormalPatterns_PropMap = new HashMap<String,ArrayList<OWLNamedIndividual>>();
		 // Add Properties of All Normal Patterns as ArrayList here:
		  GoldStd_NormalPatterns_PropMap.put("AlphaRhythm", AlphaRhythm);
		  
		  //Calculate cosine similarities with all EEG concepts and give out the most closely matched
		  for(OWLNamedIndividual pat : Patients)
		  {
			  System.out.println("Patient : "+ pat);
			  
			  
			  ArrayList<OWLNamedIndividual> common = new ArrayList<OWLNamedIndividual>();
			  //Take all the gold standard vector
			  for(OWLIndividualname goldstd_NormalPatterns : GoldStd_NormalPatterns_PropMap)
			  {
			  common.addAll(AlphaRhythm);
			  System.out.println("Patient Prop are :" + Patient_ObjProp_Map.get(pat));
			  //Dot product ; finding common elements with gold standard and given vector
			  common.retainAll(Patient_ObjProp_Map.get(pat));
			  
			  System.out.println("Common now contains : " + common);
			  double MagnitudeofPatientVector = MagnitudeofVector(Patient_ObjProp_Map.get(pat).size());
			  double MagnitudeofAlphaRhtymVector = MagnitudeofVector(AlphaRhythm.size());
			  
			  double CosineSimilarity = 0;
			  
			  if(MagnitudeofPatientVector!=0)
			  {
			   CosineSimilarity =  ((double)common.size())/(MagnitudeofPatientVector*MagnitudeofAlphaRhtymVector) ;
			  }
			  else
			  {
				  continue;
			  }
			  
			 
			  System.out.println("Cosine Similarity is : " + CosineSimilarity);
		  }
			  if(!common.isEmpty())
			  {
				  System.out.println("found");
				  //Assert this patient to AlphaRhythm
				  IRI patIri = pat.getIRI();
				  
				  OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory
			                .getOWLObjectPropertyAssertionAxiom(hasNormalPattern, pat, Alpha_Rhythms);
				  
				 AddAxiom  addAx = new AddAxiom(EEGOwl , propertyAssertion);
				 man.applyChange(addAx);
//				 man.addAxiom(EEGOwl, propertyAssertion);
			        
			        try {
			        	man.saveOntology(EEGOwl, IRI.create(file.toURI()));
				 	
					} catch (OWLOntologyStorageException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  }
		  
		  }
			

//		if Age == adult , Location = Posterior,Occipital,Attentuation == visual or mental,Semiology = eyesclosed,state  = awake , wave prop 


		
	}
	
	static Double MagnitudeofVector(double sizeofarraylist)
	{
		//Currently all the vectors are unit magnitude
		return  Math.sqrt(sizeofarraylist);
	}
	
	//Calculates Cosine Similarity based on patient properties and Compares it to Gold standard EEG Concept properties. If no match it returns a 0
	static double GetCosineSimilarityRatio(ArrayList PatientVectorList , ArrayList EEGConceptGoldVectorList)
	{
		  ArrayList<OWLNamedIndividual> common = new ArrayList<OWLNamedIndividual>();
		  //Take all the gold standard vector
		  common.addAll(EEGConceptGoldVectorList);
		  
		  //Dot product ; finding common elements with gold standard and given vector
		  common.retainAll(PatientVectorList);
		  
		  System.out.println("Common now contains : " + common);
		  double MagnitudeofPatientVector = MagnitudeofVector(PatientVectorList.size());
		  double MagnitudeofEEGConceptGoldVector = MagnitudeofVector(EEGConceptGoldVectorList.size());
		  
		  double CosineSimilarity = 0;
		  
		  if(MagnitudeofPatientVector!=0)
		  {
		   CosineSimilarity =  ((double)common.size())/(MagnitudeofPatientVector*MagnitudeofEEGConceptGoldVector) ;
		  }
		  else
		  {
		    CosineSimilarity = 0;
		  }
		  
		  return CosineSimilarity;
	}
	
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
}
