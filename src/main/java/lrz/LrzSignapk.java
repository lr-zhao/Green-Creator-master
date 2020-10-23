package lrz;

import bin.signer.ApkSigner;
import bin.signer.key.KeystoreKey;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class LrzSignapk {

    /**
     *  cmd v1签名
     * @param outputApk
     * @param NosignApk
     * @param keystorePath
     * @param keystorePW
     * @param keystoreAlias
     * @param keystoreAliasPW
     * @throws IOException
     */
    //	v1 签名
    public static void SignApkByV1(String outputApk, String NosignApk, String keystorePath,String keystorePW ,String keystoreAlias,String keystoreAliasPW) throws IOException {
        System.out.println("apk文件重签名 By v1 cmd");
//        jarsigner -digestalg SHA1 -sigalg MD5withRSA -verbose -keystore "%~dp0\sign1.keystore" -signedjar signed.apk %1 sign1.keystore -storepass 20150318
//        String sign = "jarsigner -digestalg SHA1 -sigalg MD5withRSA -verbose -keystore " + keystorePath +" -signedjar " + NosignApk + " " + outputApk + " " + keystoreName + " -storepass 20150318";

        String sign = "jarsigner -digestalg SHA1 -sigalg MD5withRSA -verbose -keystore " + keystorePath +
                " -signedjar " + outputApk + " " + NosignApk + " " + keystoreAlias + " -storepass "+keystorePW;

        System.out.println("sign : " + sign);
        CMDUtils.ExecCMD(sign);
        System.out.println("文件签名 结束" );
    }


    public static void SignApkByV2V1(String NosignApk,String signFile,String signPassword,String signAlias,String signAliasPassword) throws Exception {

            System.out.println("\n正在签名APK By v1+v2 Code：" +NosignApk);
            File outApk = new File(NosignApk);
			KeystoreKey keystoreKey = new KeystoreKey(signFile, signPassword, signAlias, signAliasPassword);
			File temp = new File(outApk.getPath() + ".tmp");
			ApkSigner.signApk(outApk, temp, keystoreKey,null);
			outApk.delete();
			temp.renameTo(outApk);
            System.out.println("\n签名APK结束 By v1+v2 Code：" +NosignApk);
    }
}
