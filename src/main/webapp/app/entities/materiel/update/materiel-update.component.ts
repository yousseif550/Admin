import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MaterielFormService, MaterielFormGroup } from './materiel-form.service';
import { IMateriel } from '../materiel.model';
import { MaterielService } from '../service/materiel.service';
import { INumeroInventaire } from 'app/entities/numero-inventaire/numero-inventaire.model';
import { NumeroInventaireService } from 'app/entities/numero-inventaire/service/numero-inventaire.service';
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

  numeroInventairesSharedCollection: INumeroInventaire[] = [];
  localisationsSharedCollection: ILocalisation[] = [];
  collaborateursSharedCollection: ICollaborateurs[] = [];

  editForm: MaterielFormGroup = this.materielFormService.createMaterielFormGroup();

  constructor(
    protected materielService: MaterielService,
    protected materielFormService: MaterielFormService,
    protected numeroInventaireService: NumeroInventaireService,
    protected localisationService: LocalisationService,
    protected collaborateursService: CollaborateursService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareNumeroInventaire = (o1: INumeroInventaire | null, o2: INumeroInventaire | null): boolean =>
    this.numeroInventaireService.compareNumeroInventaire(o1, o2);

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

    this.numeroInventairesSharedCollection = this.numeroInventaireService.addNumeroInventaireToCollectionIfMissing<INumeroInventaire>(
      this.numeroInventairesSharedCollection,
      materiel.numeroInventaire
    );
    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      materiel.localisation
    );
    this.collaborateursSharedCollection = this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(
      this.collaborateursSharedCollection,
      materiel.collaborateurs
    );
  }

  protected loadRelationshipsOptions(): void {
    this.numeroInventaireService
      .query()
      .pipe(map((res: HttpResponse<INumeroInventaire[]>) => res.body ?? []))
      .pipe(
        map((numeroInventaires: INumeroInventaire[]) =>
          this.numeroInventaireService.addNumeroInventaireToCollectionIfMissing<INumeroInventaire>(
            numeroInventaires,
            this.materiel?.numeroInventaire
          )
        )
      )
      .subscribe((numeroInventaires: INumeroInventaire[]) => (this.numeroInventairesSharedCollection = numeroInventaires));

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
          this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(collaborateurs, this.materiel?.collaborateurs)
        )
      )
      .subscribe((collaborateurs: ICollaborateurs[]) => (this.collaborateursSharedCollection = collaborateurs));
  }
}
