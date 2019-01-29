package com.easymed.leonty.easymed;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class EditPacient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pacient);

        TextView surname;
        TextView name;
        TextView patronymic;
        TextView dateOfBirth;
        TextView address;
        TextView branch;
        TextView diagnosis;
        TextView note;

        surname = findViewById(R.id.editSurname);
        name = findViewById(R.id.editName);
        patronymic = findViewById(R.id.editPatronymic);
        dateOfBirth = findViewById(R.id.editDateOfBirth);
        address = findViewById(R.id.editAddress);
        branch = findViewById(R.id.editBranch);
        diagnosis = findViewById(R.id.editDiagnosis);
        note = findViewById(R.id.editNote);

        int id = getIntent().getExtras().getInt("id", 0);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

        List<Pacient> pacient = db.pacientDao().loadPacientById(id);

        String pSurname = pacient.get(0).getSurname();
        String pName = pacient.get(0).getName();
        String pPatron = pacient.get(0).getPatronymic();
        String pBirthDate = pacient.get(0).getBirthDate();
        String pAddress = pacient.get(0).getAddress();
        String pBranch = pacient.get(0).getBranch();
        String pDiagnosis = pacient.get(0).getDiagnosis();
        String pNote = pacient.get(0).getNote();

        surname.setText(pSurname);
        name.setText(pName);
        patronymic.setText(pPatron);
        dateOfBirth.setText(pBirthDate);
        address.setText(pAddress);
        branch.setText(pBranch);
        diagnosis.setText(pDiagnosis);
        note.setText(pNote);
    }

    public void finishActivity(View view) {
        finish();
    }
}
