package com.example.clevercafe.activities.menu;

import com.example.clevercafe.model.Product;

/**
 * Created by Chudofom on 20.03.17.
 */

public interface IMenuPresenter {
    void viewInit();
    void addProductButClicked();
    void editProductButClicked(int categoryId, int productId);
    void deleteProductButClicked(int categoryId, int productId);
    void addIngredientsButClicked();
    void addPictureButClicked();

    void addCategoryButClicked();
    void editCategoryButClicked(int categoryId);
    void deleteCategoryButClicked(int categoryId);

    void submitProductFormButClicked(int categoryId, int productId, Product product, boolean editForm);
    void submitCategoryFormButClicked(int categoryId, String name, boolean editForm);

    void backToCategoryButtonClicked();
    void itemClicked(boolean categoryOnscreen, int categoryId);
}
