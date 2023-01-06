import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICollaborateurs } from '../collaborateurs.model';

@Component({
  selector: 'jhi-collaborateurs-detail',
  templateUrl: './collaborateurs-detail.component.html',
})
export class CollaborateursDetailComponent implements OnInit {
  collaborateurs: ICollaborateurs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateurs }) => {
      this.collaborateurs = collaborateurs;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
