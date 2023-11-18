import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import {
  SnackBarComponent,
  SnackBarData,
  SnackBarType,
} from '../components/snack-bar/snack-bar.component';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private static readonly SNACKBAR_DURATION_IN_SECONDS = 5;

  constructor(private readonly snackBar: MatSnackBar) {}

  showError(errorMessage: string) {
    this.showSnackBar(errorMessage, SnackBarType.Error);
  }

  showSuccess(successMessage: string) {
    this.showSnackBar(successMessage, SnackBarType.Success);
  }

  showInfo(infoMessage: string) {
    this.showSnackBar(infoMessage, SnackBarType.Info);
  }

  private showSnackBar(message: string, type: SnackBarType) {
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: NotificationService.SNACKBAR_DURATION_IN_SECONDS * 1000,
      data: {
        message,
        type,
      } as SnackBarData,
    });
  }
}
