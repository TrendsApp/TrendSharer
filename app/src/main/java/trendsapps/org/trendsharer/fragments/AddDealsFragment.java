package trendsapps.org.trendsharer.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import trendsapps.org.trendsharer.DatabaseHandler;
import trendsapps.org.trendsharer.HotDeal;
import trendsapps.org.trendsharer.R;

public class AddDealsFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button submitDeal;
    private ImageButton hideKeyBoard;
    private EditText shopName;
    private EditText discount;
    private EditText content;
    private HotDeal newDeal;
    private DatabaseHandler hotDealsDataBasse;


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
        addSubmitButton(rootView);
        addHideKeyBoardButton(rootView);
        hotDealsDataBasse = new DatabaseHandler("TrendsSharer-private_database","HotDeals",getActivity());
        return rootView;
    }

    private void addSubmitButton(View view){
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

                if (newDeal.isComplete()){
                    try {
                        hotDealsDataBasse.addDeal(newDeal);
                        Log.i("New entry","New entry has been added for " + shopName.getText().toString());
                        newAlert("New deal added","Thank you for adding a hot deal",android.R.drawable.star_on);
                    }catch (Exception e){
                        Toast.makeText(getActivity(), "Error adding the new deal", Toast.LENGTH_LONG).show();
                        Log.e("Adding record",e.getMessage());
                    }
                }else {
                    Toast.makeText(getActivity(), "Required fields are yet to be filled", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void addHideKeyBoardButton(View view){
        hideKeyBoard = (ImageButton) view.findViewById(R.id.btn_hide_keyboard);
        hideKeyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
    }

    private void newAlert(String title, String message, int icon){
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
}
