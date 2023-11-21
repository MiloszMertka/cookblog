import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { filter, map, switchMap, tap } from 'rxjs';
import { CategoryResource } from '../../api/resources/category.resource';
import { CategoryService } from '../../api/services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CreateCategoryRequest } from '../../api/requests/create-category.request';
import { UpdateCategoryRequest } from '../../api/requests/update-category.request';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.scss'],
})
export class CategoryFormComponent implements OnInit {
  private static readonly ID_PARAM_KEY = 'id';

  readonly categoryForm = new FormGroup({
    name: new FormControl('', { nonNullable: true }),
  });

  categoryId: number | null = null;

  constructor(
    private readonly categoryService: CategoryService,
    private readonly router: Router,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.getCategoryIfEditForm();
  }

  handleSubmit(event: SubmitEvent) {
    event.preventDefault();
    this.categoryId ? this.updateCategory() : this.createCategory();
  }

  private createCategory() {
    const createCategoryRequest = this.mapFormToCreateCategoryRequest();
    this.categoryService
      .createCategory(createCategoryRequest)
      .subscribe(() => this.redirectToRecipes());
  }

  private updateCategory() {
    const updateCategoryRequest = this.mapFormToUpdateCategoryRequest();
    this.categoryService
      .updateCategory(this.categoryId!, updateCategoryRequest)
      .subscribe(() => this.redirectToRecipes());
  }

  private async redirectToRecipes() {
    await this.router.navigate(['/recipes']);
  }

  private getCategoryIfEditForm() {
    this.activatedRoute.paramMap
      .pipe(
        map((paramMap) =>
          parseInt(paramMap.get(CategoryFormComponent.ID_PARAM_KEY)!),
        ),
        filter((id) => !isNaN(id)),
        tap((id) => (this.categoryId = id)),
        switchMap((id) => this.categoryService.getCategoryWithItsRecipes(id)),
      )
      .subscribe((category) => this.setFormValues(category));
  }

  private setFormValues(category: CategoryResource) {
    this.categoryForm.setValue({
      name: category.name,
    });
  }

  private mapFormToCreateCategoryRequest() {
    const { value } = this.categoryForm;
    return new CreateCategoryRequest(value.name!);
  }

  private mapFormToUpdateCategoryRequest() {
    const { value } = this.categoryForm;
    return new UpdateCategoryRequest(value.name!);
  }
}
