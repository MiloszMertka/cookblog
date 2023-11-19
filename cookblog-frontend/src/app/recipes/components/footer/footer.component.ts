import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FooterComponent {
  @Input({ required: true }) isFirstPage!: boolean;
  @Input({ required: true }) isLastPage!: boolean;
  @Output() previousPageClick = new EventEmitter<void>();
  @Output() nextPageClick = new EventEmitter<void>();

  handlePreviousPageClicked() {
    this.previousPageClick.emit();
  }

  handleNextPageClicked() {
    this.nextPageClick.emit();
  }
}
