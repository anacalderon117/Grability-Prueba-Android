package com.grability.apps.Phone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.grability.apps.Categoria.Categoria;
import com.grability.apps.Categoria.CategoriaListAdapter;
import com.grability.apps.FragmentInterface;
import com.grability.apps.R;
import com.grability.apps.Services.RequestService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ana on 08/11/2016.
 */

public class FragmentCategorias extends Fragment {

    private ListView listView;

    private FragmentInterface mCallback;

    private List<Categoria> categorias;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Init vars
        categorias = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (ListView) view.findViewById(R.id.categories);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = categorias.get(position);
                mCallback.onClicCategoria(categoria.getId());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mostrarCategorias();
    }

    public void mostrarCategorias() {

        categorias.clear();

        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Consultando categorias");
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
                        JSONObject item = apps.getJSONObject(i).getJSONObject("category").getJSONObject("attributes");
                        Categoria nuevaCategoria = new Categoria(item.getString("label"), item.getString("im:id"));

                        boolean contieneCategoria = false;
                        for (int j = 0; j < categorias.size(); j++) {
                            Categoria nc = categorias.get(j);
                            if (nc.getId().equals(nuevaCategoria.getId()) && nc.getLabel().equals(nuevaCategoria.getLabel())) {
                                contieneCategoria = true;
                                break;
                            }
                        }

                        //Agregar categoria solo si no existe en la lista
                        if (!contieneCategoria)
                            categorias.add(nuevaCategoria);
                    }

                    Activity activity = getActivity();
                    if (activity == null)
                        return;

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.dismiss();
                            CategoriaListAdapter categoriaListAdapter = new CategoriaListAdapter(getActivity().getApplicationContext(), R.layout.categoria_list_item, categorias);
                            listView.setAdapter(categoriaListAdapter);
                        }
                    });
                } catch (Exception e) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.dismiss();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (FragmentInterface) context;
    }
}
