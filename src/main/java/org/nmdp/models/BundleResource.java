package org.nmdp.models;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DomainResource;

public class BundleResource {
    /**
     * Fhir bundle object
     */
    private Bundle myFhirBundle;

    public Bundle getMyFhirBundle() {
        return myFhirBundle;
    }

    public void setMyFhirBundle(Bundle myFhirBundle) {
        this.myFhirBundle = myFhirBundle;
    }

    public BundleResource() {
        this.myFhirBundle = new Bundle();
        this.myFhirBundle.setType(Bundle.BundleType.TRANSACTION);
    }

    /**
     * Add resource to the Fhir bundle and assign necessary bundle items
     * @param theResource
     */
    public void addResource(DomainResource theResource) {
        String theFullUrl = theResource.getIdElement().getValue();
        theResource.getIdElement().setValue(null);
        myFhirBundle.addEntry()
                .setFullUrl(theFullUrl)
                .setResource(theResource)
                .getRequest()
                .setUrl(theResource.getResourceType().toString())
                .setMethod(Bundle.HTTPVerb.POST);
    }
}
