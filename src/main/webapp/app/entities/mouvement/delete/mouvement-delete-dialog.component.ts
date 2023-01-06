import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMouvement } from '../mouvement.model';
import { MouvementService } from '../service/mouvement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './mouvement-delete-dialog.component.html',
})
export class MouvementDeleteDialogComponent {
  mouvement?: IMouvement;

  constructor(protected mouvementService: MouvementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.mouvementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
