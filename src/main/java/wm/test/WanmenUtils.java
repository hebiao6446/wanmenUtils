package wm.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

public class WanmenUtils {


    public static final String Zhuomian ="/Users/admin/Desktop/";
    public static final String ZhuomianFile = Zhuomian +"kkk.txt";
    public static final String viewPath = Zhuomian +"vp/";

    public static void main(String[] args) throws Exception{

        File file = new File(ZhuomianFile);
        File file2 = new File(viewPath+"1");
        if (file2.exists()){
            file2.delete();
        }
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        readListJson();

    }

    public static void readTSFile(int tag){

        int  page_tag_flag = 1;

        String tagPath = tag+"/";
        List<String> list = FileUtiles.readFile(ZhuomianFile);

        String fileGetStr =  FileUtiles.readFile(Zhuomian+"file_get_cout.txt").get(0);
        for (String js: list) {
            JSONObject jsonObject = JSONObject.parseObject(js);
            String url = jsonObject.getJSONObject("hls").getString("pcHigh");
            String order = jsonObject.get("order")+"_"+jsonObject.getString("name");

            String shell = fileGetStr +" --compressed  '"+url+"'";

            try {

                Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shell},null,null);
                InputStreamReader ir = new InputStreamReader(process.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                String line;
                String str  = "";
                process.waitFor();
                while ((line = input.readLine()) != null){
                    if (line.endsWith(".ts")){
                        str = line;
                    }
                }

                if (str.length()>5){
                    File fl1 = new File(viewPath+tagPath);
                    if (!fl1.exists()){
                        fl1.mkdir();
                    }
                    String newOrder = (page_tag_flag) +"_"+ order;
                    if (order.contains("1")){
                        page_tag_flag++;
                    }

                    File flll = new File(viewPath+tagPath+newOrder);
                    if (!flll.exists()){
                        flll.mkdir();
                    }
                    String start = str.substring(0,str.length()-8);
                    String number = str.substring(str.length()-8).replace(".ts","");
                    int total = Integer.parseInt(number);
                    for (int nums = 0;nums<=total;nums++){
                        String smbSt = String.format("00%03d.ts",nums);
                        String ttsUrl = start+smbSt;
                        String allULRLLR = fileGetStr +" -H 'Referer:"+url+"'  'https://media.wanmen.org/"+ttsUrl+"'  --output '"+viewPath+tagPath+newOrder+"/tts"+smbSt+"'";

                         FileUtiles.appendStr(viewPath+tagPath+newOrder+"/down.sh",allULRLLR);
                        String lianjie ="file '"+viewPath+tagPath+newOrder+"/tts"+smbSt+"'";
                         FileUtiles.appendStr(viewPath+tagPath+newOrder+"/lianjie.txt",lianjie);
                        if (nums == 0){
                             FileUtiles.appendStr(viewPath+tagPath+newOrder+"/ljffmege.sh","ffmpeg  -f concat -safe 0 -i lianjie.txt -c copy \""+newOrder+".mp4\"");
                             FileUtiles.appendStr(viewPath+tagPath+newOrder+"/shanchu.sh"," rm -rf *.ts");
                        }
                    }
                }
                Thread.sleep(1000);
            }catch (Exception e){
            }

        }
    }

//    获取每节课请求的curl
    public static void readListJson() throws Exception{
        String base_lcPa = "/Users/admin/Desktop/base_lec.txt";
        String p = "/Users/admin/Desktop/eyutest.json";

        String json =    FileUtiles.readFileStr(p);

        JSONArray jsa = JSONArray.parseArray(json);

        String rpq1 = "(courses/){1}[a-z0-9]{24}";
        String rpq2 = "(lectures/){1}[a-z0-9]{24}";

        String baseectures =  FileUtiles.readFile(base_lcPa).get(0);

        for (int i=0;i<jsa.size();i++){
            JSONObject bigjob = jsa.getJSONObject(i);
            JSONArray jaaaa = bigjob.getJSONArray("children");
            for (int j = 0; j < jaaaa.size(); j++) {
                JSONObject job = jaaaa.getJSONObject(j);
                String _id  = job.getString("_id");
                String courseId  = job.getString("courseId");
                String allUrl = baseectures.replaceFirst(rpq1,"courses/"+courseId).replaceFirst(rpq2,"lectures/"+_id);
//            System.out.println(allUrl +" --output lectures_"+(i+1)+".txt");
//            String shell = allUrl +" --output lectures_"+(i+1)+".txt";
//            FileUtiles.appendStr("/Users/admin/Desktop/oux.sh",shell);
//                String shell = allUrl;
//                System.out.println(shell);
                String shell = allUrl;
                try {

                    Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shell},null,null);
                    InputStreamReader ir = new InputStreamReader(process.getInputStream());
                    LineNumberReader input = new LineNumberReader(ir);
                    String line;
                    StringBuffer stringBuffer = new StringBuffer();
                    process.waitFor();
                    while ((line = input.readLine()) != null){
                        stringBuffer.append(line);
                    }
                     FileUtiles.appendStr(ZhuomianFile,stringBuffer.toString());

                    Thread.sleep(1000);

                }catch (Exception e){

                }

            }
        }


        readTSFile(1);


    }



}
