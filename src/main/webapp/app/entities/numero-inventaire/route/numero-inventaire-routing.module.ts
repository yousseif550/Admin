import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NumeroInventaireComponent } from '../list/numero-inventaire.component';
import { NumeroInventaireDetailComponent } from '../detail/numero-inventaire-detail.component';
import { NumeroInventaireUpdateComponent } from '../update/numero-inventaire-update.component';
import { NumeroInventaireRoutingResolveService } from './numero-inventaire-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const numeroInventaireRoute: Routes = [
  {
    path: '',
    component: NumeroInventaireComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NumeroInventaireDetailComponent,
    resolve: {
      numeroInventaire: NumeroInventaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NumeroInventaireUpdateComponent,
    resolve: {
      numeroInventaire: NumeroInventaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NumeroInventaireUpdateComponent,
    resolve: {
      numeroInventaire: NumeroInventaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(numeroInventaireRoute)],
  exports: [RouterModule],
})
export class NumeroInventaireRoutingModule {}
