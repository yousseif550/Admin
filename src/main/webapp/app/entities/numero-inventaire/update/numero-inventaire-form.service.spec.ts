import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../numero-inventaire.test-samples';

import { NumeroInventaireFormService } from './numero-inventaire-form.service';

describe('NumeroInventaire Form Service', () => {
  let service: NumeroInventaireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NumeroInventaireFormService);
  });

  describe('Service methods', () => {
    describe('createNumeroInventaireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNumeroInventaireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            disponible: expect.any(Object),
            dateModification: expect.any(Object),
            commentaire: expect.any(Object),
            materielActuel: expect.any(Object),
            ancienMateriel: expect.any(Object),
            ancienProprietaire: expect.any(Object),
            nouveauProprietaire: expect.any(Object),
          })
        );
      });

      it('passing INumeroInventaire should create a new form with FormGroup', () => {
        const formGroup = service.createNumeroInventaireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            disponible: expect.any(Object),
            dateModification: expect.any(Object),
            commentaire: expect.any(Object),
            materielActuel: expect.any(Object),
            ancienMateriel: expect.any(Object),
            ancienProprietaire: expect.any(Object),
            nouveauProprietaire: expect.any(Object),
          })
        );
      });
    });

    describe('getNumeroInventaire', () => {
      it('should return NewNumeroInventaire for default NumeroInventaire initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNumeroInventaireFormGroup(sampleWithNewData);

        const numeroInventaire = service.getNumeroInventaire(formGroup) as any;

        expect(numeroInventaire).toMatchObject(sampleWithNewData);
      });

      it('should return NewNumeroInventaire for empty NumeroInventaire initial value', () => {
        const formGroup = service.createNumeroInventaireFormGroup();

        const numeroInventaire = service.getNumeroInventaire(formGroup) as any;

        expect(numeroInventaire).toMatchObject({});
      });

      it('should return INumeroInventaire', () => {
        const formGroup = service.createNumeroInventaireFormGroup(sampleWithRequiredData);

        const numeroInventaire = service.getNumeroInventaire(formGroup) as any;

        expect(numeroInventaire).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INumeroInventaire should not enable id FormControl', () => {
        const formGroup = service.createNumeroInventaireFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNumeroInventaire should disable id FormControl', () => {
        const formGroup = service.createNumeroInventaireFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
