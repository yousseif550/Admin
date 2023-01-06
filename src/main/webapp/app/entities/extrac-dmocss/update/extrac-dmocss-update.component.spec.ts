import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExtracDMOCSSFormService } from './extrac-dmocss-form.service';
import { ExtracDMOCSSService } from '../service/extrac-dmocss.service';
import { IExtracDMOCSS } from '../extrac-dmocss.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';

import { ExtracDMOCSSUpdateComponent } from './extrac-dmocss-update.component';

describe('ExtracDMOCSS Management Update Component', () => {
  let comp: ExtracDMOCSSUpdateComponent;
  let fixture: ComponentFixture<ExtracDMOCSSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let extracDMOCSSFormService: ExtracDMOCSSFormService;
  let extracDMOCSSService: ExtracDMOCSSService;
  let collaborateursService: CollaborateursService;
  let materielService: MaterielService;
  let localisationService: LocalisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExtracDMOCSSUpdateComponent],
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
      .overrideTemplate(ExtracDMOCSSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExtracDMOCSSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    extracDMOCSSFormService = TestBed.inject(ExtracDMOCSSFormService);
    extracDMOCSSService = TestBed.inject(ExtracDMOCSSService);
    collaborateursService = TestBed.inject(CollaborateursService);
    materielService = TestBed.inject(MaterielService);
    localisationService = TestBed.inject(LocalisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Collaborateurs query and add missing value', () => {
      const extracDMOCSS: IExtracDMOCSS = { id: 'CBA' };
      const collaborateur: ICollaborateurs = { id: '9a770ee1-c93e-4a5d-8b6e-37f59548dfd7' };
      extracDMOCSS.collaborateur = collaborateur;

      const collaborateursCollection: ICollaborateurs[] = [{ id: '71c6f013-149f-46ce-a9a1-2647d9d5c79c' }];
      jest.spyOn(collaborateursService, 'query').mockReturnValue(of(new HttpResponse({ body: collaborateursCollection })));
      const additionalCollaborateurs = [collaborateur];
      const expectedCollection: ICollaborateurs[] = [...additionalCollaborateurs, ...collaborateursCollection];
      jest.spyOn(collaborateursService, 'addCollaborateursToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ extracDMOCSS });
      comp.ngOnInit();

      expect(collaborateursService.query).toHaveBeenCalled();
      expect(collaborateursService.addCollaborateursToCollectionIfMissing).toHaveBeenCalledWith(
        collaborateursCollection,
        ...additionalCollaborateurs.map(expect.objectContaining)
      );
      expect(comp.collaborateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Materiel query and add missing value', () => {
      const extracDMOCSS: IExtracDMOCSS = { id: 'CBA' };
      const materiel: IMateriel = { id: '0d8daa8a-8370-4d06-8a07-36d722cd661a' };
      extracDMOCSS.materiel = materiel;

      const materielCollection: IMateriel[] = [{ id: '3a5d94fa-be1b-462a-9d82-2f64cb7f50a6' }];
      jest.spyOn(materielService, 'query').mockReturnValue(of(new HttpResponse({ body: materielCollection })));
      const additionalMateriels = [materiel];
      const expectedCollection: IMateriel[] = [...additionalMateriels, ...materielCollection];
      jest.spyOn(materielService, 'addMaterielToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ extracDMOCSS });
      comp.ngOnInit();

      expect(materielService.query).toHaveBeenCalled();
      expect(materielService.addMaterielToCollectionIfMissing).toHaveBeenCalledWith(
        materielCollection,
        ...additionalMateriels.map(expect.objectContaining)
      );
      expect(comp.materielsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Localisation query and add missing value', () => {
      const extracDMOCSS: IExtracDMOCSS = { id: 'CBA' };
      const localisation: ILocalisation = { id: 'd733fedc-74c1-4408-9642-ce0bc4a1431d' };
      extracDMOCSS.localisation = localisation;

      const localisationCollection: ILocalisation[] = [{ id: '67ccda90-ee4f-4eb6-955c-7a23355b46d3' }];
      jest.spyOn(localisationService, 'query').mockReturnValue(of(new HttpResponse({ body: localisationCollection })));
      const additionalLocalisations = [localisation];
      const expectedCollection: ILocalisation[] = [...additionalLocalisations, ...localisationCollection];
      jest.spyOn(localisationService, 'addLocalisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ extracDMOCSS });
      comp.ngOnInit();

      expect(localisationService.query).toHaveBeenCalled();
      expect(localisationService.addLocalisationToCollectionIfMissing).toHaveBeenCalledWith(
        localisationCollection,
        ...additionalLocalisations.map(expect.objectContaining)
      );
      expect(comp.localisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const extracDMOCSS: IExtracDMOCSS = { id: 'CBA' };
      const collaborateur: ICollaborateurs = { id: '1d73ac97-81dc-4ebe-a084-51f5d7c3fb40' };
      extracDMOCSS.collaborateur = collaborateur;
      const materiel: IMateriel = { id: '026788e0-6edf-4d0c-99e7-4d41e6f72650' };
      extracDMOCSS.materiel = materiel;
      const localisation: ILocalisation = { id: '06de5713-244c-4f9c-96bd-73ea6f883d9f' };
      extracDMOCSS.localisation = localisation;

      activatedRoute.data = of({ extracDMOCSS });
      comp.ngOnInit();

      expect(comp.collaborateursSharedCollection).toContain(collaborateur);
      expect(comp.materielsSharedCollection).toContain(materiel);
      expect(comp.localisationsSharedCollection).toContain(localisation);
      expect(comp.extracDMOCSS).toEqual(extracDMOCSS);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExtracDMOCSS>>();
      const extracDMOCSS = { id: 'ABC' };
      jest.spyOn(extracDMOCSSFormService, 'getExtracDMOCSS').mockReturnValue(extracDMOCSS);
      jest.spyOn(extracDMOCSSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ extracDMOCSS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: extracDMOCSS }));
      saveSubject.complete();

      // THEN
      expect(extracDMOCSSFormService.getExtracDMOCSS).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(extracDMOCSSService.update).toHaveBeenCalledWith(expect.objectContaining(extracDMOCSS));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExtracDMOCSS>>();
      const extracDMOCSS = { id: 'ABC' };
      jest.spyOn(extracDMOCSSFormService, 'getExtracDMOCSS').mockReturnValue({ id: null });
      jest.spyOn(extracDMOCSSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ extracDMOCSS: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: extracDMOCSS }));
      saveSubject.complete();

      // THEN
      expect(extracDMOCSSFormService.getExtracDMOCSS).toHaveBeenCalled();
      expect(extracDMOCSSService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExtracDMOCSS>>();
      const extracDMOCSS = { id: 'ABC' };
      jest.spyOn(extracDMOCSSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ extracDMOCSS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(extracDMOCSSService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCollaborateurs', () => {
      it('Should forward to collaborateursService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(collaborateursService, 'compareCollaborateurs');
        comp.compareCollaborateurs(entity, entity2);
        expect(collaborateursService.compareCollaborateurs).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
