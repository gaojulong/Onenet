package gaojulong.com.no;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private static String TAG = "MainActivity";
    private Button go, back, link, left, right, stop, baojing, fmqopen, fmqclose;
    private String data = "", valus;
    private TextView textView;
    String url = "http://api.heclouds.com/devices/34281442/datapoints?type=3";

    //json 数据  {"data":[{"csb":12},{"gz":11},{"mp":10},{"gm":9},{"zt":2}]}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getData();//获取OneNET数据，更新UI
    }

    private void init() {
        textView = findViewById(R.id.tv_view);

        go = findViewById(R.id.gobtn);
        back = findViewById(R.id.backbtn);
        link = findViewById(R.id.linebtn);
        left = findViewById(R.id.leftbtn);
        right = findViewById(R.id.rightbtn);
        stop = findViewById(R.id.stopbtn);
        baojing = findViewById(R.id.baojingstart);
        fmqopen = findViewById(R.id.fengmingqiopen);
        fmqclose = findViewById(R.id.fengmingqiclose);

        go.setOnClickListener(this);
        back.setOnClickListener(this);
        link.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        stop.setOnClickListener(this);
        baojing.setOnClickListener(this);
        fmqopen.setOnClickListener(this);
        fmqclose.setOnClickListener(this);

    }

    /**
     * toast工具
     *
     * @param string
     */
    private void toastUtil(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 更新textview的数据
     *
     * @param
     */
    private void updateUI(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }

    private void getData() {
        GETDate.senHttpget(url, new HttpCallbackListener() {
            @Override
            public void OnSucceed(String response) {
                String[] strings = JsonData.analysisDataJson(response);
                if (!data.equals(strings[0])) {
                    String dataView = "超声波" + strings[1] + "cm,光照" + strings[2] + ",码盘" + strings[3]
                            + "," + "光敏" + strings[4] + ",状态" + strings[5];
                    updateUI(dataView);
                }


            }

            @Override
            public void OnFailure(int code) {
                toastUtil("获取失败，错误代码：" + code);
            }

            @Override
            public void OnError(String error) {
                toastUtil(error);
            }
        });

    }

    private String connectCmdData(int cmd) {
        return "{\"command\":" + cmd + "}";
    }

    private String connectGatherData(String[] strings) {
//        String gatherData  =  "{\"data\":[{\"csb\":"+strings[0]+"}" + ",{\"gz\":11},{\"mp\":10000}," +
//                "{\"gm\":10101}," + "{\"zt\":4}]}";
        String gatherData = "{\"data\":[{\"csb\":" + strings[0] + "}" + ",{\"gz\":" + strings[1] + "}," +
                "{\"mp\":" + strings[2] + "}," +
                "{\"gm\":" + strings[3] + "}," + "{\"zt\":" + strings[4] + "}]}";
        return gatherData;
    }

    //上传控制命令
    private void postDate(String command) {

        POSTDate.senHttpPost(url, command, new HttpPOSTCallbackListener() {

            @Override
            public void OnSucceed(String response) {
                toastUtil("发送成功");
            }

            @Override
            public void OnFailure(int code) {
                toastUtil("发送失败");
            }

            @Override
            public void OnError(Exception e) {
                toastUtil("发送失败");
            }
        });

    }

    @Override
    public void onClick(View v) {
        String cmd = "-1";
        switch (v.getId()) {
            case R.id.gobtn:
                cmd = connectCmdData(0);
                break;
            case R.id.backbtn:
                cmd = connectCmdData(1);
                break;
            case R.id.linebtn:
                cmd = connectCmdData(2);
                break;
            case R.id.leftbtn:
                cmd = connectCmdData(3);
                break;
            case R.id.rightbtn:
                cmd = connectCmdData(4);
                break;
            case R.id.stopbtn:
                cmd = connectCmdData(5);
                break;
            case R.id.baojingstart:
                cmd = connectCmdData(6);
                break;
            case R.id.fengmingqiopen:
                cmd = connectCmdData(7);
                break;
            case R.id.fengmingqiclose:
                cmd = connectCmdData(8);

                /************测试发送采集数据*************/
//                String[] strings =new String[5];
//                strings[0 ] = "1";
//                strings[1 ] = "2";
//                strings[2 ] = "3";
//                strings[3 ] = "4";
//                strings[4 ] = "5";
//                cmd = connectGatherData(strings);
                break;
        }
        postDate(cmd);//发送数据到服务器

    }
}
