package com.grability.apps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ana on 09/11/2016.
 */

public class FragmentDetalle extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        TextView textView = (TextView) view.findViewById(R.id.descripcion);

        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText(bundle.getString("descripcion"));
        }

        return view;
    }
}
