package skrb.appprueba.Fragments;

import android.Manifest;
import android.R.layout;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;
import androidx.transition.Visibility;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

import daos.BuyDAO;
import database.DatabaseBuilderHelper;
import database.YporaDatabase;
import entities.BuyEntity;
import entities.CustomerEntity;
import entities.ExtraBuyEntity;
import entities.TwelveBuyEntity;
import entities.TwentyBuyEntity;
import skrb.appprueba.MainActivity;
import skrb.appprueba.R;
import skrb.appprueba.R.id;
import skrb.appprueba.R.string;


public class BuscarClienteFragment extends Fragment implements DialogConfirmarFragment.DialogConfirmarListener {
    private static final int FRAGMENT_RESULTADOS = 0;
    private Button btnEliminar;
    private String ultimoCliente;
    private String[] CLIENTES;
    private YporaDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_buscar_cliente, container, false);

        final MainActivity act = (MainActivity) getActivity();
        Objects.requireNonNull(act.getSupportActionBar()).setTitle("Buscar Cliente");

        database = DatabaseBuilderHelper.getDatabase(this.getContext());

        btnEliminar = view.findViewById(id.BotonEliminarCompra);
        Button btnAgregar = view.findViewById(id.BotonBuscarCliente);
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            btnAgregar.setEnabled(false);
        } else {
            if (CLIENTES == null) {
                CLIENTES = database.getCustomerDAO().getCustomerNames().toArray(new String[0]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), layout.simple_dropdown_item_1line, CLIENTES);
            AutoCompleteTextView textView = view.findViewById(id.InputBuscar);

            textView.setAdapter(adapter);
            btnAgregar.setOnClickListener(v -> {
                AutoCompleteTextView txt = getActivity().findViewById(R.id.InputBuscar);
                Editable nombreEditable = txt.getText();
                ultimoCliente = nombreEditable.toString();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

                View focus = getActivity().getCurrentFocus();
                if (focus != null) {
                    imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }

                if (ultimoCliente.length() == 0) {
                    showError();
                } else {
                    //ExcelReader reader = ConcreteReader.getInstance();
                    //File file = FileHelper.findFileRead(ultimoCliente);
                    //OutputInfo out;
                    //if (file.exists()) {
                    /*try {
                        out = reader.readInfo(file);
                    } catch (WorkbookException e) {
                        Log.e("Workbook error ", e.getClass().toString(), e);
                        return;
                    }*/
                    BuyEntity buyEntity = database.getBuyDAO().getLastBuy(ultimoCliente);

                    if (buyEntity == null) {
                        showError();
                    } else {
                        Bundle bnd = new Bundle();

                        CustomerEntity customer =  database.getCustomerDAO().getCustomer(ultimoCliente);

                        TwelveBuyEntity twelveBuyEntity = database.getTwelveBuyDAO().getTwelveBuy(buyEntity.getTwelveId());
                        TwentyBuyEntity twentyBuyEntity = database.getTwentyBuyDAO().getTwentyBuy(buyEntity.getTwentyId());
                        ExtraBuyEntity extraBuyEntity = database.getExtraBuyDAO().getExtraBuy(buyEntity.getExtraBuyId());

                        bnd.putString("name", ultimoCliente);

                        bnd.putDouble("balance", customer.getCustomerBalance());

                        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = format.format(buyEntity.getBuyDate());
                        bnd.putString("date", dateString);

                        bnd.putInt("twelveBalance", customer.getCustomerTwelveBalance());

                        bnd.putInt("twentyBalance", customer.getCustomerTwentyBalance());

                        bnd.putInt("canistersBalance", customer.getCustomerTwelveBalance()+customer.getCustomerTwentyBalance());


                        bnd.putInt("twelveBought", twelveBuyEntity!=null? twelveBuyEntity.getTwelveBoughtAmount(): 0);

                        bnd.putInt("twentyBought", twentyBuyEntity!=null? twentyBuyEntity.getTwentyBoughtAmount(): 0);

                        bnd.putString("description", extraBuyEntity!=null? extraBuyEntity.getExtraBuyDescription(): "");

                        setFragment(FRAGMENT_RESULTADOS, bnd);

                        btnEliminar.setVisibility(View.VISIBLE);
                    }
                    //} else {
                    //  showError();
                    //}
                }
            });
            btnEliminar.setOnClickListener(new OnClickEliminarListener());
        }

        return view;
    }

    private void showError() {
        Bundle bnd = new Bundle();
        bnd.putInt("msg", string.errorBusqueda);
        DialogFragment frag = new ErrorFragment();
        frag.setArguments(bnd);
        frag.show(Objects.requireNonNull(getFragmentManager()), "error");
    }


    private void setFragment(int position, Bundle bnd) {
        MainActivity act = (MainActivity) getActivity();


        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment frag;
        switch (position) {
            case FRAGMENT_RESULTADOS:
                fragmentManager = Objects.requireNonNull(act).getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frag = new MostrarResultadosFragment();
                Visibility transition = new Slide(Gravity.END);
                frag.setExitTransition(transition);
                frag.setEnterTransition(transition);
                frag.setArguments(bnd);
                fragmentTransaction.replace(id.fragment_resultados, frag);
                fragmentTransaction.commit();
                act.findViewById(id.fragment_resultados).setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDialogPositiveClick() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

        View focus = getActivity().getCurrentFocus();
        if (focus != null) {
            imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }

        //ExcelWriter writer = ConcreteWriter.getInstance();
        //File file = FileHelper.findFileRead(ultimoCliente);
        //if (file.exists()) {
        //    writer.deleteBuy(file);
        //}

        BuyDAO dao=database.getBuyDAO();
        dao.delete(dao.getLastBuy(ultimoCliente));

        btnEliminar.setVisibility(View.GONE);
        View fragment = getActivity().findViewById(id.fragment_resultados);
        fragment.setVisibility(View.GONE);
        Snackbar snackbarEliminado = Snackbar.make(getView(), string.msg_eliminado, Snackbar.LENGTH_LONG);
        snackbarEliminado.show();

    }

    @Override
    public int getMessageId() {
        return string.confirmar_eliminar;
    }

    private class OnClickEliminarListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

            View focus = getActivity().getCurrentFocus();
            if (focus != null) {
                imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            }

            DialogFragment frag = new DialogConfirmarFragment();
            frag.show(getFragmentManager(), "confirmar");

        }
    }
}
