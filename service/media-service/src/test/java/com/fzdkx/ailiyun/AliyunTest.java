package com.fzdkx.ailiyun;

import com.fzdkx.common.aliyun.GreenImageScan;
import com.fzdkx.common.aliyun.GreenTextScan;
import com.fzdkx.file.service.FileStorageService;
import com.fzdkx.media.MediaApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest(classes = MediaApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {

    @Autowired
    private GreenTextScan greenTextScan;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testScanText() throws Exception {
        Map<String,String> map = greenTextScan.greeTextScan("我是一个好人,冰毒");
        System.out.println(map);
    }

    @Test
    public void testScanImage() throws Exception {
        byte[] bytes = fileStorageService.downLoadFile("http://192.168.16.166:9000/leadnews/2024/02/07/ed8c88867aed468eae8be847e5a03321.jpg");
        Map<String,String> map = greenImageScan.imageScan(Arrays.asList(bytes));
        System.out.println(map);
    }
}