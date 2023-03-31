package com.example.app_th1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_th1.dbFake.TblItem;
import com.example.app_th1.model.Item;
import com.example.app_th1.model.ItemAdapter;
import com.example.app_th1.model.SpinnerAdapter;
import com.example.app_th1.utils.DataUtils;
import com.example.app_th1.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemListener {

    private int idUpdate;

    private Spinner spinnerImg;
    private Spinner spinnerText;
    private EditText eName, eDate, eDes, search;
    private RadioButton male, female, both;
    private CheckBox java, python, golang;
    private Button add, update;
    private RecyclerView recyclerView;

    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        add.setOnClickListener(view -> addItem(getDataInForm()));
        update.setOnClickListener(view -> updateItem(getDataInForm()));
        search.addTextChangedListener(doSearch());
        eDate.setOnClickListener(view -> createDatePickerDialog().show());
    }

    private void updateItem(Item item) {
        if (checkAllFields()) {
            item.setId(idUpdate);
            itemAdapter.setItem(item);
            createAdapter(itemAdapter, TblItem.getData());
            Toast.makeText(this, getResources().getString(R.string.message_add_success), Toast.LENGTH_SHORT).show();
            resetForm();
        }
    }

    private void addItem(Item item) {
        if (checkAllFields()) {
            item.setId(++Item.count);
            itemAdapter.addItem(item);
            createAdapter(itemAdapter, TblItem.getData());
            Toast.makeText(this, getResources().getString(R.string.message_add_success), Toast.LENGTH_SHORT).show();
            resetForm();
        }
    }

    private boolean checkAllFields () {
        if (DataUtils.isNullOrEmptyOrBlank(eName.getText().toString())) {
            eName.setError(getResources().getString(R.string.message_error_empty));
            return false;
        }
        if (DataUtils.isNullOrEmptyOrBlank(eDate.getText().toString())) {
            eDate.setError(getResources().getString(R.string.message_error_empty));
            return false;
        }
        if (!DateUtils.isValidate(eDate.getText().toString())) {
            eDate.setError(getResources().getString(R.string.message_error_format_date));
            return false;
        }
        return true;
    }

    private void resetForm() {
        spinnerImg.setSelection(0);
        spinnerText.setSelection(0);
        eName.setText("");
        eDate.setText("");
        eDes.setText("");
        male.setChecked(true);
        java.setChecked(false);
        python.setChecked(false);
        golang.setChecked(false);

        add.setEnabled(true);
        update.setEnabled(false);
    }

    private void initView () {
        spinnerImg = findViewById(R.id.spinnerImage);
        spinnerImg.setAdapter(new SpinnerAdapter(this));
        spinnerText = findViewById(R.id.spinnerText);
        eName = findViewById(R.id.name);
        eDate = findViewById(R.id.date);
        eDes = findViewById(R.id.description);
        search = findViewById(R.id.search);
        male = findViewById(R.id.male);
        female = findViewById(R.id.famale);
        both = findViewById(R.id.both);
        java = findViewById(R.id.java);
        python = findViewById(R.id.python);
        golang = findViewById(R.id.golang);
        add = findViewById(R.id.btnAdd);
        update = findViewById(R.id.btnUpdate);
        recyclerView = findViewById(R.id.recyclerView);
        itemAdapter = new ItemAdapter(this);
        itemAdapter.setItemListener(this);
        createAdapter(itemAdapter, TblItem.getData());
        update.setEnabled(false);
    }

    private void createAdapter (ItemAdapter adapter, List<Item> items) {
        adapter.setItems(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(View view, int position) {
        Item item = itemAdapter.getItem(position);
        setDataInForm(item);
    }

    private void setDataInForm(Item item) {
        idUpdate = item.getId();
        spinnerImg.setSelection(SpinnerAdapter.getItemSelected(item.getImg()));
        String[] months = getResources().getStringArray(R.array.month);
        for (int i = 0; i < months.length; i++) {
            if (months[i].equals(item.getMonth())) {
                spinnerText.setSelection(i);
                break;
            }
        }
        eName.setText(item.getName());
        eDate.setText(item.getDate());
        eDes.setText(item.getDescription());
        if ("male".equals(item.getGender())) {
            male.setChecked(true);
        }
        if ("female".equals(item.getGender())) {
            male.setChecked(true);
        }
        if ("both".equals(item.getGender())) {
            male.setChecked(true);
        }
        item.getLanguages().forEach(l -> {
            if ("Java".equals(l)) java.setChecked(true);
            if ("Python".equals(l)) python.setChecked(true);
            if ("Golang".equals(l)) golang.setChecked(true);
        });
        add.setEnabled(false);
        update.setEnabled(true);
    }

    private Item getDataInForm () {
        Item item = new Item();
        item.setImg((Integer) spinnerImg.getSelectedItem());
        item.setMonth((String) spinnerText.getSelectedItem());
        item.setName(eName.getText().toString());
        item.setDate(eDate.getText().toString());
        item.setDescription(eDes.getText().toString());
        if (male.isChecked()) item.setGender("male");
        if (female.isChecked()) item.setGender("female");
        if (both.isChecked()) item.setGender("both");
        List<String> l = new ArrayList<>();
        if (java.isChecked()) l.add("Java");
        if (python.isChecked()) l.add("Python");
        if (golang.isChecked()) l.add("Golang");
        item.setLanguages(l);
        return item;
    }

    private List<Item> doSearch (String keySearch) {
        List<Item> itemsSearch = new ArrayList<>();
        if (DataUtils.isNullOrEmptyOrBlank(keySearch) || DataUtils.isNullOrEmpty(itemAdapter.getItems()))
            return TblItem.getData();
        itemAdapter.getItems().forEach(item -> {
            if (item.getName().toLowerCase().contains(keySearch.toLowerCase()))
                itemsSearch.add(item);
        });
        return itemsSearch;
    }

    private TextWatcher doSearch () {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                createAdapter(itemAdapter, doSearch(search.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private DatePickerDialog createDatePickerDialog() {
        return new DatePickerDialog(this,
                (datePicker, year, month, day) -> eDate.setText(String.format("%d/%d/%d", day, month, year)),
                DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
    }
}