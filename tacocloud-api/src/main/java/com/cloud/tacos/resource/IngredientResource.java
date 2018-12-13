package com.cloud.tacos.resource;

import com.cloud.tacos.domain.Ingredient;
import com.cloud.tacos.domain.Ingredient.Type;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

public class IngredientResource extends ResourceSupport {

    @Getter
    private String name;

    @Getter
    private Type type;

    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }

}
