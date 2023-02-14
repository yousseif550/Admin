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
  disponible: FormControl<INumeroInventaire['disponible']>;
  dateModification: FormControl<INumeroInventaire['dateModification']>;
  commentaire: FormControl<INumeroInventaire['commentaire']>;
  materielActuel: FormControl<INumeroInventaire['materielActuel']>;
  ancienMateriel: FormControl<INumeroInventaire['ancienMateriel']>;
  ancienProprietaire: FormControl<INumeroInventaire['ancienProprietaire']>;
  nouveauProprietaire: FormControl<INumeroInventaire['nouveauProprietaire']>;
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
      disponible: new FormControl(numeroInventaireRawValue.disponible),
      dateModification: new FormControl(numeroInventaireRawValue.dateModification),
      commentaire: new FormControl(numeroInventaireRawValue.commentaire),
      materielActuel: new FormControl(numeroInventaireRawValue.materielActuel),
      ancienMateriel: new FormControl(numeroInventaireRawValue.ancienMateriel),
      ancienProprietaire: new FormControl(numeroInventaireRawValue.ancienProprietaire),
      nouveauProprietaire: new FormControl(numeroInventaireRawValue.nouveauProprietaire),
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
