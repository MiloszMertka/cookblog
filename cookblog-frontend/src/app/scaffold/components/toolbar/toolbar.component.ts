import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ToolbarComponent {
  private static readonly ENTER_KEY = 'Enter';

  @Output() menuButtonClick = new EventEmitter<void>();
  @Output() searchButtonClick = new EventEmitter<string>();

  handleMenuButtonClicked() {
    this.menuButtonClick.emit();
  }

  handleSearchInputKeyDown(event: KeyboardEvent) {
    if (event.key === ToolbarComponent.ENTER_KEY) {
      const query = (event.target as HTMLInputElement).value;
      this.handleSearchButtonClicked(query);
    }
  }

  handleSearchButtonClicked(query: string) {
    this.searchButtonClick.emit(query);
  }
}
