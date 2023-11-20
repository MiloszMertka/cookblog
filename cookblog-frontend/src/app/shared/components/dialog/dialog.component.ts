import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ThemePalette } from '@angular/material/core';

export interface DialogData {
  title: string;
  content: string;
  cancelButtonText?: string;
  confirmButtonText?: string;
  confirmButtonColor?: ThemePalette;
}

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DialogComponent {
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
}
