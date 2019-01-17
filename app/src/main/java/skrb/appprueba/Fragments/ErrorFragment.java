package skrb.appprueba.Fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Objects;

import skrb.appprueba.R;
import skrb.appprueba.R.string;

public class ErrorFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());

        int id = Objects.requireNonNull(getArguments()).getInt("msg");
        String msg = getString(id);
        builder.setMessage(msg)
                .setPositiveButton(string.OK, (dialog, id1) -> {
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}