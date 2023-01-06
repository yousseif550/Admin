import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITicket, NewTicket } from '../ticket.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITicket for edit and NewTicketFormGroupInput for create.
 */
type TicketFormGroupInput = ITicket | PartialWithRequiredKeyOf<NewTicket>;

type TicketFormDefaults = Pick<NewTicket, 'id'>;

type TicketFormGroupContent = {
  id: FormControl<ITicket['id'] | NewTicket['id']>;
  type: FormControl<ITicket['type']>;
  statut: FormControl<ITicket['statut']>;
  dateCreation: FormControl<ITicket['dateCreation']>;
  dateFin: FormControl<ITicket['dateFin']>;
  beneficiaire: FormControl<ITicket['beneficiaire']>;
  proprietaire: FormControl<ITicket['proprietaire']>;
};

export type TicketFormGroup = FormGroup<TicketFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TicketFormService {
  createTicketFormGroup(ticket: TicketFormGroupInput = { id: null }): TicketFormGroup {
    const ticketRawValue = {
      ...this.getFormDefaults(),
      ...ticket,
    };
    return new FormGroup<TicketFormGroupContent>({
      id: new FormControl(
        { value: ticketRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(ticketRawValue.type),
      statut: new FormControl(ticketRawValue.statut),
      dateCreation: new FormControl(ticketRawValue.dateCreation),
      dateFin: new FormControl(ticketRawValue.dateFin),
      beneficiaire: new FormControl(ticketRawValue.beneficiaire),
      proprietaire: new FormControl(ticketRawValue.proprietaire),
    });
  }

  getTicket(form: TicketFormGroup): ITicket | NewTicket {
    return form.getRawValue() as ITicket | NewTicket;
  }

  resetForm(form: TicketFormGroup, ticket: TicketFormGroupInput): void {
    const ticketRawValue = { ...this.getFormDefaults(), ...ticket };
    form.reset(
      {
        ...ticketRawValue,
        id: { value: ticketRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TicketFormDefaults {
    return {
      id: null,
    };
  }
}
