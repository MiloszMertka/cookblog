import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { RecipesComponent } from './recipes.component';
import { RecipeCardComponent } from './components/recipe-card/recipe-card.component';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  declarations: [RecipesComponent, RecipeCardComponent, FooterComponent],
  imports: [SharedModule],
  exports: [RecipesComponent],
})
export class RecipesModule {}
