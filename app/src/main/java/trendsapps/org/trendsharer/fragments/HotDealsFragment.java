package trendsapps.org.trendsharer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import trendsapps.org.trendsharer.DatabaseHandler;
import trendsapps.org.trendsharer.HotDeal;
import trendsapps.org.trendsharer.HotDealAdapter;
import trendsapps.org.trendsharer.R;

public class HotDealsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private DatabaseHandler hotDealsDataBase;
    private RecyclerView recyclerView;
    private HotDealAdapter hotDealAdapter;
    private ArrayList<HotDeal> hotdeals = new ArrayList<>();
    private Button refreshDeals ;

    private SwipeRefreshLayout mSwipeRefreshLayout ;
    public HotDealsFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HotDealsFragment newInstance(int sectionNumber) {
        HotDealsFragment fragment = new HotDealsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         updateList();
                                                         mSwipeRefreshLayout.setRefreshing(false);
                                                     }
                                                 });
//
//        mSwipeRefreshLayout.OnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
//
//            @Override
//            public void onRefresh() {
//                updateList();
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });

        //String has to be replaced with HotDeal object.


        return rootView;
    }

    public void onResume(){
        super.onResume();
        recyclerView.setAdapter(new HotDealAdapter(hotdeals));
    }


    public void updateList(){
        hotDealsDataBase = new DatabaseHandler(DatabaseHandler.DATABSENAME, "HotDeals", getActivity());

        ArrayList<HotDeal> dealsTemp = hotDealsDataBase.getDeals();
        int i = 0;
        while (i < dealsTemp.size()) {
            hotdeals.add(dealsTemp.get(i));
            i++;
        }

        hotDealAdapter = new HotDealAdapter(hotdeals);
        recyclerView.setAdapter(hotDealAdapter);

//
//        hotDealsDataBase = new DatabaseHandler(DatabaseHandler.DATABSENAME, "HotDeals", getActivity());
//
//        ArrayList<HotDeal> dealsTemp = hotDealsDataBase.getDeals();
//        int i = 0;
//        while (i < dealsTemp.size()) {
//            hotdeals.add(dealsTemp.get(i));
//            i++;
//        }
//        hotDealAdapter = new HotDealAdapter(hotdeals);
//        recyclerView.setAdapter(hotDealAdapter);

    }

    public void update(){

    }



}
