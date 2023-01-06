import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INumeroInventaire } from '../numero-inventaire.model';

@Component({
  selector: 'jhi-numero-inventaire-detail',
  templateUrl: './numero-inventaire-detail.component.html',
})
export class NumeroInventaireDetailComponent implements OnInit {
  numeroInventaire: INumeroInventaire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ numeroInventaire }) => {
      this.numeroInventaire = numeroInventaire;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
