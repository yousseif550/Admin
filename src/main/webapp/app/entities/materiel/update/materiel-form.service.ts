import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMateriel, NewMateriel } from '../materiel.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMateriel for edit and NewMaterielFormGroupInput for create.
 */
type MaterielFormGroupInput = IMateriel | PartialWithRequiredKeyOf<NewMateriel>;

type MaterielFormDefaults = Pick<NewMateriel, 'id' | 'actif' | 'isHs'>;

type MaterielFormGroupContent = {
  id: FormControl<IMateriel['id'] | NewMateriel['id']>;
  utilisation: FormControl<IMateriel['utilisation']>;
  modele: FormControl<IMateriel['modele']>;
  asset: FormControl<IMateriel['asset']>;
  actif: FormControl<IMateriel['actif']>;
  dateAttribution: FormControl<IMateriel['dateAttribution']>;
  dateRendu: FormControl<IMateriel['dateRendu']>;
  commentaire: FormControl<IMateriel['commentaire']>;
  isHs: FormControl<IMateriel['isHs']>;
  objet: FormControl<IMateriel['objet']>;
  localisation: FormControl<IMateriel['localisation']>;
  collaborateur: FormControl<IMateriel['collaborateur']>;
};

export type MaterielFormGroup = FormGroup<MaterielFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MaterielFormService {
  createMaterielFormGroup(materiel: MaterielFormGroupInput = { id: null }): MaterielFormGroup {
    const materielRawValue = {
      ...this.getFormDefaults(),
      ...materiel,
    };
    return new FormGroup<MaterielFormGroupContent>({
      id: new FormControl(
        { value: materielRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      utilisation: new FormControl(materielRawValue.utilisation),
      modele: new FormControl(materielRawValue.modele),
      asset: new FormControl(materielRawValue.asset),
      actif: new FormControl(materielRawValue.actif),
      dateAttribution: new FormControl(materielRawValue.dateAttribution),
      dateRendu: new FormControl(materielRawValue.dateRendu),
      commentaire: new FormControl(materielRawValue.commentaire),
      isHs: new FormControl(materielRawValue.isHs),
      objet: new FormControl(materielRawValue.objet),
      localisation: new FormControl(materielRawValue.localisation),
      collaborateur: new FormControl(materielRawValue.collaborateur),
    });
  }

  getMateriel(form: MaterielFormGroup): IMateriel | NewMateriel {
    return form.getRawValue() as IMateriel | NewMateriel;
  }

  resetForm(form: MaterielFormGroup, materiel: MaterielFormGroupInput): void {
    const materielRawValue = { ...this.getFormDefaults(), ...materiel };
    form.reset(
      {
        ...materielRawValue,
        id: { value: materielRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MaterielFormDefaults {
    return {
      id: null,
      actif: false,
      isHs: false,
    };
  }
}
