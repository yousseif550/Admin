import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistorique } from '../historique.model';
import { HistoriqueService } from '../service/historique.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './historique-delete-dialog.component.html',
})
export class HistoriqueDeleteDialogComponent {
  historique?: IHistorique;

  constructor(protected historiqueService: HistoriqueService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.historiqueService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
