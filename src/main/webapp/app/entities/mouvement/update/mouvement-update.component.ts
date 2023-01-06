import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MouvementFormService, MouvementFormGroup } from './mouvement-form.service';
import { IMouvement } from '../mouvement.model';
import { MouvementService } from '../service/mouvement.service';
import { Type } from 'app/entities/enumerations/type.model';

@Component({
  selector: 'jhi-mouvement-update',
  templateUrl: './mouvement-update.component.html',
})
export class MouvementUpdateComponent implements OnInit {
  isSaving = false;
  mouvement: IMouvement | null = null;
  typeValues = Object.keys(Type);

  editForm: MouvementFormGroup = this.mouvementFormService.createMouvementFormGroup();

  constructor(
    protected mouvementService: MouvementService,
    protected mouvementFormService: MouvementFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvement }) => {
      this.mouvement = mouvement;
      if (mouvement) {
        this.updateForm(mouvement);
      }
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
  }
}
