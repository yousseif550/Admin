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
  pcDom: FormControl<IInformationsTech['pcDom']>;
  pcDGFiP: FormControl<IInformationsTech['pcDGFiP']>;
  adresseSSG: FormControl<IInformationsTech['adresseSSG']>;
  adresseDGFiP: FormControl<IInformationsTech['adresseDGFiP']>;
  collaborateur: FormControl<IInformationsTech['collaborateur']>;
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
      pcDom: new FormControl(informationsTechRawValue.pcDom),
      pcDGFiP: new FormControl(informationsTechRawValue.pcDGFiP),
      adresseSSG: new FormControl(informationsTechRawValue.adresseSSG),
      adresseDGFiP: new FormControl(informationsTechRawValue.adresseDGFiP),
      collaborateur: new FormControl(informationsTechRawValue.collaborateur),
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
