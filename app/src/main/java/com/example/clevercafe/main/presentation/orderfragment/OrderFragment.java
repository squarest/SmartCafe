package com.example.clevercafe.main.presentation.orderfragment;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.databinding.OrderFragmentBinding;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.main.presentation.MainView;
import com.example.clevercafe.utils.DialogUtil;

/**
 * Created by Chudofom on 10.08.17.
 */

public class OrderFragment extends MvpAppCompatFragment implements IOrderFragment {
    private OrderFragmentBinding binding;
    private OrderAdapter orderAdapter;
    private MainView mainView;
    @InjectPresenter
    public OrderPresenter presenter;

    public static OrderFragment newInstance(long orderId) {
        Bundle args = new Bundle();
        args.putLong("orderId", orderId);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.order_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        mainView = (MainView) getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.orderList.setLayoutManager(layoutManager);
        binding.orderList.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        setClickListeners();
        presenter.viewInit(args.getLong("orderId"));
    }

    private void setClickListeners() {
        binding.submitButton.setOnClickListener(v -> presenter.submitButtonClicked());
        binding.cancelButton.setOnClickListener(v -> showOrders());
        binding.discountButton.setOnClickListener(v -> showDiscountDialog());
    }

    @Override
    public void showOrderAlertDialog(String productName, double maxProductCount) {
        AlertDialog alertDialog = DialogUtil.getWarningAlertDialog(getContext(), "Недостаточно продуктов",
                "Для добавления товара " + productName + " недостаточно продуктов. \n Возможно добавить только " + maxProductCount,
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        alertDialog.show();
    }

    @Override
    public void setOrder(Order order) {
        orderAdapter = new OrderAdapter(order, presenter);
        binding.orderList.setAdapter(orderAdapter);
        binding.orderNumber.setText("ЗАКАЗ №" + order.number);
        binding.orderSum.setText(String.format("Итого: %.2f", order.sum));

    }

    @Override
    public void showOrders() {
        mainView.showOrders();
    }

    @Override
    public void updateOrder(Order order) {
        orderAdapter.notifyDataSetChanged();
        binding.orderSum.setText(String.format("Итого: %.2f", order.sum));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showDiscountDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.discount_dialog);
        EditText editText = dialog.findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    int discount = Integer.valueOf(editable.toString());
                    if (discount > 100) editText.setText("100");
                }
            }
        });
        dialog.setTitle("Скидка");
        Button cancelCategoryButton = dialog.findViewById(R.id.cancel_button);
        cancelCategoryButton.setOnClickListener(v -> dialog.dismiss());
        Button submitCategoryButton = dialog.findViewById(R.id.submit_button);
        submitCategoryButton.setOnClickListener(v ->
        {
            int discount = Integer.valueOf(editText.getText().toString());
            presenter.discountAdded(discount);
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void showCommentDialog(long productId, String comment) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.comment_dialog);
        EditText editText = dialog.findViewById(R.id.edit_text);
        editText.setText(comment);
        dialog.setTitle("Добавление комментария");
        Button cancelCategoryButton = dialog.findViewById(R.id.cancel_button);
        cancelCategoryButton.setOnClickListener(v -> dialog.dismiss());
        Button submitCategoryButton = dialog.findViewById(R.id.submit_button);
        submitCategoryButton.setOnClickListener(v ->
        {
            presenter.commentAdded(productId, editText.getText().toString());
            dialog.dismiss();
        });
        dialog.show();
    }

}