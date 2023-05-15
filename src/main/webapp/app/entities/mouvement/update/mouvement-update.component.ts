import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MouvementFormService, MouvementFormGroup } from './mouvement-form.service';
import { IMouvement } from '../mouvement.model';
import { MouvementService } from '../service/mouvement.service';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';

@Component({
  selector: 'jhi-mouvement-update',
  templateUrl: './mouvement-update.component.html',
})
export class MouvementUpdateComponent implements OnInit {
  isSaving = false;
  mouvement: IMouvement | null = null;

  materielsSharedCollection: IMateriel[] = [];
  localisationsSharedCollection: ILocalisation[] = [];

  editForm: MouvementFormGroup = this.mouvementFormService.createMouvementFormGroup();

  constructor(
    protected mouvementService: MouvementService,
    protected mouvementFormService: MouvementFormService,
    protected materielService: MaterielService,
    protected localisationService: LocalisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMateriel = (o1: IMateriel | null, o2: IMateriel | null): boolean => this.materielService.compareMateriel(o1, o2);

  compareLocalisation = (o1: ILocalisation | null, o2: ILocalisation | null): boolean =>
    this.localisationService.compareLocalisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvement }) => {
      this.mouvement = mouvement;
      if (mouvement) {
        this.updateForm(mouvement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mouvement = this.mouvementFormService.getMouvement(this.editForm);
    if (mouvement.id !== null) {
      this.subscribeToSaveResponse(this.mouvementService.update(mouvement));
    } else {
      this.subscribeToSaveResponse(this.mouvementService.create(mouvement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMouvement>>): void {
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

  protected updateForm(mouvement: IMouvement): void {
    this.mouvement = mouvement;
    this.mouvementFormService.resetForm(this.editForm, mouvement);

    this.materielsSharedCollection = this.materielService.addMaterielToCollectionIfMissing<IMateriel>(
      this.materielsSharedCollection,
      mouvement.materiel
    );
    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      mouvement.localisation
    );
  }

  protected loadRelationshipsOptions(): void {
    this.materielService
      .query()
      .pipe(map((res: HttpResponse<IMateriel[]>) => res.body ?? []))
      .pipe(
        map((materiels: IMateriel[]) =>
          this.materielService.addMaterielToCollectionIfMissing<IMateriel>(materiels, this.mouvement?.materiel)
        )
      )
      .subscribe((materiels: IMateriel[]) => (this.materielsSharedCollection = materiels));

    this.localisationService
      .query()
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(localisations, this.mouvement?.localisation)
        )
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsSharedCollection = localisations));
  }
}
