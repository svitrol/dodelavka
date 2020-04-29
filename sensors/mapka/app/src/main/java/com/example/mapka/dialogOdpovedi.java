package com.example.mapka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class dialogOdpovedi extends AppCompatDialogFragment {
    private EditText editTextStat;
    private EditText editTextMesto;
    private ExampleDialogListener listener;

    public void setListener(ExampleDialogListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_odpovedi, null);

        builder.setView(view)
                .setTitle("Nová Místnost")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Stat = editTextStat.getText().toString();
                        String Mesto=editTextMesto.getText().toString();
                        listener.applyTexts(Stat,Mesto);
                    }
                });

        editTextStat = view.findViewById(R.id.edit_stat);
        editTextMesto = view.findViewById(R.id.edit_mesto);

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface ExampleDialogListener {
        void applyTexts(String Stat,String Mesto);
    }
}
