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

import skrb.appprueba.MainActivity;
import skrb.appprueba.R;

import static skrb.appprueba.MainActivity.PRICE_12;
import static skrb.appprueba.MainActivity.PRICE_20;
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
                    SharedPreferences.Editor preferencesEditor = getContext().getSharedPreferences(PRICE_PREFS,0).edit();
                    float precio = Float.parseFloat(precioString);
                    if (selection.equals(strings[0])) {
                        preferencesEditor.putFloat(PRICE_20, precio);
                    } else {
                        preferencesEditor.putFloat(PRICE_12,precio);
                    }
                    preferencesEditor.commit();
                }else {
                    ErrorFragment frag = new ErrorFragment();
                    frag.show(getFragmentManager(),"error");
                }
            }
        });

        return view;
    }
}
