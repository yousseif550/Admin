import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MouvementFormService } from './mouvement-form.service';
import { MouvementService } from '../service/mouvement.service';
import { IMouvement } from '../mouvement.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';

import { MouvementUpdateComponent } from './mouvement-update.component';

describe('Mouvement Management Update Component', () => {
  let comp: MouvementUpdateComponent;
  let fixture: ComponentFixture<MouvementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mouvementFormService: MouvementFormService;
  let mouvementService: MouvementService;
  let materielService: MaterielService;
  let localisationService: LocalisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MouvementUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MouvementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MouvementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mouvementFormService = TestBed.inject(MouvementFormService);
    mouvementService = TestBed.inject(MouvementService);
    materielService = TestBed.inject(MaterielService);
    localisationService = TestBed.inject(LocalisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Materiel query and add missing value', () => {
      const mouvement: IMouvement = { id: 'CBA' };
      const asset: IMateriel = { id: '45c2864c-b8e5-485a-91fd-8e0696435132' };
      mouvement.asset = asset;

      const materielCollection: IMateriel[] = [{ id: '8350b9d7-a0f8-4acf-a837-d33b95bd7d61' }];
      jest.spyOn(materielService, 'query').mockReturnValue(of(new HttpResponse({ body: materielCollection })));
      const additionalMateriels = [asset];
      const expectedCollection: IMateriel[] = [...additionalMateriels, ...materielCollection];
      jest.spyOn(materielService, 'addMaterielToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvement });
      comp.ngOnInit();

      expect(materielService.query).toHaveBeenCalled();
      expect(materielService.addMaterielToCollectionIfMissing).toHaveBeenCalledWith(
        materielCollection,
        ...additionalMateriels.map(expect.objectContaining)
      );
      expect(comp.materielsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Localisation query and add missing value', () => {
      const mouvement: IMouvement = { id: 'CBA' };
      const localisation: ILocalisation = { id: 'f986335e-8f3c-4a43-b50b-97bb0e91252c' };
      mouvement.localisation = localisation;

      const localisationCollection: ILocalisation[] = [{ id: 'b6603795-c033-4df8-9321-044bf9afe7e3' }];
      jest.spyOn(localisationService, 'query').mockReturnValue(of(new HttpResponse({ body: localisationCollection })));
      const additionalLocalisations = [localisation];
      const expectedCollection: ILocalisation[] = [...additionalLocalisations, ...localisationCollection];
      jest.spyOn(localisationService, 'addLocalisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mouvement });
      comp.ngOnInit();

      expect(localisationService.query).toHaveBeenCalled();
      expect(localisationService.addLocalisationToCollectionIfMissing).toHaveBeenCalledWith(
        localisationCollection,
        ...additionalLocalisations.map(expect.objectContaining)
      );
      expect(comp.localisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mouvement: IMouvement = { id: 'CBA' };
      const asset: IMateriel = { id: '7847b3bf-b54c-4a81-a6cb-a44c55f9a9c9' };
      mouvement.asset = asset;
      const localisation: ILocalisation = { id: '91dfe69a-e9a9-4d85-927d-a0ddfe79d908' };
      mouvement.localisation = localisation;

      activatedRoute.data = of({ mouvement });
      comp.ngOnInit();

      expect(comp.materielsSharedCollection).toContain(asset);
      expect(comp.localisationsSharedCollection).toContain(localisation);
      expect(comp.mouvement).toEqual(mouvement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMouvement>>();
      const mouvement = { id: 'ABC' };
      jest.spyOn(mouvementFormService, 'getMouvement').mockReturnValue(mouvement);
      jest.spyOn(mouvementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvement }));
      saveSubject.complete();

      // THEN
      expect(mouvementFormService.getMouvement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mouvementService.update).toHaveBeenCalledWith(expect.objectContaining(mouvement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMouvement>>();
      const mouvement = { id: 'ABC' };
      jest.spyOn(mouvementFormService, 'getMouvement').mockReturnValue({ id: null });
      jest.spyOn(mouvementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mouvement }));
      saveSubject.complete();

      // THEN
      expect(mouvementFormService.getMouvement).toHaveBeenCalled();
      expect(mouvementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMouvement>>();
      const mouvement = { id: 'ABC' };
      jest.spyOn(mouvementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mouvement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mouvementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMateriel', () => {
      it('Should forward to materielService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(materielService, 'compareMateriel');
        comp.compareMateriel(entity, entity2);
        expect(materielService.compareMateriel).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLocalisation', () => {
      it('Should forward to localisationService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(localisationService, 'compareLocalisation');
        comp.compareLocalisation(entity, entity2);
        expect(localisationService.compareLocalisation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
