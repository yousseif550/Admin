import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INumeroInventaire, NewNumeroInventaire } from '../numero-inventaire.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INumeroInventaire for edit and NewNumeroInventaireFormGroupInput for create.
 */
type NumeroInventaireFormGroupInput = INumeroInventaire | PartialWithRequiredKeyOf<NewNumeroInventaire>;

type NumeroInventaireFormDefaults = Pick<NewNumeroInventaire, 'id' | 'disponible'>;

type NumeroInventaireFormGroupContent = {
  id: FormControl<INumeroInventaire['id'] | NewNumeroInventaire['id']>;
  zone: FormControl<INumeroInventaire['zone']>;
  valeur: FormControl<INumeroInventaire['valeur']>;
  disponible: FormControl<INumeroInventaire['disponible']>;
  ancienMateriel: FormControl<INumeroInventaire['ancienMateriel']>;
  dateModification: FormControl<INumeroInventaire['dateModification']>;
  materielActuel: FormControl<INumeroInventaire['materielActuel']>;
};

export type NumeroInventaireFormGroup = FormGroup<NumeroInventaireFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NumeroInventaireFormService {
  createNumeroInventaireFormGroup(numeroInventaire: NumeroInventaireFormGroupInput = { id: null }): NumeroInventaireFormGroup {
    const numeroInventaireRawValue = {
      ...this.getFormDefaults(),
      ...numeroInventaire,
    };
    return new FormGroup<NumeroInventaireFormGroupContent>({
      id: new FormControl(
        { value: numeroInventaireRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      zone: new FormControl(numeroInventaireRawValue.zone),
      valeur: new FormControl(numeroInventaireRawValue.valeur),
      disponible: new FormControl(numeroInventaireRawValue.disponible),
      ancienMateriel: new FormControl(numeroInventaireRawValue.ancienMateriel),
      dateModification: new FormControl(numeroInventaireRawValue.dateModification),
      materielActuel: new FormControl(numeroInventaireRawValue.materielActuel),
    });
  }

  getNumeroInventaire(form: NumeroInventaireFormGroup): INumeroInventaire | NewNumeroInventaire {
    return form.getRawValue() as INumeroInventaire | NewNumeroInventaire;
  }

  resetForm(form: NumeroInventaireFormGroup, numeroInventaire: NumeroInventaireFormGroupInput): void {
    const numeroInventaireRawValue = { ...this.getFormDefaults(), ...numeroInventaire };
    form.reset(
      {
        ...numeroInventaireRawValue,
        id: { value: numeroInventaireRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NumeroInventaireFormDefaults {
    return {
      id: null,
      disponible: false,
    };
  }
}
