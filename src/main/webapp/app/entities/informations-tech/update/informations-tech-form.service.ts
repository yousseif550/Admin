import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInformationsTech, NewInformationsTech } from '../informations-tech.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInformationsTech for edit and NewInformationsTechFormGroupInput for create.
 */
type InformationsTechFormGroupInput = IInformationsTech | PartialWithRequiredKeyOf<NewInformationsTech>;

type InformationsTechFormDefaults = Pick<NewInformationsTech, 'id'>;

type InformationsTechFormGroupContent = {
  id: FormControl<IInformationsTech['id'] | NewInformationsTech['id']>;
  ipdfipConnexion: FormControl<IInformationsTech['ipdfipConnexion']>;
  ipfixDMOCSS: FormControl<IInformationsTech['ipfixDMOCSS']>;
  adressMAC: FormControl<IInformationsTech['adressMAC']>;
  iPTeletravail: FormControl<IInformationsTech['iPTeletravail']>;
  adresseDGFiP: FormControl<IInformationsTech['adresseDGFiP']>;
  pcDGFiP: FormControl<IInformationsTech['pcDGFiP']>;
};

export type InformationsTechFormGroup = FormGroup<InformationsTechFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InformationsTechFormService {
  createInformationsTechFormGroup(informationsTech: InformationsTechFormGroupInput = { id: null }): InformationsTechFormGroup {
    const informationsTechRawValue = {
      ...this.getFormDefaults(),
      ...informationsTech,
    };
    return new FormGroup<InformationsTechFormGroupContent>({
      id: new FormControl(
        { value: informationsTechRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      ipdfipConnexion: new FormControl(informationsTechRawValue.ipdfipConnexion),
      ipfixDMOCSS: new FormControl(informationsTechRawValue.ipfixDMOCSS),
      adressMAC: new FormControl(informationsTechRawValue.adressMAC),
      iPTeletravail: new FormControl(informationsTechRawValue.iPTeletravail),
      adresseDGFiP: new FormControl(informationsTechRawValue.adresseDGFiP),
      pcDGFiP: new FormControl(informationsTechRawValue.pcDGFiP),
    });
  }

  getInformationsTech(form: InformationsTechFormGroup): IInformationsTech | NewInformationsTech {
    return form.getRawValue() as IInformationsTech | NewInformationsTech;
  }

  resetForm(form: InformationsTechFormGroup, informationsTech: InformationsTechFormGroupInput): void {
    const informationsTechRawValue = { ...this.getFormDefaults(), ...informationsTech };
    form.reset(
      {
        ...informationsTechRawValue,
        id: { value: informationsTechRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InformationsTechFormDefaults {
    return {
      id: null,
    };
  }
}
