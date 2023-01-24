import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ExtracDMOCSSFormService, ExtracDMOCSSFormGroup } from './extrac-dmocss-form.service';
import { IExtracDMOCSS } from '../extrac-dmocss.model';
import { ExtracDMOCSSService } from '../service/extrac-dmocss.service';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { Etat } from 'app/entities/enumerations/etat.model';

@Component({
  selector: 'jhi-extrac-dmocss-update',
  templateUrl: './extrac-dmocss-update.component.html',
})
export class ExtracDMOCSSUpdateComponent implements OnInit {
  isSaving = false;
  extracDMOCSS: IExtracDMOCSS | null = null;
  etatValues = Object.keys(Etat);

  collaborateursSharedCollection: ICollaborateurs[] = [];
  materielsSharedCollection: IMateriel[] = [];
  localisationsSharedCollection: ILocalisation[] = [];

  editForm: ExtracDMOCSSFormGroup = this.extracDMOCSSFormService.createExtracDMOCSSFormGroup();

  constructor(
    protected extracDMOCSSService: ExtracDMOCSSService,
    protected extracDMOCSSFormService: ExtracDMOCSSFormService,
    protected collaborateursService: CollaborateursService,
    protected materielService: MaterielService,
    protected localisationService: LocalisationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCollaborateurs = (o1: ICollaborateurs | null, o2: ICollaborateurs | null): boolean =>
    this.collaborateursService.compareCollaborateurs(o1, o2);

  compareMateriel = (o1: IMateriel | null, o2: IMateriel | null): boolean => this.materielService.compareMateriel(o1, o2);

  compareLocalisation = (o1: ILocalisation | null, o2: ILocalisation | null): boolean =>
    this.localisationService.compareLocalisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extracDMOCSS }) => {
      this.extracDMOCSS = extracDMOCSS;
      if (extracDMOCSS) {
        this.updateForm(extracDMOCSS);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const extracDMOCSS = this.extracDMOCSSFormService.getExtracDMOCSS(this.editForm);
    if (extracDMOCSS.id !== null) {
      this.subscribeToSaveResponse(this.extracDMOCSSService.update(extracDMOCSS));
    } else {
      this.subscribeToSaveResponse(this.extracDMOCSSService.create(extracDMOCSS));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtracDMOCSS>>): void {
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

  protected updateForm(extracDMOCSS: IExtracDMOCSS): void {
    this.extracDMOCSS = extracDMOCSS;
    this.extracDMOCSSFormService.resetForm(this.editForm, extracDMOCSS);

    this.collaborateursSharedCollection = this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(
      this.collaborateursSharedCollection,
      extracDMOCSS.collaborateur
    );
    this.materielsSharedCollection = this.materielService.addMaterielToCollectionIfMissing<IMateriel>(
      this.materielsSharedCollection,
      extracDMOCSS.materiel
    );
    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      extracDMOCSS.localisation
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
            this.extracDMOCSS?.collaborateur
          )
        )
      )
      .subscribe((collaborateurs: ICollaborateurs[]) => (this.collaborateursSharedCollection = collaborateurs));

    this.materielService
      .query()
      .pipe(map((res: HttpResponse<IMateriel[]>) => res.body ?? []))
      .pipe(
        map((materiels: IMateriel[]) =>
          this.materielService.addMaterielToCollectionIfMissing<IMateriel>(materiels, this.extracDMOCSS?.materiel)
        )
      )
      .subscribe((materiels: IMateriel[]) => (this.materielsSharedCollection = materiels));

    this.localisationService
      .query()
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(localisations, this.extracDMOCSS?.localisation)
        )
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsSharedCollection = localisations));
  }
}
