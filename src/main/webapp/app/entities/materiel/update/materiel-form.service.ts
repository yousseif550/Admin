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

type MaterielFormDefaults = Pick<NewMateriel, 'id' | 'actif' | 'isHs' | 'majBios'>;

type MaterielFormGroupContent = {
  id: FormControl<IMateriel['id'] | NewMateriel['id']>;
  utilisation: FormControl<IMateriel['utilisation']>;
  modele: FormControl<IMateriel['modele']>;
  asset: FormControl<IMateriel['asset']>;
  dateAttribution: FormControl<IMateriel['dateAttribution']>;
  dateRendu: FormControl<IMateriel['dateRendu']>;
  actif: FormControl<IMateriel['actif']>;
  isHs: FormControl<IMateriel['isHs']>;
  cleAntiVol: FormControl<IMateriel['cleAntiVol']>;
  adressMAC: FormControl<IMateriel['adressMAC']>;
  stationDgfip: FormControl<IMateriel['stationDgfip']>;
  ipdfip: FormControl<IMateriel['ipdfip']>;
  iPTeletravail: FormControl<IMateriel['iPTeletravail']>;
  bios: FormControl<IMateriel['bios']>;
  majBios: FormControl<IMateriel['majBios']>;
  commentaire: FormControl<IMateriel['commentaire']>;
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
      dateAttribution: new FormControl(materielRawValue.dateAttribution),
      dateRendu: new FormControl(materielRawValue.dateRendu),
      actif: new FormControl(materielRawValue.actif),
      isHs: new FormControl(materielRawValue.isHs),
      cleAntiVol: new FormControl(materielRawValue.cleAntiVol),
      adressMAC: new FormControl(materielRawValue.adressMAC),
      stationDgfip: new FormControl(materielRawValue.stationDgfip),
      ipdfip: new FormControl(materielRawValue.ipdfip),
      iPTeletravail: new FormControl(materielRawValue.iPTeletravail),
      bios: new FormControl(materielRawValue.bios),
      majBios: new FormControl(materielRawValue.majBios),
      commentaire: new FormControl(materielRawValue.commentaire),
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
      majBios: false,
    };
  }
}
