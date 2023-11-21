import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RecipesModule } from './recipes/recipes.module';
import { ScaffoldModule } from './scaffold/scaffold.module';
import { RecipeModule } from './recipe/recipe.module';
import { ErrorHandlerInterceptor } from './api/interceptors/error-handler.interceptor';
import { RecipeFormModule } from './recipe-form/recipe-form.module';
import { CategoryFormModule } from './category-form/category-form.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ScaffoldModule,
    RecipesModule,
    RecipeModule,
    RecipeFormModule,
    CategoryFormModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
