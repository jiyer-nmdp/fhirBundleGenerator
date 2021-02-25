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
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.dstu3.model.*;
import org.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the input bundle and manages generation of transaction bundle
 */
public class ParseInputBundle
{
    /**
     * Input JSONObject bundle
     */
    private JSONObject myInputBundle;

    /**
     * String format of Fhir bundle
     */
    private String myFhirOutput;

    public String getMyFhirOutput() {
        return myFhirOutput;
    }

    public void setMyFhirOutput(String myFhirOutput) {
        this.myFhirOutput = myFhirOutput;
    }

    public JSONObject getMyInputBundle() {
        return myInputBundle;
    }

    public void setMyInputBundle(JSONObject myInputBundle) {
        this.myInputBundle = myInputBundle;
    }

    /**
     * Parse input bundle
     */
    public void parseInput() {
        JSONArray aEntries = myInputBundle.getJSONArray("entry");
        extractResources(aEntries);
    }

    /**
     * Extract Resources from input bundle entries
     * @param theEntries
     */
    public void extractResources(JSONArray theEntries)
    {
        List<DomainResource> aDomainResources = new ArrayList<>();
        FhirContext ctx = FhirContext.forDstu3();
        IParser aParser = ctx.newJsonParser();
        BundleGenerator aBG = new BundleGenerator();

        for (int index = 0; index < theEntries.length(); index++)
        {
            JSONObject aEntry = theEntries.getJSONObject(index);
            JSONObject aResource = (aEntry.keySet().contains("resource")) ? aEntry.getJSONObject("resource") : null;
            DomainResource aDR = getFhirResource(aResource != null ? aResource : aEntry, aParser);
            aDomainResources.add(aDR);
        }

        aBG.generateFhirBundle(aDomainResources);
        setMyFhirOutput(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(aBG.getMyBundleResource().getMyFhirBundle()));
    }

    /**
     * Get FHIR DomainResource for each resource type
     * @param theResource
     * @param theParser
     * @return
     */
    private DomainResource getFhirResource(JSONObject theResource, IParser theParser)
    {
        switch(theResource.getString("resourceType"))
        {
            case "Observation" :
                return theParser.parseResource(Observation.class, theResource.toString());
            case "Patient" :
                return theParser.parseResource(Patient.class, theResource.toString());
            case "Provenance" :
                return theParser.parseResource(Provenance.class, theResource.toString());
            case "Device" :
                return theParser.parseResource(Device.class, theResource.toString());
            case "ValueSet":
                return theParser.parseResource(ValueSet.class, theResource.toString());
            case "CodeSystem":
                return theParser.parseResource(CodeSystem.class, theResource.toString());
            default:
                return theParser.parseResource(DomainResource.class, theResource.toString());
        }
    }
}
