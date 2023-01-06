import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../suivi.test-samples';

import { SuiviFormService } from './suivi-form.service';

describe('Suivi Form Service', () => {
  let service: SuiviFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SuiviFormService);
  });

  describe('Service methods', () => {
    describe('createSuiviFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSuiviFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            envoiKitAccueil: expect.any(Object),
            documentSigner: expect.any(Object),
            commandePCDom: expect.any(Object),
            compteSSG: expect.any(Object),
            listeNTIC: expect.any(Object),
            accesTeams: expect.any(Object),
            accesPulseDGFiP: expect.any(Object),
            profilPCDom: expect.any(Object),
            commanderPCDGFiP: expect.any(Object),
            creationBALPDGFiP: expect.any(Object),
            creationCompteAD: expect.any(Object),
            soclagePC: expect.any(Object),
            dmocssIpTT: expect.any(Object),
            installationLogiciel: expect.any(Object),
            commentaires: expect.any(Object),
            collaborateur: expect.any(Object),
          })
        );
      });

      it('passing ISuivi should create a new form with FormGroup', () => {
        const formGroup = service.createSuiviFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            envoiKitAccueil: expect.any(Object),
            documentSigner: expect.any(Object),
            commandePCDom: expect.any(Object),
            compteSSG: expect.any(Object),
            listeNTIC: expect.any(Object),
            accesTeams: expect.any(Object),
            accesPulseDGFiP: expect.any(Object),
            profilPCDom: expect.any(Object),
            commanderPCDGFiP: expect.any(Object),
            creationBALPDGFiP: expect.any(Object),
            creationCompteAD: expect.any(Object),
            soclagePC: expect.any(Object),
            dmocssIpTT: expect.any(Object),
            installationLogiciel: expect.any(Object),
            commentaires: expect.any(Object),
            collaborateur: expect.any(Object),
          })
        );
      });
    });

    describe('getSuivi', () => {
      it('should return NewSuivi for default Suivi initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSuiviFormGroup(sampleWithNewData);

        const suivi = service.getSuivi(formGroup) as any;

        expect(suivi).toMatchObject(sampleWithNewData);
      });

      it('should return NewSuivi for empty Suivi initial value', () => {
        const formGroup = service.createSuiviFormGroup();

        const suivi = service.getSuivi(formGroup) as any;

        expect(suivi).toMatchObject({});
      });

      it('should return ISuivi', () => {
        const formGroup = service.createSuiviFormGroup(sampleWithRequiredData);

        const suivi = service.getSuivi(formGroup) as any;

        expect(suivi).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISuivi should not enable id FormControl', () => {
        const formGroup = service.createSuiviFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSuivi should disable id FormControl', () => {
        const formGroup = service.createSuiviFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
