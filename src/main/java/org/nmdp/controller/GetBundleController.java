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
    public ResponseEntity<String> getBundle(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "patientId", required = true) Object patientId,@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "familyName", required = true) Object familyName,@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "numReps", required = true) Object numReps) {
        try {
            GenerateLoincBundle aGLB = new GenerateLoincBundle();
            aGLB.setMyPatientId((String)patientId);
            aGLB.setMyPatientName((String)familyName);
            aGLB.setMyNumReps(Integer.parseInt((String)numReps));
            aGLB.createFHIR();
            return new ResponseEntity<>(aGLB.getMyFhirOutput(), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Couldn't serialize response for content type application/json", e);
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
