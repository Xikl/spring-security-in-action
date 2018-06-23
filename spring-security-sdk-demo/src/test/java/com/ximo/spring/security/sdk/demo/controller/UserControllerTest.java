package com.ximo.spring.security.sdk.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenQueryAllUserSuccess() throws Exception {
        String resultString = this.mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultString);
    }

    @Test
    public void whenQueryOneUserSuccess() throws Exception {
        String resultString = this.mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("test_username"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultString);
    }

    @Test
    public void whenQueryOneUserFail() throws Exception {
        this.mockMvc.perform(get("/user/aa"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenQueryUserByConditionSuccess() throws Exception {
        this.mockMvc.perform(post("/user")
                .param("username", "zzz")
                .param("age", "18")
                .param("ageTo", "22")
                .param("xxx", "xxx")
                .param("size", "12")
                .param("page", "1")
                .param("sort", "username,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    public void whenCreateUserSuccess() throws Exception {
        long birthday = new Date().getTime();
        //language=JSON
        String content = "{\n" +
                "  \"username\":\"zwz\",\n" +
                "  \"password\":\"1234\",\n" +
                "  \"birthday\":"+ birthday+"\n" +
                "}";
        this.mockMvc
                .perform(post("/user")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").isNotEmpty());

    }

    @Test
    public void whenUpdateUserSuccess() throws Exception {
        long birthday = LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        //language=JSON
        String content = "{\n" +
                "  \"userId\":1,\n" +
                "  \"username\":\"zwz\",\n" +
                "  \"password\":\"1234\",\n" +
                "  \"birthday\":" + birthday + "\n" +
                "}";
        this.mockMvc
                .perform(put("/user")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").isNotEmpty());

    }

    @Test
    public void whenDeleteUserSuccess() throws Exception {
        this.mockMvc
                .perform(put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }


}