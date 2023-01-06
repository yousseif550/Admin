import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICollaborateurs } from '../collaborateurs.model';
import { CollaborateursService } from '../service/collaborateurs.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './collaborateurs-delete-dialog.component.html',
})
export class CollaborateursDeleteDialogComponent {
  collaborateurs?: ICollaborateurs;

  constructor(protected collaborateursService: CollaborateursService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.collaborateursService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
