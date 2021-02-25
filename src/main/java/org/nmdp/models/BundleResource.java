package org.nmdp.models;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.DomainResource;

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
     * Support added for Patient resouerce generation
     * @param theResource
     */
    public void addResource(DomainResource theResource) {
        String theFullUrl = theResource.getIdElement().getValue();
        theResource.getIdElement().setValue(null);

        switch (theResource.getResourceType()) {
            case Patient:
                myFhirBundle.addEntry()
                        .setFullUrl(theFullUrl)
                        .setResource(theResource)
                        .getRequest()
                        .setUrl(theResource.getResourceType().toString())
                        .setIfNoneExist("identifier=http://hospital.smarthealthit.org|231678")
                        .setMethod(Bundle.HTTPVerb.POST);
                break;
            default:
                myFhirBundle.addEntry()
                        .setFullUrl(theFullUrl)
                        .setResource(theResource)
                        .getRequest()
                        .setUrl(theResource.getResourceType().toString())
                        .setMethod(Bundle.HTTPVerb.POST);
                break;
        }
    }
}
