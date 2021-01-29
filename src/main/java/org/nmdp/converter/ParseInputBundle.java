package org.nmdp.converter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.*;
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
        FhirContext ctx = FhirContext.forR4();
        IParser aParser = ctx.newJsonParser();
        BundleGenerator aBG = new BundleGenerator();

        for (int index = 0; index < theEntries.length(); index++)
        {
            JSONObject aEntry = theEntries.getJSONObject(index);
            JSONObject aResource = aEntry.getJSONObject("resource");
            aDomainResources.add(getFhirResource(aResource, aParser));
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
            default :
                return null;
        }
    }
}
