package com.fzdkx.common.tess4j;

import lombok.Data;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.awt.image.BufferedImage;

/**
 * @author 发着呆看星
 * @create 2024/2/18
 */
@Data
@ConfigurationProperties(prefix = "tess4j")
public class Tess4jClient {
    // 字体库路径
    private String dataPath;
    // 字体
    private String language;

    public String doOCR(BufferedImage image) throws TesseractException {
        // 创建 Tesseract 对象
        Tesseract tesseract = new Tesseract();
        // 设置字体库路径
        tesseract.setDatapath(dataPath);
        // 设置识别字体
        tesseract.setLanguage(language);
        // 只需OCR识别
        String result = tesseract.doOCR(image);
        //替换回车和tal键  使结果为一行
        result = result.replaceAll("\\r|\\n", "-").replaceAll(" ", "");
        return result;
    }
}
