package tw.com.iisigroup.apirequestdemo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void APIRequest(View v) {
        getCaseVisitInfo("","", mCB);
    }

    public static void getCaseVisitInfo(String token, String visit_id, Callback cb) {
        String url = "https://maps.google.com/maps/api/directions/json?origin=25.027479, 121.470839&destination=25.027309, 121.471095";
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient httpClient = builder.build();

//        JSONObject jbody = new JSONObject();
//        try {
//            jbody.put("Token", token);
//            jbody.put("ID", visit_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, jbody.toString());
//        Request request = new Request.Builder()
//                .addHeader("Content-Type", "application/json")
//                .post(body)
//                .url(url)
//                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        httpClient.newCall(request).enqueue(cb);
    }


    Callback mCB = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String msg;
            try {
                if (200 == response.code()) {
                    msg = response.body().string();
                } else {
                    msg = "存取錯誤。Code: "+response.code();
                }
                showDialog(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void showDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("API Demo")
                        .setMessage(msg)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

    }
}
