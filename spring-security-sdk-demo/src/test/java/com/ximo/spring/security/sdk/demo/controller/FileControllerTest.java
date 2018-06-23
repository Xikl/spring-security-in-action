package com.ximo.spring.security.sdk.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author 朱文赵
 * @date 2018/6/24
 * @description
 */
public class FileControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenUploadFileSuccess() throws Exception {
        mockMvc.perform(multipart("/file")
                .file(new MockMultipartFile("file", "test.txt",
                        MediaType.MULTIPART_FORM_DATA_VALUE, "test content".getBytes("utf-8"))))
                .andExpect(status().isOk());
    }
}