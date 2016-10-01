package trendsapps.org.trendsharer.bluetoothService;

import android.bluetooth.BluetoothAdapter;
import java.io.Serializable;

/**
 * Created by dilan on 9/26/16.
 */
public class
SerializableBluetoothAdapter implements Serializable {
    BluetoothAdapter adapter;

    public SerializableBluetoothAdapter(BluetoothAdapter adapter){
        this.adapter = adapter;
    }

    public BluetoothAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }
}
