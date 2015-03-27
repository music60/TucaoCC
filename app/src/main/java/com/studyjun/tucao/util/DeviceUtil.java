package com.studyjun.tucao.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


/**
 *
 * @ClassName: DeviceInterface
 * @Description: TODO 获取设备信息接口
 * @author studyjun
 * @date 2014-12-18 下午1:35:58
 *
 */
public class DeviceUtil {


    private static final String TAG = "DeviceInterface";
    private String mGameObject;

    /** 没有网络 */
    public static final int NETWORKTYPE_INVALID = 0;
    /** wap网络 */
    public static final int NETWORKTYPE_WAP = 1;
    /** 2G网络 */
    public static final int NETWORKTYPE_2G = 2;

    /** 3G和3G以上网络，或统称为快速网络 */
    public static final int NETWORKTYPE_3G = 3;

    /** wifi网络 */
    public static final int NETWORKTYPE_WIFI = 4;



    private int mNetWorkType = NETWORKTYPE_INVALID;

    private TelephonyManager tm = null;

    private Context activity;

    public DeviceUtil(Context context) {
        this.activity = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        tm = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);

    }


    /**
     * 获取imei
     */
    public String getDeviceId() {
        return tm.getDeviceId();
    }

    /**
     * 设备型号
     *
     * @return
     */
    public String getDeviceName() {
        Build bd = new Build();
        return Build.MODEL;
    }

    /**
     * 设备商家
     *
     * @return
     */
    public String getDeviceProduct() {
        Build bd = new Build();
        return Build.MANUFACTURER;
    }

    /**
     * 设备SDK版本
     *
     * @return
     */
    public int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 设备的系统版本
     *
     * @return
     */
    public String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取mac
     *
     * @return
     */
    public String getMacIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

    /**
     * 运营商
     *
     * @return
     */
    public String getSimOperatorName() {
        // return tm.getSimOperatorName();
        String simOperatorName = "";
        String operatorString = tm.getSimOperator();

        if (operatorString == null) {
            simOperatorName = "unkonw";
        }

        if (operatorString.equals("46000") || operatorString.equals("46002")) {
            // 中国移动
            simOperatorName = "中国移动";
        } else if (operatorString.equals("46001")) {
            // 中国联通
            simOperatorName = "中国联通";
        } else if (operatorString.equals("46003")) {
            // 中国电信
            simOperatorName = "中国电信";
        }

        // error
        return simOperatorName;
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context
     *            上下文
     * @return
     */
    public String getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G
                        : NETWORKTYPE_2G)
                        : NETWORKTYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }

        String networkType = "unknow";
        switch (mNetWorkType) {
            case NETWORKTYPE_INVALID:
                networkType = "invalid";
                break;
            case NETWORKTYPE_2G:
                networkType = "2g";
                break;
            case NETWORKTYPE_WAP:
                networkType = "wap";
                break;
            case NETWORKTYPE_3G:
                networkType = "3g";
                break;
            case NETWORKTYPE_WIFI:
                networkType = "wifi";
                break;

            default:
                break;
        }
        return networkType;
    }

    /*
     * 例如：IMSI(国际移动用户识别码) for a GSM phone. 需要权限：READ_PHONE_STATE
     */
    public String getImsi() {
        return tm.getSubscriberId();
    }

    /**
     * 判断2g/3g+
     *
     * @param context
     * @return
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 判断网络是否可用 <br>
     *
     * @param context
     * @return
     */
    public static boolean haveInternet(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();

        if (info == null || !info.isConnected()) {
            return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to
            // disable internet while roaming, just return false
            // 是否在漫游，可根据程序需求更改返回值
            return false;
        }
        return true;
    }




    /**
//     * 获取设备分辨率
//     *
//     *
//     * @return
//     */
//    public String getResolution() {
//        // DisplayMetricsmetrics ＝ new DisplayMetrics();
//        // getWindowManager().getDefaultDisplay().getMetrics;
//        int screen_w = 0;
//        int screen_h = 0;
//        int ver = Build.VERSION.SDK_INT;
//
//        DisplayMetrics dm = new DisplayMetrics();
//        android.view.Display display = activity.getWindowManager().getDefaultDisplay();
//        display.getMetrics(dm);
//
//        screen_w = dm.widthPixels;
//        screen_h = dm.heightPixels;
//
//        if (ver < 13) { // 根据版本号的不同获取的方法也不同
//            screen_h = dm.heightPixels;
//        } else if (ver == 13) {
//            try {
//                Method mt = display.getClass().getMethod("getRealHeight");
//                screen_h = (Integer) mt.invoke(display);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (ver > 13) {
//            try {
//                Method mt = display.getClass().getMethod("getRawHeight");
//                screen_h = (Integer) mt.invoke(display);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return screen_w + "*" + screen_h;
//    }
}
