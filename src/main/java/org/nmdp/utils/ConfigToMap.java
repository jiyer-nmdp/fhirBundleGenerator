package org.nmdp.utils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by jiyer
 * This class will parse a config file / data-definition / dictionary file into key-value pairs in a map and return the Data-structure for use in the app
 */

public class ConfigToMap
{
    private Map<String, String> myDataDefinitions;
    private File myConfigFile;

    public ConfigToMap(File theConfigFile)
    {
        myDataDefinitions = new LinkedHashMap<>();
        myConfigFile = theConfigFile;
    }

    public Map<String, String> readFiletoMap()
    {
        try
        {
            Scanner aScanner = new Scanner(myConfigFile);
            while (aScanner.hasNext())
            {
                String aLine = aScanner.nextLine();
                String aKey = aLine.substring(0, aLine.indexOf("="));
                String aValue = aLine.substring(aLine.indexOf("=") + 1);
                myDataDefinitions.put(aKey,aValue);
            }
        } catch (Exception e)
        {}
        return myDataDefinitions;
    }
}
