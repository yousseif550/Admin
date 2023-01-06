import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LocalisationFormService, LocalisationFormGroup } from './localisation-form.service';
import { ILocalisation } from '../localisation.model';
import { LocalisationService } from '../service/localisation.service';

@Component({
  selector: 'jhi-localisation-update',
  templateUrl: './localisation-update.component.html',
})
export class LocalisationUpdateComponent implements OnInit {
  isSaving = false;
  localisation: ILocalisation | null = null;

  editForm: LocalisationFormGroup = this.localisationFormService.createLocalisationFormGroup();

  constructor(
    protected localisationService: LocalisationService,
    protected localisationFormService: LocalisationFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localisation }) => {
      this.localisation = localisation;
      if (localisation) {
        this.updateForm(localisation);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localisation = this.localisationFormService.getLocalisation(this.editForm);
    if (localisation.id !== null) {
      this.subscribeToSaveResponse(this.localisationService.update(localisation));
    } else {
      this.subscribeToSaveResponse(this.localisationService.create(localisation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalisation>>): void {
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

  protected updateForm(localisation: ILocalisation): void {
    this.localisation = localisation;
    this.localisationFormService.resetForm(this.editForm, localisation);
  }
}
