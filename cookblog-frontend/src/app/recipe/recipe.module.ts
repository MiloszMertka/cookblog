import { NgModule } from '@angular/core';
import { RecipeComponent } from './recipe.component';
import { SharedModule } from '../shared/shared.module';
import { CommentsComponent } from './components/comments/comments.component';
import { CommentFormComponent } from './components/comment-form/comment-form.component';

@NgModule({
  declarations: [RecipeComponent, CommentsComponent, CommentFormComponent],
  imports: [SharedModule],
  exports: [RecipeComponent],
})
export class RecipeModule {}
