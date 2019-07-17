package com.brt.bref.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
 
    public static void main(String[] args) {
        //要遍历的路径
        File file = new File("F:\\workspace\\bref");
        //目标文件
        File targetFile = new File("E:\\asb.txt");
        List<File> fileList = new ArrayList<>();
        func(file, fileList);
        System.out.println("文件总数：" + fileList.size() + "条");
        readFileToList(fileList, targetFile);
    }
 
    /**
     * 读操作方法
     */
    public static void readFileToList(List<File> fileList, File targetFile) {
        if (targetFile.exists()) {// 判断文件是否存在
            targetFile.delete();
        }
        try {
            targetFile.createNewFile();// 如果文件不存在创建文件
        } catch (IOException e) {
            System.out.println("创建文件异常!");
            e.printStackTrace();
        }
        int count = 0;
        for (File file : fileList) {
            if (!file.exists()) {// 判断文件是否存在
                System.out.println("文件" + file.getName() + "不存在!");
                continue;
            }
            System.out.println("文件绝对路径 :" + file.getAbsolutePath());
            InputStreamReader inp;
            BufferedReader br;
            FileOutputStream fos;
            PrintStream ps;
            String str;
            try {
                inp = new InputStreamReader(new FileInputStream(file), "UTF-8");//字节流字符流转化的桥梁
                br = new BufferedReader(inp);
 
                fos = new FileOutputStream(targetFile, true);// 文件输出流  追加
                ps = new PrintStream(fos);
 
                //ps.println("======第" + count + "个文件，文件名：《" + file.getName() + "》");
                //ps.println("======文件绝对路径 :" + file.getAbsolutePath());
                ps.println();
                while ((str = br.readLine()) != null) {
                    ps.println(str); // 执行写操作
                }
                count++;
                // 关闭流
                ps.close();
                fos.close();
                br.close();
                inp.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("导入文件总数：" + count + "条");
    }
 
    private static void func(File file, List<File> fileList) {
        File[] fs = file.listFiles();
        if (null == fs || fs.length == 0) {
            return;
        }
        for (File f : fs) {
            if (f.isDirectory()) {//若是目录，则递归打印该目录下的文件
                func(f, fileList);
            } else if (f.isFile()) {//若是文件
                List<String> suffixList = new ArrayList<>();
                suffixList.add("xml");
                suffixList.add("java");
                if (f.getName().lastIndexOf(".") == -1) {
                    continue;
                }
                //文件类型
                String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                if (suffixList.contains(suffix)) {
                    fileList.add(f);
                }
            }
        }
    }
}
