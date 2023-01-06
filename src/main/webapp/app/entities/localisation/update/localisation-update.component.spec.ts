import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LocalisationFormService } from './localisation-form.service';
import { LocalisationService } from '../service/localisation.service';
import { ILocalisation } from '../localisation.model';

import { LocalisationUpdateComponent } from './localisation-update.component';

describe('Localisation Management Update Component', () => {
  let comp: LocalisationUpdateComponent;
  let fixture: ComponentFixture<LocalisationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let localisationFormService: LocalisationFormService;
  let localisationService: LocalisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LocalisationUpdateComponent],
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
      .overrideTemplate(LocalisationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LocalisationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    localisationFormService = TestBed.inject(LocalisationFormService);
    localisationService = TestBed.inject(LocalisationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const localisation: ILocalisation = { id: 'CBA' };

      activatedRoute.data = of({ localisation });
      comp.ngOnInit();

      expect(comp.localisation).toEqual(localisation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocalisation>>();
      const localisation = { id: 'ABC' };
      jest.spyOn(localisationFormService, 'getLocalisation').mockReturnValue(localisation);
      jest.spyOn(localisationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ localisation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: localisation }));
      saveSubject.complete();

      // THEN
      expect(localisationFormService.getLocalisation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(localisationService.update).toHaveBeenCalledWith(expect.objectContaining(localisation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocalisation>>();
      const localisation = { id: 'ABC' };
      jest.spyOn(localisationFormService, 'getLocalisation').mockReturnValue({ id: null });
      jest.spyOn(localisationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ localisation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: localisation }));
      saveSubject.complete();

      // THEN
      expect(localisationFormService.getLocalisation).toHaveBeenCalled();
      expect(localisationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILocalisation>>();
      const localisation = { id: 'ABC' };
      jest.spyOn(localisationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ localisation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(localisationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
