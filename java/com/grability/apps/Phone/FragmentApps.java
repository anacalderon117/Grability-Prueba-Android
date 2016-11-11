package com.grability.apps.Phone;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.grability.apps.App.App;
import com.grability.apps.App.AppListAdapter;
import com.grability.apps.FragmentInterface;
import com.grability.apps.R;
import com.grability.apps.Services.RequestService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ana on 09/11/2016.
 */

public class FragmentApps extends Fragment {

    private View view;

    private ListView listView;

    private FragmentInterface mCallback;

    private String idCategoria;

    private List<App> appsArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Init vars
        appsArray = new ArrayList<>();

        view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (ListView) view.findViewById(R.id.categories);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App app = appsArray.get(position);
                mCallback.onClicApp(app);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            idCategoria = bundle.getString("idCategoria");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mostrarApps();
    }

    public void mostrarApps() {

        appsArray.clear();

        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Consultando aplicaciones");
        progress.setMessage("Espera un momento por favor");
        progress.setCancelable(false);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                RequestService requestService = new RequestService();
                try {
                    JSONObject response = requestService.doGetRequest("https://itunes.apple.com/us/rss/topfreeapplications/limit=20/json");
                    JSONObject feed = response.getJSONObject("feed");
                    JSONArray apps = feed.getJSONArray("entry");

                    for (int i = 0; i < apps.length(); i++) {

                        JSONObject item = apps.getJSONObject(i);
                        JSONObject categoria = item.getJSONObject("category").getJSONObject("attributes");

                        if (categoria.getString("im:id").equals(idCategoria)) {
                            App app = new App(item.getJSONArray("im:image").getJSONObject(2).getString("label"),item.getJSONObject("im:name").getString("label"),item.getJSONObject("summary").getString("label"));
                            appsArray.add(app);
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AppListAdapter categoriaListAdapter = new AppListAdapter(getActivity().getApplicationContext(), R.layout.app_list_item, appsArray);
                            listView.setAdapter(categoriaListAdapter);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progress.dismiss();
            }
        }).start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallback = (FragmentInterface) context;
    }
}
