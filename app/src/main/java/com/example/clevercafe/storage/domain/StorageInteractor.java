package com.example.clevercafe.storage.domain;

import com.example.clevercafe.data.repositories.IngredientRepository;
import com.example.clevercafe.entities.Ingredient;
import com.example.clevercafe.entities.IngredientCategory;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Chudofom on 24.07.17.
 */

public class StorageInteractor implements IStrorageInteractor {
    public IngredientRepository ingredientRepository;

    public StorageInteractor(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Observable<ArrayList<IngredientCategory>> loadCategories() {
        return Observable.create(e ->
        {
            ArrayList<IngredientCategory> categories = ingredientRepository.getCategories();
            if (categories != null) e.onNext(categories);
            else e.onError(new NullPointerException());
        });
    }

    @Override
    public Completable addCategory(IngredientCategory category) {
        return Completable.create(e ->
        {
            if (ingredientRepository.findCategory(category.name) == null) {
                ingredientRepository.addCategory(category);
                e.onComplete();
            } else e.onError(new Exception("Категория уже существует"));
        });
    }

    @Override
    public Completable editCategory(IngredientCategory category) {
        return Completable.create(e ->
        {
            ingredientRepository.addCategory(category);
            e.onComplete();
        });
    }

    @Override
    public Completable deleteCategory(IngredientCategory category) {
        return Completable.create(e ->
        {
            ingredientRepository.deleteCategory(category);
            e.onComplete();
        });
    }

    @Override
    public Completable addIngredient(Ingredient ingredient) {
        return Completable.create(e ->
        {
            if (ingredientRepository.findIngredient(ingredient.name) == null) {
                ingredientRepository.addIngredient(ingredient);
                e.onComplete();
            } else e.onError(new Exception("Товар уже существует"));
        });
    }

    @Override
    public Completable editIngredient(Ingredient ingredient) {
        return Completable.create(e ->
        {
            ingredientRepository.addIngredient(ingredient);
            e.onComplete();
        });
    }

    @Override
    public Completable deleteIngredient(Ingredient ingredient) {
        return Completable.create(e ->
        {
            ingredientRepository.deleteIngredient(ingredient);
            e.onComplete();
        });
    }

    @Override
    public Completable subtractIngredient(Ingredient ingredient) {
        return Completable.create(e -> {
            long id = ingredient.id;
            double quantity = ingredientRepository.getIngredientsQuantity(id);
            ingredient.quantity = quantity - (ingredient.quantity);
            if (ingredient.quantity < 0) ingredient.quantity = 0.0;
            ingredientRepository.addIngredient(ingredient);
            e.onComplete();
        });
    }
}
