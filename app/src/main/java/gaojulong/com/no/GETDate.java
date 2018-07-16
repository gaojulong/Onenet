package gaojulong.com.no;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GETDate {
    private static final String TAG = "GETData";
    private static final String ApiKey = "42OFVvlP3tSSS1TrP4tYd3KcilE="; //个人使用就建议填产品key，设备key在二进制获取那里会权限不足


    public static void senHttpget(final String url, final HttpCallbackListener httpCallbackListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int responseCode = 0;
                    HttpURLConnection conn = null;
                    try {
                        // 利用string url构建URL对象
                        URL mURL = new URL(url);
                        conn = (HttpURLConnection) mURL.openConnection();

                        conn.setRequestMethod("GET");
                        conn.setReadTimeout(5000);
                        conn.setConnectTimeout(10000);
                        conn.setRequestProperty("api-key", ApiKey);


                         responseCode = conn.getResponseCode();
                        Log.i(TAG, "GET" + responseCode);
                        if (responseCode == 200) {

                            InputStream is = conn.getInputStream();
                            String response = getStringFromInputStream(is);
                            httpCallbackListener.OnSucceed(response);
                            Log.i(TAG, "获取成功" + response);
                        } else {
                            httpCallbackListener.OnFailure(responseCode);
                            Log.i(TAG, "请求失败，错误代码" + responseCode);
                            throw new NetworkErrorException("response status is " + responseCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        httpCallbackListener.OnError("程序异常+错误代码"+responseCode);
                        Log.e(TAG, "程序异常");
                    } finally {
                        if (conn != null) {
                            conn.disconnect();
                            Log.i(TAG, "执行结束关闭连接");
                        }
                    }
                    try {
                        Thread.sleep(200);
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }


}
