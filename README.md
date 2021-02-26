# fhirBundleGenerator

### Introduction
- This is a Standalone set of APIs for generating Fhir transaction bundle (STU3) from other fhir bundle or collection of fhir resources
   - /getBundle - a GET Api
        -   query parameters - patientId, familyName and numReps
        -   patientId becomes part of the Patient resource Identifier and will be used to check for duplicate Patient resources
        -   familyName is put in as the Patient's Family_Name
        -   numReps determines the total number of duplicate Observations to be created per Loinc Code
        -   The given name is set to "Test" by default in the code
   - /bundleConverter/convert 
        -   This is a POST API
        -   Accepts a JSON object (not JSON array) as input - can be a Bundle Resource or a collection of Resources
        -   Generates a FHIR transaction Bundle connecting the collection of resources
### DevOps
   - Clone the repository locally
   - Run 'mvn clean install'
   - Start the API by running 'java -jar fhirBundleGenerator-1.0-SNAPSHOT.jar'
   - Api will start on port 8090 by default
    
### Testing 
- Only STU3 is supported right now. Support for other versions will be added soon

- The LOINC codes and the respective Names are in a map-file 'loinc_desc_map.txt' in the "resources" folder
    -   Simply add / update the LOINC codes and the Name for generating different ones
    -   Please use the same structure when modifying and be careful not to add any unnecessary "new-line" or "white-space" characters.

- POST API
    -   Post a json fhir bundle to http://localhost:8090/bundleConverter/convert
    -   The input should be a JSON object (not a JSON array)
    -   Alternately, a collection of "entry" elements can also be submitted as input without a Bundle resource 
    -   The response will be a fhir transaction bundle.


- GET API
    -   Test using /getBundle?patientId=231678&patientName=Subject_2 - this is a sample API call
    -   Notice the generated Bundle and the LOINC observation resources

- Validate using FHIR validator