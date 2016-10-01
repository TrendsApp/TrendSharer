package trendsapps.org.trendsharer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;

import trendsapps.org.trendsharer.JSONParser.JSONMessage;
import trendsapps.org.trendsharer.JSONParser.JSONParser;
import trendsapps.org.trendsharer.Model.HotDeal;
import trendsapps.org.trendsharer.Model.Packet;
import trendsapps.org.trendsharer.bluetoothService.BluetoothService;
import trendsapps.org.trendsharer.bluetoothService.Constants;
import trendsapps.org.trendsharer.fragments.AddDealsFragment;
import trendsapps.org.trendsharer.fragments.FavouritesFragment;
import trendsapps.org.trendsharer.fragments.HotDealsFragment;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static String receivedMessage = "";
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Button sendButton;
    private int REQUEST_ENABLE_BT = 34;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<BluetoothDevice> deviceList;
    Boolean connected = false;
    /**
     * Member object for the chat services
     */


    private BluetoothService[] mChatServiceArray;




    /**
     * UUIDs
     */
    private final String uuid1 = "8ce255c0-200a-11e0-ac64-0800200c9a66";
    private final String uuid2 = "8ce255c0-200a-11e0-ac64-0800200c8a55";
    private final String uuid3 = "8ce255c0-200a-4df4-ac64-0800200c8a44";
    private final String uuid4 = "7bc24b5f-416c-4c4f-aa3f-9e4caf8354a2";
    private final String uuid5 = "a561e540-c44b-46cc-a9f1-b07c546ee1e2";
    private final String uuid6 = "3d61d75b-fafa-403a-ae8b-fb8754957fb2";
    private final String uuid7 = "1666ffca-5b99-4838-a00b-60a667d5448e";

    /*Number of strings changed*/

    private String[] uuidArray =  {uuid1,uuid2,uuid3,uuid4,uuid5,uuid6,uuid7};
    /**
     * Temp variables
     */
  //  private final String acceptingDeviceAddress = "8C:BE:BE:79:68:13"; // kitta phone


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("Discovery","Start discovery");
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("Discover","found device: " + device.getName());
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                   // mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    for(int i=0;i<3;i++){
                        if(mChatServiceArray[i].getState() == BluetoothService.STATE_LISTEN){
                            Log.i("Connect","Connecting device: " + device.getName() + " to: " + i);
                            mChatServiceArray[i].connect(device,false);
                            break;
                        }
                    }
                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i("Discovery","Finished discovery");
                bluetoothAdapter.startDiscovery();
            }
        }
    };

    public Activity getActivity(){
        return this;
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FragmentActivity activity = (FragmentActivity)getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    Log.i("State","changed");
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Log.i("Connected","#######");
                          /*  setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mConversationArrayAdapter.clear();
                            break;*/
                            // Make a toast
//                            Toast.makeText(this,"Device Connected with another",Toast.LENGTH_LONG);
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            /*setStatus(R.string.title_connecting);
                            break;*/
                            Toast.makeText(getApplicationContext(),"Device Connected",Toast.LENGTH_LONG);
                            break;
                        case BluetoothService.STATE_LISTEN:
                            break;
                        case BluetoothService.STATE_NONE:
                            break;
                          /*  setStatus(R.string.title_not_connected);
                            break;*/
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                   /* byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;*/
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    try{
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    receivedMessage = readMessage;
//                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    readMessage(readMessage);
                    }catch (Exception e){
                        Log.i("Error Message",e.getMessage());
                    }

                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                   /* // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;*/
                case Constants.MESSAGE_TOAST:
                   /* if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;*/
            }
        }
    };

    /**
     * Read message from connected device
     * @param message
     */
    public void readMessage(String message){
        Log.i("Message recieved", message);
        try{
            JSONObject json = new JSONObject(message);
            JSONObject header = (JSONObject)json.get("header");
            JSONObject body = (JSONObject) json.get("body");

            int message_id = (int)header.get("messageID");
            String device_address = (String)header.get("deviceAddress");
            String shop = (String) body.get("shop");
            String discount = (String) body.get("discount");
            String content = (String)body.get("content");
            int duration = (int)body.get("duration");
            String timetamp = (String)body.get("timeStamp");


            HotDeal deal = new HotDeal(message_id,shop,discount);
            deal.setStoredDate(new Timestamp(Long.parseLong(timetamp)));
            deal.setContent(content);
            deal.setDuration(duration);
            DatabaseHandler handler = DatabaseHandler.getInstance(DatabaseHandler.DATABSENAME, "HotDeals", getActivity());
            handler.addDeal(deal);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * create sending message and call sendMessage
     * @param v
     */
    public void sendData(View v){
        try {
            // creating json object
            Random random = new Random();
            int message_id = random.nextInt();
            String device_address = BluetoothAdapter.getDefaultAdapter().getName();
            String shop = "shop " + device_address;
            String discount = "2.5";
            String content = "This is an awesome deal";
            int duration = 2000;
            String timetamp = String.valueOf(new Date().getTime());
            JSONMessage jsonMessage = new JSONMessage(message_id, device_address, shop, discount, content, duration, timetamp);
            //sending message
            for (int i = 0; i < 3; i++)
                this.sendMessage(jsonMessage.getJSONMessage().toString(), mChatServiceArray[i]);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Sends a given message to the given bluetooth service
     *
     * @param message A string of text to send.
     */
    public void sendMessage(String message,BluetoothService service) {
        // Check that we're actually connected before trying anything
        if (service.getState() != BluetoothService.STATE_CONNECTED) {
            Log.i("Not Connected","Message Sending");
            return;
        }
        // Check that there's actually something to send
        if (message.length() > 0) {
            byte[] send = message.getBytes();
            service.write(send);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        for(int i=0;i<7;i++){
            if (mChatServiceArray[i] != null) {
                // Only if the state is STATE_NONE, do we know that we haven't started already
                if (mChatServiceArray[i].getState() == BluetoothService.STATE_NONE) {
                    // Start the Bluetooth chat services
                    mChatServiceArray[i].start();
                }
            }
        }

        ensureDiscoverable();
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (bluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChatServiceArray = new BluetoothService[7];
        for(int i=0;i<7;i++){
            mChatServiceArray[i] = new BluetoothService(this,mHandler,uuidArray[i]);
            Log.i("starting","chat service " + i + " is starting");
        }


        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = receivedMessage;
                Snackbar.make(view, "Latest hot deal is: "+message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        startBluetoothService();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(int i=0;i<3;i++){
            if (mChatServiceArray[i] != null) {
                mChatServiceArray[i] .stop();
            }
        }

        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    private void startBluetoothService(){
        deviceList = new ArrayList<BluetoothDevice>();
        //initializing bluetooth connection
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Log.e("connection","no bluetooth radio found");
        }
        //creating client connection
        if (!bluetoothAdapter.isEnabled()) {
            Log.i("initialization","bluetooth not enabled");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
            discoverAndConnect();
        }
    }

    public void discoverAndConnect(){
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver,filter);

        filter = new IntentFilter(BluetoothDevice.ACTION_UUID);
        registerReceiver(mReceiver,filter);

        for(int i=0;i<7;i++){
            mChatServiceArray[i].start();
        }
        bluetoothAdapter.startDiscovery();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
               Log.i("connection","bluetooth enabled");
                discoverAndConnect();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return AddDealsFragment.newInstance(position +1);
                case 1:
                    return HotDealsFragment.newInstance(position +1);
                case 2:
                    return FavouritesFragment.newInstance(position +1);
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "Favourites";
            }
            return null;
        }
    }


}
