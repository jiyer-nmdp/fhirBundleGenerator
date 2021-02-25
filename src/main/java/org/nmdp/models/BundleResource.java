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
