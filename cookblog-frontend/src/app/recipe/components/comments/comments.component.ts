import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CommentResource } from '../../../api/resources/comment.resource';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CommentsComponent {
  @Input({ required: true }) comments!: CommentResource[];
  @Input({ required: true }) commentForm!: FormGroup<{
    author: FormControl<string>;
    content: FormControl<string>;
  }>;
  @Output() commentDelete = new EventEmitter<CommentResource>();
  @Output() commentSubmit = new EventEmitter<void>();

  handleCommentDelete(comment: CommentResource) {
    this.commentDelete.emit(comment);
  }

  handleCommentSubmit() {
    this.commentSubmit.emit();
  }
}
