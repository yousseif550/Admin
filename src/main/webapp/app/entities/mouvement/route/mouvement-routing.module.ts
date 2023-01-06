import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MouvementComponent } from '../list/mouvement.component';
import { MouvementDetailComponent } from '../detail/mouvement-detail.component';
import { MouvementUpdateComponent } from '../update/mouvement-update.component';
import { MouvementRoutingResolveService } from './mouvement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const mouvementRoute: Routes = [
  {
    path: '',
    component: MouvementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MouvementDetailComponent,
    resolve: {
      mouvement: MouvementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MouvementUpdateComponent,
    resolve: {
      mouvement: MouvementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MouvementUpdateComponent,
    resolve: {
      mouvement: MouvementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mouvementRoute)],
  exports: [RouterModule],
})
export class MouvementRoutingModule {}
