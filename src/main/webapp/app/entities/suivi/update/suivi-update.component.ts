import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SuiviFormService, SuiviFormGroup } from './suivi-form.service';
import { ISuivi } from '../suivi.model';
import { SuiviService } from '../service/suivi.service';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';
import { Etat } from 'app/entities/enumerations/etat.model';

@Component({
  selector: 'jhi-suivi-update',
  templateUrl: './suivi-update.component.html',
})
export class SuiviUpdateComponent implements OnInit {
  isSaving = false;
  suivi: ISuivi | null = null;
  etatValues = Object.keys(Etat);

  collaborateursSharedCollection: ICollaborateurs[] = [];

  editForm: SuiviFormGroup = this.suiviFormService.createSuiviFormGroup();

  constructor(
    protected suiviService: SuiviService,
    protected suiviFormService: SuiviFormService,
    protected collaborateursService: CollaborateursService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCollaborateurs = (o1: ICollaborateurs | null, o2: ICollaborateurs | null): boolean =>
    this.collaborateursService.compareCollaborateurs(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suivi }) => {
      this.suivi = suivi;
      if (suivi) {
        this.updateForm(suivi);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const suivi = this.suiviFormService.getSuivi(this.editForm);
    if (suivi.id !== null) {
      this.subscribeToSaveResponse(this.suiviService.update(suivi));
    } else {
      this.subscribeToSaveResponse(this.suiviService.create(suivi));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuivi>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(suivi: ISuivi): void {
    this.suivi = suivi;
    this.suiviFormService.resetForm(this.editForm, suivi);

    this.collaborateursSharedCollection = this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(
      this.collaborateursSharedCollection,
      suivi.collaborateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.collaborateursService
      .query()
      .pipe(map((res: HttpResponse<ICollaborateurs[]>) => res.body ?? []))
      .pipe(
        map((collaborateurs: ICollaborateurs[]) =>
          this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(collaborateurs, this.suivi?.collaborateur)
        )
      )
      .subscribe((collaborateurs: ICollaborateurs[]) => (this.collaborateursSharedCollection = collaborateurs));
  }
}
