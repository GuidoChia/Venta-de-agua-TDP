package skrb.appprueba.Fragments;

import android.R.layout;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.R.array;
import skrb.appprueba.R.id;

import static skrb.appprueba.MainActivity.PRICE_12;
import static skrb.appprueba.MainActivity.PRICE_20;
import static skrb.appprueba.MainActivity.PRICE_BOT;
import static skrb.appprueba.MainActivity.PRICE_DEST;
import static skrb.appprueba.MainActivity.PRICE_DISP_MESA;
import static skrb.appprueba.MainActivity.PRICE_PREFS;

public class EstablecerPrecioFragment extends Fragment {
    public static final int DEF_VALUE_20 = 70;
    public static final int DEF_VALUE_12 = 50;
    public static final int DEF_VALUE_BOT = 120;
    public static final int DEF_VALUE_DEST = 40;
    public static final int DEF_VALUE_DISP_MESA = 180;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_establecer_precio, container, false);

        MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Establecer precio");

        Spinner spinner = view.findViewById(id.spinner_precios);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()),
                array.precios_array, layout.simple_spinner_item);

        adapter.setDropDownViewResource(layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        updatePrecios(view);

        Button btn = view.findViewById(id.BotonConfirmarPrecio);
        btn.setOnClickListener(v -> {
            String selection = spinner.getSelectedItem().toString();
            String[] strings = getResources().getStringArray(R.array.precios_array);

            TextInputLayout lay = view.findViewById(R.id.inputLayoutPrecioFinal);
            EditText editText = lay.getEditText();
            String precioString = editText.getText().toString();

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

            View focus = getActivity().getCurrentFocus();
            if (focus != null) {
                imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            }

            if (!TextUtils.isEmpty(precioString)) {
                SharedPreferences.Editor preferencesEditor = getContext().getSharedPreferences(PRICE_PREFS, 0).edit();
                float precio = Float.parseFloat(precioString);
                if (selection.equals(strings[0])) {
                    preferencesEditor.putFloat(PRICE_20, precio);
                } else if (selection.equals(strings[1])) {
                    preferencesEditor.putFloat(PRICE_12, precio);
                } else if (selection.equals(strings[2])) {
                    preferencesEditor.putFloat(PRICE_BOT, precio);
                } else if (selection.equals(strings[3])) {
                    preferencesEditor.putFloat(PRICE_DEST, precio);
                } else if (selection.equals(strings[4])){
                    preferencesEditor.putFloat(PRICE_DISP_MESA, precio);
                }
                preferencesEditor.apply();
                updatePrecios(view);
            } else {
                Bundle bnd = new Bundle();
                bnd.putInt("msg", R.string.errorEstablecer);
                DialogFragment frag = new ErrorFragment();
                frag.setArguments(bnd);
                frag.show(getFragmentManager(), "error");
            }
        });

        return view;
    }

    private void updatePrecios(View view) {
        SharedPreferences prefs = Objects.requireNonNull(getContext()).getSharedPreferences(PRICE_PREFS, 0);

        TextView txt = view.findViewById(id.precio_actual_20);
        txt.setText("Precio 20: " + prefs.getFloat(PRICE_20, DEF_VALUE_20));


        txt = view.findViewById(id.precio_actual_12);
        txt.setText("Precio 12: " + prefs.getFloat(PRICE_12, DEF_VALUE_12));

        txt = view.findViewById(id.precio_actual_bot);
        txt.setText("Precio botellitas: " + prefs.getFloat(PRICE_BOT, DEF_VALUE_BOT));

        txt = view.findViewById(id.precio_actual_dest);
        txt.setText("Precio destilada: " + prefs.getFloat(PRICE_DEST, DEF_VALUE_DEST));

        txt = view.findViewById(id.precio_actual_dispenser_mesa);
        txt.setText("Precio dispenser de mesa: " + prefs.getFloat(PRICE_DISP_MESA, DEF_VALUE_DISP_MESA));

    }
}
