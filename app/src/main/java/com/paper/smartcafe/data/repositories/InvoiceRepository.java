package com.paper.smartcafe.data.repositories;

import com.paper.smartcafe.data.dao.DatabaseDao;
import com.paper.smartcafe.data.entities.InvoiceIngredient;
import com.paper.smartcafe.entities.Ingredient;
import com.paper.smartcafe.entities.Invoice;

import java.util.ArrayList;

/**
 * Created by Chudofom on 07.07.17.
 */

public class InvoiceRepository {
    public DatabaseDao databaseDao;

    public InvoiceRepository(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    //insert
    public void addInvoice(Invoice invoice) {
        long newRowID = databaseDao.insertInvoice(invoice);
        if (invoice.ingredients != null && invoice.ingredients.size() > 0) {
            invoice.id = (int) newRowID;
            deleteIngredients(invoice.id);
            addIngredients(invoice);
        }
    }

    private void addIngredients(Invoice invoice) {
        for (int i = 0; i < invoice.ingredients.size(); i++) {
            Ingredient ingredient = invoice.ingredients.get(i);
            InvoiceIngredient invoiceIngredient = new InvoiceIngredient();
            invoiceIngredient.ingredientId = ingredient.id;
            invoiceIngredient.invoiceId = invoice.id;
            invoiceIngredient.ingredientName = ingredient.name;
            invoiceIngredient.units = ingredient.units;
            invoiceIngredient.cost = ingredient.cost;
            invoiceIngredient.quantity = invoice.getIngredientCount(ingredient.id);
            databaseDao.insertInvoiceIngredient(invoiceIngredient);

            Ingredient storageIngredient = databaseDao.getIngredient(ingredient.id);
            storageIngredient.cost = ingredient.cost;
            storageIngredient.quantity += invoice.getIngredientCount(ingredient.id);
            databaseDao.insertIngredient(storageIngredient);

        }
    }

    //get

    public ArrayList<Invoice> getInvoices() {
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) databaseDao.getInvoices();
        if (invoices.size() > 0) {
            for (Invoice invoice : invoices) {
                //findIngredientsById
                invoice.ingredients = getIngredients(invoice);
            }
        }
        return invoices;

    }

    private ArrayList<Ingredient> getIngredients(Invoice invoice) {
        ArrayList<InvoiceIngredient> invoiceIngredients = (ArrayList<InvoiceIngredient>) databaseDao.getInvoiceIngredients(invoice.id);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        if (invoiceIngredients != null && invoiceIngredients.size() > 0) {
            for (InvoiceIngredient invoiceIngredient : invoiceIngredients) {
                if (databaseDao.getIngredient(invoiceIngredient.ingredientId) != null) {
                    ingredients.add(databaseDao.getIngredient(invoiceIngredient.ingredientId));
                } else {
                    Ingredient ingredient = new Ingredient();
                    ingredient.id = invoiceIngredient.ingredientId;
                    ingredient.name = invoiceIngredient.ingredientName;
                    ingredient.units = invoiceIngredient.units;
                    ingredient.cost = invoiceIngredient.cost;
                    ingredients.add(ingredient);
                }
                invoice.setIngredientCount(invoiceIngredient.ingredientId, invoiceIngredient.quantity);
            }
        }
        return ingredients;
    }

    //delete
    public void deleteInvoice(Invoice invoice) {
        databaseDao.deleteInvoice(invoice);
        if (invoice.ingredients != null && invoice.ingredients.size() > 0) {
            deleteIngredients(invoice.id);
        }
    }

    public void deleteIngredients(long invoiceId) {
        databaseDao.deleteInvoiceIngredients(invoiceId);

    }
}