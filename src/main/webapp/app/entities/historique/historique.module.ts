import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HistoriqueComponent } from './list/historique.component';
import { HistoriqueDetailComponent } from './detail/historique-detail.component';
import { HistoriqueUpdateComponent } from './update/historique-update.component';
import { HistoriqueDeleteDialogComponent } from './delete/historique-delete-dialog.component';
import { HistoriqueRoutingModule } from './route/historique-routing.module';

@NgModule({
  imports: [SharedModule, HistoriqueRoutingModule],
  declarations: [HistoriqueComponent, HistoriqueDetailComponent, HistoriqueUpdateComponent, HistoriqueDeleteDialogComponent],
})
export class HistoriqueModule {}
