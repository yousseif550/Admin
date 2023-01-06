import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../historique.test-samples';

import { HistoriqueFormService } from './historique-form.service';

describe('Historique Form Service', () => {
  let service: HistoriqueFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistoriqueFormService);
  });

  describe('Service methods', () => {
    describe('createHistoriqueFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHistoriqueFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pC: expect.any(Object),
            zone: expect.any(Object),
            dateMouvement: expect.any(Object),
            ancienProprietaire: expect.any(Object),
            nouveauProprietaire: expect.any(Object),
            materiel: expect.any(Object),
          })
        );
      });

      it('passing IHistorique should create a new form with FormGroup', () => {
        const formGroup = service.createHistoriqueFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pC: expect.any(Object),
            zone: expect.any(Object),
            dateMouvement: expect.any(Object),
            ancienProprietaire: expect.any(Object),
            nouveauProprietaire: expect.any(Object),
            materiel: expect.any(Object),
          })
        );
      });
    });

    describe('getHistorique', () => {
      it('should return NewHistorique for default Historique initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createHistoriqueFormGroup(sampleWithNewData);

        const historique = service.getHistorique(formGroup) as any;

        expect(historique).toMatchObject(sampleWithNewData);
      });

      it('should return NewHistorique for empty Historique initial value', () => {
        const formGroup = service.createHistoriqueFormGroup();

        const historique = service.getHistorique(formGroup) as any;

        expect(historique).toMatchObject({});
      });

      it('should return IHistorique', () => {
        const formGroup = service.createHistoriqueFormGroup(sampleWithRequiredData);

        const historique = service.getHistorique(formGroup) as any;

        expect(historique).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHistorique should not enable id FormControl', () => {
        const formGroup = service.createHistoriqueFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHistorique should disable id FormControl', () => {
        const formGroup = service.createHistoriqueFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
