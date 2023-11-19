import { NgModule } from '@angular/core';
import { ScaffoldComponent } from './scaffold.component';
import { SharedModule } from '../shared/shared.module';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { DrawerComponent } from './components/drawer/drawer.component';

@NgModule({
  declarations: [ScaffoldComponent, ToolbarComponent, DrawerComponent],
  imports: [SharedModule],
  exports: [ScaffoldComponent],
})
export class ScaffoldModule {}
