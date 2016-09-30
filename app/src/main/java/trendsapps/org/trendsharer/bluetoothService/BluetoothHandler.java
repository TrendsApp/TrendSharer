package trendsapps.org.trendsharer.bluetoothService;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by dilan on 9/26/16.
 */
public class BluetoothHandler extends IntentService{

    public BluetoothHandler(){
        super("bluetoothHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Gets data from the incoming Intent
      //  SerializableBluetoothAdapter serializableBluetoothAdapter = (SerializableBluetoothAdapter)intent.getExtras().getSerializable("bluetoothAdapterBundle");
       /* BluetoothAdapter bluetoothAdapter = serializableBluetoothAdapter.getAdapter();
        Log.i("connectio",bluetoothAdapter.getAddress());*/

    }
}
