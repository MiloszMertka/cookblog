import { NgModule } from '@angular/core';
import { RecipeComponent } from './recipe.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [RecipeComponent],
  imports: [SharedModule],
  exports: [RecipeComponent],
})
export class RecipeModule {}
