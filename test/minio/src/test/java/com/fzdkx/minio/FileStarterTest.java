package com.fzdkx.minio;

import com.fzdkx.file.service.FileStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author 发着呆看星
 * @create 2024/2/5
 */
@SpringBootTest(classes = MinIoApplication.class)
@RunWith(SpringRunner.class)
public class FileStarterTest {
    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testUpdateImgFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\发着呆看星\\Pictures\\Camera Roll\\武.webp");
            String filePath = fileStorageService.uploadImgFile("", "武.webp", fileInputStream);
            System.out.println(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
