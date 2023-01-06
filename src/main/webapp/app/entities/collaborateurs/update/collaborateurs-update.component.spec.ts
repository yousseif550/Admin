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

import { CollaborateursUpdateComponent } from './collaborateurs-update.component';

describe('Collaborateurs Management Update Component', () => {
  let comp: CollaborateursUpdateComponent;
  let fixture: ComponentFixture<CollaborateursUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let collaborateursFormService: CollaborateursFormService;
  let collaborateursService: CollaborateursService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const collaborateurs: ICollaborateurs = { id: 'CBA' };

      activatedRoute.data = of({ collaborateurs });
      comp.ngOnInit();

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
});
