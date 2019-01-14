package skrb.appprueba.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import skrb.appprueba.MainActivity;
import skrb.appprueba.R;

import static skrb.appprueba.MainActivity.PRICE_12;
import static skrb.appprueba.MainActivity.PRICE_20;
import static skrb.appprueba.MainActivity.PRICE_BOT;
import static skrb.appprueba.MainActivity.PRICE_DEST;
import static skrb.appprueba.MainActivity.PRICE_PREFS;

public class EstablecerPrecioFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_establecer_precio, container, false);

        MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Establecer precio");

        Spinner spinner = view.findViewById(R.id.spinner_precios);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.precios_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        updatePrecios(view);

        Button btn = view.findViewById(R.id.BotonConfirmarPrecio);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = spinner.getSelectedItem().toString();
                String[] strings = getResources().getStringArray(R.array.precios_array);

                TextInputLayout lay = view.findViewById(R.id.inputLayoutPrecioFinal);
                EditText editText = lay.getEditText();
                String precioString = editText.getText().toString();

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
                    }
                    preferencesEditor.commit();
                    updatePrecios(view);
                } else {
                    Bundle bnd = new Bundle();
                    bnd.putInt("msg", R.string.errorEstablecer);
                    ErrorFragment frag = new ErrorFragment();
                    frag.setArguments(bnd);
                    frag.show(getFragmentManager(), "error");
                }
            }
        });

        return view;
    }

    private void updatePrecios(View view){
        SharedPreferences prefs = getContext().getSharedPreferences(PRICE_PREFS, 0);

        TextView txt = view.findViewById(R.id.precio_actual_20);
        txt.setText("Precio 20: " +prefs.getFloat(PRICE_20,70));

        txt = view.findViewById(R.id.precio_actual_12);
        txt.setText("Precio 12: "+prefs.getFloat(PRICE_12,50));

        txt = view.findViewById(R.id.precio_actual_bot);
        txt.setText("Precio botellitas: "+prefs.getFloat(PRICE_BOT,120));

        txt = view.findViewById(R.id.precio_actual_dest);
        txt.setText("Precio destilada: "+prefs.getFloat(PRICE_DEST,40));

    }
}
