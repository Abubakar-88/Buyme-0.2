package com.buyme.question;


import javax.servlet.http.HttpServletRequest;

import com.buyme.ControllerHelper;
import com.buyme.common.entity.VoteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyme.common.entity.Customer;
import com.buyme.common.entity.VoteResultDTO;

@RestController
public class QuestionVoteRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionVoteRestController.class);

    private ControllerHelper controllerHelper;

    private QuestionVoteService service;

    public QuestionVoteRestController(ControllerHelper controllerHelper,
                                      QuestionVoteService service) {
        super();
        this.controllerHelper = controllerHelper;
        this.service = service;
    }

    @PostMapping("/vote_question/{id}/{type}")
    public VoteResultDTO voteQuestion(@PathVariable(name = "id") Integer questionId,
                                      @PathVariable(name = "type") String type, HttpServletRequest request) {

        LOGGER.info("QuestionController | voteQuestion is called");

        Customer customer = controllerHelper.getAuthenticatedCustomer(request);

        LOGGER.info("QuestionController | voteQuestion | customer : " + customer.getFullName());

        if (customer == null) {
            return VoteResultDTO.fail("You must login to vote the question.");
        }

        VoteType voteType = VoteType.valueOf(type.toUpperCase());

        return service.doVote(questionId, customer, voteType);
    }

}

