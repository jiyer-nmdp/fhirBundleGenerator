package org.nmdp.utils;

import org.hl7.fhir.dstu3.model.IdType;

/**
 * Auto generte a FHIR UUID
 */
public class FhirGuid {
    public static IdType genereateUrn() {
        return IdType.newRandomUuid();
    }
}