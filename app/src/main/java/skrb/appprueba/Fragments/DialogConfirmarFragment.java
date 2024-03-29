package skrb.appprueba.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import skrb.appprueba.R;

public class DialogConfirmarFragment extends DialogFragment {
    private DialogConfirmarListener mListener;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());

        builder.setMessage(getString(mListener.getMessageId()))
                .setPositiveButton(R.string.CONFIRM, (dialog, id1) -> mListener.onDialogPositiveClick())
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
        void onDialogPositiveClick();

        int getMessageId();
    }
}
