package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.QuestionCountsDao;
import com.kenzie.capstone.service.model.QuestionCountsRecord;
import com.kenzie.capstone.service.model.QuestionCountsRequest;
import com.kenzie.capstone.service.model.QuestionCountsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionServiceTest {

    private QuestionCountsDao questionCountsDao;

    private CheckQuestionCountsService checkQuestionCountsService;

    @BeforeAll
    void setup(){
        this.questionCountsDao = mock(QuestionCountsDao.class);
        this.checkQuestionCountsService = new CheckQuestionCountsService(questionCountsDao);

    }

    @Test
    void addQuestionTest(){
        ArgumentCaptor<QuestionCountsRecord> questionCountsRecordArgumentCaptor = ArgumentCaptor.forClass(QuestionCountsRecord.class);

        //GIVEN

        QuestionCountsRequest questionCountsRequest = new QuestionCountsRequest();
        questionCountsRequest.setQuestionId(1);

        QuestionCountsRecord questionCountsRecord = new QuestionCountsRecord();
        questionCountsRecord.setQuestionId(questionCountsRequest.getQuestionId());


        when(questionCountsDao.getQuestionCounts(questionCountsRequest.getQuestionId())).thenReturn(Arrays.asList(questionCountsRecord) );

        //WHEN

        QuestionCountsResponse response = this.checkQuestionCountsService.addQuestion(questionCountsRequest);

        //THEN
        verify(questionCountsDao, times(1)).storeQuestionCountsData(questionCountsRecordArgumentCaptor.capture());



        assertNotNull(response, "A response is returned");
        assertEquals(questionCountsRequest.getQuestionId(), response.getQuestionId(), "The response id should match");


    }

}
