import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InformationsTechComponent } from '../list/informations-tech.component';
import { InformationsTechDetailComponent } from '../detail/informations-tech-detail.component';
import { InformationsTechUpdateComponent } from '../update/informations-tech-update.component';
import { InformationsTechRoutingResolveService } from './informations-tech-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const informationsTechRoute: Routes = [
  {
    path: '',
    component: InformationsTechComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InformationsTechDetailComponent,
    resolve: {
      informationsTech: InformationsTechRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InformationsTechUpdateComponent,
    resolve: {
      informationsTech: InformationsTechRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InformationsTechUpdateComponent,
    resolve: {
      informationsTech: InformationsTechRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(informationsTechRoute)],
  exports: [RouterModule],
})
export class InformationsTechRoutingModule {}
