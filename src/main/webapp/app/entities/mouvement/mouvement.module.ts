import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MouvementComponent } from './list/mouvement.component';
import { MouvementDetailComponent } from './detail/mouvement-detail.component';
import { MouvementUpdateComponent } from './update/mouvement-update.component';
import { MouvementDeleteDialogComponent } from './delete/mouvement-delete-dialog.component';
import { MouvementRoutingModule } from './route/mouvement-routing.module';

@NgModule({
  imports: [SharedModule, MouvementRoutingModule],
  declarations: [MouvementComponent, MouvementDetailComponent, MouvementUpdateComponent, MouvementDeleteDialogComponent],
})
export class MouvementModule {}
