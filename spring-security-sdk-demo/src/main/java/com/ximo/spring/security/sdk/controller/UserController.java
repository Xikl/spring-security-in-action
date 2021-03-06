package com.ximo.spring.security.sdk.controller;

import com.ximo.spring.security.sdk.core.util.ConstraintUtils;
import com.ximo.spring.security.sdk.core.util.ResultUtils;
import com.ximo.spring.security.sdk.core.vo.ResultVO;
import com.ximo.spring.security.sdk.domain.User;
import com.ximo.spring.security.sdk.dto.UserQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/6/22
 * @description 用户controller
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @PostMapping("/register")
    public ResultVO register(User user, HttpServletRequest request) {
        //获得用户唯一标示
        String username = user.getUsername();
        //将该数据一起放到spring social中 和 openId一起保存进去
        providerSignInUtils.doPostSignUp(username, new ServletWebRequest(request));
        return ResultVO.success();

    }


    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @GetMapping("/me")
    public ResultVO getCurrentUser(Authentication authentication) {
        return ResultVO.success(authentication.getPrincipal());

    }

    @GetMapping("/me-2")
    public ResultVO getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return ResultVO.success(user);

    }

    @GetMapping("/{userId:\\d+}")
    public ResultVO<User> findOne(@PathVariable("userId") Integer userId) {
        System.out.println(userId);
        return ResultVO.success(new User(1, "test_username", "12345", new Date()));
    }

    @GetMapping
    public ResultVO<List<User>> findAll() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        return ResultVO.success(userList);
    }

    @PostMapping("/list")
    public ResultVO<List<User>> findByCondition(UserQueryCondition condition,
                                                @PageableDefault(page = 1, size = 10, sort = "username,asc") Pageable pageable) {
        System.out.println(condition);

        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());
        return ResultVO.success(new ArrayList<>());
    }

    @PostMapping()
    public ResultVO<Map<String, Integer>> createUser(@Validated @RequestBody User user, BindingResult errors) {
        ConstraintUtils.orElseThrow(errors);
        System.out.println(user);
        return ResultVO.success(ResultUtils.one("userId", 2));
    }

    @PutMapping()
    public ResultVO<Map<String, Integer>> updateUser(@Validated @RequestBody User user, BindingResult errors) {
        ConstraintUtils.orElseThrow(errors);
        System.out.println(user);
        return ResultVO.success(ResultUtils.one("userId", 2));
    }


    @DeleteMapping("/{userId:\\d+}")
    public ResultVO deleteUser(@PathVariable("userId") String userId) {
        System.out.println(userId);
        return ResultVO.success();
    }

}
