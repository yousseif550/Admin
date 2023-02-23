import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TypematerielFormService, TypematerielFormGroup } from './typemateriel-form.service';
import { ITypemateriel } from '../typemateriel.model';
import { TypematerielService } from '../service/typemateriel.service';

@Component({
  selector: 'jhi-typemateriel-update',
  templateUrl: './typemateriel-update.component.html',
})
export class TypematerielUpdateComponent implements OnInit {
  isSaving = false;
  typemateriel: ITypemateriel | null = null;

  editForm: TypematerielFormGroup = this.typematerielFormService.createTypematerielFormGroup();

  constructor(
    protected typematerielService: TypematerielService,
    protected typematerielFormService: TypematerielFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typemateriel }) => {
      this.typemateriel = typemateriel;
      if (typemateriel) {
        this.updateForm(typemateriel);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typemateriel = this.typematerielFormService.getTypemateriel(this.editForm);
    if (typemateriel.id !== null) {
      this.subscribeToSaveResponse(this.typematerielService.update(typemateriel));
    } else {
      this.subscribeToSaveResponse(this.typematerielService.create(typemateriel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypemateriel>>): void {
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

  protected updateForm(typemateriel: ITypemateriel): void {
    this.typemateriel = typemateriel;
    this.typematerielFormService.resetForm(this.editForm, typemateriel);
  }
}
