package skrb.appprueba.Fragments;

import android.R.layout;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.R.array;
import skrb.appprueba.R.id;
import skrb.appprueba.helpers.Constants;

import static skrb.appprueba.helpers.Constants.DEF_VALUE_DEST_1;
import static skrb.appprueba.helpers.Constants.DEF_VALUE_DEST_2;
import static skrb.appprueba.helpers.Constants.DEF_VALUE_DEST_5;
import static skrb.appprueba.helpers.Constants.DEF_VALUE_DEST_LITRO;
import static skrb.appprueba.helpers.Constants.PRICE_12;
import static skrb.appprueba.helpers.Constants.PRICE_20;
import static skrb.appprueba.helpers.Constants.PRICE_BOT;
import static skrb.appprueba.helpers.Constants.PRICE_DEST_1;
import static skrb.appprueba.helpers.Constants.PRICE_DEST_2;
import static skrb.appprueba.helpers.Constants.PRICE_DEST_5;
import static skrb.appprueba.helpers.Constants.PRICE_DISP_MESA;
import static skrb.appprueba.helpers.Constants.PRICE_DEST_LITRO;
import static skrb.appprueba.helpers.Constants.PRICE_PREFS;

public class EstablecerPrecioFragment extends Fragment {

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
                    preferencesEditor.putFloat(PRICE_DEST_5, precio);
                } else if (selection.equals(strings[4])) {
                    preferencesEditor.putFloat(PRICE_DEST_2, precio);
                } else if (selection.equals(strings[5])) {
                    preferencesEditor.putFloat(PRICE_DEST_1, precio);
                } else if (selection.equals(strings[6])) {
                    preferencesEditor.putFloat(PRICE_DISP_MESA, precio);
                } else if (selection.equals(strings[7])) {
                    preferencesEditor.putFloat(PRICE_DEST_LITRO, precio);
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

        String[] titles = getResources().getStringArray(R.array.precios_array);

        TextView txt = view.findViewById(id.precio_actual_20);
        txt.setText(titles[0] + ": " + prefs.getFloat(PRICE_20, Constants.DEF_VALUE_20));

        txt = view.findViewById(id.precio_actual_12);
        txt.setText(titles[1] + ": " + prefs.getFloat(PRICE_12, Constants.DEF_VALUE_12));

        txt = view.findViewById(id.precio_actual_bot);
        txt.setText(titles[2] + ": " + prefs.getFloat(PRICE_BOT, Constants.DEF_VALUE_BOT));

        txt = view.findViewById(id.precio_actual_dest_5);
        txt.setText(titles[3] + ": " + prefs.getFloat(PRICE_DEST_5, DEF_VALUE_DEST_5));

        txt = view.findViewById(id.precio_actual_dest_2);
        txt.setText(titles[4] + ": " + prefs.getFloat(PRICE_DEST_2, DEF_VALUE_DEST_2));

        txt = view.findViewById(id.precio_actual_dest_1);
        txt.setText(titles[5] + ": " + prefs.getFloat(PRICE_DEST_1, DEF_VALUE_DEST_1));

        txt = view.findViewById(id.precio_actual_dispenser_mesa);
        txt.setText(titles[6] + ": " + prefs.getFloat(PRICE_DISP_MESA, Constants.DEF_VALUE_DISP_MESA));

        txt = view.findViewById(id.precio_actual_destilada_litro);
        txt.setText(titles[7] + ": " + prefs.getFloat(PRICE_DEST_LITRO, DEF_VALUE_DEST_LITRO));


    }
}
