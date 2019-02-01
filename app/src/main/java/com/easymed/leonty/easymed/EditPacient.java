package com.easymed.leonty.easymed;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;

public class EditPacient extends AppCompatActivity implements DeleteDialogFragment.DeleteDialogListener {

    int id;
    AppDatabase db;
    Pacient pacient;

    TextView surname;
    TextView name;
    TextView patronymic;
    TextView dateOfBirth;
    TextView address;
    TextView branch;
    TextView diagnosis;
    TextView note;

    String pSurname;
    String pName;
    String pPatron;
    String pBirthDate;
    String pAddress;
    String pBranch;
    String pDiagnosis;
    String pNote;

    List<PacientFields> pacientFieldsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pacient);

        surname = findViewById(R.id.editSurname);
        name = findViewById(R.id.editName);
        patronymic = findViewById(R.id.editPatronymic);
        dateOfBirth = findViewById(R.id.editDateOfBirth);
        address = findViewById(R.id.editAddress);
        branch = findViewById(R.id.editBranch);
        diagnosis = findViewById(R.id.editDiagnosis);
        note = findViewById(R.id.editNote);

        id = getIntent().getExtras().getInt("id", 0);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

        pacient = db.pacientDao().loadPacientById(id);

        pSurname = pacient.getSurname();
        pName = pacient.getName();
        pPatron = pacient.getPatronymic();
        pBirthDate = pacient.getBirthDate();
        pAddress = pacient.getAddress();
        pBranch = pacient.getBranch();
        pDiagnosis = pacient.getDiagnosis();
        pNote = pacient.getNote();

        surname.setText(pSurname);
        name.setText(pName);
        patronymic.setText(pPatron);
        dateOfBirth.setText(pBirthDate);
        address.setText(pAddress);
        branch.setText(pBranch);
        diagnosis.setText(pDiagnosis);
        note.setText(pNote);

        new PacientFields(surname, pSurname);
        new PacientFields(name, pName);
        new PacientFields(patronymic, pPatron);
        new PacientFields(dateOfBirth, pBirthDate);
        new PacientFields(address, pAddress);
        new PacientFields(branch, pBranch);
        new PacientFields(diagnosis, pDiagnosis);
        new PacientFields(note, pNote);

        coloriser(pacientFieldsList);
    }

    class PacientFields {
        TextView textView;
        String value;

        PacientFields(TextView textView, String value) {
            this.textView = textView;
            this.value = value;
            pacientFieldsList.add(this);
        }
    }

    void coloriser(List<PacientFields> list) {

        for (PacientFields element : list) {
            final TextView textView = element.textView;
            final String value = element.value;
            final Drawable background = textView.getBackground();

            textView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(!textView.getText().toString().equals(value)) {
                        textView.setBackgroundColor(Color.YELLOW);
                    }
                    else {
                        textView.setBackground(background);
                    }
                }
            });
        }
    }

    public void finishActivity(View view) {
        finish();
    }

    public void saveChanges(View view) {
        if(!(surname.getText().toString().isEmpty() && name.getText().toString().isEmpty() && patronymic.getText().toString().isEmpty())) {
            db.pacientDao().updatePacient(
                    surname.getText().toString(),
                    name.getText().toString(),
                    patronymic.getText().toString(),
                    dateOfBirth.getText().toString(),
                    address.getText().toString(),
                    diagnosis.getText().toString(),
                    branch.getText().toString(),
                    note.getText().toString(),
                    id
            );
            Intent intent = new Intent(EditPacient.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    R.string.PacientNameToaster, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void showDeleteDialog(View view) {
        DialogFragment dialog = new DeleteDialogFragment();
        dialog.show(getSupportFragmentManager(), "DeleteDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        db.pacientDao().deletePacient(pacient);
        Intent intent = new Intent(EditPacient.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
}
