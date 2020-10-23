package lrz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CMDUtils {

    public static boolean ExecCMD(String command){
        boolean flag = false;
        try{
            Process pro = Runtime.getRuntime().exec("cmd /c  "+command);
            BufferedReader br = new BufferedReader(new InputStreamReader(pro
                    .getInputStream(),Charset.forName("GBK"))); //虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。 //Charset.forName("GBK")
            String msg = null;
            while ((msg = br.readLine()) != null) {
                System.out.println(msg);
            }
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /*cmd /c dir 是执行完dir命令后关闭命令窗口
      cmd /k dir 是执行完dir命令后不关闭命令窗口
      cmd /c start dir  会打开一个新窗口后执行dir命令，原窗口会关闭
      cmd /k start dir  会打开一个新窗口后执行dir命令，原窗口不会关闭
      cmd /?  查看帮助信息*/
}
