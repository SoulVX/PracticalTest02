package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.net.Socket;

public class CommunicationThread extends Thread {
    private final Socket socket;
    private final String serverTextEditText;
    public CommunicationThread(Socket socket, String serverTextEditText) {
        this.socket = socket;
        this.serverTextEditText = serverTextEditText;
    }

    @Override
    public void run() {
        try {
            Log.v(Constants.TAG, "Connection opened with " + socket.getInetAddress() + ":" + socket.getLocalPort());
            Utilities.getWriter(socket).println(serverTextEditText);
            socket.close();
            Log.v(Constants.TAG, "Connection closed");
        } catch (Exception exception) {
            Log.e(Constants.TAG, "An exception has occurred: " + exception.getMessage());
        }
    }
}
