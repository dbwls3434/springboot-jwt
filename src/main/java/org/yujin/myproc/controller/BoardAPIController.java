package org.yujin.myproc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardAPIController {

    @GetMapping("/list")
    public @ResponseBody String listPage() {

        return ("잘 되었습니다.");
    }
}
