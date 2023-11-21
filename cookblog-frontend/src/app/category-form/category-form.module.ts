import { NgModule } from '@angular/core';
import { CategoryFormComponent } from './category-form/category-form.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [CategoryFormComponent],
  imports: [SharedModule],
  exports: [CategoryFormComponent],
})
export class CategoryFormModule {}
