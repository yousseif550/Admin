import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CollaborateursComponent } from './list/collaborateurs.component';
import { CollaborateursDetailComponent } from './detail/collaborateurs-detail.component';
import { CollaborateursUpdateComponent } from './update/collaborateurs-update.component';
import { CollaborateursDeleteDialogComponent } from './delete/collaborateurs-delete-dialog.component';
import { CollaborateursRoutingModule } from './route/collaborateurs-routing.module';

@NgModule({
  imports: [SharedModule, CollaborateursRoutingModule],
  declarations: [
    CollaborateursComponent,
    CollaborateursDetailComponent,
    CollaborateursUpdateComponent,
    CollaborateursDeleteDialogComponent,
  ],
})
export class CollaborateursModule {}
