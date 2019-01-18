package skrb.appprueba.Fragments;

import android.R.layout;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import infos.BuyInfo;
import infos.ConcreteBuyInfo;
import infos.ConcretePriceInfo;
import infos.PriceInfo;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.R.id;
import skrb.appprueba.R.string;
import writer.ConcreteWriter;
import writer.ExcelWriter;

import static skrb.appprueba.Fragments.EstablecerPrecioFragment.DEF_VALUE_12;
import static skrb.appprueba.Fragments.EstablecerPrecioFragment.DEF_VALUE_20;
import static skrb.appprueba.helpers.fileRW.findFileWrite;
import static skrb.appprueba.helpers.fileRW.initClientes;


public class AgregarClienteFragment extends Fragment implements OnDateSetListener {

    private static final String[] CLIENTES = initClientes();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_agregar_cliente, container, false);

        MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Agregar Cliente");

        int day, month, year;
        Calendar actualDate = Calendar.getInstance();
        day = actualDate.get(Calendar.DAY_OF_MONTH);
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);
        final DatePickerDialog dialogFecha = new DatePickerDialog(Objects.requireNonNull(getContext()), this, year, month, day);

        Button botonFecha = view.findViewById(id.BotonFecha);
        botonFecha.setText(day + "/" + (month + 1) + '/' + year);
        botonFecha.setOnClickListener(v -> dialogFecha.show());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()),
                layout.simple_dropdown_item_1line, CLIENTES);
        AutoCompleteTextView textView =
                view.findViewById(id.NombreCliente);

        textView.setAdapter(adapter);



        Button botonConfirmar = view.findViewById(id.BotonConfirmar);
        botonConfirmar.setOnClickListener(new OnClickConfirmarListener(view));

        return view;
    }

    void writeToFile(ExcelWriter writer, EditText[] editTexts, View view) {


        int i = 0;

        EditText input = editTexts[i];
        String name = input.getText().toString();
        i++;

        input = editTexts[i];
        int bidones20 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int bidones12 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int bidones_devueltos_20 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int bidones_devueltos_12 = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int dinero_pagado = Integer.parseInt(input.getText().toString());

        Button bt = view.findViewById(id.BotonFecha);
        String dateString = bt.getText().toString();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            Log.e("Parse error ", e.getStackTrace().toString());
        }

        BuyInfo info = new ConcreteBuyInfo(dinero_pagado, bidones20, bidones12, bidones_devueltos_20,
                bidones_devueltos_12, date, name);


        double twelvePrice,
                twentyPrice;

        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences(MainActivity.PRICE_PREFS, 0);
        twelvePrice = preferences.getFloat(MainActivity.PRICE_12, DEF_VALUE_12);
        twentyPrice = preferences.getFloat(MainActivity.PRICE_20, DEF_VALUE_20);

        PriceInfo prices = new ConcretePriceInfo(twelvePrice, twentyPrice);

        File file = findFileWrite(name);

        writer.WriteBuy(info, prices, file);

        Snackbar snackbarAgregado = Snackbar.make(view, string.msg_agregado, Snackbar.LENGTH_LONG);
        snackbarAgregado.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        MainActivity act = (MainActivity) getActivity();
        Button btn = Objects.requireNonNull(act).findViewById(id.BotonFecha);
        btn.setText(day + "/" + (month + 1) + '/' + year);
    }


    private class OnClickConfirmarListener implements View.OnClickListener {
        private final View view;

        public OnClickConfirmarListener(View view) {
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            ExcelWriter writer = ConcreteWriter.getInstance();

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

            View focus = getActivity().getCurrentFocus();
            if (focus != null) {
                imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            }

            TextInputLayout lay = view.findViewById(R.id.InputLayoutClient);
            EditText[] editTexts = {
                    lay.getEditText(),
                    view.findViewById(R.id.BidonesLlevoDe20),
                    view.findViewById(R.id.BidonesLlevoDe12),
                    view.findViewById(R.id.BidonesDevueltos20),
                    view.findViewById(R.id.BidonesDevueltos12),
                    view.findViewById(R.id.DineroPagado)
            };

            if (checkInputs(editTexts)) {
                writeToFile(writer, editTexts, view);
            } else {
                Bundle bnd = new Bundle();
                bnd.putInt("msg", R.string.errorAgregar);
                DialogFragment frag = new ErrorFragment();
                frag.setArguments(bnd);
                frag.show(getFragmentManager(), "error");
            }


        }

        private boolean checkInputs(EditText[] inputs) {
            for (EditText input : inputs) {
                if (TextUtils.isEmpty(input.getText().toString())) {
                    return false;
                }
            }
            return true;
        }
    }
}
