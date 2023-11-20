import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { SnackBarComponent } from './components/snack-bar/snack-bar.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { RouterModule } from '@angular/router';
import { DialogComponent } from './components/dialog/dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { FormatUnitPipe } from './pipes/format-unit.pipe';

const modulesToExport = [
  CommonModule,
  RouterModule,
  HttpClientModule,
  ReactiveFormsModule,
  MatSnackBarModule,
  MatIconModule,
  MatButtonModule,
  MatCardModule,
  MatToolbarModule,
  MatInputModule,
  MatFormFieldModule,
  MatSidenavModule,
  MatListModule,
  MatDialogModule,
  MatSelectModule,
];

@NgModule({
  declarations: [SnackBarComponent, DialogComponent, FormatUnitPipe],
  imports: [...modulesToExport],
  exports: [...modulesToExport, FormatUnitPipe],
})
export class SharedModule {}
