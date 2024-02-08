package com.fzdkx.ailiyun;

import com.fzdkx.common.aliyun.Scan;
import com.fzdkx.media.MediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author 发着呆看星
 * @create 2024/2/7
 */
@SpringBootTest(classes = MediaApplication.class)
@RunWith(SpringRunner.class)
public class ScanTest {
    @Autowired
    private Scan scan;

    @Test
    public void scanTextTest() throws Exception {
        scan.scanText("111");
    }

    @Test
    public void scanImageTest() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("http://192.168.16.166:9000/leadnews/2024/02/07/ed8c88867aed468eae8be847e5a03321.jpg");
        scan.scanImage(list);
    }
}
