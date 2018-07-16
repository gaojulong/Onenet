package gaojulong.com.no;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class POSTDate {
    private static final String ApiKey = "42OFVvlP3tSSS1TrP4tYd3KcilE=";
   private static int responseCode;

    public static void senHttpPost(final String url, final String command, final HttpPOSTCallbackListener httpPOSTCallbackListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                    HttpURLConnection conn = null;
//                     String datastr = "{\"command\":"+command+"}";
                String datastr = command;
                     byte[] data = datastr.getBytes();
                    try {
                        // 利用string url构建URL对象
                        URL mURL = new URL(url);
                        conn = (HttpURLConnection) mURL.openConnection();

                        conn.setRequestMethod("POST");
                        conn.setReadTimeout(5000);
                        conn.setConnectTimeout(10000);
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setRequestProperty("api-key", ApiKey);
                        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
                        conn.setChunkedStreamingMode(5);
                        conn.connect();

                        OutputStream outStream = conn.getOutputStream();
                        outStream.write(data);
                        outStream.flush();
                        outStream.close();

                        responseCode = conn.getResponseCode();
                        Log.e("POST", "POST" + responseCode);
                        if (responseCode == 200) {

                            InputStream is = conn.getInputStream();
                            String response = GETDate.getStringFromInputStream(is);
                            Log.i("获取成功", "" + response);
                            httpPOSTCallbackListener.OnSucceed("获取成功");
                        } else {
                            httpPOSTCallbackListener.OnFailure(responseCode);
                            Log.i("请求失败", "请求失败，错误代码" + responseCode);
                            throw new NetworkErrorException("response status is " + responseCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        httpPOSTCallbackListener.OnError(e);
                        Log.e("程序异常", "程序异常: "+responseCode);
                    } finally {
                        if (conn != null) {
                            conn.disconnect();
                            Log.i("TAG", "执行结束关闭连接");
                        }
                    }
                }
        }).start();

    }
}
