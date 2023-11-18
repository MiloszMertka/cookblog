import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { SnackBarComponent } from './components/snack-bar/snack-bar.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

const modulesToExport = [
  CommonModule,
  HttpClientModule,
  ReactiveFormsModule,
  MatSnackBarModule,
  MatIconModule,
  MatButtonModule,
];

@NgModule({
  declarations: [SnackBarComponent],
  imports: [...modulesToExport],
  exports: [...modulesToExport],
})
export class SharedModule {}
