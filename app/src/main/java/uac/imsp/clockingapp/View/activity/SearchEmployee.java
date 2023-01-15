package uac.imsp.clockingapp.View.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import uac.imsp.clockingapp.Controller.control.SearchEmployeeController;
import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.EmployeeListViewAdapter;
import uac.imsp.clockingapp.View.util.IsearchEmployeeView;
import uac.imsp.clockingapp.View.util.ToastMessage;

public class SearchEmployee extends AppCompatActivity
    implements IsearchEmployeeView,
        AdapterView.OnItemClickListener,View.OnClickListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener,
        View.OnFocusChangeListener,
        DialogInterface.OnClickListener {
    private  ListView list;
    EmployeeListViewAdapter adapter;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    SearchEmployeeController searchEmployeePresenter;

    private int  Number;
    private  androidx.appcompat.widget.SearchView search;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_employee);
        ( (Toolbar) findViewById(R.id.toolbar)).setTitle(R.string.menu_search_button);
        initView();
        searchEmployeePresenter=new SearchEmployeeController(this);
        //get employees list onStart
        searchEmployeePresenter.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Result result =  adapter.getItem(position);
        Number= result.getNumber();
        searchEmployeePresenter.onEmployeeSelected(Number);
    }

    @Override
    public void onNoEmployeeFound() {
        String message=getString(R.string.no_employee);
        list.setVisibility(View.GONE);
        new ToastMessage(this,message);
    }

    @Override
    public void onEmployeeFound(ArrayList<Result> employeeList) {
        adapter = new EmployeeListViewAdapter(this, employeeList);
        this.list.setAdapter(adapter);
        this.list.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmployeeSelected() {
        String tilte=getString(R.string.select_option);

        String  [] options=getResources().getStringArray(R.array.actions);
        builder=new AlertDialog.Builder(this);
        builder.setTitle(tilte);
        builder.setItems(options,this);
        dialog=builder.create();
        dialog.show();



    }

    @Override
    public void onUpdate() {

        startActivity( (new Intent(SearchEmployee.this,UpdateEmployee.class)).putExtra("ACTION_NUMBER",Number));
    }

    @Override
    public void onDelete() {
        Intent intent = new Intent(SearchEmployee.this, DeleteEmployee.class);
        intent.putExtra("CURRENT_USER",getIntent().getIntExtra("CURRENT_USER",0)
        );
        intent.putExtra("ACTION_NUMBER",Number);
        startActivity(intent);

    }

    @Override
    public void onPresenceReport() {

        startActivity((new Intent(SearchEmployee.this,ConsultPresenceReport.class)).
                putExtra("ACTION_NUMBER",Number));

    }

    @Override
    public void onStatistics() {

        startActivity((new Intent(SearchEmployee.this,ConsultStatisticsByEmployee.class)).
                putExtra("ACTION_NUMBER",Number));

    }

    public void initView(){
        search = findViewById(R.id.search);
        search.setOnSearchClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list=findViewById(R.id.employee_list);
        list.setVisibility(View.GONE);
        search.setOnQueryTextListener(this);
        search.setOnFocusChangeListener(this);
        list.setOnItemClickListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String data = search.getQuery().toString().trim();
        searchEmployeePresenter.onSearch(data);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        onQueryTextSubmit(newText);
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        searchEmployeePresenter.onOptionSelected(which);

    }

    @Override
    public void onClick(View v) {
        search.setIconifiedByDefault(false);

    }

    @Override
    public void onFocusChange(@NonNull View v, boolean hasFocus) {
        v.requestFocus();

    }
}