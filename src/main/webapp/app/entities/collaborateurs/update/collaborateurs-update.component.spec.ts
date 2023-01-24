import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CollaborateursFormService } from './collaborateurs-form.service';
import { CollaborateursService } from '../service/collaborateurs.service';
import { ICollaborateurs } from '../collaborateurs.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { IProjet } from 'app/entities/projet/projet.model';
import { ProjetService } from 'app/entities/projet/service/projet.service';

import { CollaborateursUpdateComponent } from './collaborateurs-update.component';

describe('Collaborateurs Management Update Component', () => {
  let comp: CollaborateursUpdateComponent;
  let fixture: ComponentFixture<CollaborateursUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let collaborateursFormService: CollaborateursFormService;
  let collaborateursService: CollaborateursService;
  let localisationService: LocalisationService;
  let projetService: ProjetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CollaborateursUpdateComponent],
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
      .overrideTemplate(CollaborateursUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CollaborateursUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    collaborateursFormService = TestBed.inject(CollaborateursFormService);
    collaborateursService = TestBed.inject(CollaborateursService);
    localisationService = TestBed.inject(LocalisationService);
    projetService = TestBed.inject(ProjetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Localisation query and add missing value', () => {
      const collaborateurs: ICollaborateurs = { id: 'CBA' };
      const localisation: ILocalisation = { id: '0cc5bf18-c003-492e-9f3b-cc7dd5175948' };
      collaborateurs.localisation = localisation;

      const localisationCollection: ILocalisation[] = [{ id: 'c9527361-66fb-4b44-8711-3f9044685396' }];
      jest.spyOn(localisationService, 'query').mockReturnValue(of(new HttpResponse({ body: localisationCollection })));
      const additionalLocalisations = [localisation];
      const expectedCollection: ILocalisation[] = [...additionalLocalisations, ...localisationCollection];
      jest.spyOn(localisationService, 'addLocalisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ collaborateurs });
      comp.ngOnInit();

      expect(localisationService.query).toHaveBeenCalled();
      expect(localisationService.addLocalisationToCollectionIfMissing).toHaveBeenCalledWith(
        localisationCollection,
        ...additionalLocalisations.map(expect.objectContaining)
      );
      expect(comp.localisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Projet query and add missing value', () => {
      const collaborateurs: ICollaborateurs = { id: 'CBA' };
      const projets: IProjet[] = [{ id: 'adfc9712-1b9b-4332-8954-b2bec986d8f5' }];
      collaborateurs.projets = projets;

      const projetCollection: IProjet[] = [{ id: '2c5b8057-6381-4c0f-9a63-995ebcf268a3' }];
      jest.spyOn(projetService, 'query').mockReturnValue(of(new HttpResponse({ body: projetCollection })));
      const additionalProjets = [...projets];
      const expectedCollection: IProjet[] = [...additionalProjets, ...projetCollection];
      jest.spyOn(projetService, 'addProjetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ collaborateurs });
      comp.ngOnInit();

      expect(projetService.query).toHaveBeenCalled();
      expect(projetService.addProjetToCollectionIfMissing).toHaveBeenCalledWith(
        projetCollection,
        ...additionalProjets.map(expect.objectContaining)
      );
      expect(comp.projetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const collaborateurs: ICollaborateurs = { id: 'CBA' };
      const localisation: ILocalisation = { id: '66316c35-c3ed-4580-acae-981dae0d5f4e' };
      collaborateurs.localisation = localisation;
      const projet: IProjet = { id: '02ff6ccd-6be1-4450-938c-57d5358cda7d' };
      collaborateurs.projets = [projet];

      activatedRoute.data = of({ collaborateurs });
      comp.ngOnInit();

      expect(comp.localisationsSharedCollection).toContain(localisation);
      expect(comp.projetsSharedCollection).toContain(projet);
      expect(comp.collaborateurs).toEqual(collaborateurs);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborateurs>>();
      const collaborateurs = { id: 'ABC' };
      jest.spyOn(collaborateursFormService, 'getCollaborateurs').mockReturnValue(collaborateurs);
      jest.spyOn(collaborateursService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborateurs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: collaborateurs }));
      saveSubject.complete();

      // THEN
      expect(collaborateursFormService.getCollaborateurs).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(collaborateursService.update).toHaveBeenCalledWith(expect.objectContaining(collaborateurs));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborateurs>>();
      const collaborateurs = { id: 'ABC' };
      jest.spyOn(collaborateursFormService, 'getCollaborateurs').mockReturnValue({ id: null });
      jest.spyOn(collaborateursService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborateurs: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: collaborateurs }));
      saveSubject.complete();

      // THEN
      expect(collaborateursFormService.getCollaborateurs).toHaveBeenCalled();
      expect(collaborateursService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborateurs>>();
      const collaborateurs = { id: 'ABC' };
      jest.spyOn(collaborateursService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborateurs });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(collaborateursService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLocalisation', () => {
      it('Should forward to localisationService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(localisationService, 'compareLocalisation');
        comp.compareLocalisation(entity, entity2);
        expect(localisationService.compareLocalisation).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProjet', () => {
      it('Should forward to projetService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(projetService, 'compareProjet');
        comp.compareProjet(entity, entity2);
        expect(projetService.compareProjet).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
