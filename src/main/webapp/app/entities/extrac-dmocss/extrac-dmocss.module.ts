import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExtracDMOCSSComponent } from './list/extrac-dmocss.component';
import { ExtracDMOCSSDetailComponent } from './detail/extrac-dmocss-detail.component';
import { ExtracDMOCSSUpdateComponent } from './update/extrac-dmocss-update.component';
import { ExtracDMOCSSDeleteDialogComponent } from './delete/extrac-dmocss-delete-dialog.component';
import { ExtracDMOCSSRoutingModule } from './route/extrac-dmocss-routing.module';

@NgModule({
  imports: [SharedModule, ExtracDMOCSSRoutingModule],
  declarations: [ExtracDMOCSSComponent, ExtracDMOCSSDetailComponent, ExtracDMOCSSUpdateComponent, ExtracDMOCSSDeleteDialogComponent],
})
export class ExtracDMOCSSModule {}
