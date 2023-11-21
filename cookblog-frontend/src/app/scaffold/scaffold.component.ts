import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../api/services/category.service';
import { filter, Observable } from 'rxjs';
import { CategoryResource } from '../api/resources/category.resource';
import { Router } from '@angular/router';
import { DialogService } from '../shared/services/dialog.service';

@Component({
  selector: 'app-scaffold',
  templateUrl: './scaffold.component.html',
  styleUrls: ['./scaffold.component.scss'],
})
export class ScaffoldComponent implements OnInit {
  categories$: Observable<CategoryResource[]> | null = null;
  drawerOpened = false;

  constructor(
    private readonly categoryService: CategoryService,
    private readonly dialogService: DialogService,
    private readonly router: Router,
  ) {}

  ngOnInit() {
    this.getCategories();
  }

  handleMenuButtonClicked() {
    this.drawerOpened = !this.drawerOpened;
  }

  async handleSearchButtonClicked(query: string) {
    await this.router.navigate(['search', query]);
  }

  handleDeleteCategoryClicked(category: CategoryResource) {
    this.dialogService
      .openDialog({
        title: 'Are you sure you want to delete this category?',
        content: `You are about to delete category ${category.name}.`,
        confirmButtonText: 'Delete',
        confirmButtonColor: 'warn',
      })
      .afterClosed()
      .pipe(filter((result) => result === true))
      .subscribe(() => this.deleteCategory(category));
  }

  private deleteCategory(category: CategoryResource) {
    this.categoryService
      .deleteCategory(category.id!)
      .subscribe(() => this.getCategories());
  }

  private getCategories() {
    this.categories$ = this.categoryService.getAllCategories();
  }
}
