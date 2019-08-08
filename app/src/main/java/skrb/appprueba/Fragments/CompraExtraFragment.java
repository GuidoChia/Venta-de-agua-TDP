package skrb.appprueba.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import infos.ConcreteExtraBuyInfo;
import infos.ExtraBuyInfo;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import writer.ConcreteWriter;
import writer.ExcelWriter;

import static skrb.appprueba.helpers.FileHelper.findFileWrite;
import static skrb.appprueba.helpers.FileHelper.initClientes;

public class CompraExtraFragment extends Fragment implements DatePickerDialog.OnDateSetListener, DialogConfirmarFragment.DialogConfirmarListener {
    private String[] CLIENTES;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_compra_extra, container, false);

        MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Agregar Compra Extra");

        int day, month, year;
        Calendar actualDate = Calendar.getInstance();
        day = actualDate.get(Calendar.DAY_OF_MONTH);
        month = actualDate.get(Calendar.MONTH);
        year = actualDate.get(Calendar.YEAR);
        final DatePickerDialog dialogFecha = new DatePickerDialog(Objects.requireNonNull(getContext()), this, year, month, day);

        Button botonFecha = view.findViewById(R.id.BotonFecha);
        botonFecha.setText(day + "/" + (month + 1) + '/' + year);
        botonFecha.setOnClickListener(v -> dialogFecha.show());

        Button botonConfirmar = view.findViewById(R.id.BotonConfirmar);
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            botonConfirmar.setEnabled(false);
        } else {
            if (CLIENTES == null) {
                CLIENTES = initClientes();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                    android.R.layout.simple_dropdown_item_1line, CLIENTES);
            AutoCompleteTextView textView = view.findViewById(R.id.NombreCliente);

            textView.setAdapter(adapter);

            botonConfirmar.setOnClickListener(new CompraExtraFragment.OnClickConfirmarListener(view));
        }

        return view;
    }


    private void writeToFile(ExcelWriter writer, EditText[] editTexts, View view) {


        int i = 0;

        EditText input = editTexts[i];
        String name = input.getText().toString();
        i++;

        input = editTexts[i];
        int paid = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        int total = Integer.parseInt(input.getText().toString());
        i++;

        input = editTexts[i];
        String description = input.getText().toString();
        i++;

        Button bt = view.findViewById(R.id.BotonFecha);
        String dateString = bt.getText().toString();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            Log.e("Parse error ", e.getClass().toString(), e);
        }

        ExtraBuyInfo info = new ConcreteExtraBuyInfo(paid, total, description, name, date);

        File file = findFileWrite(name);

        writer.writeExtraBuy(info, file);

        Snackbar snackbarAgregado = Snackbar.make(view, R.string.msg_agregado, Snackbar.LENGTH_LONG);
        snackbarAgregado.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        MainActivity act = (MainActivity) getActivity();
        Button btn = Objects.requireNonNull(act).findViewById(R.id.BotonFecha);
        btn.setText(day + "/" + (month + 1) + '/' + year);
    }

    private boolean checkInputs(EditText[] inputs) {
        for (EditText input : inputs) {
            if (TextUtils.isEmpty(input.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDialogPositiveClick() {

        View view = CompraExtraFragment.this.getView();

        ExcelWriter writer = ConcreteWriter.getInstance();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

        View focus = getActivity().getCurrentFocus();
        if (focus != null) {
            imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }

        EditText[] editTexts = getEditTexts(view);
        writeToFile(writer, editTexts, view);

    }

    @Override
    public int getMessageId() {
        return R.string.confirmar_compra;
    }

    @NonNull
    private EditText[] getEditTexts(View view) {
        TextInputLayout lay = view.findViewById(R.id.InputLayoutClient);
        return new EditText[]{
                lay.getEditText(),
                view.findViewById(R.id.DineroPagado),
                view.findViewById(R.id.precio_total),
                view.findViewById(R.id.descripcion),
        };
    }

    private class OnClickConfirmarListener implements View.OnClickListener {
        private final View view;

        OnClickConfirmarListener(View view) {
            this.view = view;
        }

        @Override
        public void onClick(View v) {

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

            View focus = getActivity().getCurrentFocus();
            if (focus != null) {
                imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            }

            EditText[] editTexts = getEditTexts(view);

            if (checkInputs(editTexts)) {
                DialogFragment frag = new DialogConfirmarFragment();
                frag.show(getFragmentManager(), "confirmar");
            } else {
                Bundle bnd = new Bundle();
                bnd.putInt("msg", R.string.errorAgregar);
                DialogFragment frag = new ErrorFragment();
                frag.setArguments(bnd);
                frag.show(getFragmentManager(), "error");
            }


        }


    }
}
