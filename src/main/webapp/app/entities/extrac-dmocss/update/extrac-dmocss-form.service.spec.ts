import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../extrac-dmocss.test-samples';

import { ExtracDMOCSSFormService } from './extrac-dmocss-form.service';

describe('ExtracDMOCSS Form Service', () => {
  let service: ExtracDMOCSSFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExtracDMOCSSFormService);
  });

  describe('Service methods', () => {
    describe('createExtracDMOCSSFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createExtracDMOCSSFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            adressePhysiqueDGFiP: expect.any(Object),
            date: expect.any(Object),
            ipPcDgfip: expect.any(Object),
            ipVpnIPSEC: expect.any(Object),
            ioTeletravail: expect.any(Object),
            statut: expect.any(Object),
            numVersion: expect.any(Object),
            collaborateur: expect.any(Object),
            materiel: expect.any(Object),
            bureauActuel: expect.any(Object),
            bureauDeplacement: expect.any(Object),
            localisation: expect.any(Object),
          })
        );
      });

      it('passing IExtracDMOCSS should create a new form with FormGroup', () => {
        const formGroup = service.createExtracDMOCSSFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            adressePhysiqueDGFiP: expect.any(Object),
            date: expect.any(Object),
            ipPcDgfip: expect.any(Object),
            ipVpnIPSEC: expect.any(Object),
            ioTeletravail: expect.any(Object),
            statut: expect.any(Object),
            numVersion: expect.any(Object),
            collaborateur: expect.any(Object),
            materiel: expect.any(Object),
            bureauActuel: expect.any(Object),
            bureauDeplacement: expect.any(Object),
            localisation: expect.any(Object),
          })
        );
      });
    });

    describe('getExtracDMOCSS', () => {
      it('should return NewExtracDMOCSS for default ExtracDMOCSS initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createExtracDMOCSSFormGroup(sampleWithNewData);

        const extracDMOCSS = service.getExtracDMOCSS(formGroup) as any;

        expect(extracDMOCSS).toMatchObject(sampleWithNewData);
      });

      it('should return NewExtracDMOCSS for empty ExtracDMOCSS initial value', () => {
        const formGroup = service.createExtracDMOCSSFormGroup();

        const extracDMOCSS = service.getExtracDMOCSS(formGroup) as any;

        expect(extracDMOCSS).toMatchObject({});
      });

      it('should return IExtracDMOCSS', () => {
        const formGroup = service.createExtracDMOCSSFormGroup(sampleWithRequiredData);

        const extracDMOCSS = service.getExtracDMOCSS(formGroup) as any;

        expect(extracDMOCSS).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IExtracDMOCSS should not enable id FormControl', () => {
        const formGroup = service.createExtracDMOCSSFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewExtracDMOCSS should disable id FormControl', () => {
        const formGroup = service.createExtracDMOCSSFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
