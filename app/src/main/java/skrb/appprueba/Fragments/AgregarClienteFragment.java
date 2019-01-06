package skrb.appprueba.Fragments;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import infos.BuyInfo;
import infos.ConcreteBuyInfo;
import infos.ConcretePriceInfo;
import infos.PriceInfo;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import writer.ConcreteWriter;
import writer.ExcelWriter;


public class AgregarClienteFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_agregar_cliente, container, false);

        MainActivity act = (MainActivity) getActivity();
        act.getSupportActionBar().setTitle("Agregar Cliente");

        int day, month, year;
        Calendar actualDate = Calendar.getInstance();
        day = actualDate.get(Calendar.DAY_OF_MONTH);
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);
        final DatePickerDialog dialogFecha = new DatePickerDialog(this.getContext(), AgregarClienteFragment.this, year, month, day);

        Button botonFecha = view.findViewById(R.id.BotonFecha);
        botonFecha.setText(day + "/" + (month+1) + "/" + year);
        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFecha.show();
            }
        });

        Button botonConfirmar = view.findViewById(R.id.BotonConfirmar);
        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExcelWriter writer = new ConcreteWriter();

                TextInputEditText input = view.findViewById(R.id.NombreCliente);
                //String name = input.getText().toString();
                String name ="caca";

                input = view.findViewById(R.id.BidonesLlevoDe20);
                int bidones20 = Integer.parseInt(input.getText().toString());

                input = view.findViewById(R.id.BidonesLlevoDe12);
                int bidones12 = Integer.parseInt(input.getText().toString());

                input = view.findViewById(R.id.BidonesDevueltos20);
                int bidones_devueltos_20 = Integer.parseInt(input.getText().toString());

                input = view.findViewById(R.id.BidonesDevueltos12);
                int bidones_devueltos_12 = Integer.parseInt(input.getText().toString());

                input = view.findViewById(R.id.DineroPagado);
                int dinero_pagado = Integer.parseInt(input.getText().toString());

                Button bt = view.findViewById(R.id.BotonFecha);
                String dateString = bt.getText().toString();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = format.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                BuyInfo info = new ConcreteBuyInfo(dinero_pagado,bidones20,bidones12,bidones_devueltos_20,
                        bidones_devueltos_12,date,name);

                /* esto va a cambiar*/
                PriceInfo prices = new ConcretePriceInfo(50,70);

                writer.WriteBuy(info, prices);
            }
        });

        return view;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        MainActivity act = (MainActivity) getActivity();
        Button btn = act.findViewById(R.id.BotonFecha);
        btn.setText(day + "/" + (month+1) + "/" + year);
    }
}
