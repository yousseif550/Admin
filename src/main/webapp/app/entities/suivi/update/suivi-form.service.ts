import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISuivi, NewSuivi } from '../suivi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISuivi for edit and NewSuiviFormGroupInput for create.
 */
type SuiviFormGroupInput = ISuivi | PartialWithRequiredKeyOf<NewSuivi>;

type SuiviFormDefaults = Pick<NewSuivi, 'id'>;

type SuiviFormGroupContent = {
  id: FormControl<ISuivi['id'] | NewSuivi['id']>;
  envoiKitAccueil: FormControl<ISuivi['envoiKitAccueil']>;
  documentSigner: FormControl<ISuivi['documentSigner']>;
  commandePCDom: FormControl<ISuivi['commandePCDom']>;
  compteSSG: FormControl<ISuivi['compteSSG']>;
  listeNTIC: FormControl<ISuivi['listeNTIC']>;
  accesTeams: FormControl<ISuivi['accesTeams']>;
  accesPulseDGFiP: FormControl<ISuivi['accesPulseDGFiP']>;
  profilPCDom: FormControl<ISuivi['profilPCDom']>;
  commanderPCDGFiP: FormControl<ISuivi['commanderPCDGFiP']>;
  creationBALPDGFiP: FormControl<ISuivi['creationBALPDGFiP']>;
  creationCompteAD: FormControl<ISuivi['creationCompteAD']>;
  soclagePC: FormControl<ISuivi['soclagePC']>;
  dmocssIpTT: FormControl<ISuivi['dmocssIpTT']>;
  installationLogiciel: FormControl<ISuivi['installationLogiciel']>;
  commentaires: FormControl<ISuivi['commentaires']>;
  collaborateur: FormControl<ISuivi['collaborateur']>;
};

export type SuiviFormGroup = FormGroup<SuiviFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SuiviFormService {
  createSuiviFormGroup(suivi: SuiviFormGroupInput = { id: null }): SuiviFormGroup {
    const suiviRawValue = {
      ...this.getFormDefaults(),
      ...suivi,
    };
    return new FormGroup<SuiviFormGroupContent>({
      id: new FormControl(
        { value: suiviRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      envoiKitAccueil: new FormControl(suiviRawValue.envoiKitAccueil),
      documentSigner: new FormControl(suiviRawValue.documentSigner),
      commandePCDom: new FormControl(suiviRawValue.commandePCDom),
      compteSSG: new FormControl(suiviRawValue.compteSSG),
      listeNTIC: new FormControl(suiviRawValue.listeNTIC),
      accesTeams: new FormControl(suiviRawValue.accesTeams),
      accesPulseDGFiP: new FormControl(suiviRawValue.accesPulseDGFiP),
      profilPCDom: new FormControl(suiviRawValue.profilPCDom),
      commanderPCDGFiP: new FormControl(suiviRawValue.commanderPCDGFiP),
      creationBALPDGFiP: new FormControl(suiviRawValue.creationBALPDGFiP),
      creationCompteAD: new FormControl(suiviRawValue.creationCompteAD),
      soclagePC: new FormControl(suiviRawValue.soclagePC),
      dmocssIpTT: new FormControl(suiviRawValue.dmocssIpTT),
      installationLogiciel: new FormControl(suiviRawValue.installationLogiciel),
      commentaires: new FormControl(suiviRawValue.commentaires),
      collaborateur: new FormControl(suiviRawValue.collaborateur),
    });
  }

  getSuivi(form: SuiviFormGroup): ISuivi | NewSuivi {
    return form.getRawValue() as ISuivi | NewSuivi;
  }

  resetForm(form: SuiviFormGroup, suivi: SuiviFormGroupInput): void {
    const suiviRawValue = { ...this.getFormDefaults(), ...suivi };
    form.reset(
      {
        ...suiviRawValue,
        id: { value: suiviRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SuiviFormDefaults {
    return {
      id: null,
    };
  }
}
