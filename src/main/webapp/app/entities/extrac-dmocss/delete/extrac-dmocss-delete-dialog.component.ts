import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExtracDMOCSS } from '../extrac-dmocss.model';
import { ExtracDMOCSSService } from '../service/extrac-dmocss.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './extrac-dmocss-delete-dialog.component.html',
})
export class ExtracDMOCSSDeleteDialogComponent {
  extracDMOCSS?: IExtracDMOCSS;

  constructor(protected extracDMOCSSService: ExtracDMOCSSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.extracDMOCSSService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
