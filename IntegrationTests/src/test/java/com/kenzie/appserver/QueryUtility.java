package com.kenzie.appserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.controller.model.CategoryCreateRequest;
import com.kenzie.appserver.service.model.Category;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class QueryUtility {

    public CategoryControllerClient categoryControllerClient;


    private final MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();


    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.categoryControllerClient = new CategoryControllerClient();


    }
    public class CategoryControllerClient {
        public ResultActions createQuestion(CategoryCreateRequest categoryCreateRequest) throws Exception {
            return mvc.perform(post("/category/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(categoryCreateRequest)));
        }
        public ResultActions getRandomQuestion() throws Exception {
            return mvc.perform(get("/category/random")
                    .accept(MediaType.APPLICATION_JSON));
        }
    }


}
