import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypemateriel } from '../typemateriel.model';

@Component({
  selector: 'jhi-typemateriel-detail',
  templateUrl: './typemateriel-detail.component.html',
})
export class TypematerielDetailComponent implements OnInit {
  typemateriel: ITypemateriel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typemateriel }) => {
      this.typemateriel = typemateriel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
