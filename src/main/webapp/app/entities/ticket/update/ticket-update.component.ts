import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TicketFormService, TicketFormGroup } from './ticket-form.service';
import { ITicket } from '../ticket.model';
import { TicketService } from '../service/ticket.service';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';
import { Etat } from 'app/entities/enumerations/etat.model';

@Component({
  selector: 'jhi-ticket-update',
  templateUrl: './ticket-update.component.html',
})
export class TicketUpdateComponent implements OnInit {
  isSaving = false;
  ticket: ITicket | null = null;
  etatValues = Object.keys(Etat);

  collaborateursSharedCollection: ICollaborateurs[] = [];

  editForm: TicketFormGroup = this.ticketFormService.createTicketFormGroup();

  constructor(
    protected ticketService: TicketService,
    protected ticketFormService: TicketFormService,
    protected collaborateursService: CollaborateursService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCollaborateurs = (o1: ICollaborateurs | null, o2: ICollaborateurs | null): boolean =>
    this.collaborateursService.compareCollaborateurs(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticket }) => {
      this.ticket = ticket;
      if (ticket) {
        this.updateForm(ticket);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ticket = this.ticketFormService.getTicket(this.editForm);
    if (ticket.id !== null) {
      this.subscribeToSaveResponse(this.ticketService.update(ticket));
    } else {
      this.subscribeToSaveResponse(this.ticketService.create(ticket));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITicket>>): void {
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

  protected updateForm(ticket: ITicket): void {
    this.ticket = ticket;
    this.ticketFormService.resetForm(this.editForm, ticket);

    this.collaborateursSharedCollection = this.collaborateursService.addCollaborateursToCollectionIfMissing<ICollaborateurs>(
      this.collaborateursSharedCollection,
      ticket.beneficiaire,
      ticket.proprietaire
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
            this.ticket?.beneficiaire,
            this.ticket?.proprietaire
          )
        )
      )
      .subscribe((collaborateurs: ICollaborateurs[]) => (this.collaborateursSharedCollection = collaborateurs));
  }
}
