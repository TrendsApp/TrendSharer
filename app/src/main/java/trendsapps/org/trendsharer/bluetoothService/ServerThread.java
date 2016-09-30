package trendsapps.org.trendsharer.bluetoothService;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by dilan on 9/26/16.
 */
public class ServerThread extends Thread {

    private final BluetoothServerSocket serverSocket;
    private BluetoothAdapter bluetoothAdapter;

    public ServerThread(BluetoothAdapter bluetoothAdapter){
        this.bluetoothAdapter = bluetoothAdapter;
        BluetoothServerSocket tmp = null;
        try{
            tmp = this.bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("trend", UUID.fromString("f3c74f47-1d38-49ed-8bbc-0369b3eb277c"));
        }catch (IOException e){

        }
        serverSocket = tmp;
    }

    public void run(){
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {

            try {
                socket = serverSocket.accept();
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    //   manageConnectedSocket(socket);
                    serverSocket.close();
                    break;
                }
            } catch (Exception e) {
                break;
            }

        }

    }
}
