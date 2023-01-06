import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProjet, NewProjet } from '../projet.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProjet for edit and NewProjetFormGroupInput for create.
 */
type ProjetFormGroupInput = IProjet | PartialWithRequiredKeyOf<NewProjet>;

type ProjetFormDefaults = Pick<NewProjet, 'id' | 'collaborateurs'>;

type ProjetFormGroupContent = {
  id: FormControl<IProjet['id'] | NewProjet['id']>;
  nom: FormControl<IProjet['nom']>;
  dP: FormControl<IProjet['dP']>;
  stucture: FormControl<IProjet['stucture']>;
  informations: FormControl<IProjet['informations']>;
  collaborateurs: FormControl<IProjet['collaborateurs']>;
};

export type ProjetFormGroup = FormGroup<ProjetFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjetFormService {
  createProjetFormGroup(projet: ProjetFormGroupInput = { id: null }): ProjetFormGroup {
    const projetRawValue = {
      ...this.getFormDefaults(),
      ...projet,
    };
    return new FormGroup<ProjetFormGroupContent>({
      id: new FormControl(
        { value: projetRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(projetRawValue.nom),
      dP: new FormControl(projetRawValue.dP),
      stucture: new FormControl(projetRawValue.stucture),
      informations: new FormControl(projetRawValue.informations),
      collaborateurs: new FormControl(projetRawValue.collaborateurs ?? []),
    });
  }

  getProjet(form: ProjetFormGroup): IProjet | NewProjet {
    return form.getRawValue() as IProjet | NewProjet;
  }

  resetForm(form: ProjetFormGroup, projet: ProjetFormGroupInput): void {
    const projetRawValue = { ...this.getFormDefaults(), ...projet };
    form.reset(
      {
        ...projetRawValue,
        id: { value: projetRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProjetFormDefaults {
    return {
      id: null,
      collaborateurs: [],
    };
  }
}
