package com.fzdkx.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 发着呆看星
 * @create 2024/2/18
 * DFA：确定有穷自动机
 */
@Slf4j
public class SensitiveWordUtil {
    /**
     * 敏感词map
     * 一个Key中又两个属性
     * 1）是否为终点
     * 2）下一个字符
     */
    private static Map<String, Object> dictionaryMap;

    /**
     * 初始化敏感词map
     */
    public static void initMap(List<String> words) {
        if (words == null) {
            log.error("敏感词列表不能为空");
            return;
        }
        // 创建一个敏感词库map
        HashMap<String, Object> map = new HashMap<>(words.size());
        HashMap<String, Object> curMap;
        // 取出每一个敏感词，进行存储
        for (String word : words) {
            // 对curMap进行赋值，使其为第一层
            curMap = map;
            // 将敏感词转成字符数组，对每个字符进行遍历
            char[] chars = word.toCharArray();
            for (char c : chars) {
                // 判断当前字符在当前层是否出现
                String str = String.valueOf(c);
                HashMap<String, Object> wordMap = (HashMap<String, Object>) curMap.get(str);
                if (wordMap == null) {
                    // 如果未出现，手动创建下一层
                    wordMap = new HashMap<>();
                    // 当前数据并非最后一个字符，设置为0
                    wordMap.put("isEnd", "0");
                    // 将创建的下一层添加为当前层的子
                    curMap.put(str, wordMap);
                }
                // 替换curMap的引用，使其进入下一层
                curMap = wordMap;
            }
            // 设置敏感词最后一个字符的 isEnd 为 1
            curMap.put("isEnd", "1");
        }
        // 对属性进行覆盖
        dictionaryMap = map;
    }

    /**
     * 判断以 beginIndex索引开头的 字符是否为敏感词
     *  |-- 如果是，那么返回匹配的敏感词长度
     *  |-- 如果不是，那么结束方法，返回0
     * @param text 文本
     * @param beginIndex 开始匹配的下标
     */
    private static int checkWord(String text, int beginIndex) {
        if (dictionaryMap == null){
            throw new RuntimeException("字典不能为空");
        }
        boolean isEnd = false;
        int wordLength = 0;
        Map<String,Object> curMap = dictionaryMap;
        char[] chars = text.toCharArray();
        for (int i = beginIndex; i < chars.length; i++) {
            // 获取一个字符
            String str = String.valueOf(chars[i]);
            // 判断该字符在当前层中是否存在，赋值给curMap
            curMap = (Map<String, Object>) curMap.get(str);
            if (curMap == null) {
                // 如果不存在,代表当前索引开头的字符不是敏感词，结束方法
                break;
            }else {
                // 如果存在,curMap则已经进入下一层
                // 且代表匹配到敏感词的一部分，匹配长度++
                wordLength++;
                // 如果匹配的是结尾词，那么代表文本包含敏感词汇
                if ("1".equals(curMap.get("isEnd"))){
                    isEnd = true;
                }
            }
        }
        // 如果匹配到敏感词的几个字符，未完全匹配，那么不算
        if (!isEnd){
            wordLength = 0;
        }
        return wordLength;
    }

    /**
     * 获取匹配的敏感词和命中的次数
     */
    public static Map<String,Integer> matchWords(String text){
        Map<String, Integer> wordMap = new HashMap<>();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            // 默认对每个字符为开头，进行检测
            int wordLength = checkWord(text, i);
            // 如果该字符开头匹配到敏感词
            if (wordLength > 0){
                // 获取敏感词
                String word = text.substring(i, i + wordLength);
                // 新增敏感词的匹配次数
                if (wordMap.containsKey(word)){
                    // 如果已添加
                    wordMap.put(word,wordMap.get(word) + 1);
                }else {
                    // 如果未添加
                    wordMap.put(word,1);
                }
                // 索引跳过敏感词
                i += wordLength - 1;
            }
        }
        return wordMap;
    }
}
