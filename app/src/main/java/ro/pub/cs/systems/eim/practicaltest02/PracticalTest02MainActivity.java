package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class PracticalTest02MainActivity extends AppCompatActivity {

    EditText portEditText, urlEditText;

    Button startServerButton, displayInfoButton;

    TextView serverMessageTextView;

    HashMap<String, String> localCache = new HashMap<>();

    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        portEditText = findViewById(R.id.port_edit_text);
        urlEditText = findViewById(R.id.url_edit_text);
        startServerButton = findViewById(R.id.start_server_button);
        displayInfoButton = findViewById(R.id.display_message_button);
        serverMessageTextView = findViewById(R.id.server_message_text_view);

        startServerButton.setOnClickListener( v-> {
            if(serverThread == null)
                serverThread = new ServerThread(portEditText, serverMessageTextView);
            serverThread.startServer();
        });

        displayInfoButton.setOnClickListener( v-> {
            String url = urlEditText.getText().toString();
            if(localCache.containsKey(url)) {
                serverMessageTextView.setText(localCache.get(url));
            } else {
                new ClientAsyncTask(serverMessageTextView, localCache).execute(url);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }
}