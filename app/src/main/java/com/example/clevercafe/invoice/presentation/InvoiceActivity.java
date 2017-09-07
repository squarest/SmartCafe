package com.example.clevercafe.invoice.presentation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.base.BaseActivity;
import com.example.clevercafe.databinding.ActivityInvocesBinding;
import com.example.clevercafe.entities.Invoice;
import com.example.clevercafe.invoice.presentation.adapters.InvoiceListAdapter;
import com.example.clevercafe.storage.presentation.invoiceIngredients.InvoiceIngredientActivity;
import com.example.clevercafe.utils.DialogUtil;
import com.example.clevercafe.utils.dateTime.DateTimeUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class InvoiceActivity extends BaseActivity implements InvoiceView {

    @InjectPresenter
    public InvoicePresenter presenter;

    private ActivityInvocesBinding binding;
    private Invoice curInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoces);
        createToolbar("");
        createDrawer();
        binding.invoiceList.setLayoutManager(new LinearLayoutManager(this));
        presenter.viewInit();
        setClickListeners();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1: {//edit
                presenter.editInvoiceButClicked(item.getOrder());
                break;
            }
            case 2: {//remove
                DialogUtil.getDeleteAlertDialog(this, "Удаление накладной", "Вы действительно хотите удалить накладную?", (dialogInterface, i) -> {
                    presenter.invoiceDeleted(item.getOrder());
                }).show();
                break;
            }

        }
        return super.onContextItemSelected(item);
    }

    private void setClickListeners() {
        binding.addInvoiceButton.setOnClickListener(v -> showAddForm());
        binding.submitButton.setOnClickListener(v -> {
            presenter.addInvoice(getInvoice());
            hideForm();
        });
        binding.cancelButton.setOnClickListener(v -> hideForm());
        binding.addIngredientsButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, InvoiceIngredientActivity.class);
            intent.putExtra("invoice", curInvoice);
            startActivityForResult(intent, 0);
        });
        binding.invoiceDate.setOnClickListener(v -> setDate());
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(InvoiceActivity.this,
                (datepicker, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    binding.invoiceDate.setText(DateTimeUtil.dateToString(calendar.getTime()));
                    curInvoice.date = calendar.getTime();
                }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null && data.getSerializableExtra("invoice") != null) {
                Invoice result = (Invoice) data.getSerializableExtra("invoice");
                curInvoice.ingredients = result.ingredients;
                curInvoice.ingredientsCount = result.ingredientsCount;
                curInvoice.sum = result.sum;
            }
        } else Toast.makeText(this, "Добавление отменено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInvoices(ArrayList<Invoice> invoices) {
        binding.invoiceList.setAdapter(new InvoiceListAdapter(invoices));
    }

    @Override
    public void showAddForm() {
        curInvoice = new Invoice(new ArrayList<>());
        clearForm();
        binding.addInvoiceForm.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEditForm(Invoice invoice) {
        clearForm();
        binding.addInvoiceForm.setVisibility(View.VISIBLE);
        curInvoice = invoice;
        setInvoice(invoice);

    }

    private void hideForm() {
        binding.addInvoiceForm.setVisibility(View.INVISIBLE);
    }

    private void clearForm() {
        binding.invoiceName.setText("");
        binding.supplierName.setText("");
        binding.invoiceDate.setText("");
    }

    private void setInvoice(Invoice invoice) {
        binding.invoiceName.setText(invoice.name);
        binding.supplierName.setText(invoice.supplierName);
        binding.invoiceDate.setText("");
    }

    private Invoice getInvoice() {
        // TODO: 21.08.17 check empty fields
        curInvoice.name = binding.invoiceName.getText().toString();
        curInvoice.supplierName = binding.supplierName.getText().toString();
        return curInvoice;
    }
}
