package org.nmdp.converter;

import org.hl7.fhir.r4.model.DomainResource;
import org.nmdp.models.BundleResource;

import java.util.List;
import java.util.Objects;

public class BundleGenerator {

    private BundleResource myBundleResource;

    public BundleResource getMyBundleResource() {
        return myBundleResource;
    }

    public BundleGenerator() {
        myBundleResource = new BundleResource();
    }

    public void generateFhirBundle(List<DomainResource> theResources)
    {
        theResources.stream().filter(Objects::nonNull)
                .forEach(theResource -> myBundleResource.addResource(theResource));
    }
}