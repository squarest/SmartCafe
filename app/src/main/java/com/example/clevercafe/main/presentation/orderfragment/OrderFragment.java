package com.example.clevercafe.main.presentation.orderfragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.order_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = (MainView) getActivity();
        binding.orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        setClickListeners();
        presenter.viewInit();
    }

    private void setClickListeners() {

        binding.submitButton.setOnClickListener(v -> presenter.submitButtonClicked());
        binding.cancelButton.setOnClickListener(v -> showOrders());
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

    }

    @Override
    public void showOrders() {
        mainView.showOrders();
    }

    @Override
    public void updateOrder() {
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
