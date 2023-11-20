import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {
  DialogComponent,
  DialogData,
} from '../components/dialog/dialog.component';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  constructor(private readonly dialog: MatDialog) {}

  openDialog(dialogConfig: DialogData) {
    return this.dialog.open(DialogComponent, {
      data: dialogConfig,
    });
  }
}
