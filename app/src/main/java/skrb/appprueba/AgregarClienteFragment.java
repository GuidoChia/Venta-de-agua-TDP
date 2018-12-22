package skrb.appprueba;

import android.support.v4.app.Fragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;


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
        final DatePickerDialog dialogFecha = new DatePickerDialog(this.getContext(), AgregarClienteFragment.this, year, month + 1, day);


        Button botonFecha = view.findViewById(R.id.BotonFecha);
        botonFecha.setText(day + "/" + (month+1) + "/" + year);
        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFecha.show();
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
