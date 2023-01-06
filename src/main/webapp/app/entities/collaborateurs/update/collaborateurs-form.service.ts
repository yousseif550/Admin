import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICollaborateurs, NewCollaborateurs } from '../collaborateurs.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICollaborateurs for edit and NewCollaborateursFormGroupInput for create.
 */
type CollaborateursFormGroupInput = ICollaborateurs | PartialWithRequiredKeyOf<NewCollaborateurs>;

type CollaborateursFormDefaults = Pick<NewCollaborateurs, 'id' | 'prestataire' | 'isActif' | 'projets'>;

type CollaborateursFormGroupContent = {
  id: FormControl<ICollaborateurs['id'] | NewCollaborateurs['id']>;
  nom: FormControl<ICollaborateurs['nom']>;
  identifiant: FormControl<ICollaborateurs['identifiant']>;
  tel: FormControl<ICollaborateurs['tel']>;
  prestataire: FormControl<ICollaborateurs['prestataire']>;
  isActif: FormControl<ICollaborateurs['isActif']>;
  dateEntree: FormControl<ICollaborateurs['dateEntree']>;
  dateSortie: FormControl<ICollaborateurs['dateSortie']>;
  projets: FormControl<ICollaborateurs['projets']>;
};

export type CollaborateursFormGroup = FormGroup<CollaborateursFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CollaborateursFormService {
  createCollaborateursFormGroup(collaborateurs: CollaborateursFormGroupInput = { id: null }): CollaborateursFormGroup {
    const collaborateursRawValue = {
      ...this.getFormDefaults(),
      ...collaborateurs,
    };
    return new FormGroup<CollaborateursFormGroupContent>({
      id: new FormControl(
        { value: collaborateursRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nom: new FormControl(collaborateursRawValue.nom),
      identifiant: new FormControl(collaborateursRawValue.identifiant),
      tel: new FormControl(collaborateursRawValue.tel),
      prestataire: new FormControl(collaborateursRawValue.prestataire),
      isActif: new FormControl(collaborateursRawValue.isActif),
      dateEntree: new FormControl(collaborateursRawValue.dateEntree),
      dateSortie: new FormControl(collaborateursRawValue.dateSortie),
      projets: new FormControl(collaborateursRawValue.projets ?? []),
    });
  }

  getCollaborateurs(form: CollaborateursFormGroup): ICollaborateurs | NewCollaborateurs {
    return form.getRawValue() as ICollaborateurs | NewCollaborateurs;
  }

  resetForm(form: CollaborateursFormGroup, collaborateurs: CollaborateursFormGroupInput): void {
    const collaborateursRawValue = { ...this.getFormDefaults(), ...collaborateurs };
    form.reset(
      {
        ...collaborateursRawValue,
        id: { value: collaborateursRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CollaborateursFormDefaults {
    return {
      id: null,
      prestataire: false,
      isActif: false,
      projets: [],
    };
  }
}
