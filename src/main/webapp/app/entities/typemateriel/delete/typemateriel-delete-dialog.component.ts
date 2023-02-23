import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypemateriel } from '../typemateriel.model';
import { TypematerielService } from '../service/typemateriel.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './typemateriel-delete-dialog.component.html',
})
export class TypematerielDeleteDialogComponent {
  typemateriel?: ITypemateriel;

  constructor(protected typematerielService: TypematerielService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.typematerielService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
