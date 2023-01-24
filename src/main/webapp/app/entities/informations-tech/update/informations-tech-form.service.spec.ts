import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../informations-tech.test-samples';

import { InformationsTechFormService } from './informations-tech-form.service';

describe('InformationsTech Form Service', () => {
  let service: InformationsTechFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InformationsTechFormService);
  });

  describe('Service methods', () => {
    describe('createInformationsTechFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInformationsTechFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ipdfipConnexion: expect.any(Object),
            ipfixDMOCSS: expect.any(Object),
            adressMAC: expect.any(Object),
            iPTeletravail: expect.any(Object),
            adresseDGFiP: expect.any(Object),
            pcDGFiP: expect.any(Object),
          })
        );
      });

      it('passing IInformationsTech should create a new form with FormGroup', () => {
        const formGroup = service.createInformationsTechFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ipdfipConnexion: expect.any(Object),
            ipfixDMOCSS: expect.any(Object),
            adressMAC: expect.any(Object),
            iPTeletravail: expect.any(Object),
            adresseDGFiP: expect.any(Object),
            pcDGFiP: expect.any(Object),
          })
        );
      });
    });

    describe('getInformationsTech', () => {
      it('should return NewInformationsTech for default InformationsTech initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInformationsTechFormGroup(sampleWithNewData);

        const informationsTech = service.getInformationsTech(formGroup) as any;

        expect(informationsTech).toMatchObject(sampleWithNewData);
      });

      it('should return NewInformationsTech for empty InformationsTech initial value', () => {
        const formGroup = service.createInformationsTechFormGroup();

        const informationsTech = service.getInformationsTech(formGroup) as any;

        expect(informationsTech).toMatchObject({});
      });

      it('should return IInformationsTech', () => {
        const formGroup = service.createInformationsTechFormGroup(sampleWithRequiredData);

        const informationsTech = service.getInformationsTech(formGroup) as any;

        expect(informationsTech).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInformationsTech should not enable id FormControl', () => {
        const formGroup = service.createInformationsTechFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInformationsTech should disable id FormControl', () => {
        const formGroup = service.createInformationsTechFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
