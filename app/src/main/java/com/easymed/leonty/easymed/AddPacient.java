package com.easymed.leonty.easymed;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPacient extends AppCompatActivity {

    EditText surname;
    EditText name;
    EditText patronymic;
    EditText birth;
    EditText address;
    EditText diagnosis;
    EditText branch;
    EditText note;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pacient);

        surname = findViewById(R.id.editSurname);
        name = findViewById(R.id.editName);
        patronymic = findViewById(R.id.editPatronymic);
        birth = findViewById(R.id.editDateOfBirth);
        address = findViewById(R.id.editAddress);
        diagnosis = findViewById(R.id.editDiagnosis);
        branch = findViewById(R.id.editBranch);
        note = findViewById(R.id.editNote);
        save = findViewById(R.id.buttonSave);

        new DateOfBirthPicker(AddPacient.this, birth);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(surname.getText().toString().isEmpty() && name.getText().toString().isEmpty() && patronymic.getText().toString().isEmpty())) {
                    Pacient pacient = new Pacient(
                            surname.getText().toString(),
                            name.getText().toString(),
                            patronymic.getText().toString(),
                            birth.getText().toString(),
                            address.getText().toString(),
                            diagnosis.getText().toString(),
                            branch.getText().toString(),
                            note.getText().toString());
                    db.pacientDao().insertAll(pacient);

                    startActivity(new Intent(AddPacient.this, MainActivity.class));
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            R.string.PacientNameToaster, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    public void finishActivity(View view) {
        finish();
    }
}
