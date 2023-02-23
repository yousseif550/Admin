import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../typemateriel.test-samples';

import { TypematerielFormService } from './typemateriel-form.service';

describe('Typemateriel Form Service', () => {
  let service: TypematerielFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypematerielFormService);
  });

  describe('Service methods', () => {
    describe('createTypematerielFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTypematerielFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });

      it('passing ITypemateriel should create a new form with FormGroup', () => {
        const formGroup = service.createTypematerielFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
          })
        );
      });
    });

    describe('getTypemateriel', () => {
      it('should return NewTypemateriel for default Typemateriel initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTypematerielFormGroup(sampleWithNewData);

        const typemateriel = service.getTypemateriel(formGroup) as any;

        expect(typemateriel).toMatchObject(sampleWithNewData);
      });

      it('should return NewTypemateriel for empty Typemateriel initial value', () => {
        const formGroup = service.createTypematerielFormGroup();

        const typemateriel = service.getTypemateriel(formGroup) as any;

        expect(typemateriel).toMatchObject({});
      });

      it('should return ITypemateriel', () => {
        const formGroup = service.createTypematerielFormGroup(sampleWithRequiredData);

        const typemateriel = service.getTypemateriel(formGroup) as any;

        expect(typemateriel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITypemateriel should not enable id FormControl', () => {
        const formGroup = service.createTypematerielFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTypemateriel should disable id FormControl', () => {
        const formGroup = service.createTypematerielFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
