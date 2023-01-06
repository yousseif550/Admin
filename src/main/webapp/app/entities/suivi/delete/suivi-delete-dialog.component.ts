import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISuivi } from '../suivi.model';
import { SuiviService } from '../service/suivi.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './suivi-delete-dialog.component.html',
})
export class SuiviDeleteDialogComponent {
  suivi?: ISuivi;

  constructor(protected suiviService: SuiviService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.suiviService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
