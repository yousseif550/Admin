import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExtracDMOCSSComponent } from '../list/extrac-dmocss.component';
import { ExtracDMOCSSDetailComponent } from '../detail/extrac-dmocss-detail.component';
import { ExtracDMOCSSUpdateComponent } from '../update/extrac-dmocss-update.component';
import { ExtracDMOCSSRoutingResolveService } from './extrac-dmocss-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const extracDMOCSSRoute: Routes = [
  {
    path: '',
    component: ExtracDMOCSSComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExtracDMOCSSDetailComponent,
    resolve: {
      extracDMOCSS: ExtracDMOCSSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExtracDMOCSSUpdateComponent,
    resolve: {
      extracDMOCSS: ExtracDMOCSSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExtracDMOCSSUpdateComponent,
    resolve: {
      extracDMOCSS: ExtracDMOCSSRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(extracDMOCSSRoute)],
  exports: [RouterModule],
})
export class ExtracDMOCSSRoutingModule {}
