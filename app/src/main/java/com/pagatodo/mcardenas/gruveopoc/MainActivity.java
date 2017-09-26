package com.pagatodo.mcardenas.gruveopoc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gruveo.sdk.Gruveo;
import com.gruveo.sdk.interfaces.GruveoClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String SIGNER_URL = "https://api-demo.gruveo.com/signer";
    private Gruveo.EventsListener eventsListener ;
    private int REQUEST_CALL = 1010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsListener = new Gruveo.EventsListener() {
            @Override
            public void callInit(boolean b, @NotNull String s) {

            }

            @Override
            public void requestToSignApiAuthToken(@NotNull String token) {
                try {
                    Gruveo.Companion.authorize(signToken(token));
                } catch( IOException ignored ){

                }
            }

            @Override
            public void callEstablished(@NotNull String s) {

            }

            @Override
            public void callEnd(@NotNull Intent intent, boolean b) {

            }

            @Override
            public void recordingStateChanged(boolean b, boolean b1) {

            }
        };

        final Bundle otherExtras = new Bundle();
        otherExtras.putBoolean(Gruveo.GRV_EXTRA_VIBRATE_IN_CHAT, false);

        final String result = new Gruveo.Builder(this)
                .callCode("gruveorocks")
                .videoCall(true)
                .clientId("demo")
                .eventsListener(eventsListener)
                .requestCode(REQUEST_CALL)
                .otherExtras(otherExtras)
                .build();


        switch( result ){
            case Gruveo.GRV_INIT_MISSING_CALL_CODE: {
                break;
            }
            case Gruveo.GRV_INIT_INVALID_CALL_CODE: {
                break;
            }
            case Gruveo.GRV_INIT_MISSING_CLIENT_ID: {
                break;
            }
            case Gruveo.GRV_INIT_OFFLINE: {
                break;
            }
            default: {
                break;
            }
        }
    }


    private String signToken(String token) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), token);
        Request request = new Request.Builder()
                .url(SIGNER_URL)
                .post(body)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();
        return response.body().string();
    }


}
