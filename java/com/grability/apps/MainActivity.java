package com.grability.apps;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.grability.apps.App.App;
import com.grability.apps.Phone.FragmentApps;
import com.grability.apps.Phone.FragmentCategorias;
import com.grability.apps.Services.BroadcastService;
import com.grability.apps.Services.FragmentService;
import com.grability.apps.Tablet.FragmentAppsTablet;
import com.grability.apps.Tablet.FragmentCategoriasTablet;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentInterface {

    private BroadcastService broadcastService;
    private FragmentService fragmentService;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (!isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        broadcastService = new BroadcastService(this);
        fragmentService = new FragmentService(getSupportFragmentManager());

        if (isTablet) {
            fragmentService.adicionarFragmento("CATEGORIAS", new FragmentCategoriasTablet(), null);
        } else {
            fragmentService.adicionarFragmento("CATEGORIAS", new FragmentCategorias(), null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        broadcastService.removerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();

        broadcastService.registrarReceiver();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.inicio) {
            if (isTablet) {
                fragmentService.adicionarFragmento("CATEGORIAS", new FragmentCategoriasTablet(), null);
            } else {
                fragmentService.adicionarFragmento("CATEGORIAS", new FragmentCategorias(), null);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClicCategoria(String id) {

        Bundle bundle = new Bundle();
        bundle.putString("idCategoria", id);

        if (isTablet) {
            fragmentService.adicionarFragmento("APPS", new FragmentAppsTablet(), bundle);
            return;
        }
        fragmentService.adicionarFragmento("APPS", new FragmentApps(), bundle);
    }

    @Override
    public void onClicApp(App app) {

        Bundle bundle = new Bundle();
        bundle.putString("label", app.getLabel());
        bundle.putString("descripcion", app.getDescripcion());

        fragmentService.adicionarFragmento("DETALLE", new FragmentDetalle(), bundle);
    }
}
