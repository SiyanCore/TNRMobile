package com.siyanmo.tnrmobile.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.siyanmo.tnrmobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateOrderFragment extends Fragment {


    public UpdateOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_order, container, false);
    }


}
