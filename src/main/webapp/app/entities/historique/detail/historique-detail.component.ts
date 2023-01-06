import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistorique } from '../historique.model';

@Component({
  selector: 'jhi-historique-detail',
  templateUrl: './historique-detail.component.html',
})
export class HistoriqueDetailComponent implements OnInit {
  historique: IHistorique | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historique }) => {
      this.historique = historique;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
