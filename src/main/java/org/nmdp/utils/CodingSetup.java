
package org.nmdp.utils;

import org.hl7.fhir.dstu3.model.Coding;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to generate Coding Resource
 */
public class CodingSetup {

    private List<Coding> myCodingList;

    public List<Coding> getMyCodingList() {
        return myCodingList;
    }

    public void setMyCodingList(List<Coding> myCodingList) {
        this.myCodingList = myCodingList;
    }

    public CodingSetup()
    {
        myCodingList = new ArrayList<>();
    }

    public void addCoding(String theSystem, String theCode, String theDisplay)
    {
        Coding aCoding = new Coding();
        aCoding.setSystem(theSystem);
        aCoding.setCode(theCode);
        aCoding.setDisplay(theDisplay);
        myCodingList.add(aCoding);
    }

    public void addCoding(String theSystem, String theCode, String theVersion, String theDisplay)
    {
        Coding aCoding = new Coding();
        aCoding.setSystem(theSystem);
        aCoding.setVersion(theVersion);
        aCoding.setCode(theCode);
        aCoding.setDisplay(theDisplay);
        myCodingList.add(aCoding);
    }
}
