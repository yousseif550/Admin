import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMouvement, NewMouvement } from '../mouvement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMouvement for edit and NewMouvementFormGroupInput for create.
 */
type MouvementFormGroupInput = IMouvement | PartialWithRequiredKeyOf<NewMouvement>;

type MouvementFormDefaults = Pick<NewMouvement, 'id'>;

type MouvementFormGroupContent = {
  id: FormControl<IMouvement['id'] | NewMouvement['id']>;
  date: FormControl<IMouvement['date']>;
  type: FormControl<IMouvement['type']>;
  source: FormControl<IMouvement['source']>;
  destination: FormControl<IMouvement['destination']>;
  user: FormControl<IMouvement['user']>;
};

export type MouvementFormGroup = FormGroup<MouvementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MouvementFormService {
  createMouvementFormGroup(mouvement: MouvementFormGroupInput = { id: null }): MouvementFormGroup {
    const mouvementRawValue = {
      ...this.getFormDefaults(),
      ...mouvement,
    };
    return new FormGroup<MouvementFormGroupContent>({
      id: new FormControl(
        { value: mouvementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      date: new FormControl(mouvementRawValue.date),
      type: new FormControl(mouvementRawValue.type),
      source: new FormControl(mouvementRawValue.source),
      destination: new FormControl(mouvementRawValue.destination),
      user: new FormControl(mouvementRawValue.user),
    });
  }

  getMouvement(form: MouvementFormGroup): IMouvement | NewMouvement {
    return form.getRawValue() as IMouvement | NewMouvement;
  }

  resetForm(form: MouvementFormGroup, mouvement: MouvementFormGroupInput): void {
    const mouvementRawValue = { ...this.getFormDefaults(), ...mouvement };
    form.reset(
      {
        ...mouvementRawValue,
        id: { value: mouvementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MouvementFormDefaults {
    return {
      id: null,
    };
  }
}
