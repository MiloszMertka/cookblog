import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../api/services/recipe.service';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { UnitResource } from '../api/resources/unit.resource';
import { CategoryService } from '../api/services/category.service';
import { map, Observable, switchMap, tap } from 'rxjs';
import { CategoryResource } from '../api/resources/category.resource';
import { CreateRecipeRequest } from '../api/requests/create-recipe.request';
import { ImageResource } from '../api/resources/image.resource';
import { IngredientResource } from '../api/resources/ingredient.resource';
import { QuantityResource } from '../api/resources/quantity.resource';
import { ActivatedRoute, Router } from '@angular/router';
import { RecipeResource } from '../api/resources/recipe.resource';
import { UpdateRecipeRequest } from '../api/requests/update-recipe.request';

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.component.html',
  styleUrls: ['./recipe-form.component.scss'],
})
export class RecipeFormComponent implements OnInit {
  private static readonly ID_PARAM_KEY = 'id';

  readonly recipeForm = new FormGroup({
    title: new FormControl('', { nonNullable: true }),
    description: new FormControl('', { nonNullable: true }),
    ingredients: new FormArray([
      new FormGroup({
        name: new FormControl('', { nonNullable: true }),
        amount: new FormControl<number>(1, { nonNullable: true }),
        unit: new FormControl<UnitResource>(UnitResource.Piece, {
          nonNullable: true,
        }),
      }),
    ]),
    instructions: new FormControl('', { nonNullable: true }),
    image: new FormControl('', { nonNullable: true }),
    category: new FormControl('', { nonNullable: true }),
    preparationTimeInMinutes: new FormControl<number | null>(null),
    portions: new FormControl<number | null>(null),
    calorificValue: new FormControl<number | null>(null),
  });

  recipeId: number | null = null;
  categories$: Observable<CategoryResource[]> | null = null;

  constructor(
    private readonly recipeService: RecipeService,
    private readonly categoryService: CategoryService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  get units(): UnitResource[] {
    return Object.values(UnitResource);
  }

  ngOnInit() {
    this.getRecipeIfEditForm();
    this.getCategories();
  }

  handleDeleteIngredientButtonClicked(index: number) {
    this.recipeForm.controls.ingredients.removeAt(index);
  }

  handleAddIngredientButtonClicked() {
    this.recipeForm.controls.ingredients.push(
      new FormGroup({
        name: new FormControl('', { nonNullable: true }),
        amount: new FormControl<number>(1, { nonNullable: true }),
        unit: new FormControl<UnitResource>(UnitResource.Piece, {
          nonNullable: true,
        }),
      }),
    );
  }

  handleSubmit(event: SubmitEvent) {
    event.preventDefault();
    this.recipeId ? this.updateRecipe() : this.createRecipe();
  }

  private createRecipe() {
    const createRecipeRequest = this.mapFormToCreateRecipeRequest();
    this.recipeService
      .createRecipe(createRecipeRequest)
      .subscribe(() => this.redirectToRecipes());
  }

  private updateRecipe() {
    const updateRecipeRequest = this.mapFormToUpdateRecipeRequest();
    this.recipeService
      .updateRecipe(this.recipeId!, updateRecipeRequest)
      .subscribe(() => this.redirectToRecipes());
  }

  private async redirectToRecipes() {
    await this.router.navigate(['/recipes']);
  }

  private getCategories() {
    this.categories$ = this.categoryService.getAllCategories();
  }

  private getRecipeIfEditForm() {
    this.activatedRoute.paramMap
      .pipe(
        map((paramMap) =>
          parseInt(paramMap.get(RecipeFormComponent.ID_PARAM_KEY)!),
        ),
        tap((id) => (this.recipeId = id)),
        switchMap((id) => this.recipeService.getRecipe(id)),
      )
      .subscribe((recipe) => this.setFormValues(recipe));
  }

  private setFormValues(recipe: RecipeResource) {
    this.createIngredientsControls(recipe.ingredients);
    this.recipeForm.setValue({
      title: recipe.title,
      description: recipe.description,
      ingredients: recipe.ingredients.map((ingredient) => ({
        name: ingredient.name,
        amount: ingredient.quantity.amount,
        unit: ingredient.quantity.unit,
      })),
      instructions: recipe.instructions,
      image: recipe.image.path,
      category: '',
      preparationTimeInMinutes: recipe.preparationTimeInMinutes,
      portions: recipe.portions,
      calorificValue: recipe.calorificValue,
    });
    this.setFormRecipeCategory();
  }

  private createIngredientsControls(ingredients: IngredientResource[]) {
    this.recipeForm.controls.ingredients.controls = [];
    return ingredients.forEach((ingredient) => {
      const ingredientControls = new FormGroup({
        name: new FormControl(ingredient.name, { nonNullable: true }),
        amount: new FormControl<number>(ingredient.quantity.amount, {
          nonNullable: true,
        }),
        unit: new FormControl<UnitResource>(ingredient.quantity.unit, {
          nonNullable: true,
        }),
      });
      this.recipeForm.controls.ingredients.push(ingredientControls);
    });
  }

  private setFormRecipeCategory() {
    return this.recipeService
      .getRecipeCategory(this.recipeId!)
      .pipe(map((category) => category.name))
      .subscribe((categoryName) => {
        this.recipeForm.controls.category.setValue(categoryName);
      });
  }

  private mapFormToCreateRecipeRequest() {
    const { value } = this.recipeForm;
    return new CreateRecipeRequest(
      value.title!,
      value.description!,
      value.ingredients!.map(
        (ingredient) =>
          new IngredientResource(
            null,
            ingredient.name!,
            new QuantityResource(null, ingredient.amount!, ingredient.unit!),
          ),
      ),
      value.instructions!,
      new ImageResource(null, value.image!),
      new CategoryResource(null, value.category!, null),
      value.preparationTimeInMinutes ?? undefined,
      value.portions ?? undefined,
      value.calorificValue ?? undefined,
    );
  }

  private mapFormToUpdateRecipeRequest() {
    const { value } = this.recipeForm;
    return new UpdateRecipeRequest(
      value.title!,
      value.description!,
      value.ingredients!.map(
        (ingredient) =>
          new IngredientResource(
            null,
            ingredient.name!,
            new QuantityResource(null, ingredient.amount!, ingredient.unit!),
          ),
      ),
      value.instructions!,
      new ImageResource(null, value.image!),
      new CategoryResource(null, value.category!, null),
      value.preparationTimeInMinutes ?? undefined,
      value.portions ?? undefined,
      value.calorificValue ?? undefined,
    );
  }
}
