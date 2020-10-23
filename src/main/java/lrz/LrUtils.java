package lrz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LrUtils {
    /**
     * @Author：
     * @Description：获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件，所以不用递归获取
     * @Date：
     */
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                //文件名，不包含路径
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        return files;
    }

    public static void  ReadConfig() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.txt")) {
            properties.load(fis);

        File srcApk = new File(properties.getProperty("apk.src"));
        File signApk = new File(properties.getProperty("apk.signed"));
        File outApk = new File(properties.getProperty("apk.out"));
        boolean signEnable = properties.getProperty("sign.enable").equalsIgnoreCase("true");
        String signFile = properties.getProperty("sign.file");
        String signPassword = properties.getProperty("sign.password");
        String signAlias = properties.getProperty("sign.alias");
        String signAliasPassword = properties.getProperty("sign.aliasPassword");
        }catch(IOException ioException){

    }
    }

    //		String path = "C:\\Users\\dell\\Desktop\\lrzhaowork\\classes.dex";
//		String arscPath = "/Users/hluwa/APK/1_18-1/resources.arsc";
//		ArscFile arscFile = ARSCParser.ParseArsc(new File(arscPath));
//		System.out.println(arscFile.getStringById(0x7F050000));
//		System.exit(0);
//		DexFile dex =new DexFile();
//
//		DexChanger changer = new DexChanger(new File(path));
//		DexFile dexfile = changer.getDexFile();
//		String magiclist[] = {
//				"com.google.android.gms.ads.AdView.loadAd",
//				"com.google.android.gms.ads.InterstitialAd.loadAd",
//				"com.google.android.gms.ads.reward.RewardedVideoAd.loadAd",
//				"com.google.android.gms.ads.NativeExpressAdView.loadAd",
//				"com.mopub.mobileads.AdViewController.loadAd",
//				"com.mopub.mobileads.MoPubInterstitial$MoPubInterstitialView.loadAd"
//		};
//		for (insns_item insns : dexfile.getAllInsnsItem()) {
//			if (insns.opcode.toString().startsWith("INVOKE")) {
//
//				changer.move(insns.getFileOff() + 2); // invoke系列指令格式 A|G|op BBBB F|E|D|C ,所以off + 2是methodId
//				int methodId = changer.nextShort() & 0xFFFF; // 转为无符号数
//
//				if (methodId < 0 || methodId > dexfile.getHeader().method_ids_size) { // invoke-custom
//					continue;// 调用的索引有可能是FFFFFE,防止其他意外情况,过滤掉非正常methodId
//				}
//				String mtd = dexfile.getNameByMethodId(methodId);
//				if(mtd.indexOf("loadAd") != -1) {
//					System.out.println(mtd);
//				}
//				for(String magic : magiclist) {
//					if(mtd.indexOf(magic) != -1) {
//						changer.setNop(insns);
//						System.out.println(insns.getFileOff() + " - invoke method " + mtd);
//					}
//				}
//			}
//		}
//		changer.flush();
}
