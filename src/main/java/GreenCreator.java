

import hluwa.dex.DexChanger;
import hluwa.dex.format.DexFile;
import hluwa.dex.format.insns_item;
import lrz.CompactAlgorithm;
import lrz.LrzSignapk;
import lrz.UnZipFile;
import java.io.*;



public class GreenCreator {
	public static boolean JarMode = false;
//	public static boolean JarMode = true;

	public static void main(String args[]) throws Exception {


		boolean signEnable = true;
		String appName = "lrz";
		File directory = new File("");//设定为当前文件夹
		String basepath = ""; //当前工程的路径
		String filepath = ""; // 需要执行的apk 路径
		String keystorePath = "";
		String keystorePW = "";
		String keystoreAlias = "";
		String keystoreAliasPW = "";

		if(JarMode){
			//jar 用
			basepath =directory.getAbsolutePath()+"\\";
		    filepath = args[0];
			keystorePath = args[1];
			keystorePW = args[2];
			keystoreAlias = args[3];
			keystoreAliasPW = args[4];

		}else {
			//idea 用
			 basepath = "C:\\Users\\dell\\Desktop\\lrzhaowork"+"\\";
			 filepath = "C:\\Users\\dell\\Desktop\\lrzhaowork\\"+appName+".apk";
			 keystorePath = basepath+"sign1.keystore";
			 keystorePW = "20150318";
			 keystoreAlias = "sign1.keystore";
			 keystoreAliasPW = "20150318";
		}
		// appName 获取app的名字  C:/123/app.apk
		File targetapk =new File(filepath);
		appName =targetapk.getName();

		// appNamedir app的名字去.apk    targetDir---> C:/123/app
		String appNamedir= appName.substring(0,appName.length()-4);
		String targetDir = basepath+appNamedir;

		// zip解压apk  1 需要解压的apk   2 指定解压目录
		UnZipFile.unZipFiles(filepath,basepath);

		File targetApkDir = new File(targetDir);
		File[] tempList = targetApkDir.listFiles();

		for(File file :tempList){
			// classes 执行去广告
			if (file.isFile()&&file.getName().startsWith("classes")){
				MoveAd(file.getPath());
			}
			//删除签名文件夹
			if(file.isDirectory()&&file.getName().equals("META-INF")){
//				deleteDir(file.getAbsolutePath());

				File[] signfile  = file.listFiles();
				for(File file1 :signfile){
//					if(file1.isFile()&&file.getName().endsWith(".RSA") ||file1.isFile()&&file1.getName().endsWith(".SF")||file1.isFile()&&file1.getName().endsWith(".MF")){
//						file1.delete();
//					}
					if(file1.isFile()){
						file1.delete();
					}
				}
				System.out.println("已删除旧签名信息");
			}
		}

		//
		File ftargetDir =new File(targetDir);
		// C:/123/lrz-noads.apk
//		File outfile = new File( basepath,ftargetDir.getName()+"-noads.apk");
		File outApk = new File( basepath,ftargetDir.getName()+"-noads.apk");

		// 把 targetDir 文件夹 zip压缩为 C:/123/lrz-noads.apk
		new CompactAlgorithm(outApk).zipFiles(ftargetDir);


		if (signEnable) {
			System.out.println("\n正在签名APK：" + outApk.getName());

			//v1 签名
			LrzSignapk.SignApkByV1(basepath + "signed.apk", outApk.getAbsolutePath(), keystorePath, keystorePW, keystoreAlias, keystoreAliasPW);

			//v1+v2 签名
//			LrzSignapk.SignApkByV2V1(outApk.getAbsolutePath(),keystorePath,keystorePW,keystoreAlias,keystoreAliasPW);

			if (new File(targetDir).exists()) {
				deleteDir(targetDir);
				outApk.delete();

			}
		}
	}
	public static void MoveAd(String path) throws IOException {

		DexFile dex =new DexFile();

		System.out.println(path);
		DexChanger changer = new DexChanger(new File(path));
		DexFile dexfile = changer.getDexFile();
		String magiclist[] = {
				"loadAd(",
				"loadad(",
				"loadaD(",
				"showAd(",
				"loadBannerAd(",
				"loadBanner(",
				"_loadBanner(",
				"onUnityBannerLoaded(",
				"onUnityBannerShow(",
				"loadAndShowBanner(",
				"preloadAd(",
				"preloadAds(",
				"com.baidu.mobads.openad.addEventListener",
				"ShowAd(",
				"com.unity3d.services.banners.UnityBanners.loadBanner",
                "UnityAdsImplementation.show(",
				"UnityAd.initialize(",
//				"com.unity3d.ads.UnityAds.isReady(",
				"com.unity3d.ads.UnityAds.show(",
                "ShowAdPlacementContent.show(",
//				"com.unity3d.services.ads.api.AdUnit.open",
				"com.unity3d.ads.UnityAds.initialize",
				"com.unity3d.services.ads.UnityAdsImplementation.initialize",
				"com.chartboost.sdk.Chartboost.Interstitial(",
				"com.applovin.mediation.unity.MaxUnityAdManager.showRewardedAd(",

//				"showInterstitial(",
//				"cacheRewardedVideo(",
		};
//        String magiclist[] = {
//
//                "loadBannerAd(",
//                "loadBanner(",
//                "loadAndShowBanner(",
//                "loadInterstitial(",
//                "showInterstitial(",
//        };

		String magiclist1[] = {

//				"com.mopub.mobileads.AdViewController.loadAd",
//				"com.mopub.mobileads.MoPubInterstitial$MoPubInterstitialView.loadAd",
//
////				Tencent
//				"com.qq.e.ads.banner2.UnifiedBannerVie.loadAD",
//				"com.qq.e.ads.banner.BannerVie.loadAD",
//				"com.qq.e.ads.rewardvideo.RewardVideoA.loadAD",

//				google
//				"com.google.android.gms.ads.AdView.loadAd",
//				"com.google.android.gms.ads.InterstitialAd.loadAd",
//				"com.google.android.gms.ads.reward.RewardedVideoAd.loadAd",
//				"com.google.android.gms.ads.NativeExpressAdView.loadAd",
//				"com.google.ads.mediation.unity.UnitySingleto.loadAd",
//				"com.google.android.gms.ads.BaseAdVie.loadAd",
//				"com.google.android.gms.ads.reward.RewardedVideoA.loadAd",
//				"com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapte.loadAd",
//				"com.google.android.gms.ads.reward.RewardedVideoA.loadAd",
//				"com.google.android.gms.ads.AdLoade.loadAd",

//				"com.analytics.sdk.view.AdVie.loadAd",
//				"com.atmosplayads.AtmosplayRewardVide.loadAd",
//				"com.ironsource.sdk.IronSourceNetwor.loadAd",
//				"com.ironsource.sdk.ISNAdView.ISNAdVie.loadAd",
////vungle
//				"com.vungle.warren.AdLoade.loadAd",
//				"com.vungle.warren.Vungl.loadAd",
				//facebook
//                "com.facebook.ads.internal.api.InterstitialAdAp.loadAd",
//                "com.facebook.ads.internal.api.InterstitialAdAp.loadAdFromBid",
//                "com.facebook.ads.internal.api.NativeAdBaseAp.loadAd",
//                "com.facebook.ads.internal.api.NativeAdsManagerAp.loadAds",
//                "com.facebook.ads.internal.api.RewardedVideoAdAp.loadAd",
//                "com.facebook.ads.internal.api.InstreamVideoAdViewAp.loadAd",
//                "com.facebook.ads.AdVie.loadAd",

//         GooglePlayServicesAvailable
//                "com.google.android.gms.common.GoogleApiAvailability.isGooglePlayServicesAvailable",
//				"com.google.android.gms.common.GoogleApiAvailabilityLight.isGooglePlayServicesAvailable",
//				"com.google.android.gms.common.GooglePlayServicesUtilLight.isGooglePlayServicesAvailable"
		};
		for (insns_item insns : dexfile.getAllInsnsItem()) {
			if (insns.opcode.toString().startsWith("INVOKE")) {

				changer.move(insns.getFileOff() + 2); // invoke系列指令格式 A|G|op BBBB F|E|D|C ,所以off + 2是methodId
				int methodId = changer.nextShort() & 0xFFFF; // 转为无符号数

				if (methodId < 0 || methodId > dexfile.getHeader().method_ids_size) { // invoke-custom
					continue;// 调用的索引有可能是FFFFFE,防止其他意外情况,过滤掉非正常methodId
				}
				String mtd = dexfile.getNameByMethodId(methodId);

//				if(mtd.indexOf("isGooglePlayServicesAvailable") != -1) {
//					  System.out.println(mtd);
//                    changer.setNop(insns);
//
//                }
				for(String magic : magiclist) {
					if(mtd.indexOf(magic) != -1) {
						changer.setNop(insns);
						System.out.println(insns.getFileOff() + " - invoke method " + mtd);
					}
				}
			}
		}
		changer.flush();
	}

	/**
	 * 迭代删除文件夹
	 * @param dirPath 文件夹路径
	 */
	public static void deleteDir(String dirPath)
	{
		File file = new File(dirPath);
		if(file.isFile())
		{
			file.delete();
		}else
		{
			File[] files = file.listFiles();
			if(files == null)
			{
				file.delete();
			}else
			{
				for (int i = 0; i < files.length; i++)
				{
					deleteDir(files[i].getAbsolutePath());
				}
				file.delete();
			}
		}
	}
}
