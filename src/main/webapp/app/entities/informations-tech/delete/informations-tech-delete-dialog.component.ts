import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInformationsTech } from '../informations-tech.model';
import { InformationsTechService } from '../service/informations-tech.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './informations-tech-delete-dialog.component.html',
})
export class InformationsTechDeleteDialogComponent {
  informationsTech?: IInformationsTech;

  constructor(protected informationsTechService: InformationsTechService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.informationsTechService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
