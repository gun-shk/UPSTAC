package org.upgrad.upstac.testrequests;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.User;

import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.flow.TestRequestFlow;
import org.upgrad.upstac.testrequests.flow.TestRequestFlowService;
import org.upgrad.upstac.testrequests.lab.models.CreateLabResult;
import org.upgrad.upstac.testrequests.models.CreateTestRequest;
import org.upgrad.upstac.testrequests.models.RequestStatus;
import org.upgrad.upstac.testrequests.services.TestRequestCreateService;
import org.upgrad.upstac.testrequests.services.TestRequestQueryService;
import org.upgrad.upstac.testrequests.services.TestRequestUpdateService;

import java.util.List;
import java.util.Optional;

import static org.upgrad.upstac.exception.UpgradResponseStatusException.asBadRequest;


@RestController
@RequestMapping("/api/testrequests")
public class TestRequestController {

    Logger log = LoggerFactory.getLogger(TestRequestController.class);


    @Autowired
    private TestRequestService testRequestService;

    @Autowired
    private UserLoggedInService userLoggedInService;

    @Autowired
    private TestRequestQueryService testRequestQueryService;

    @Autowired
    private TestRequestCreateService testRequestCreateService;

    @Autowired
    private TestRequestFlowService testRequestFlowService;



    // Added



//// Added  ends


    @PostMapping("/api/testrequests")
    public TestRequest createRequest(@RequestBody CreateTestRequest testRequest) {
        try {
            User user = userLoggedInService.getLoggedInUser();
            TestRequest result = testRequestService.createTestRequestFrom(user, testRequest);
            return result;
        }  catch (AppException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/api/testrequests")
    public List<TestRequest> requestHistory() {

        User user = userLoggedInService.getLoggedInUser();
        return testRequestService.getHistoryFor(user);

    }

    @GetMapping("/api/testrequests/{id}")
    public Optional<TestRequest> getById(@PathVariable Long id) {

        return testRequestQueryService.getTestRequestById(id);

    }
}
