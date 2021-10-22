package lexicalanalyzer;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;

public class preprocessor {
    //预处理器
    public void readRe(String path, ArrayList<String> array) throws IOException {
        //从文件中读取保留字，界符等
        FileInputStream inputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        while((str = bufferedReader.readLine()) != null) {
            array.add(str);
        }
        inputStream.close();
        bufferedReader.close();
    }

    public String readProgram(String path) throws IOException{

        FileInputStream inputStream = new FileInputStream(path);
        String wholestring ="";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str="";
        int cout=0;
        while((str = bufferedReader.readLine()) != null) {
            //读入的同时去除'//'注释
            int begin=str.indexOf("//");
            if(begin!=-1){
            String tempstring=str.substring(begin);
            str=str.replace(tempstring,"");
            }
            wholestring=wholestring+str+"\r";
            cout++;
        }
        inputStream.close();
        bufferedReader.close();

        int temp=0;

        while(wholestring.indexOf("/*")!=-1){
            // 去除块注释
            int begin=wholestring.indexOf("/*");
            int end=wholestring.indexOf("*/",begin);
            if(end==-1){
                int index=0;
                int line=0;
                int lastindex=0;
                while ((index = wholestring.indexOf("\r", index)) != -1) {
                    if(index>begin)
                        break;
                    lastindex=index;
                    index = index + 1;
                    line++;
                }
                line=line+temp+1;
                int col=begin-lastindex;
                System.out.println("注释未配对！"+"line:"+line+" "+"col:"+col);
                System.exit(1);
            }
            int tempindex=0;
            String tempstring=wholestring.substring(begin,end+2);
            while ((tempindex = tempstring.indexOf("\r", tempindex)) != -1) {
                tempindex = tempindex + 1;
                temp++;
            }
            wholestring=wholestring.replace(tempstring,"");
        }

        int index=0;
        int lastindex=0;
        int begin;
        int line=0;
        if((begin = wholestring.indexOf("*/")) != -1){
            while ((index = wholestring.indexOf("\r", index)) != -1) {
                if(index>begin)
                    break;
                lastindex=index;
                index = index + 1;
                line++;
            }
            line=line+temp+1;
            int col=begin-lastindex;
            System.out.println("注释未配对！"+"line:"+line+" "+"col:"+col);
            System.exit(1);
        }

        wholestring=wholestring.replace(" ","");//去除空格
        wholestring=wholestring.replace("\t","");//去除制表符
        wholestring=wholestring.replace("\r","");//去除回车符
        wholestring=wholestring.replace("\n","");//去除换行符
        return wholestring;
    }
}
