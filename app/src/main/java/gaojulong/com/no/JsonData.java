package gaojulong.com.no;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonData {
    private static final String TAG = "JsonData";

    /**
     * 解析采集信息数据
     * @param jsonstr
     * @return
     */
    public static String[] analysisDataJson(String jsonstr) {
        String[] resurl = new String[6];
        JSONObject jsonObject = JSON.parseObject(jsonstr);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject1.getJSONArray("datastreams");

        JSONObject jsonObject2 = jsonArray.getJSONObject(0);

        JSONArray jsonArray1 = jsonObject2.getJSONArray("datapoints");
        JSONObject jsonObject3 = jsonArray1.getJSONObject(0);

        resurl[0] = jsonObject3.getString("at");
        JSONArray jsonArray2 =jsonObject3.getJSONArray("value");

        JSONObject csb = jsonArray2.getJSONObject(0);
        resurl[1] = csb.getString("csb");

        JSONObject gz = jsonArray2.getJSONObject(1);
        resurl[2] = gz.getString("gz");

        JSONObject mp = jsonArray2.getJSONObject(2);
        resurl[3] = mp.getString("mp");

        JSONObject gm = jsonArray2.getJSONObject(3);
        resurl[4] = gm.getString("gm");

        JSONObject zt = jsonArray2.getJSONObject(4);
        resurl[5] = zt.getString("zt");
//        Log.e(TAG, resurl[0]+resurl[1]+resurl[2]+resurl[3]+resurl[4]+resurl[5]);
        return resurl;

    }

}
