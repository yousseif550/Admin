import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MaterielFormService } from './materiel-form.service';
import { MaterielService } from '../service/materiel.service';
import { IMateriel } from '../materiel.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';

import { MaterielUpdateComponent } from './materiel-update.component';

describe('Materiel Management Update Component', () => {
  let comp: MaterielUpdateComponent;
  let fixture: ComponentFixture<MaterielUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let materielFormService: MaterielFormService;
  let materielService: MaterielService;
  let localisationService: LocalisationService;
  let collaborateursService: CollaborateursService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MaterielUpdateComponent],
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
      .overrideTemplate(MaterielUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterielUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    materielFormService = TestBed.inject(MaterielFormService);
    materielService = TestBed.inject(MaterielService);
    localisationService = TestBed.inject(LocalisationService);
    collaborateursService = TestBed.inject(CollaborateursService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Localisation query and add missing value', () => {
      const materiel: IMateriel = { id: 'CBA' };
      const localisation: ILocalisation = { id: '2a24990c-8897-46bd-bcc1-0b702031e123' };
      materiel.localisation = localisation;

      const localisationCollection: ILocalisation[] = [{ id: '0ed38a3a-2a56-4d92-a649-dd938ea8b8ba' }];
      jest.spyOn(localisationService, 'query').mockReturnValue(of(new HttpResponse({ body: localisationCollection })));
      const additionalLocalisations = [localisation];
      const expectedCollection: ILocalisation[] = [...additionalLocalisations, ...localisationCollection];
      jest.spyOn(localisationService, 'addLocalisationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ materiel });
      comp.ngOnInit();

      expect(localisationService.query).toHaveBeenCalled();
      expect(localisationService.addLocalisationToCollectionIfMissing).toHaveBeenCalledWith(
        localisationCollection,
        ...additionalLocalisations.map(expect.objectContaining)
      );
      expect(comp.localisationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Collaborateurs query and add missing value', () => {
      const materiel: IMateriel = { id: 'CBA' };
      const collaborateurs: ICollaborateurs = { id: '936d983e-ec8a-4995-a9be-6cce21342c0a' };
      materiel.collaborateurs = collaborateurs;

      const collaborateursCollection: ICollaborateurs[] = [{ id: '78a9579b-4dd0-48f6-97a1-9f95404ed48f' }];
      jest.spyOn(collaborateursService, 'query').mockReturnValue(of(new HttpResponse({ body: collaborateursCollection })));
      const additionalCollaborateurs = [collaborateurs];
      const expectedCollection: ICollaborateurs[] = [...additionalCollaborateurs, ...collaborateursCollection];
      jest.spyOn(collaborateursService, 'addCollaborateursToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ materiel });
      comp.ngOnInit();

      expect(collaborateursService.query).toHaveBeenCalled();
      expect(collaborateursService.addCollaborateursToCollectionIfMissing).toHaveBeenCalledWith(
        collaborateursCollection,
        ...additionalCollaborateurs.map(expect.objectContaining)
      );
      expect(comp.collaborateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const materiel: IMateriel = { id: 'CBA' };
      const localisation: ILocalisation = { id: '80d9e66f-cb8e-4bd5-9f5d-fe92feb5fcf9' };
      materiel.localisation = localisation;
      const collaborateurs: ICollaborateurs = { id: 'f05dfd9a-37bb-4e68-ba27-81d159e6f6ca' };
      materiel.collaborateurs = collaborateurs;

      activatedRoute.data = of({ materiel });
      comp.ngOnInit();

      expect(comp.localisationsSharedCollection).toContain(localisation);
      expect(comp.collaborateursSharedCollection).toContain(collaborateurs);
      expect(comp.materiel).toEqual(materiel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMateriel>>();
      const materiel = { id: 'ABC' };
      jest.spyOn(materielFormService, 'getMateriel').mockReturnValue(materiel);
      jest.spyOn(materielService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materiel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materiel }));
      saveSubject.complete();

      // THEN
      expect(materielFormService.getMateriel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(materielService.update).toHaveBeenCalledWith(expect.objectContaining(materiel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMateriel>>();
      const materiel = { id: 'ABC' };
      jest.spyOn(materielFormService, 'getMateriel').mockReturnValue({ id: null });
      jest.spyOn(materielService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materiel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: materiel }));
      saveSubject.complete();

      // THEN
      expect(materielFormService.getMateriel).toHaveBeenCalled();
      expect(materielService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMateriel>>();
      const materiel = { id: 'ABC' };
      jest.spyOn(materielService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ materiel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(materielService.update).toHaveBeenCalled();
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

    describe('compareCollaborateurs', () => {
      it('Should forward to collaborateursService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(collaborateursService, 'compareCollaborateurs');
        comp.compareCollaborateurs(entity, entity2);
        expect(collaborateursService.compareCollaborateurs).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
