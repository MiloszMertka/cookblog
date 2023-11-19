import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../api/services/category.service';
import { Observable } from 'rxjs';
import { CategoryResource } from '../api/resources/category.resource';
import { Router } from '@angular/router';

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
    private readonly router: Router,
  ) {}

  ngOnInit() {
    this.categories$ = this.categoryService.getAllCategories();
  }

  handleMenuButtonClicked() {
    this.drawerOpened = !this.drawerOpened;
  }

  async handleSearchButtonClicked(query: string) {
    await this.router.navigate(['search', query]);
  }
}
