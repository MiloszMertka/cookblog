import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../api/services/recipe.service';
import { filter, map, Observable, tap } from 'rxjs';
import { RecipeResource } from '../api/resources/recipe.resource';
import { PageQuery } from '../api/pagination/page-query';
import { ActivatedRoute } from '@angular/router';
import { CategoryService } from '../api/services/category.service';
import { AuthService } from '../api/services/auth.service';
import { DialogService } from '../shared/services/dialog.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.scss'],
})
export class RecipesComponent implements OnInit {
  private static readonly RECIPES_PATH = 'recipes';
  private static readonly CATEGORIES_PATH = 'categories';
  private static readonly SEARCH_PATH = 'search';
  private static readonly PAGE_SIZE = 12;

  recipes$: Observable<RecipeResource[]> | null = null;
  urlSegments: string[] = [];
  currentPage = 0;
  isFirstPage = true;
  isLastPage = false;
  paginated = true;
  isAuthenticated = false;

  constructor(
    private readonly recipeService: RecipeService,
    private readonly categoryService: CategoryService,
    private readonly authService: AuthService,
    private readonly dialogService: DialogService,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.isAuthenticated = this.authService.isAuthenticated();
    this.activatedRoute.url
      .pipe(
        map((urlSegments) => urlSegments.map((urlSegment) => urlSegment.path)),
      )
      .subscribe((urlSegments) => {
        this.urlSegments = urlSegments;
        this.getRecipesByCurrentPath();
      });
  }

  handleNextPageClicked() {
    if (this.isLastPage) {
      return;
    }

    this.currentPage++;
    this.getPageOfRecipes();
  }

  handlePreviousPageClicked() {
    if (this.isFirstPage) {
      return;
    }

    this.currentPage--;
    this.getPageOfRecipes();
  }

  handleDeleteButtonClicked(recipe: RecipeResource) {
    this.dialogService
      .openDialog({
        title: 'Are you sure you want to delete this recipe?',
        content: `You are about to delete recipe ${recipe.title}.`,
        confirmButtonText: 'Delete',
        confirmButtonColor: 'warn',
      })
      .afterClosed()
      .pipe(filter((result) => result === true))
      .subscribe(() => this.deleteRecipe(recipe));
  }

  private deleteRecipe(recipe: RecipeResource) {
    this.recipeService.deleteRecipe(recipe.id).subscribe(() => {
      this.getRecipesByCurrentPath();
    });
  }

  private getRecipesByCurrentPath() {
    const path = this.urlSegments[0] ?? '';
    switch (path) {
      case RecipesComponent.RECIPES_PATH:
        this.paginated = true;
        this.getPageOfRecipes();
        break;
      case RecipesComponent.CATEGORIES_PATH:
        this.paginated = false;
        const categoryId = Number(this.urlSegments[1]);
        this.getCategoryRecipes(categoryId);
        break;
      case RecipesComponent.SEARCH_PATH:
        this.paginated = false;
        const searchTerm = this.urlSegments[1];
        this.getRecipesBySearchTerm(searchTerm);
        break;
      default:
        this.paginated = true;
        this.getPageOfRecipes();
    }
  }

  private getPageOfRecipes() {
    const pageQuery = new PageQuery(
      this.currentPage,
      RecipesComponent.PAGE_SIZE,
    );
    this.recipes$ = this.recipeService.getPageOfRecipes(pageQuery).pipe(
      tap((pageResponse) => {
        this.currentPage = pageResponse.number;
        this.isFirstPage = pageResponse.first;
        this.isLastPage = pageResponse.last;
      }),
      map((pageResponse) => pageResponse.content),
    );
  }

  private getCategoryRecipes(categoryId: number) {
    this.recipes$ = this.categoryService
      .getCategoryWithItsRecipes(categoryId)
      .pipe(map((category) => category.recipes ?? []));
  }

  private getRecipesBySearchTerm(searchTerm: string) {
    this.recipes$ = this.recipeService.searchRecipes(searchTerm);
  }
}
