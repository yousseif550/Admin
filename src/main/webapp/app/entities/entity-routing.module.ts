import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'mouvement',
        data: { pageTitle: 'ihmApp.mouvement.home.title' },
        loadChildren: () => import('./mouvement/mouvement.module').then(m => m.MouvementModule),
      },
      {
        path: 'suivi',
        data: { pageTitle: 'ihmApp.suivi.home.title' },
        loadChildren: () => import('./suivi/suivi.module').then(m => m.SuiviModule),
      },
      {
        path: 'collaborateurs',
        data: { pageTitle: 'ihmApp.collaborateurs.home.title' },
        loadChildren: () => import('./collaborateurs/collaborateurs.module').then(m => m.CollaborateursModule),
      },
      {
        path: 'projet',
        data: { pageTitle: 'ihmApp.projet.home.title' },
        loadChildren: () => import('./projet/projet.module').then(m => m.ProjetModule),
      },
      {
        path: 'informations-tech',
        data: { pageTitle: 'ihmApp.informationsTech.home.title' },
        loadChildren: () => import('./informations-tech/informations-tech.module').then(m => m.InformationsTechModule),
      },
      {
        path: 'ticket',
        data: { pageTitle: 'ihmApp.ticket.home.title' },
        loadChildren: () => import('./ticket/ticket.module').then(m => m.TicketModule),
      },
      {
        path: 'localisation',
        data: { pageTitle: 'ihmApp.localisation.home.title' },
        loadChildren: () => import('./localisation/localisation.module').then(m => m.LocalisationModule),
      },
      {
        path: 'materiel',
        data: { pageTitle: 'ihmApp.materiel.home.title' },
        loadChildren: () => import('./materiel/materiel.module').then(m => m.MaterielModule),
      },
      {
        path: 'historique',
        data: { pageTitle: 'ihmApp.historique.home.title' },
        loadChildren: () => import('./historique/historique.module').then(m => m.HistoriqueModule),
      },
      {
        path: 'numero-inventaire',
        data: { pageTitle: 'ihmApp.numeroInventaire.home.title' },
        loadChildren: () => import('./numero-inventaire/numero-inventaire.module').then(m => m.NumeroInventaireModule),
      },
      {
        path: 'extrac-dmocss',
        data: { pageTitle: 'ihmApp.extracDMOCSS.home.title' },
        loadChildren: () => import('./extrac-dmocss/extrac-dmocss.module').then(m => m.ExtracDMOCSSModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
