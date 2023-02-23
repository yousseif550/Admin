import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypematerielComponent } from '../list/typemateriel.component';
import { TypematerielDetailComponent } from '../detail/typemateriel-detail.component';
import { TypematerielUpdateComponent } from '../update/typemateriel-update.component';
import { TypematerielRoutingResolveService } from './typemateriel-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const typematerielRoute: Routes = [
  {
    path: '',
    component: TypematerielComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypematerielDetailComponent,
    resolve: {
      typemateriel: TypematerielRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypematerielUpdateComponent,
    resolve: {
      typemateriel: TypematerielRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypematerielUpdateComponent,
    resolve: {
      typemateriel: TypematerielRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typematerielRoute)],
  exports: [RouterModule],
})
export class TypematerielRoutingModule {}
