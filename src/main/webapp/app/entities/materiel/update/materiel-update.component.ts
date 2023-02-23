import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MaterielFormService, MaterielFormGroup } from './materiel-form.service';
import { IMateriel } from '../materiel.model';
import { MaterielService } from '../service/materiel.service';
import { ITypemateriel } from 'app/entities/typemateriel/typemateriel.model';
import { TypematerielService } from 'app/entities/typemateriel/service/typemateriel.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';

@Component({
  selector: 'jhi-materiel-update',
  templateUrl: './materiel-update.component.html',
})
export class MaterielUpdateComponent implements OnInit {
  isSaving = false;
  materiel: IMateriel | null = null;

  typematerielsSharedCollection: ITypemateriel[] = [];
  localisationsSharedCollection: ILocalisation[] = [];
  collaborateursSharedCollection: ICollaborateurs[] = [];

  editForm: MaterielFormGroup = this.materielFormService.createMaterielFormGroup();

  constructor(
    protected materielService: MaterielService,
    protected materielFormService: MaterielFormService,
    protected typematerielService: TypematerielService,
    protected localisationService: LocalisationService,
    protected collaborateursService: CollaborateursService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTypemateriel = (o1: ITypemateriel | null, o2: ITypemateriel | null): boolean =>
    this.typematerielService.compareTypemateriel(o1, o2);

  compareLocalisation = (o1: ILocalisation | null, o2: ILocalisation | null): boolean =>
    this.localisationService.compareLocalisation(o1, o2);

  compareCollaborateurs = (o1: ICollaborateurs | null, o2: ICollaborateurs | null): boolean =>
    this.collaborateursService.compareCollaborateurs(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ materiel }) => {
      this.materiel = materiel;
      if (materiel) {
        this.updateForm(materiel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const materiel = this.materielFormService.getMateriel(this.editForm);
    if (materiel.id !== null) {
      this.subscribeToSaveResponse(this.materielService.update(materiel));
    } else {
      this.subscribeToSaveResponse(this.materielService.create(materiel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMateriel>>): void {
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

  protected updateForm(materiel: IMateriel): void {
    this.materiel = materiel;
    this.materielFormService.resetForm(this.editForm, materiel);

    this.typematerielsSharedCollection = this.typematerielService.addTypematerielToCollectionIfMissing<ITypemateriel>(
      this.typematerielsSharedCollection,
      materiel.objet
    );
    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      materiel.localisation
    );
    this.collaborateursSharedCollection = this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(
      this.collaborateursSharedCollection,
      materiel.collaborateur
    );
  }

  protected loadRelationshipsOptions(): void {
    this.typematerielService
      .query()
      .pipe(map((res: HttpResponse<ITypemateriel[]>) => res.body ?? []))
      .pipe(
        map((typemateriels: ITypemateriel[]) =>
          this.typematerielService.addTypematerielToCollectionIfMissing<ITypemateriel>(typemateriels, this.materiel?.objet)
        )
      )
      .subscribe((typemateriels: ITypemateriel[]) => (this.typematerielsSharedCollection = typemateriels));

    this.localisationService
      .query()
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(localisations, this.materiel?.localisation)
        )
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsSharedCollection = localisations));

    this.collaborateursService
      .query()
      .pipe(map((res: HttpResponse<ICollaborateurs[]>) => res.body ?? []))
      .pipe(
        map((collaborateurs: ICollaborateurs[]) =>
          this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(collaborateurs, this.materiel?.collaborateur)
        )
      )
      .subscribe((collaborateurs: ICollaborateurs[]) => (this.collaborateursSharedCollection = collaborateurs));
  }
}
