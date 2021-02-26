/*
 * Copyright (c) 2020 Be The Match operated by National Marrow Donor Program (NMDP).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the License.
 */

package org.nmdp.converter;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.dstu3.model.*;

import org.nmdp.utils.CodingSetup;
import org.nmdp.utils.ConfigToMap;
import org.nmdp.utils.FhirGuid;

import java.io.File;
import java.util.*;

/**
 * Loinc Based Bundle Generation Class
 */
public class GenerateLoincBundle
{
    private static final Map<String, String> myLoincDescMap = (new ConfigToMap(new File(System.getenv("LOINC_DESC_MAP")))).readFiletoMap();

    private String myPatientId;

    private String myPatientName;

    private int myNumReps;

    public void setMyNumReps(int myNumReps) {
        this.myNumReps = myNumReps;
    }

    public void setMyPatientName(String myPatientName) {
        this.myPatientName = myPatientName;
    }

    public void setMyPatientId(String myPatientId) {
        this.myPatientId = myPatientId;
    }

    private String myFhirOutput;

    public String getMyFhirOutput() {
        return myFhirOutput;
    }

    public void setMyFhirOutput(String myFhirOutput) {
        this.myFhirOutput = myFhirOutput;
    }

    /**
     * Generate the FHIR bundle here
     * Line 55-56 below has code that can be repeated
     * for generatng duplicate Observations in the same bundle
     */
    public void createFHIR()
    {
        BundleGenerator aBG = new BundleGenerator();
        List<DomainResource> aDomainResources = new ArrayList<>();
        DomainResource aP = generatePatient();
        aDomainResources.add(aP);
        //NOTE: Following line of code can be repeated as many times
        // as needed to generate duplicate copies of each observation
        for (int aCount=0; aCount < myNumReps; aCount++ )
        {
            myLoincDescMap.keySet().stream().filter(Objects::nonNull)
                    .forEach(aKey -> aDomainResources.add(getResource(aKey, myLoincDescMap.get(aKey), aP)));
        }
        aBG.generateFhirBundle(aDomainResources);
        FhirContext ctx = FhirContext.forDstu3();
        setMyFhirOutput(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(aBG.getMyBundleResource().getMyFhirBundle()));
    }

    /**
     * Create Resource and Return as DomainResource
     * @param theLoincCode
     * @param theLoincDesc
     * @param thePat
     * @return
     */
    public DomainResource getResource(String theLoincCode, String theLoincDesc, DomainResource thePat)
    {
        Observation aObs = new Observation();
        aObs.setId(FhirGuid.genereateUrn());
        aObs.setStatus(Observation.ObservationStatus.FINAL);

        // Adding Category Coding to Observation
        CodeableConcept aCategoryCC = new CodeableConcept();
        List<CodeableConcept> aCategoryCCList = new ArrayList<>();
        CodingSetup aCodingSetup = new CodingSetup();
        aCodingSetup.addCoding("http://terminology.hl7.org/CodeSystem/observation-category", "laboratory", "Laboratory");
        aCategoryCC.setCoding(aCodingSetup.getMyCodingList());
        aCategoryCC.setText("Laboratory");
        aCategoryCCList.add(aCategoryCC);
        aObs.setCategory(aCategoryCCList);

        // Adding Loinc Coding to Observation
        CodingSetup aLoincCode = new CodingSetup();
        aLoincCode.addCoding("http://loinc.org", theLoincCode, theLoincDesc);
        CodeableConcept aLoincCC = new CodeableConcept();
        aLoincCC.setCoding(aLoincCode.getMyCodingList());
        aLoincCC.setText(theLoincDesc);
        aObs.setCode(aLoincCC);

        // Set Subject Reference using input DomainResource (Patient for now)
        Reference aSubjRef = new Reference();
        aSubjRef.setReference(thePat.getIdElement().getValue());
        aObs.setSubject(aSubjRef);

        // Adding Effective Date and Issued Date to Observation
        DateTimeType aTypeDate = new DateTimeType(new Date());
        aObs.setEffective(aTypeDate);
        aObs.setIssued(aTypeDate.getValue());

        return aObs;
    }

    /**
     * Generate Patient resource using the Patient ID and FamilyName provided in the API Query parameter
     * @return
     */
    public DomainResource generatePatient()
    {
        Patient aPatient = new Patient();
        aPatient.setId(FhirGuid.genereateUrn());
        Identifier aPatientIdentifer = new Identifier();
        aPatientIdentifer.setSystem("http://hospital.smarthealthit.org");
        aPatientIdentifer.setValue(myPatientId);
        aPatient.addIdentifier(aPatientIdentifer);
        HumanName aHumanName = new HumanName();
        List<HumanName> aListHN = new ArrayList<>();
        aHumanName.setFamily(myPatientName);
        List<StringType> theGiven = new ArrayList<>();
        StringType aST = new StringType();
        aST.setValueAsString("Test");
        theGiven.add(aST);
        aHumanName.setGiven(theGiven);
        aListHN.add(aHumanName);
        aPatient.setName(aListHN);
        aPatient.setGender(Enumerations.AdministrativeGender.MALE);
        return aPatient;
    }
}
