package lexicalanalyzer;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class processor {
    public void process(String programpath, HashMap<String, Integer> hm, String path) throws IOException {
        FileWriter fw=new FileWriter(path);
        BufferedWriter bw=new BufferedWriter(fw);

        FileReader reader=new FileReader(programpath);
        BufferedReader br=new BufferedReader(reader);

        String program=null;
        int row=0;
        int col=0;
        int temprow=0;
        int tempcol=0;
        boolean Annotation=false;
        while ((program=br.readLine())!=null){
            row++;
            col=0;


            if(!Annotation&&program.contains("/*")){
                //System.out.println(1111);
                temprow=row;

                Annotation=true;
                int i=program.indexOf("/*");
                tempcol=i;
                program=program.substring(0,i);
            }
            if(Annotation&&program.contains("*/")) {
                //有未配对的注释且该行中含有"*/"
                int i=program.indexOf("*/");
                program=program.substring(i+2);
                Annotation=false;
            }
            if(program.contains("//")){
                int i=program.indexOf("//");
                program=program.substring(0,i);
            }
            if(program.isBlank()){
                continue;
            }
            if(!Annotation){
                String temprogram=program;
               // System.out.println(program);
                //program= program.replace(" ","");
                program=program.replace("\t","");
                program=program.replace("\n","");
                program= program.replace("\r","");
                //System.out.println(program);
                char[] pro=program.toCharArray();
                int i=0;
               // System.out.println(pro.length);
                //System.out.println(program);
                while(i<pro.length-1){
                    //System.out.println(row);
                    if(Isdigit(pro[i])){
                        String tempstr="";
                        tempstr=tempstr+String.valueOf(pro[i]);
                        i++;
                        while(Isdigit(pro[i])||pro[i]=='.'||Isletter(pro[i])){
                            tempstr=tempstr+String.valueOf(pro[i]);
                            i++;
                        }
                        if(tempstr.contains("x")){
                            tempstr=tempstr.substring(tempstr.indexOf("x"));
                            if(tempstr.contains(".")){
                                bw.write("("+tempstr+","+103+")"+"\n");//十六进制整数
                            }else{
                                bw.write("("+tempstr+","+104+")"+"\n");//十六进制浮点数
                            }

                        }else{
                            if(tempstr.contains(".")){
                                bw.write("("+tempstr+","+101+")"+"\n");//十进制整数
                            }else{
                                bw.write("("+tempstr+","+102+")"+"\n");//十进制浮点数
                            }
                        }

                        // bw.flush();

                    }else if(Isletter(pro[i])){
                        String tempstr="";
                        tempstr=tempstr+String.valueOf(pro[i]);
                        i++;
                        while(Isletter(pro[i])||Isdigit(pro[i])){
                            tempstr=tempstr+String.valueOf(pro[i]);
                            i++;
                        }
                        if(hm.containsKey(tempstr)){
                            bw.write("("+tempstr+","+hm.get(tempstr)+")"+"\n");
                            // bw.flush();
                        }
                        else{
                            bw.write("("+tempstr+","+100+")"+"\n");
                            // bw.flush();
                        }
                    }else{
                        String str=String.valueOf(pro[i]);
                        if(hm.containsKey(str)){
                            if((i+1<pro.length)){
                                String tempstr=String.valueOf(pro[i])+String.valueOf(pro[i+1]);

                                if(hm.containsKey(tempstr)){
                                    bw.write("("+tempstr+","+hm.get(tempstr)+")"+"\n");
                                    i=i+2;
                                    // bw.flush();
                                }
                                else{
                                   // System.out.println(str);
                                    i++;
                                    bw.write("("+str+","+hm.get(str)+")"+"\n");

                                    //bw.flush();
                                }
                            }
                        }else{
                            if(pro[i]==' '){
                                i++;
                            }else{
                                System.out.println("line:"+row+","+"col:"+i+" 出现不合法字符");
                                i++;
                            }
                        }

                    }
                }
            }


        }
        if(Annotation){
            System.out.println("line:"+temprow+","+"col:"+tempcol+" 注释未配对");
        }
        bw.flush();
    }

    public boolean Isdigit(char ch){
        if(ch<='9'&&ch>='0'){
            return true;
        }
        else
            return false;
    }
    public boolean Isletter(char ch){
        if((ch<='Z'&&ch>='A')||(ch<='z'&&ch>='a')||(ch=='_')){
            return true;
        }
        else
            return false;
    }
}
