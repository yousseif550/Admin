import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IExtracDMOCSS, NewExtracDMOCSS } from '../extrac-dmocss.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExtracDMOCSS for edit and NewExtracDMOCSSFormGroupInput for create.
 */
type ExtracDMOCSSFormGroupInput = IExtracDMOCSS | PartialWithRequiredKeyOf<NewExtracDMOCSS>;

type ExtracDMOCSSFormDefaults = Pick<NewExtracDMOCSS, 'id'>;

type ExtracDMOCSSFormGroupContent = {
  id: FormControl<IExtracDMOCSS['id'] | NewExtracDMOCSS['id']>;
  adressePhysiqueDGFiP: FormControl<IExtracDMOCSS['adressePhysiqueDGFiP']>;
  date: FormControl<IExtracDMOCSS['date']>;
  bureauDeplacement: FormControl<IExtracDMOCSS['bureauDeplacement']>;
  ipPcDgfip: FormControl<IExtracDMOCSS['ipPcDgfip']>;
  ipVpnIPSEC: FormControl<IExtracDMOCSS['ipVpnIPSEC']>;
  ipTeletravail: FormControl<IExtracDMOCSS['ipTeletravail']>;
  statut: FormControl<IExtracDMOCSS['statut']>;
  numVersion: FormControl<IExtracDMOCSS['numVersion']>;
  collaborateur: FormControl<IExtracDMOCSS['collaborateur']>;
  materiel: FormControl<IExtracDMOCSS['materiel']>;
  bureauActuel: FormControl<IExtracDMOCSS['bureauActuel']>;
  localisation: FormControl<IExtracDMOCSS['localisation']>;
};

export type ExtracDMOCSSFormGroup = FormGroup<ExtracDMOCSSFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExtracDMOCSSFormService {
  createExtracDMOCSSFormGroup(extracDMOCSS: ExtracDMOCSSFormGroupInput = { id: null }): ExtracDMOCSSFormGroup {
    const extracDMOCSSRawValue = {
      ...this.getFormDefaults(),
      ...extracDMOCSS,
    };
    return new FormGroup<ExtracDMOCSSFormGroupContent>({
      id: new FormControl(
        { value: extracDMOCSSRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      adressePhysiqueDGFiP: new FormControl(extracDMOCSSRawValue.adressePhysiqueDGFiP),
      date: new FormControl(extracDMOCSSRawValue.date),
      bureauDeplacement: new FormControl(extracDMOCSSRawValue.bureauDeplacement),
      ipPcDgfip: new FormControl(extracDMOCSSRawValue.ipPcDgfip),
      ipVpnIPSEC: new FormControl(extracDMOCSSRawValue.ipVpnIPSEC),
      ipTeletravail: new FormControl(extracDMOCSSRawValue.ipTeletravail),
      statut: new FormControl(extracDMOCSSRawValue.statut),
      numVersion: new FormControl(extracDMOCSSRawValue.numVersion),
      collaborateur: new FormControl(extracDMOCSSRawValue.collaborateur),
      materiel: new FormControl(extracDMOCSSRawValue.materiel),
      bureauActuel: new FormControl(extracDMOCSSRawValue.bureauActuel),
      localisation: new FormControl(extracDMOCSSRawValue.localisation),
    });
  }

  getExtracDMOCSS(form: ExtracDMOCSSFormGroup): IExtracDMOCSS | NewExtracDMOCSS {
    return form.getRawValue() as IExtracDMOCSS | NewExtracDMOCSS;
  }

  resetForm(form: ExtracDMOCSSFormGroup, extracDMOCSS: ExtracDMOCSSFormGroupInput): void {
    const extracDMOCSSRawValue = { ...this.getFormDefaults(), ...extracDMOCSS };
    form.reset(
      {
        ...extracDMOCSSRawValue,
        id: { value: extracDMOCSSRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ExtracDMOCSSFormDefaults {
    return {
      id: null,
    };
  }
}
