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
  bureauActuel: FormControl<IExtracDMOCSS['bureauActuel']>;
  bureauDeplacement: FormControl<IExtracDMOCSS['bureauDeplacement']>;
  date: FormControl<IExtracDMOCSS['date']>;
  ipPcDGFiP: FormControl<IExtracDMOCSS['ipPcDGFiP']>;
  ipVpnIPSEC: FormControl<IExtracDMOCSS['ipVpnIPSEC']>;
  collaborateur: FormControl<IExtracDMOCSS['collaborateur']>;
  materiel: FormControl<IExtracDMOCSS['materiel']>;
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
      bureauActuel: new FormControl(extracDMOCSSRawValue.bureauActuel),
      bureauDeplacement: new FormControl(extracDMOCSSRawValue.bureauDeplacement),
      date: new FormControl(extracDMOCSSRawValue.date),
      ipPcDGFiP: new FormControl(extracDMOCSSRawValue.ipPcDGFiP),
      ipVpnIPSEC: new FormControl(extracDMOCSSRawValue.ipVpnIPSEC),
      collaborateur: new FormControl(extracDMOCSSRawValue.collaborateur),
      materiel: new FormControl(extracDMOCSSRawValue.materiel),
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
