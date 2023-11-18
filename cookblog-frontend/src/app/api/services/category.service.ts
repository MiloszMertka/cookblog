import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CategoryResource } from '../resources/category.resource';
import { environment } from '../../../environments/environment';
import { CreateCategoryRequest } from '../requests/create-category.request';
import { UpdateCategoryRequest } from '../requests/update-category.request';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private static readonly CATEGORIES_ENDPOINT = '/categories';

  constructor(private readonly httpClient: HttpClient) {}

  getAllCategories() {
    return this.httpClient.get<CategoryResource[]>(
      `${environment.apiBaseUrl}${CategoryService.CATEGORIES_ENDPOINT}`,
    );
  }

  getCategoryWithItsRecipes(id: number) {
    return this.httpClient.get<CategoryResource>(
      `${environment.apiBaseUrl}${CategoryService.CATEGORIES_ENDPOINT}/${id}`,
    );
  }

  createCategory(createCategoryRequest: CreateCategoryRequest) {
    return this.httpClient.post<void>(
      `${environment.apiBaseUrl}${CategoryService.CATEGORIES_ENDPOINT}`,
      createCategoryRequest,
    );
  }

  updateCategory(id: number, updateCategoryRequest: UpdateCategoryRequest) {
    return this.httpClient.patch<void>(
      `${environment.apiBaseUrl}${CategoryService.CATEGORIES_ENDPOINT}/${id}`,
      updateCategoryRequest,
    );
  }

  deleteCategory(id: number) {
    return this.httpClient.delete<void>(
      `${environment.apiBaseUrl}${CategoryService.CATEGORIES_ENDPOINT}/${id}`,
    );
  }
}
