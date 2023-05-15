import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../mouvement.test-samples';

import { MouvementFormService } from './mouvement-form.service';

describe('Mouvement Form Service', () => {
  let service: MouvementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MouvementFormService);
  });

  describe('Service methods', () => {
    describe('createMouvementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMouvementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            type: expect.any(Object),
            source: expect.any(Object),
            destination: expect.any(Object),
            user: expect.any(Object),
            commentaire: expect.any(Object),
            materiel: expect.any(Object),
            localisation: expect.any(Object),
          })
        );
      });

      it('passing IMouvement should create a new form with FormGroup', () => {
        const formGroup = service.createMouvementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            date: expect.any(Object),
            type: expect.any(Object),
            source: expect.any(Object),
            destination: expect.any(Object),
            user: expect.any(Object),
            commentaire: expect.any(Object),
            materiel: expect.any(Object),
            localisation: expect.any(Object),
          })
        );
      });
    });

    describe('getMouvement', () => {
      it('should return NewMouvement for default Mouvement initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMouvementFormGroup(sampleWithNewData);

        const mouvement = service.getMouvement(formGroup) as any;

        expect(mouvement).toMatchObject(sampleWithNewData);
      });

      it('should return NewMouvement for empty Mouvement initial value', () => {
        const formGroup = service.createMouvementFormGroup();

        const mouvement = service.getMouvement(formGroup) as any;

        expect(mouvement).toMatchObject({});
      });

      it('should return IMouvement', () => {
        const formGroup = service.createMouvementFormGroup(sampleWithRequiredData);

        const mouvement = service.getMouvement(formGroup) as any;

        expect(mouvement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMouvement should not enable id FormControl', () => {
        const formGroup = service.createMouvementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMouvement should disable id FormControl', () => {
        const formGroup = service.createMouvementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
