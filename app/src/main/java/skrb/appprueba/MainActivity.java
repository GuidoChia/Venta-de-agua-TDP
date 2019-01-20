package skrb.appprueba;


import android.Manifest.permission;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Objects;

import skrb.appprueba.Fragments.AboutFragment;
import skrb.appprueba.Fragments.AgregarClienteFragment;
import skrb.appprueba.Fragments.BuscarClienteFragment;
import skrb.appprueba.Fragments.CalcularDiaFragment;
import skrb.appprueba.Fragments.CalcularFragment;
import skrb.appprueba.Fragments.EstablecerPrecioFragment;
import skrb.appprueba.Fragments.RecorridoFragment;
import skrb.appprueba.R.drawable;
import skrb.appprueba.R.id;
import skrb.appprueba.R.layout;

import static skrb.appprueba.Fragments.EstablecerPrecioFragment.DEF_VALUE_12;
import static skrb.appprueba.Fragments.EstablecerPrecioFragment.DEF_VALUE_20;
import static skrb.appprueba.Fragments.EstablecerPrecioFragment.DEF_VALUE_BOT;
import static skrb.appprueba.Fragments.EstablecerPrecioFragment.DEF_VALUE_DEST;

public class MainActivity extends AppCompatActivity {

    private static final int FRAGMENT_AGREGAR = 0;
    private static final int FRAGMENT_BUSCAR = 1;
    private static final int FRAGMENT_ABOUT = 2;
    private static final int FRAGMENT_CALCULAR = 3;
    private static final int FRAGMENT_PRECIO = 4;
    private static final int FRAGMENT_CALCULAR_DIA = 5;
    private static final int FRAGMENT_CALCULAR_RECORRIDO = 6;
    public static final String PRICE_PREFS = "prices";
    public static final String PRICE_20 = "price_20";
    public static final String PRICE_12 = "price_12";
    public static final String PRICE_BOT = "price_botellitas";
    public static final String PRICE_DEST = "price_destilada";
    private static final int PERMISSION_WRITE = 0;

    private DrawerLayout mDrawerLayout;
    private ActionBar actBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        Toolbar tb = findViewById(id.toolbar);
        setSupportActionBar(tb);

        actBar = getSupportActionBar();
        Objects.requireNonNull(actBar).setDisplayHomeAsUpEnabled(true);
        actBar.setHomeAsUpIndicator(drawable.ic_menu);

        mDrawerLayout = findViewById(id.drawer_layout);

        if (ContextCompat.checkSelfPermission(this,
                permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_WRITE);

        }

        NavigationView navView = findViewById(id.NavigationView);
        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.AgregarCliente:
                            setFragment(FRAGMENT_AGREGAR);
                            break;
                        case R.id.BuscarCliente:
                            setFragment(FRAGMENT_BUSCAR);
                            break;
                        case R.id.About:
                            setFragment(FRAGMENT_ABOUT);
                            break;
                        case R.id.Calcular:
                            setFragment(FRAGMENT_CALCULAR);
                            break;
                        case R.id.EstablecerPrecio:
                            setFragment(FRAGMENT_PRECIO);
                            break;
                        case id.Calcular_dia:
                            setFragment(FRAGMENT_CALCULAR_DIA);
                            break;
                        case id.Calcular_recorrido:
                            setFragment(FRAGMENT_CALCULAR_RECORRIDO);
                            break;
                    }

                    mDrawerLayout.closeDrawers();

                    return true;
                }
        );

        initializePrefs();

        if (savedInstanceState == null) {
            setFragment(FRAGMENT_AGREGAR);
        }

    }

    private void initializePrefs() {
        SharedPreferences preferences = getSharedPreferences(PRICE_PREFS, 0);

        if (preferences.getBoolean("first_time", true)) {
            Editor edit = preferences.edit();
            edit.putFloat(PRICE_20, DEF_VALUE_20);
            edit.putFloat(PRICE_12, DEF_VALUE_12);
            edit.putFloat(PRICE_BOT, DEF_VALUE_BOT);
            edit.putFloat(PRICE_DEST, DEF_VALUE_DEST);
            edit.putBoolean("first_time", false);
            edit.apply();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(int position) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag;
        switch (position) {
            case FRAGMENT_AGREGAR:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new AgregarClienteFragment();
                fragmentTransaction.replace(id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_BUSCAR:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new BuscarClienteFragment();
                fragmentTransaction.replace(id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_ABOUT:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new AboutFragment();
                fragmentTransaction.replace(id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_CALCULAR:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new CalcularFragment();
                fragmentTransaction.replace(id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_PRECIO:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new EstablecerPrecioFragment();
                fragmentTransaction.replace(id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_CALCULAR_DIA:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new CalcularDiaFragment();
                fragmentTransaction.replace(id.fragment, frag);
                fragmentTransaction.commit();
                break;
            case FRAGMENT_CALCULAR_RECORRIDO:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new RecorridoFragment();
                fragmentTransaction.replace(id.fragment, frag);
                fragmentTransaction.commit();
                break;
        }
    }
}
