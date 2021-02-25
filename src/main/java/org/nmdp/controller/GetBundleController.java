package org.nmdp.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.api.hml.GetBundleApi;
import org.nmdp.converter.GenerateLoincBundle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/getBundle")
@CrossOrigin
public class GetBundleController implements GetBundleApi {

    @ApiOperation(value = "", nickname = "getBundle", notes = "", response = String.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return Fhir bundle", response = String.class),
            @ApiResponse(code = 500, message = "Error / Exception") })
    @RequestMapping(value= "",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<String> getBundle(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "patientId", required = true) String patientId, @NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "patientName", required = true) String patientName) {
        try {

            GenerateLoincBundle aGLB = new GenerateLoincBundle();
            aGLB.setMyPatientId(patientId);
            aGLB.setMyPatientName(patientName);
            aGLB.createFHIR();
            return new ResponseEntity<>(aGLB.getMyFhirOutput(), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
