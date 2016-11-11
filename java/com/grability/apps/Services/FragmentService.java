package com.grability.apps.Services;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.grability.apps.R;

/**
 * Created by ana on 08/11/2016.
 */

public class FragmentService {

    FragmentManager fragmentManager;
    String FRAGMENT_TAG_PRINCIPAL = null;

    public FragmentService (FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    /**
     * Adiciona un fragmento
     * @param tag : Tag del fragmento
     * @param newFragment : Nuevo Fragment Class
     * @param bundle : Argumentos
     */
    public void adicionarFragmento (String tag, Fragment newFragment, Bundle bundle) {

        Boolean esPrimerFragmento = false;

        if (FRAGMENT_TAG_PRINCIPAL == null)
            esPrimerFragmento = true;

        FRAGMENT_TAG_PRINCIPAL = tag;

        if (bundle != null)
            newFragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment, newFragment, tag);
        // Si no es la primera vista la agrega
        if (!esPrimerFragmento)
            ft.addToBackStack(tag);

        ft.commit();
    }
}

