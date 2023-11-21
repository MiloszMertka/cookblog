import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentFormComponent {
  @Input({ required: true }) commentForm!: FormGroup<{
    author: FormControl<string>;
    content: FormControl<string>;
  }>;
  @Output() commentSubmit = new EventEmitter<void>();

  handleSubmit(event: SubmitEvent) {
    event.preventDefault();
    this.commentSubmit.emit();
  }
}
