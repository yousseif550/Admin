import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHistorique, NewHistorique } from '../historique.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHistorique for edit and NewHistoriqueFormGroupInput for create.
 */
type HistoriqueFormGroupInput = IHistorique | PartialWithRequiredKeyOf<NewHistorique>;

type HistoriqueFormDefaults = Pick<NewHistorique, 'id'>;

type HistoriqueFormGroupContent = {
  id: FormControl<IHistorique['id'] | NewHistorique['id']>;
  pc: FormControl<IHistorique['pc']>;
  zone: FormControl<IHistorique['zone']>;
  dateMouvement: FormControl<IHistorique['dateMouvement']>;
  ancienProprietaire: FormControl<IHistorique['ancienProprietaire']>;
  nouveauProprietaire: FormControl<IHistorique['nouveauProprietaire']>;
  materiel: FormControl<IHistorique['materiel']>;
};

export type HistoriqueFormGroup = FormGroup<HistoriqueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HistoriqueFormService {
  createHistoriqueFormGroup(historique: HistoriqueFormGroupInput = { id: null }): HistoriqueFormGroup {
    const historiqueRawValue = {
      ...this.getFormDefaults(),
      ...historique,
    };
    return new FormGroup<HistoriqueFormGroupContent>({
      id: new FormControl(
        { value: historiqueRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      pc: new FormControl(historiqueRawValue.pc),
      zone: new FormControl(historiqueRawValue.zone),
      dateMouvement: new FormControl(historiqueRawValue.dateMouvement),
      ancienProprietaire: new FormControl(historiqueRawValue.ancienProprietaire),
      nouveauProprietaire: new FormControl(historiqueRawValue.nouveauProprietaire),
      materiel: new FormControl(historiqueRawValue.materiel),
    });
  }

  getHistorique(form: HistoriqueFormGroup): IHistorique | NewHistorique {
    return form.getRawValue() as IHistorique | NewHistorique;
  }

  resetForm(form: HistoriqueFormGroup, historique: HistoriqueFormGroupInput): void {
    const historiqueRawValue = { ...this.getFormDefaults(), ...historique };
    form.reset(
      {
        ...historiqueRawValue,
        id: { value: historiqueRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): HistoriqueFormDefaults {
    return {
      id: null,
    };
  }
}
