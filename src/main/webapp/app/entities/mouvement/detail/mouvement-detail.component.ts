import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMouvement } from '../mouvement.model';

@Component({
  selector: 'jhi-mouvement-detail',
  templateUrl: './mouvement-detail.component.html',
})
export class MouvementDetailComponent implements OnInit {
  mouvement: IMouvement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvement }) => {
      this.mouvement = mouvement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
