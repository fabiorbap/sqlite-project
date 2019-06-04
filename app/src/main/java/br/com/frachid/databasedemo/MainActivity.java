package br.com.frachid.databasedemo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseAdapter databaseAdapter;
    EditText name, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        setupDatabaseHelper();
    }

    private void setupDatabaseHelper() {
        databaseAdapter = new DatabaseAdapter(this);
    }

    public void addUser(View view) {
        String nameValue = name.getText().toString().trim();
        String emailValue = email.getText().toString().trim();
        long id = databaseAdapter.insertData(nameValue, emailValue);
        if (id < 0){
            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "One record successfully inserted", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewUsers(View view) {
        Toast.makeText(this, databaseAdapter.getAllData(), Toast.LENGTH_LONG).show();
    }
}
