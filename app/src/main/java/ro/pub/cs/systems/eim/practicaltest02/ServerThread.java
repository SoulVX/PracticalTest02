package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private boolean isRunning;

    private ServerSocket serverSocket;

    private final EditText serverPortEditText;
    private final TextView serverMessageTextView;

    public ServerThread(EditText serverPortEditText, TextView serverMessageTextView) {
        this.serverPortEditText = serverPortEditText;
        this.serverMessageTextView = serverMessageTextView;
    }

    public void startServer() {
        isRunning = true;
        start();
        Log.v(Constants.TAG, "startServer() method was invoked");
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
        }
        Log.v(Constants.TAG, "stopServer() method was invoked");
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(serverPortEditText.getText().toString()));
            while (isRunning) {
                Socket socket = serverSocket.accept();
                if (socket != null) {
                    CommunicationThread communicationThread = new CommunicationThread(socket, serverMessageTextView.getText().toString());
                    communicationThread.start();
                }
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
        }
    }
}