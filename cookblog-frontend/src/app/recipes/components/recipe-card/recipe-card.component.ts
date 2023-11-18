import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { RecipeResource } from '../../../api/resources/recipe.resource';

@Component({
  selector: 'app-recipe-card',
  templateUrl: './recipe-card.component.html',
  styleUrls: ['./recipe-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RecipeCardComponent {
  @Input({ required: true }) recipe!: RecipeResource;
}
