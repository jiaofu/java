package com.shuiliu.blockchain.first;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("api/v1")
public class FirstController {
    @GetMapping("time")
    public long time(){
        return System.currentTimeMillis();
    }
}
