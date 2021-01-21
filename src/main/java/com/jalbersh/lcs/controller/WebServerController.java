package com.jalbersh.lcs.controller;

import com.jalbersh.lcs.model.LCSRequest;
import com.jalbersh.lcs.model.LCSResponse;
import com.jalbersh.lcs.service.LCSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * WebServerController handles the incoming requests from the Web Server
 */
@Api("Rest API for LCS operations")
@Controller
@CrossOrigin
public class WebServerController {
    private static final Logger logger = LoggerFactory.getLogger(WebServerController.class);

    private LCSService lcsService;

    @Autowired
    public WebServerController(LCSService orderService) {
        this.lcsService = orderService;
    }

//    POST /lcs

    // Web client command to make official requests to an instance's application
    @ApiOperation(value = "Description: returns LCS", response = ResponseEntity.class)
    @RequestMapping(value="/lcs", method = RequestMethod.POST)
    public ResponseEntity<LCSResponse> getLCS(@RequestBody LCSRequest request) {
        logger.info("POST getLCS");
        LCSResponse result = null;
        ResponseEntity<LCSResponse> response = null;
        try {
            result = lcsService.process(request);
            response = ResponseEntity.ok().body(result);
        } catch (Exception e) {
            result = new LCSResponse();
            result.getValues().add("Input must be a set of unique strings");
            response = ResponseEntity.badRequest().body(result);
        }
        return response;
    }

}