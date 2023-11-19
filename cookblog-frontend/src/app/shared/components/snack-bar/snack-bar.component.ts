import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import {
  MAT_SNACK_BAR_DATA,
  MatSnackBarRef,
} from '@angular/material/snack-bar';

export enum SnackBarType {
  Error,
  Success,
  Info,
}

export interface SnackBarData {
  message: string;
  type: SnackBarType;
}

@Component({
  selector: 'app-snack-bar',
  templateUrl: './snack-bar.component.html',
  styleUrls: ['./snack-bar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SnackBarComponent {
  data = inject<SnackBarData>(MAT_SNACK_BAR_DATA);
  snackBarRef = inject(MatSnackBarRef);

  get SnackBarType() {
    return SnackBarType;
  }
}
