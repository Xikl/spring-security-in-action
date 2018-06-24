package com.ximo.spring.security.sdk.domain;

import com.ximo.spring.security.sdk.annotation.NameNotRepeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Collection;
import java.util.Date;

/**
 * @author 朱文赵
 * @date 2018/6/23
 * @description 该类实现了UserDetails接口
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@NameNotRepeat(message = "用户名不能重复")
public class User implements UserDetails {

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

    /** 用户权限 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
    }

    /** 是否过期 */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** 是否被锁定 */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** 密码是否过期 */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** 是否可用 一般是被删除的用户 */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
