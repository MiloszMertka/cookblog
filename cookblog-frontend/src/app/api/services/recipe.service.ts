import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PageQuery } from '../pagination/page-query';
import { environment } from '../../../environments/environment';
import { PageResponse } from '../pagination/page-response';
import { RecipeResource } from '../resources/recipe.resource';
import { CreateRecipeRequest } from '../requests/create-recipe.request';
import { UpdateRecipeRequest } from '../requests/update-recipe.request';
import { CommentRecipeRequest } from '../requests/comment-recipe.request';
import { CategoryResource } from '../resources/category.resource';

@Injectable({
  providedIn: 'root',
})
export class RecipeService {
  private static readonly RECIPES_ENDPOINT = '/recipes';

  constructor(private readonly httpClient: HttpClient) {}

  getPageOfRecipes(pageQuery: PageQuery) {
    const params = pageQuery.toHttpParams();
    return this.httpClient.get<PageResponse<RecipeResource>>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}`,
      {
        params,
      },
    );
  }

  searchRecipes(query: string) {
    return this.httpClient.get<RecipeResource[]>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}/search/${query}`,
    );
  }

  getRecipe(id: number) {
    return this.httpClient.get<RecipeResource>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}/${id}`,
    );
  }

  getRecipeCategory(id: number) {
    return this.httpClient.get<CategoryResource>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}/${id}/category`,
    );
  }

  createRecipe(createRecipeRequest: CreateRecipeRequest) {
    return this.httpClient.post<void>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}`,
      createRecipeRequest.toRequestBody(),
    );
  }

  updateRecipe(id: number, updateRecipeRequest: UpdateRecipeRequest) {
    return this.httpClient.put<void>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}/${id}`,
      updateRecipeRequest.toRequestBody(),
    );
  }

  deleteRecipe(id: number) {
    return this.httpClient.delete<void>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}/${id}`,
    );
  }

  commentRecipe(id: number, commentRecipeRequest: CommentRecipeRequest) {
    return this.httpClient.post<void>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}/${id}/comment`,
      commentRecipeRequest.toRequestBody(),
    );
  }

  deleteComment(id: number, commentId: number) {
    return this.httpClient.delete<void>(
      `${environment.apiBaseUrl}${RecipeService.RECIPES_ENDPOINT}/${id}/comment/${commentId}`,
    );
  }
}
