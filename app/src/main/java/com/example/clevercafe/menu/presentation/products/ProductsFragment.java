package com.example.clevercafe.menu.presentation.products;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.clevercafe.R;
import com.example.clevercafe.databinding.ProductsFragmentBinding;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.utils.DialogUtil;
import com.example.clevercafe.utils.RecyclerItemClickListener;
import com.example.clevercafe.utils.Utility;

import java.util.ArrayList;

/**
 * Created by Chudofom on 31.07.17.
 */

public class ProductsFragment extends MvpAppCompatFragment implements IProductsFragment {
    private ProductsFragmentBinding binding;
    @InjectPresenter
    public ProductsPresenter presenter;
    private ProductListAdapter productListAdapter;
    private boolean isEditMode;

    public static ProductsFragment newInstance(long categoryId, boolean isEditMode) {
        Bundle args = new Bundle();
        args.putLong("categoryId", categoryId);
        args.putBoolean("editMode", isEditMode);
        ProductsFragment fragment = new ProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.products_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        long id = args.getLong("categoryId", -1);
        isEditMode = args.getBoolean("editMode");
        if (id == -1) {
            new NullPointerException().printStackTrace();
        } else {
            binding.productTable.setLayoutManager(new GridLayoutManager(getContext(),
                    Utility.calculateNoOfColumns(getContext())));
            binding.productTable.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                    (v, position) -> {
                        presenter.productClicked(position);
                    }));
            presenter.productsInit(id);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //edit
            case 1: {
                presenter.editProductButClicked(productListAdapter.getPosition());
                break;
            }
            //remove
            case 2: {
                DialogUtil.getDeleteAlertDialog(getContext(), "Удаление продукта", "Вы действительно хотите удалить продукт?", (dialogInterface, i) -> {
                    presenter.deleteProductButClicked(productListAdapter.getPosition());
                }).show();
                break;
            }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void showProducts(ArrayList<Product> products) {
        productListAdapter = new ProductListAdapter(products, isEditMode);
        binding.productTable.setAdapter(productListAdapter);
    }

}
