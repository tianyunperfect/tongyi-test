package org.example.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件:
 * 创建
 * 是否存在
 * 读取字符串
 * 读取全部行
 * 按行读取
 * 删除
 * <p>
 * 文件夹:
 * 是否为空
 * 文件列表
 * 删除文件夹
 *
 * @author mlamp
 * @date 2022/05/21
 */
public class FileUtil {
    public static final String DefaultEncode = "UTF-8";

    public static String readToString(String filePath, String encode) throws UnsupportedEncodingException {
        File file = new File(filePath);
        Long fileLength = new File(filePath).length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(fileContent, encode);
    }

    public static String readToString(String filePath) throws UnsupportedEncodingException {
        return readToString(filePath, DefaultEncode);
    }

    public static List<String> readToLines(String filePath, String encode) throws UnsupportedEncodingException {
        String[] strArr = readToString(filePath, encode).split("\\n");
        ArrayList<String> strings = new ArrayList<>(strArr.length);
        Collections.addAll(strings, strArr);
        return strings;
    }

    public static List<String> readToLines(String filePath) throws UnsupportedEncodingException {
        return readToLines(filePath, DefaultEncode);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //System.out.println(FileUtil.readToString("pom.xml"));
        System.out.println(readToLines("pom.xml").size());
    }


}
