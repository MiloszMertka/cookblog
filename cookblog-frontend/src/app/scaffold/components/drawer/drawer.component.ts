import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { CategoryResource } from '../../../api/resources/category.resource';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-drawer',
  templateUrl: './drawer.component.html',
  styleUrls: ['./drawer.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DrawerComponent {
  @Input({ required: true }) categories$!: Observable<
    CategoryResource[]
  > | null;
  @Input({ required: true }) opened!: boolean;
  @Output() deleteCategoryClick = new EventEmitter<CategoryResource>();

  handleDeleteCategoryClicked(category: CategoryResource) {
    this.deleteCategoryClick.emit(category);
  }
}
