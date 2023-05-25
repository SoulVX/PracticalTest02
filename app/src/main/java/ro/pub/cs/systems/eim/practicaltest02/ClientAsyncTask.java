package ro.pub.cs.systems.eim.practicaltest02;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ClientAsyncTask extends AsyncTask<String, String, Void> {

    TextView serverMessageTextView;

    HashMap<String, String> localCache;

    public ClientAsyncTask(TextView serverMessageTextView, HashMap<String, String> localCache) {
        this.serverMessageTextView = serverMessageTextView;
        this.localCache = localCache;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            System.out.println("ClientAsyncTask: " + strings[0]);
            String response = new DefaultHttpClient().execute(
                    new HttpGet(strings[0]),
                    new BasicResponseHandler()
            );
            publishProgress(strings[0], response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        serverMessageTextView.append(progress[1] + "\n");
        localCache.put(progress[0], progress[1]);
    }
}
