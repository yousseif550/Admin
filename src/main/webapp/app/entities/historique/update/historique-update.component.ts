import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { HistoriqueFormService, HistoriqueFormGroup } from './historique-form.service';
import { IHistorique } from '../historique.model';
import { HistoriqueService } from '../service/historique.service';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';

@Component({
  selector: 'jhi-historique-update',
  templateUrl: './historique-update.component.html',
})
export class HistoriqueUpdateComponent implements OnInit {
  isSaving = false;
  historique: IHistorique | null = null;

  collaborateursSharedCollection: ICollaborateurs[] = [];
  materielsSharedCollection: IMateriel[] = [];

  editForm: HistoriqueFormGroup = this.historiqueFormService.createHistoriqueFormGroup();

  constructor(
    protected historiqueService: HistoriqueService,
    protected historiqueFormService: HistoriqueFormService,
    protected collaborateursService: CollaborateursService,
    protected materielService: MaterielService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCollaborateurs = (o1: ICollaborateurs | null, o2: ICollaborateurs | null): boolean =>
    this.collaborateursService.compareCollaborateurs(o1, o2);

  compareMateriel = (o1: IMateriel | null, o2: IMateriel | null): boolean => this.materielService.compareMateriel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historique }) => {
      this.historique = historique;
      if (historique) {
        this.updateForm(historique);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historique = this.historiqueFormService.getHistorique(this.editForm);
    if (historique.id !== null) {
      this.subscribeToSaveResponse(this.historiqueService.update(historique));
    } else {
      this.subscribeToSaveResponse(this.historiqueService.create(historique));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistorique>>): void {
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

  protected updateForm(historique: IHistorique): void {
    this.historique = historique;
    this.historiqueFormService.resetForm(this.editForm, historique);

    this.collaborateursSharedCollection = this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(
      this.collaborateursSharedCollection,
      historique.ancienProprietaire,
      historique.nouveauProprietaire
    );
    this.materielsSharedCollection = this.materielService.addMaterielToCollectionIfMissing<IMateriel>(
      this.materielsSharedCollection,
      historique.materiel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.collaborateursService
      .query()
      .pipe(map((res: HttpResponse<ICollaborateurs[]>) => res.body ?? []))
      .pipe(
        map((collaborateurs: ICollaborateurs[]) =>
          this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(
            collaborateurs,
            this.historique?.ancienProprietaire,
            this.historique?.nouveauProprietaire
          )
        )
      )
      .subscribe((collaborateurs: ICollaborateurs[]) => (this.collaborateursSharedCollection = collaborateurs));

    this.materielService
      .query()
      .pipe(map((res: HttpResponse<IMateriel[]>) => res.body ?? []))
      .pipe(
        map((materiels: IMateriel[]) =>
          this.materielService.addMaterielToCollectionIfMissing<IMateriel>(materiels, this.historique?.materiel)
        )
      )
      .subscribe((materiels: IMateriel[]) => (this.materielsSharedCollection = materiels));
  }
}
