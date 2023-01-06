import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMateriel } from '../materiel.model';
import { MaterielService } from '../service/materiel.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './materiel-delete-dialog.component.html',
})
export class MaterielDeleteDialogComponent {
  materiel?: IMateriel;

  constructor(protected materielService: MaterielService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.materielService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
