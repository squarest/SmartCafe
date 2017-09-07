package com.example.clevercafe.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.clevercafe.data.entities.CompleteOrderProduct;
import com.example.clevercafe.data.entities.InvoiceIngredient;
import com.example.clevercafe.data.entities.OrderProduct;
import com.example.clevercafe.data.entities.ProductIngredient;
import com.example.clevercafe.entities.CompleteOrder;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;
import com.example.clevercafe.entities.Invoice;
import com.example.clevercafe.entities.Order;
import com.example.clevercafe.entities.Product;
import com.example.clevercafe.entities.ProductCategory;

import java.util.List;

/**
 * Created by Chudofom on 24.07.17.
 */
@Dao
public interface DatabaseDao {
    //Invoices
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertInvoice(Invoice invoice);

    @Delete
    void deleteInvoice(Invoice invoice);

    @Query("SELECT * FROM invoices ORDER BY date ASC")
    List<Invoice> getInvoices();


    //Orders
    @Insert
    long insertOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Query("SELECT * FROM orders")
    List<Order> getOrders();


    //completeOrders
    @Insert
    long insertCompleteOrder(CompleteOrder order);

    @Delete
    void deleteCompleteOrder(CompleteOrder order);

    @Query("SELECT * FROM completeOrders")
    List<CompleteOrder> getCompleteOrders();


    //ingredientCategory
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredientCategory(IngredientCategory category);

    @Delete
    void deleteIngredientCategory(IngredientCategory category);

    @Query("SELECT * FROM ingredientCategories ORDER BY name ASC")
    List<IngredientCategory> getIngredientCategories();


    //ingredients
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredient(Ingredient ingredient);

    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Query("DELETE FROM ingredients WHERE categoryId = :categoryId")
    void deleteIngredients(long categoryId);

    @Query("SELECT * FROM ingredients WHERE categoryId = :categoryId ORDER BY name ASC")
    List<Ingredient> getIngredients(long categoryId);

    @Query("SELECT * FROM ingredients WHERE id = :id")
    Ingredient getIngredient(long id);

    @Query("SELECT quantity FROM ingredients WHERE id = :id")
    double getIngredientQuantity(long id);


    //productIngredients
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductIngredient(ProductIngredient productIngredient);

    @Query("SELECT * FROM productIngredients WHERE productId = :productId")
    List<ProductIngredient> getProductIngredients(long productId);

    @Query("DELETE FROM productIngredients WHERE productId = :productId")
    void deleteProductIngredients(long productId);


    //invoiceIngredients
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInvoiceIngredient(InvoiceIngredient invoiceIngredient);

    @Query("SELECT * FROM invoiceIngredients WHERE invoiceId = :invoiceId")
    List<InvoiceIngredient> getInvoiceIngredients(long invoiceId);

    @Query("DELETE FROM invoiceIngredients WHERE invoiceId = :invoiceId")
    void deleteInvoiceIngredients(long invoiceId);


    //productCategory
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProductCategory(ProductCategory category);

    @Delete
    void deleteProductCategory(ProductCategory category);

    @Query("SELECT * FROM productCategories WHERE id = :id")
    ProductCategory getProductCategory(long id);


    @Query("SELECT * FROM productCategories")
    List<ProductCategory> getProductCategories();


    //products
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM products WHERE id = :id")
    Product getProduct(long id);

    @Query("DELETE FROM products WHERE categoryId = :categoryId")
    void deleteProducts(long categoryId);

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    List<Product> getProducts(long categoryId);


    //orderProduct
    @Insert
    void insertOrderProduct(OrderProduct orderProduct);

    @Insert
    void insertOrderProducts(List<OrderProduct> orderProduct);

    @Query("SELECT * FROM orderProducts WHERE orderId = :orderId")
    List<OrderProduct> getOrderProducts(long orderId);

    @Query("DELETE FROM orderProducts WHERE orderId = :orderId")
    void deleteOrderProducts(long orderId);


    //CompleteOrderProduct
    @Insert
    void insertCompleteOrderProduct(CompleteOrderProduct completeOrderProduct);

    @Query("SELECT * FROM completeOrderProducts WHERE orderId = :orderId")
    List<CompleteOrderProduct> getCompleteOrderProducts(long orderId);

    @Query("DELETE FROM completeOrderProducts WHERE orderId = :orderId")
    void deleteCompleteOrderProducts(long orderId);

}
