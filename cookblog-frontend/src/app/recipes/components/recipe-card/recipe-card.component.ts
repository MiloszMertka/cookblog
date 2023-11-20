import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { RecipeResource } from '../../../api/resources/recipe.resource';

@Component({
  selector: 'app-recipe-card',
  templateUrl: './recipe-card.component.html',
  styleUrls: ['./recipe-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RecipeCardComponent {
  @Input({ required: true }) recipe!: RecipeResource;
  @Input({ required: true }) isAuthenticated!: boolean;
  @Output() deleteButtonClick = new EventEmitter<RecipeResource>();

  handleDeleteButtonClicked() {
    this.deleteButtonClick.emit(this.recipe);
  }
}
