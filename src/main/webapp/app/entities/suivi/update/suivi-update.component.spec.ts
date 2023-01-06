import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SuiviFormService } from './suivi-form.service';
import { SuiviService } from '../service/suivi.service';
import { ISuivi } from '../suivi.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';

import { SuiviUpdateComponent } from './suivi-update.component';

describe('Suivi Management Update Component', () => {
  let comp: SuiviUpdateComponent;
  let fixture: ComponentFixture<SuiviUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let suiviFormService: SuiviFormService;
  let suiviService: SuiviService;
  let collaborateursService: CollaborateursService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SuiviUpdateComponent],
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
      .overrideTemplate(SuiviUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SuiviUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    suiviFormService = TestBed.inject(SuiviFormService);
    suiviService = TestBed.inject(SuiviService);
    collaborateursService = TestBed.inject(CollaborateursService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Collaborateurs query and add missing value', () => {
      const suivi: ISuivi = { id: 'CBA' };
      const collaborateur: ICollaborateurs = { id: 'eb099185-bf47-4d08-8ba3-8d85a2258289' };
      suivi.collaborateur = collaborateur;

      const collaborateursCollection: ICollaborateurs[] = [{ id: '6d2d2855-e311-478f-a490-fe1ac3930890' }];
      jest.spyOn(collaborateursService, 'query').mockReturnValue(of(new HttpResponse({ body: collaborateursCollection })));
      const additionalCollaborateurs = [collaborateur];
      const expectedCollection: ICollaborateurs[] = [...additionalCollaborateurs, ...collaborateursCollection];
      jest.spyOn(collaborateursService, 'addCollaborateursToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ suivi });
      comp.ngOnInit();

      expect(collaborateursService.query).toHaveBeenCalled();
      expect(collaborateursService.addCollaborateursToCollectionIfMissing).toHaveBeenCalledWith(
        collaborateursCollection,
        ...additionalCollaborateurs.map(expect.objectContaining)
      );
      expect(comp.collaborateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const suivi: ISuivi = { id: 'CBA' };
      const collaborateur: ICollaborateurs = { id: '16cab79e-3c03-47c0-88a6-3ba0be636d91' };
      suivi.collaborateur = collaborateur;

      activatedRoute.data = of({ suivi });
      comp.ngOnInit();

      expect(comp.collaborateursSharedCollection).toContain(collaborateur);
      expect(comp.suivi).toEqual(suivi);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISuivi>>();
      const suivi = { id: 'ABC' };
      jest.spyOn(suiviFormService, 'getSuivi').mockReturnValue(suivi);
      jest.spyOn(suiviService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ suivi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: suivi }));
      saveSubject.complete();

      // THEN
      expect(suiviFormService.getSuivi).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(suiviService.update).toHaveBeenCalledWith(expect.objectContaining(suivi));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISuivi>>();
      const suivi = { id: 'ABC' };
      jest.spyOn(suiviFormService, 'getSuivi').mockReturnValue({ id: null });
      jest.spyOn(suiviService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ suivi: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: suivi }));
      saveSubject.complete();

      // THEN
      expect(suiviFormService.getSuivi).toHaveBeenCalled();
      expect(suiviService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISuivi>>();
      const suivi = { id: 'ABC' };
      jest.spyOn(suiviService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ suivi });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(suiviService.update).toHaveBeenCalled();
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
  });
});
