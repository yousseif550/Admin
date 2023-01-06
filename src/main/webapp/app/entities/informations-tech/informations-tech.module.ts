import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InformationsTechComponent } from './list/informations-tech.component';
import { InformationsTechDetailComponent } from './detail/informations-tech-detail.component';
import { InformationsTechUpdateComponent } from './update/informations-tech-update.component';
import { InformationsTechDeleteDialogComponent } from './delete/informations-tech-delete-dialog.component';
import { InformationsTechRoutingModule } from './route/informations-tech-routing.module';

@NgModule({
  imports: [SharedModule, InformationsTechRoutingModule],
  declarations: [
    InformationsTechComponent,
    InformationsTechDetailComponent,
    InformationsTechUpdateComponent,
    InformationsTechDeleteDialogComponent,
  ],
})
export class InformationsTechModule {}
