package com.ximo.spring.security.sdk.domain;

import com.ximo.spring.security.sdk.annotation.NameNotRepeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@NameNotRepeat(message = "用户名不能重复")
public class User {

    /** 用户ID */
    private Integer userId;

    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 密码 */
    private String password;

    /** 生日 */
    @Past(message = "生日必须是过去的时间")
    private Date birthday;


}
