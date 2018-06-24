package com.ximo.spring.security.sdk.dto;

import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description 用户查询条件
 */
@Data
public class UserQueryCondition {

    /** 用户名 */
    private String username;

    /** 年龄 */
    private Integer age;

    /** 年龄范围 */
    private Integer ageTo;

    /** 任意值 */
    private String xxx;


}
