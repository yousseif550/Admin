import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITypemateriel, NewTypemateriel } from '../typemateriel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITypemateriel for edit and NewTypematerielFormGroupInput for create.
 */
type TypematerielFormGroupInput = ITypemateriel | PartialWithRequiredKeyOf<NewTypemateriel>;

type TypematerielFormDefaults = Pick<NewTypemateriel, 'id'>;

type TypematerielFormGroupContent = {
  id: FormControl<ITypemateriel['id'] | NewTypemateriel['id']>;
  type: FormControl<ITypemateriel['type']>;
};

export type TypematerielFormGroup = FormGroup<TypematerielFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TypematerielFormService {
  createTypematerielFormGroup(typemateriel: TypematerielFormGroupInput = { id: null }): TypematerielFormGroup {
    const typematerielRawValue = {
      ...this.getFormDefaults(),
      ...typemateriel,
    };
    return new FormGroup<TypematerielFormGroupContent>({
      id: new FormControl(
        { value: typematerielRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(typematerielRawValue.type),
    });
  }

  getTypemateriel(form: TypematerielFormGroup): ITypemateriel | NewTypemateriel {
    return form.getRawValue() as ITypemateriel | NewTypemateriel;
  }

  resetForm(form: TypematerielFormGroup, typemateriel: TypematerielFormGroupInput): void {
    const typematerielRawValue = { ...this.getFormDefaults(), ...typemateriel };
    form.reset(
      {
        ...typematerielRawValue,
        id: { value: typematerielRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TypematerielFormDefaults {
    return {
      id: null,
    };
  }
}
