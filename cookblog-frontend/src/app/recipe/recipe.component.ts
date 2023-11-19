import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../api/services/recipe.service';
import { ActivatedRoute } from '@angular/router';
import { map, Observable, switchMap, tap } from 'rxjs';
import { RecipeResource } from '../api/resources/recipe.resource';
import { FormControl, FormGroup } from '@angular/forms';
import { CommentRecipeRequest } from '../api/requests/comment-recipe.request';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss'],
})
export class RecipeComponent implements OnInit {
  private static readonly ID_PARAM_KEY = 'id';

  readonly commentForm = new FormGroup({
    author: new FormControl('', { nonNullable: true }),
    content: new FormControl('', { nonNullable: true }),
  });

  recipeId: number | null = null;
  recipe$: Observable<RecipeResource> | null = null;

  constructor(
    private readonly recipeService: RecipeService,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.getRecipe();
  }

  handleCommentSubmit(event: SubmitEvent) {
    event.preventDefault();
    const { author, content } = this.commentForm.value;
    this.commentRecipe(author!, content!);
  }

  private getRecipe() {
    this.recipe$ = this.activatedRoute.paramMap.pipe(
      map((paramMap) => parseInt(paramMap.get(RecipeComponent.ID_PARAM_KEY)!)),
      tap((id) => (this.recipeId = id)),
      switchMap((id) => this.recipeService.getRecipe(id)),
    );
  }

  private commentRecipe(author: string, content: string) {
    const commentRecipeRequest = new CommentRecipeRequest(author, content);
    console.log(commentRecipeRequest);
    this.recipeService
      .commentRecipe(this.recipeId!, commentRecipeRequest)
      .subscribe(() => {
        this.commentForm.reset();
        this.getRecipe();
      });
  }
}
