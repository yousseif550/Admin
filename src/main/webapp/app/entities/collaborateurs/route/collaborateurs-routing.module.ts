import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CollaborateursComponent } from '../list/collaborateurs.component';
import { CollaborateursDetailComponent } from '../detail/collaborateurs-detail.component';
import { CollaborateursUpdateComponent } from '../update/collaborateurs-update.component';
import { CollaborateursRoutingResolveService } from './collaborateurs-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const collaborateursRoute: Routes = [
  {
    path: '',
    component: CollaborateursComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CollaborateursDetailComponent,
    resolve: {
      collaborateurs: CollaborateursRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CollaborateursUpdateComponent,
    resolve: {
      collaborateurs: CollaborateursRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CollaborateursUpdateComponent,
    resolve: {
      collaborateurs: CollaborateursRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(collaborateursRoute)],
  exports: [RouterModule],
})
export class CollaborateursRoutingModule {}
