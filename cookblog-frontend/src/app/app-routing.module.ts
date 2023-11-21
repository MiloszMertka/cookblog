import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecipesComponent } from './recipes/recipes.component';
import { RecipeComponent } from './recipe/recipe.component';
import { RecipeFormComponent } from './recipe-form/recipe-form.component';
import { CategoryFormComponent } from './category-form/category-form/category-form.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'recipes',
    pathMatch: 'full',
  },
  {
    path: 'categories/create',
    component: CategoryFormComponent,
  },
  {
    path: 'categories/edit/:id',
    component: CategoryFormComponent,
  },
  {
    path: 'recipes',
    component: RecipesComponent,
  },
  {
    path: 'categories/:id',
    component: RecipesComponent,
  },
  {
    path: 'search/:query',
    component: RecipesComponent,
  },
  {
    path: 'recipes/create',
    component: RecipeFormComponent,
  },
  {
    path: 'recipes/edit/:id',
    component: RecipeFormComponent,
  },
  {
    path: 'recipes/:id',
    component: RecipeComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
