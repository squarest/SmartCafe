package com.example.clevercafe.menu.presentation.categories;

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
import com.example.clevercafe.databinding.CategoriesFragmentBinding;
import com.example.clevercafe.entities.ProductCategory;
import com.example.clevercafe.menu.presentation.MenuView;
import com.example.clevercafe.utils.DialogUtil;
import com.example.clevercafe.utils.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by Chudofom on 31.07.17.
 */

public class CategoriesFragment extends MvpAppCompatFragment implements ICategoriesFragment {
    private CategoriesFragmentBinding binding;
    @InjectPresenter
    public CategoriesPresenter presenter;
    private CategoryListAdapter categoryListAdapter;
    private MenuView menuView;
    private boolean isEditMode;
    private ArrayList<ProductCategory> categories = new ArrayList<>();

    public static CategoriesFragment newInstance(boolean isEditMode) {
        Bundle args = new Bundle();
        args.putBoolean("editMode", isEditMode);
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.categories_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        long id = args.getLong("categoryId", -1);
        isEditMode = args.getBoolean("editMode");
        binding.categoryTable.setLayoutManager(new GridLayoutManager(getContext(), 3));
        presenter.categoriesInit();
        menuView = (MenuView) getActivity();
        binding.categoryTable.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                (v, position) -> {
                    menuView.showProducts(categories.get(position).id, categories.get(position).name);
                }));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //edit
            case 1: {
                presenter.editCategoryButClicked(categoryListAdapter.getPosition());
                break;
            }
            //remove
            case 2: {
                DialogUtil.getDeleteAlertDialog(getContext(), "Удаление категории", "Вы действительно хотите удалить категорию?", (dialogInterface, i) -> {
                    presenter.deleteCategoryButClicked(categoryListAdapter.getPosition());
                }).show();
                break;
            }

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void showCategories(ArrayList<ProductCategory> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        categoryListAdapter = new CategoryListAdapter(categories, isEditMode);
        binding.categoryTable.setAdapter(categoryListAdapter);
    }


}
