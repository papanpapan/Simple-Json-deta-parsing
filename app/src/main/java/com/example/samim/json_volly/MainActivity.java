package com.example.samim.json_volly;

import android.renderscript.Allocation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private ImageView mImageViewResult0, mImageViewResult1, mImageViewResult2;
    private RequestQueue mQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult=(TextView)findViewById(R.id.text_view_result);


        mQueue= Volley.newRequestQueue(this);
        jsonParse();

    }

    private void jsonParse() {
        String url="https://reqres.in/api/users";
        JsonObjectRequest request = 
        new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
                                String id = data.getString("id");
                                String firstName = data.getString("first_name");
                                String lastName = data.getString("last_name");
                                mTextViewResult.append(id + "\n" + firstName +"\n"+ lastName+"\n\n");

                                //image part

                                mImageViewResult0=(ImageView)findViewById(R.id.image_view_result0);
                                mImageViewResult1=(ImageView)findViewById(R.id.image_view_result1);
                                mImageViewResult2=(ImageView)findViewById(R.id.image_view_result2);

                                Picasso.with(getApplicationContext()).load(jsonArray.getJSONObject(0).getString("avatar")+"\n\n").into(mImageViewResult0);
                                Picasso.with(getApplicationContext()).load(jsonArray.getJSONObject(1).getString("avatar")+"\n\n").into(mImageViewResult1);
                                Picasso.with(getApplicationContext()).load(jsonArray.getJSONObject(2).getString("avatar")+"\n\n").into(mImageViewResult2);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
                mQueue.add(request);
    }
}
