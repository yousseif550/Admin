import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HistoriqueComponent } from '../list/historique.component';
import { HistoriqueDetailComponent } from '../detail/historique-detail.component';
import { HistoriqueUpdateComponent } from '../update/historique-update.component';
import { HistoriqueRoutingResolveService } from './historique-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const historiqueRoute: Routes = [
  {
    path: '',
    component: HistoriqueComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HistoriqueDetailComponent,
    resolve: {
      historique: HistoriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HistoriqueUpdateComponent,
    resolve: {
      historique: HistoriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HistoriqueUpdateComponent,
    resolve: {
      historique: HistoriqueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(historiqueRoute)],
  exports: [RouterModule],
})
export class HistoriqueRoutingModule {}
