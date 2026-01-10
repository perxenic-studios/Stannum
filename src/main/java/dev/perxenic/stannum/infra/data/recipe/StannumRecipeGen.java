package dev.perxenic.stannum.infra.data.recipe;

import com.google.common.collect.ImmutableList;
import dev.perxenic.stannum.registry.block.StannumBlocks;
import dev.perxenic.stannum.registry.item.StannumItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static dev.perxenic.stannum.Stannum.snLoc;

@ParametersAreNonnullByDefault
public final class StannumRecipeGen extends RecipeProvider {
    public static List<ItemLike> TIN_SMELTABLES = ImmutableList.of(
            StannumItems.RAW_TIN,
            StannumBlocks.TIN_ORE,
            StannumBlocks.DEEPSLATE_TIN_ORE
    );

    public StannumRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        oreSmelting(
                recipeOutput,
                TIN_SMELTABLES,
                RecipeCategory.MISC,
                StannumItems.TIN_INGOT,
                0.7f,
                200,
                "tin_ingot"
        );
        oreBlasting(
                recipeOutput,
                TIN_SMELTABLES,
                RecipeCategory.MISC,
                StannumItems.TIN_INGOT,
                0.7f,
                100,
                "tin_ingot"
        );
    }

    protected static void oreSmelting(
            RecipeOutput recipeOutput,
            List<ItemLike> ingredients,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int cookingTime,
            String group
    ) {
        oreCooking(
                recipeOutput,
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(
            RecipeOutput recipeOutput,
            List<ItemLike> ingredients,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int cookingTime,
            String group
    ) {
        oreCooking(
                recipeOutput,
                RecipeSerializer.BLASTING_RECIPE,
                BlastingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_blasting"
        );
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput recipeOutput,
            RecipeSerializer<T> serializer,
            AbstractCookingRecipe.Factory<T> recipeFactory,
            List<ItemLike> ingredients,
            RecipeCategory category,
            ItemLike result,
            float experience,
            int cookingTime,
            String group,
            String suffix
    ) {
        for(ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(
                    Ingredient.of(itemlike),
                    category,
                    result,
                    experience,
                    cookingTime,
                    serializer,
                    recipeFactory
            ).group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, snLoc(getItemName(result) + suffix + "_" + getItemName(itemlike)));
        }
    }
}
