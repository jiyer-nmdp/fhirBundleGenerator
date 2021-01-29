# fhirBundleGenerator
- This is a Standalone API for generating Fhir transaction bundle from other fhir bundle or collection of fhir resources

##DevOps
   - Clone the repository locally
   - Run 'mvn clean install'
   - Start the API by running 'java -jar fhirBundleGenerator-1.0-SNAPSHOT.jar'
   - Api will start on port 8090 by default
    
##Testing
- Post a json fhir bundle to http://localhost:8090/bundleConverter/convert
    - The input should be a JSON object (not a JSON array)
    - Alternately, a collection of "entry" elements can also be submitted as input without a Bundle resource 
- The response will be a fhir transaction bundle.
- Validate using fhir validator as needed