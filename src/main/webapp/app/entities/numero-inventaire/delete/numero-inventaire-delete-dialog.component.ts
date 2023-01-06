import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INumeroInventaire } from '../numero-inventaire.model';
import { NumeroInventaireService } from '../service/numero-inventaire.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './numero-inventaire-delete-dialog.component.html',
})
export class NumeroInventaireDeleteDialogComponent {
  numeroInventaire?: INumeroInventaire;

  constructor(protected numeroInventaireService: NumeroInventaireService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.numeroInventaireService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
