package com.app.demo.controller;

import com.app.demo.utils.VerifyCodeImg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by lh on 2017/3/24.
 */
@Controller
@RequestMapping("/code")
public class VerifyCodeController {

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String toCodePage(Model model) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(str.length());
            stringBuffer.append(str.charAt(index));
        }
        String codeStr = stringBuffer.toString();
        model.addAttribute("code", codeStr);
        return "verifyCode";
    }

    @RequestMapping(value = "/binary/page", method = RequestMethod.GET)
    public void toBinaryCodePage(HttpServletResponse response){
        VerifyCodeImg codeImg = new VerifyCodeImg();
        try {
            response.setHeader("Content-Type","image/jped");
            codeImg.write(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
