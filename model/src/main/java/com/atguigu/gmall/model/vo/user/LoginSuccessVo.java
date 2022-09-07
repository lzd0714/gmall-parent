package com.atguigu.gmall.model.vo.user;

import lombok.Data;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-06 21:27
 **/
@Data
public class LoginSuccessVo {
    private String token; //用户的令牌。
    private String nickName; //用户
}
