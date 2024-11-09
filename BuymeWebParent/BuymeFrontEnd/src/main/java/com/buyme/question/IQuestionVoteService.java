package com.buyme.question;

import java.util.List;

import com.buyme.common.entity.Customer;
import com.buyme.common.entity.VoteResultDTO;
import com.buyme.common.entity.VoteType;
import com.buyme.common.entity.question.Question;
import com.buyme.common.entity.question.QuestionVote;

public interface IQuestionVoteService {

    public VoteResultDTO doVote(Integer questionId, Customer customer, VoteType voteType);
    public VoteResultDTO undoVote(QuestionVote vote, Integer questionId, VoteType voteType);
    public void markQuestionsVotedForProductByCustomer(List<Question> listQuestions,
                                                       Integer productId, Integer customerId);


}