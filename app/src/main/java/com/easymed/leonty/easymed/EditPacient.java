package com.easymed.leonty.easymed;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

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

        String pSurname = pacient.getSurname();
        String pName = pacient.getName();
        String pPatron = pacient.getPatronymic();
        String pBirthDate = pacient.getBirthDate();
        String pAddress = pacient.getAddress();
        String pBranch = pacient.getBranch();
        String pDiagnosis = pacient.getDiagnosis();
        String pNote = pacient.getNote();

        surname.setText(pSurname);
        name.setText(pName);
        patronymic.setText(pPatron);
        dateOfBirth.setText(pBirthDate);
        address.setText(pAddress);
        branch.setText(pBranch);
        diagnosis.setText(pDiagnosis);
        note.setText(pNote);

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void finishActivity(View view) {
        finish();
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
