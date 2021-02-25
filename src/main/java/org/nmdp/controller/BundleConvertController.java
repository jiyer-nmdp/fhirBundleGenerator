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

    @ApiOperation(value = "", nickname = "searchset2transaction", notes = "", response = String.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return the json bundle", response = String.class),
            @ApiResponse(code = 500, message = "ErrorException") })
    @RequestMapping(value = "/convert",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<String> searchset2transaction(@ApiParam(value = "" ,required=true )  @Valid @RequestBody String searchSet) {
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
