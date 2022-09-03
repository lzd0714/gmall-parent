package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-09-02 20:36
 **/
@RestController
public class HelloController {
    @GetMapping("/haha")
    public Result haha(){
        return Result.ok();
    }
}
