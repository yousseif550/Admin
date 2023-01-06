import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SuiviComponent } from '../list/suivi.component';
import { SuiviDetailComponent } from '../detail/suivi-detail.component';
import { SuiviUpdateComponent } from '../update/suivi-update.component';
import { SuiviRoutingResolveService } from './suivi-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const suiviRoute: Routes = [
  {
    path: '',
    component: SuiviComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SuiviDetailComponent,
    resolve: {
      suivi: SuiviRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SuiviUpdateComponent,
    resolve: {
      suivi: SuiviRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SuiviUpdateComponent,
    resolve: {
      suivi: SuiviRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(suiviRoute)],
  exports: [RouterModule],
})
export class SuiviRoutingModule {}
