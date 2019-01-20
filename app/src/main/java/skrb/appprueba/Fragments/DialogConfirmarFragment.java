package skrb.appprueba.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import skrb.appprueba.R;

public class DialogConfirmarFragment extends DialogFragment {
    private DialogConfirmarListener mListener;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(getString(R.string.confirmar_compra))
                .setPositiveButton(R.string.CONFIRM, (dialog, id1) -> mListener.onDialogPositiveClick(DialogConfirmarFragment.this))
                .setNegativeButton(R.string.CANCEL, (dialog, which) -> DialogConfirmarFragment.this.getDialog().cancel());

        // Create the AlertDialog object and return it
        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        Fragment frag = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.fragment);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogConfirmarListener) frag;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface DialogConfirmarListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }
}
