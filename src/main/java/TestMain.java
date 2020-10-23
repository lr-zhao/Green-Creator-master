import com.google.gson.Gson;
import hluwa.dex.DexChanger;
import hluwa.dex.format.DexFile;
import hluwa.dex.format.insns_item;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

public class TestMain {
    public static void main(String[] args) throws IOException {


//        String dexpath=  args[0];

//        String dexpath = "C:\\Users\\dell\\Desktop\\lrzhaowork\\classes.dex";
//        MoveAd(dexpath);

        String str = "name=OnRewardedAdReceivedRewardEvent\n"+
        "rewardAmount=0\n"+
        "adUnitId=fc98d609c656615e\n"+
        "rewardLabel=";
        System.out.println(new Gson().toJson(str.getBytes("UTF-8")));
        System.out.println(new Gson().toJson(bytes2hex(str.getBytes("UTF-8"))));
    }

    public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            // 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {
                tmp = "0" + tmp;
            }
            sb.append("0x"+tmp+",");
        }
        return sb.toString();

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
                "preloadAd(",
                "preloadAds(",
                "com.baidu.mobads.openad.addEventListener",
                "ShowAd(",
                "com.unity3d.services.banners.UnityBanners.loadBanner",
                "UnityAd.initialize(",
                "com.unity3d.services.ads.api.AdUnit.open",
                "com.unity3d.ads.UnityAds.initialize",
                "com.unity3d.services.ads.UnityAdsImplementation.initialize",
        };
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



}
