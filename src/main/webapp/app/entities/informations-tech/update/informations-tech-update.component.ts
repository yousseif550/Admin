import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { InformationsTechFormService, InformationsTechFormGroup } from './informations-tech-form.service';
import { IInformationsTech } from '../informations-tech.model';
import { InformationsTechService } from '../service/informations-tech.service';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';

@Component({
  selector: 'jhi-informations-tech-update',
  templateUrl: './informations-tech-update.component.html',
})
export class InformationsTechUpdateComponent implements OnInit {
  isSaving = false;
  informationsTech: IInformationsTech | null = null;

  materielsSharedCollection: IMateriel[] = [];

  editForm: InformationsTechFormGroup = this.informationsTechFormService.createInformationsTechFormGroup();

  constructor(
    protected informationsTechService: InformationsTechService,
    protected informationsTechFormService: InformationsTechFormService,
    protected materielService: MaterielService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMateriel = (o1: IMateriel | null, o2: IMateriel | null): boolean => this.materielService.compareMateriel(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ informationsTech }) => {
      this.informationsTech = informationsTech;
      if (informationsTech) {
        this.updateForm(informationsTech);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const informationsTech = this.informationsTechFormService.getInformationsTech(this.editForm);
    if (informationsTech.id !== null) {
      this.subscribeToSaveResponse(this.informationsTechService.update(informationsTech));
    } else {
      this.subscribeToSaveResponse(this.informationsTechService.create(informationsTech));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInformationsTech>>): void {
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

  protected updateForm(informationsTech: IInformationsTech): void {
    this.informationsTech = informationsTech;
    this.informationsTechFormService.resetForm(this.editForm, informationsTech);

    this.materielsSharedCollection = this.materielService.addMaterielToCollectionIfMissing<IMateriel>(
      this.materielsSharedCollection,
      informationsTech.pcDGFiP
    );
  }

  protected loadRelationshipsOptions(): void {
    this.materielService
      .query()
      .pipe(map((res: HttpResponse<IMateriel[]>) => res.body ?? []))
      .pipe(
        map((materiels: IMateriel[]) =>
          this.materielService.addMaterielToCollectionIfMissing<IMateriel>(materiels, this.informationsTech?.pcDGFiP)
        )
      )
      .subscribe((materiels: IMateriel[]) => (this.materielsSharedCollection = materiels));
  }
}
