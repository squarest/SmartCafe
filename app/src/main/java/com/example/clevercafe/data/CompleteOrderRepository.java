package com.example.clevercafe.data;

import com.example.clevercafe.data.dao.DatabaseDao;
import com.example.clevercafe.data.entities.CompleteOrderProduct;
import com.example.clevercafe.entities.CompleteOrder;
import com.example.clevercafe.entities.Order;

/**
 * Created by Chudofom on 07.07.17.
 */

public class CompleteOrderRepository {
    public DatabaseDao databaseDao;

    public CompleteOrderRepository(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    public void addCompleteOrder(Order order) {
        CompleteOrder completeOrder = new CompleteOrder(order.costSum, order.sum, order.products);
        completeOrder.id = databaseDao.insertCompleteOrder(completeOrder);
        if (order.products != null && order.products.size() > 0) addProducts(completeOrder);
    }


    private void addProducts(CompleteOrder order) {
        for (int i = 0; i < order.products.size(); i++) {
            CompleteOrderProduct orderProduct = new CompleteOrderProduct(order.id, order.products.get(i).id);
            databaseDao.insertCompleteOrderProduct(orderProduct);
        }
    }

//    public ArrayList<CompleteOrder> getCompleteOrders() {
//        ArrayList<CompleteOrder> orders = (ArrayList<CompleteOrder>) databaseDao.getCompleteOrders();
//        for (CompleteOrder order : orders) {
//            order.products = getProducts(order.id);
//        }
//        return orders;
//    }
//
//    private ArrayList<Product> getProducts(long orderId) {
//        ArrayList<CompleteOrderProduct> orderProducts = (ArrayList<CompleteOrderProduct>) databaseDao.getCompleteOrderProducts(orderId);
//        ArrayList<Product> products = new ArrayList<>();
//        if (orderProducts != null && orderProducts.size() > 0) {
//            for (CompleteOrderProduct orderProduct : orderProducts) {
//                Product product = databaseDao.getProduct(orderProduct.productId);
//                product.ingredients = getIngredients(product.id);
//                products.add(product);
//            }
//        }
//        return products;
//
//    }
//
//    private ArrayList<Ingredient> getIngredients(long productId) {
//        ArrayList<ProductIngredient> productIngredients = (ArrayList<ProductIngredient>) databaseDao.getProductIngredients(productId);
//        ArrayList<Ingredient> ingredients = new ArrayList<>();
//        if (productIngredients != null && productIngredients.size() > 0) {
//            for (ProductIngredient productIngredient : productIngredients) {
//                ingredients.add(databaseDao.getIngredient(productIngredient.ingredientId));
//            }
//        }
//        return ingredients;
//    }
//
//    public void editCompleteOrder(CompleteOrder order) {
//        databaseDao.updateCompleteOrder(order);
//        if (order.products != null && order.products.size() > 0) {
//            deleteProducts(order.id);
//            addProducts(order);
//        }
//    }
//
//
//    public void deleteCompleteOrder(CompleteOrder order) {
//        databaseDao.deleteCompleteOrder(order);
//        if (order.products != null && order.products.size() > 0) {
//            deleteProducts(order.id);
//        }
//
//    }
//
//    private void deleteProducts(long orderId) {
//        databaseDao.deleteCompleteOrderProducts(orderId);
//    }
}
