import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CollaborateursFormService, CollaborateursFormGroup } from './collaborateurs-form.service';
import { ICollaborateurs } from '../collaborateurs.model';
import { CollaborateursService } from '../service/collaborateurs.service';

@Component({
  selector: 'jhi-collaborateurs-update',
  templateUrl: './collaborateurs-update.component.html',
})
export class CollaborateursUpdateComponent implements OnInit {
  isSaving = false;
  collaborateurs: ICollaborateurs | null = null;

  editForm: CollaborateursFormGroup = this.collaborateursFormService.createCollaborateursFormGroup();

  constructor(
    protected collaborateursService: CollaborateursService,
    protected collaborateursFormService: CollaborateursFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateurs }) => {
      this.collaborateurs = collaborateurs;
      if (collaborateurs) {
        this.updateForm(collaborateurs);
      }
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
  }
}
