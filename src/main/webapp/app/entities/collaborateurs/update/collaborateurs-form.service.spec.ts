import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../collaborateurs.test-samples';

import { CollaborateursFormService } from './collaborateurs-form.service';

describe('Collaborateurs Form Service', () => {
  let service: CollaborateursFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollaborateursFormService);
  });

  describe('Service methods', () => {
    describe('createCollaborateursFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCollaborateursFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            societe: expect.any(Object),
            identifiant: expect.any(Object),
            tel: expect.any(Object),
            prestataire: expect.any(Object),
            isActif: expect.any(Object),
            dateEntree: expect.any(Object),
            dateSortie: expect.any(Object),
            localisation: expect.any(Object),
            projets: expect.any(Object),
          })
        );
      });

      it('passing ICollaborateurs should create a new form with FormGroup', () => {
        const formGroup = service.createCollaborateursFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nom: expect.any(Object),
            societe: expect.any(Object),
            identifiant: expect.any(Object),
            tel: expect.any(Object),
            prestataire: expect.any(Object),
            isActif: expect.any(Object),
            dateEntree: expect.any(Object),
            dateSortie: expect.any(Object),
            localisation: expect.any(Object),
            projets: expect.any(Object),
          })
        );
      });
    });

    describe('getCollaborateurs', () => {
      it('should return NewCollaborateurs for default Collaborateurs initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCollaborateursFormGroup(sampleWithNewData);

        const collaborateurs = service.getCollaborateurs(formGroup) as any;

        expect(collaborateurs).toMatchObject(sampleWithNewData);
      });

      it('should return NewCollaborateurs for empty Collaborateurs initial value', () => {
        const formGroup = service.createCollaborateursFormGroup();

        const collaborateurs = service.getCollaborateurs(formGroup) as any;

        expect(collaborateurs).toMatchObject({});
      });

      it('should return ICollaborateurs', () => {
        const formGroup = service.createCollaborateursFormGroup(sampleWithRequiredData);

        const collaborateurs = service.getCollaborateurs(formGroup) as any;

        expect(collaborateurs).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICollaborateurs should not enable id FormControl', () => {
        const formGroup = service.createCollaborateursFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCollaborateurs should disable id FormControl', () => {
        const formGroup = service.createCollaborateursFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
