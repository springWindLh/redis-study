package com.app.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Random;

/**
 * Created by lh on 2017/3/24.
 */
@Controller
@RequestMapping("/code")
public class VerifyCodeController {

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String toCodePage(Model model) {
        String str = "0123456789";
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(9);
            stringBuffer.append(str.charAt(index));
        }
        String codeStr = stringBuffer.toString();
        model.addAttribute("code", codeStr);
        return "verifyCode";
    }
}
