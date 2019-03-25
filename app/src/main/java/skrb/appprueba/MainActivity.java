package skrb.appprueba;


import android.Manifest.permission;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.transition.Slide;
import android.support.transition.Visibility;
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
import android.view.Gravity;
import android.view.MenuItem;

import java.util.Objects;

import skrb.appprueba.Fragments.AboutFragment;
import skrb.appprueba.Fragments.AgregarClienteFragment;
import skrb.appprueba.Fragments.BuscarClienteFragment;
import skrb.appprueba.Fragments.CalcularAnioFragment;
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
import static skrb.appprueba.Fragments.EstablecerPrecioFragment.DEF_VALUE_DISP_MESA;

/**
 * Main activity for the app. It handles the flow between the principal fragments.
 */
public class MainActivity extends AppCompatActivity {

    private static final int FRAGMENT_AGREGAR = 0;
    private static final int FRAGMENT_BUSCAR = 1;
    private static final int FRAGMENT_ABOUT = 2;
    private static final int FRAGMENT_CALCULAR = 3;
    private static final int FRAGMENT_PRECIO = 4;
    private static final int FRAGMENT_CALCULAR_DIA = 5;
    private static final int FRAGMENT_CALCULAR_RECORRIDO = 6;
    private static final int FRAGMENT_CALCULAR_AÑO = 7;
    public static final String PRICE_PREFS = "prices";
    public static final String PRICE_20 = "price_20";
    public static final String PRICE_12 = "price_12";
    public static final String PRICE_BOT = "price_botellitas";
    public static final String PRICE_DEST = "price_destilada";
    public static final String PRICE_DISP_MESA = "price_dispenser_mesa";
    private static final int PERMISSION_WRITE = 0;
    private static final int ANIMATION_DEFAULT_TIME= 300;

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
                        case id.Calcular_anio:
                            setFragment(FRAGMENT_CALCULAR_AÑO);
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
            edit.putFloat(PRICE_DISP_MESA, DEF_VALUE_DISP_MESA);
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
        if (ContextCompat.checkSelfPermission(this,
                permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_WRITE);

        }
        switch (position) {
            case FRAGMENT_AGREGAR:
                changeFragment(new AgregarClienteFragment());
                break;
            case FRAGMENT_BUSCAR:
                changeFragment(new BuscarClienteFragment());
                break;
            case FRAGMENT_ABOUT:
                changeFragment(new AboutFragment());
                break;
            case FRAGMENT_CALCULAR:
                changeFragment(new CalcularFragment());
                break;
            case FRAGMENT_PRECIO:
                changeFragment(new EstablecerPrecioFragment());
                break;
            case FRAGMENT_CALCULAR_DIA:
                changeFragment(new CalcularDiaFragment());
                break;
            case FRAGMENT_CALCULAR_RECORRIDO:
                changeFragment(new RecorridoFragment());
                break;
            case FRAGMENT_CALCULAR_AÑO:
                changeFragment(new CalcularAnioFragment());
                break;
        }
    }

    private void changeFragment(Fragment frag) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        Visibility transition = new Slide(Gravity.END);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frag.setEnterTransition(transition);
        frag.setExitTransition(transition);
        fragmentTransaction.replace(id.fragment, frag);
        fragmentTransaction.commit();
    }
}
