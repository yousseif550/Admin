import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuivi } from '../suivi.model';

@Component({
  selector: 'jhi-suivi-detail',
  templateUrl: './suivi-detail.component.html',
})
export class SuiviDetailComponent implements OnInit {
  suivi: ISuivi | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suivi }) => {
      this.suivi = suivi;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
