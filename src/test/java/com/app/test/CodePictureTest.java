package com.app.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by lh on 2017/3/24.
 */
public class CodePictureTest {
    public static Random random = new Random();

    public static int randomNumber(int min, int max) {
        int num = 0;
        num = random.nextInt(max - min) + min;
        return num;
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        //在内存中创建一副图片
        int w = 120;
        int h = 50;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //在图片上画一个矩形当背景
        Graphics graphics = img.getGraphics();
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(new Color(244, 244, 244));
        g.fillRect(0, 0, w, h);

        String str = "0123456789";
        for (int i = 0; i < 4; i++) {
            g.setColor(new Color(134, 208, 171));
            int randomHeight = randomNumber(h - 20, h);
//            g.rotate(Math.toRadians(10), w/2, h/2);
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            char c = str.charAt(randomNumber(0, str.length()));
            g.drawString(String.valueOf(c), 10 + i * 30, randomHeight);
        }

        //画随机线
        for (int i = 0; i < 15; i++) {
            g.setColor(new Color(randomNumber(50, 180), randomNumber(50, 180), randomNumber(50, 180)));
            g.drawLine(randomNumber(0, w), randomNumber(0, h), randomNumber(0, w), randomNumber(0, h));
        }
        //把内存中创建的图像输出到文件中
        File file = new File("vcode.png");
        ImageIO.write(img, "png", file);
        System.out.println("图片输出完成");

    }
}
