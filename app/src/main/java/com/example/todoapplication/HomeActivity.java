package com.example.todoapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.todoapplication.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final TodoFragment todoFragment = new TodoFragment();
    private final StatisticFragment statisticFragment = new StatisticFragment();
    private final MaterialDatePicker<Long> dueDatePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Select Due Date")
            .setCalendarConstraints(new CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build())
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Hide Action Bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();


        loadFragment(new TodoFragment());

        binding.navView.setOnNavigationItemSelectedListener(this);
        binding.addTodoFab.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        return loadFragment(item.getItemId() == R.id.todo_menu_item ? todoFragment : statisticFragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_todo_fab) {

            View view = getLayoutInflater().inflate(R.layout.fragment_add_todo, null);

            TextInputEditText titleInputText = view.findViewById(R.id.titile_text_edit);
            TextInputEditText dateLayout = view.findViewById(R.id.due_date_edit_text);
            dateLayout.setOnClickListener(this);
            dateLayout.setText(new SimpleDateFormat("dd/MM/yyyy").format(Date.from(Instant.now())));

            dueDatePicker.addOnPositiveButtonClickListener(selection -> {
                dateLayout.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(selection)));
            });

            dateLayout.setOnClickListener(v1 -> dueDatePicker.show(getSupportFragmentManager(), ""));

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Add To Do");
            dialog.setView(view);
            dialog.setCancelable(false);
            dialog.setNegativeButton("Cancel", (dialog1, which) -> {
            });
            dialog.setPositiveButton("Add Todo", null);


            AlertDialog inflatedDialog = dialog.create();
            inflatedDialog.setOnShowListener(dialog1 -> {
                Button button = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(view1 -> {
                    String title = titleInputText.getText().toString();
                    if (title.isEmpty()) {
                        titleInputText.setError("Title Cannot be Null");
                    } else {
                        try {
                            Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateLayout.getText().toString());
                            TodoFragment.viewModel.addTodo(new Todo(title, dueDate, false)).addOnCompleteListener(task -> {
                                if (task.isSuccessful())
                                    dialog1.dismiss();
                            });

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
            inflatedDialog.show();
        }
    }
}