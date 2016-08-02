package trendsapps.org.trendsharer.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trendsapps.org.trendsharer.DatabaseHandler;
import trendsapps.org.trendsharer.HotDeal;
import trendsapps.org.trendsharer.R;

public class AddDealsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button submitDeal;
    private ImageButton hideKeyBoard;
    private ImageButton takeASnap;
    private ImageView imageView;
    private EditText shopName;
    private EditText discount;
    private EditText content;
    private HotDeal newDeal;
    private DatabaseHandler hotDealsDataBase;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Bitmap image = null;
    private Uri outputFileUri;


    public AddDealsFragment() {
    }


    public static AddDealsFragment newInstance(int sectionNumber) {
        AddDealsFragment fragment = new AddDealsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_deals, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        addSubmitButton(rootView);
        addHideKeyBoardButton(rootView);
        addTakeASnapButton(rootView);
        hotDealsDataBase = new DatabaseHandler("TrendsSharer-private_database", "HotDeals", getActivity());
        return rootView;
    }

    private void addSubmitButton(View view) {
        submitDeal = (Button) view.findViewById(R.id.btn_signup);
        shopName = (EditText) view.findViewById(R.id.input_shopName);
        discount = (EditText) view.findViewById(R.id.input_discount);
        content = (EditText) view.findViewById(R.id.input_content);

        newDeal = new HotDeal();
        submitDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newDeal.setShopName(shopName.getText().toString());
                newDeal.setDiscount(discount.getText().toString());
                newDeal.setContent(content.getText().toString());
                if (image!= null){
                    newDeal.setImage(image);
                }

                if (newDeal.isComplete()) {
                    try {
                        hotDealsDataBase.addDeal(newDeal);
                        Log.i("New entry", "New entry has been added for " + shopName.getText().toString());
                        newAlert("New deal added", "Thank you for adding a hot deal", android.R.drawable.star_on);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error adding the new deal", Toast.LENGTH_LONG).show();
                        Log.e("Adding record", e.getMessage());
                    }
                } else {
                    Toast.makeText(getActivity(), "Required fields are yet to be filled", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void addHideKeyBoardButton(View view) {
        hideKeyBoard = (ImageButton) view.findViewById(R.id.btn_hide_keyboard);
        hideKeyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
    }

    private void addTakeASnapButton(View view) {
        takeASnap = (ImageButton) view.findViewById(R.id.btn_takeASnap);
        takeASnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageIntent();
            }
        });
    }

    private void newAlert(String title, String message, int icon) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shopName.getText().clear();
                        discount.getText().clear();
                        content.getText().clear();
                    }
                })
                .setIcon(icon)
                .show();
    }

    private void openImageIntent() {


        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "trendSharer.png";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);


        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        getActivity().startActivityFromFragment(this, chooserIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }
                try {
                    image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    imageView.setImageBitmap(image);
                    Toast.makeText(getActivity(), "Image successfully loaded", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getActivity(), "Problem capturing the image", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }

}
