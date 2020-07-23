package wm.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtiles {


    public static String readFileStr(String path){
       StringBuffer stringBuffer  = new StringBuffer();

        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + line + ": " + tempString);
                stringBuffer.append(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return stringBuffer.toString();
    }

    public static List<String> readFile(String path){
        List<String> list = new ArrayList<>();

        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + line + ": " + tempString);
                list.add(tempString);
                    line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

            return list;
    }


    public static void appendStr(String fileName,String content) {
        FileWriter writer = null;

        try {

            File wf = new File(fileName);
            if (!wf.exists()){
                wf.createNewFile();
            }

            String fileCont = readFileStr(fileName);
            if (fileCont.contains(content)) return;



            writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
