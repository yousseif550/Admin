import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypematerielComponent } from './list/typemateriel.component';
import { TypematerielDetailComponent } from './detail/typemateriel-detail.component';
import { TypematerielUpdateComponent } from './update/typemateriel-update.component';
import { TypematerielDeleteDialogComponent } from './delete/typemateriel-delete-dialog.component';
import { TypematerielRoutingModule } from './route/typemateriel-routing.module';

@NgModule({
  imports: [SharedModule, TypematerielRoutingModule],
  declarations: [TypematerielComponent, TypematerielDetailComponent, TypematerielUpdateComponent, TypematerielDeleteDialogComponent],
})
export class TypematerielModule {}
