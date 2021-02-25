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
