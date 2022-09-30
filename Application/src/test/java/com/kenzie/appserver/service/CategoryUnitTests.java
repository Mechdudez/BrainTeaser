package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CategoryRepository;
import com.kenzie.appserver.repositories.model.CategoryRecord;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.capstone.service.client.CheckQuestionCountsServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CategoryUnitTests {
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private CheckQuestionCountsServiceClient client;

    @BeforeEach
    void setup() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository, client);
    }
    /** ------------------------------------------------------------------------
     *  categoryService.getQuestionById
     *  ------------------------------------------------------------------------ **/

    @Test
    void getQuestionById() {
        // GIVEN
        Integer questionId = 1;

        CategoryRecord record = new CategoryRecord();
        record.setQuestionId(questionId);
        record.setQuestions("How many days are in a week?");
        record.setAnswers("7");
        record.setDifficultyOfAQuestion("easy");
        when(categoryRepository.findById(questionId)).thenReturn(Optional.of(record));
        // WHEN
        Category category = categoryService.getQuestionById(questionId);

        // THEN
        Assertions.assertNotNull(category, "The category is returned");
        Assertions.assertEquals(record.getQuestionId(), category.getQuestionId(), "The question id matches");
        Assertions.assertEquals(record.getQuestions(), category.getQuestions(), "The question matches");
        Assertions.assertEquals(record.getDifficultyOfAQuestion(), category.getDifficultyOfAQuestion(), "Difficulty matches");
        Assertions.assertEquals(record.getAnswers(), category.getAnswers(), "The answer matches");
    }

    /** ------------------------------------------------------------------------
     *  categoryService.createOneQuestion
     *  ------------------------------------------------------------------------ **/


    @Test
    void createOneQuestion() {
        // GIVEN
        Integer questionId = 1;
        Category newQuestion = new Category(questionId, "How many days are in a week?", "easy", "7");
        ArgumentCaptor<CategoryRecord> categoryRecordCaptor = ArgumentCaptor.forClass(CategoryRecord.class);

        // WHEN
        CategoryRecord categoryRecord = categoryService.createOneQuestion(newQuestion);

        // THEN
        Assertions.assertNotNull(categoryRecord);
        verify(categoryRepository).save(categoryRecordCaptor.capture());
        CategoryRecord record = categoryRecordCaptor.getValue();

        Assertions.assertNotNull(record, "The category record is returned");
        Assertions.assertEquals(record.getQuestionId(), newQuestion.getQuestionId(), "The question id matches");
        Assertions.assertEquals(record.getQuestions(), newQuestion.getQuestions(), "The question matches");

        //Do I need to check for this?
        Assertions.assertEquals(record.getDifficultyOfAQuestion(), newQuestion.getDifficultyOfAQuestion(), "The difficulty matches");
        Assertions.assertEquals(record.getAnswers(), newQuestion.getAnswers(), "The answers matches");
    }

    /** ------------------------------------------------------------------------
     *  categoryService.getAllQuestions
     *  ------------------------------------------------------------------------ **/

    @Test
    void getAllQuestions_returnsMultipleQuestions() {
        // GIVEN
        CategoryRecord record1 = new CategoryRecord();
        record1.setQuestionId(1);
        record1.setQuestions("How many days are in a week?");
        record1.setDifficultyOfAQuestion("easy");
        record1.setAnswers("7");

        CategoryRecord record2 = new CategoryRecord();
        record2.setQuestionId(2);
        record2.setQuestions("How many weeks are in a year?");
        record2.setDifficultyOfAQuestion("easy");
        record2.setAnswers("52");


        List<CategoryRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);

        // WHEN
        when(categoryRepository.findAll()).thenReturn(recordList);

        // THEN
        Assertions.assertNotNull(recordList, "All questions are returned");
        Assertions.assertEquals(2, recordList.size(), "There are two questions in the list");

        for (CategoryRecord question : recordList) {
            if (question.getQuestionId() == record1.getQuestionId()) {
                Assertions.assertEquals(record1.getQuestionId(), question.getQuestionId(), "The question id matches");
                Assertions.assertEquals(record1.getQuestions(), question.getQuestions(), "The question matches");
                Assertions.assertEquals(record1.getDifficultyOfAQuestion(), question.getDifficultyOfAQuestion(), "The difficulty matches");
                Assertions.assertEquals(record1.getAnswers(), question.getAnswers(), "The answer matches");
            } else if (question.getQuestionId() == record2.getQuestionId()) {
                Assertions.assertEquals(record2.getQuestionId(), question.getQuestionId(), "The question id matches");
                Assertions.assertEquals(record2.getQuestions(), question.getQuestions(), "The question matches");
                Assertions.assertEquals(record2.getDifficultyOfAQuestion(), question.getDifficultyOfAQuestion(), "The difficulty matches");
                Assertions.assertEquals(record2.getAnswers(), question.getAnswers(), "The answer matches");
            } else {
                Assertions.assertTrue(false, "Question returned that was not in the records!");
            }
        }
    }
}
