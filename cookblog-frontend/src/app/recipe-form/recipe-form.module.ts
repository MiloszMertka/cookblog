import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { RecipeFormComponent } from './recipe-form.component';

@NgModule({
  declarations: [RecipeFormComponent],
  imports: [SharedModule],
  exports: [RecipeFormComponent],
})
export class RecipeFormModule {}
