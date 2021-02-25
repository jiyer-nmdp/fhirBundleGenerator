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
import io.swagger.api.hml.BundleConverterApi;
import org.json.JSONObject;
import org.nmdp.converter.ParseInputBundle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/bundleConverter")
@CrossOrigin
public class BundleConvertController implements BundleConverterApi {

    @ApiOperation(value = "", nickname = "bundleConverter", notes = "", response = String.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return the json bundle", response = String.class),
            @ApiResponse(code = 500, message = "Error / Exception") })
    @RequestMapping(value = "/convert",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<String> bundleConverter(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String searchSet) {
        try {
            ParseInputBundle aPIB = new ParseInputBundle();
            aPIB.setMyInputBundle(new JSONObject(searchSet));
            aPIB.parseInput();
            return new ResponseEntity<>(aPIB.getMyFhirOutput() , HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Errors" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
