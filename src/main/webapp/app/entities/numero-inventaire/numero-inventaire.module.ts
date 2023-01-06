import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NumeroInventaireComponent } from './list/numero-inventaire.component';
import { NumeroInventaireDetailComponent } from './detail/numero-inventaire-detail.component';
import { NumeroInventaireUpdateComponent } from './update/numero-inventaire-update.component';
import { NumeroInventaireDeleteDialogComponent } from './delete/numero-inventaire-delete-dialog.component';
import { NumeroInventaireRoutingModule } from './route/numero-inventaire-routing.module';

@NgModule({
  imports: [SharedModule, NumeroInventaireRoutingModule],
  declarations: [
    NumeroInventaireComponent,
    NumeroInventaireDetailComponent,
    NumeroInventaireUpdateComponent,
    NumeroInventaireDeleteDialogComponent,
  ],
})
export class NumeroInventaireModule {}
