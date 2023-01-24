import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CollaborateursFormService, CollaborateursFormGroup } from './collaborateurs-form.service';
import { ICollaborateurs } from '../collaborateurs.model';
import { CollaborateursService } from '../service/collaborateurs.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

@Component({
  selector: 'jhi-collaborateurs-update',
  templateUrl: './collaborateurs-update.component.html',
})
export class CollaborateursUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateurs | null = null;

  localisationsSharedCollection: ILocalisation[] = [];
  projetsSharedCollection: IProjet[] = [];

  editForm: CollaborateursFormGroup = this.collaborateursFormService.createCollaborateursFormGroup();

  constructor(
    protected collaborateursService: CollaborateursService,
    protected collaborateursFormService: CollaborateursFormService,
    protected localisationService: LocalisationService,
    protected projetService: ProjetService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLocalisation = (o1: ILocalisation | null, o2: ILocalisation | null): boolean =>
    this.localisationService.compareLocalisation(o1, o2);

  compareProjet = (o1: IProjet | null, o2: IProjet | null): boolean => this.projetService.compareProjet(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateurs }) => {
      this.collaborateurs = collaborateurs;
      if (collaborateurs) {
        this.updateForm(collaborateurs);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collaborateurs = this.collaborateursFormService.getCollaborateurs(this.editForm);
    if (collaborateurs.id !== null) {
      this.subscribeToSaveResponse(this.collaborateursService.update(collaborateurs));
    } else {
      this.subscribeToSaveResponse(this.collaborateursService.create(collaborateurs));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollaborateurs>>): void {
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

  protected updateForm(collaborateurs: ICollaborateurs): void {
    this.collaborateurs = collaborateurs;
    this.collaborateursFormService.resetForm(this.editForm, collaborateurs);

    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      collaborateurs.localisation
    );
    this.projetsSharedCollection = this.projetService.addProjetToCollectionIfMissing<IProjet>(
      this.projetsSharedCollection,
      ...(collaborateurs.projets ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.localisationService
      .query()
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(localisations, this.collaborateurs?.localisation)
        )
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsSharedCollection = localisations));

    this.projetService
      .query()
      .pipe(map((res: HttpResponse<IProjet[]>) => res.body ?? []))
      .pipe(
        map((projets: IProjet[]) =>
          this.projetService.addProjetToCollectionIfMissing<IProjet>(projets, ...(this.collaborateurs?.projets ?? []))
        )
      )
      .subscribe((projets: IProjet[]) => (this.projetsSharedCollection = projets));
  }
}
