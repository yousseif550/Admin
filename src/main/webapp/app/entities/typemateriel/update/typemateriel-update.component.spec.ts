import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TypematerielFormService } from './typemateriel-form.service';
import { TypematerielService } from '../service/typemateriel.service';
import { ITypemateriel } from '../typemateriel.model';

import { TypematerielUpdateComponent } from './typemateriel-update.component';

describe('Typemateriel Management Update Component', () => {
  let comp: TypematerielUpdateComponent;
  let fixture: ComponentFixture<TypematerielUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let typematerielFormService: TypematerielFormService;
  let typematerielService: TypematerielService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TypematerielUpdateComponent],
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
      .overrideTemplate(TypematerielUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypematerielUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    typematerielFormService = TestBed.inject(TypematerielFormService);
    typematerielService = TestBed.inject(TypematerielService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const typemateriel: ITypemateriel = { id: 'CBA' };

      activatedRoute.data = of({ typemateriel });
      comp.ngOnInit();

      expect(comp.typemateriel).toEqual(typemateriel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypemateriel>>();
      const typemateriel = { id: 'ABC' };
      jest.spyOn(typematerielFormService, 'getTypemateriel').mockReturnValue(typemateriel);
      jest.spyOn(typematerielService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typemateriel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typemateriel }));
      saveSubject.complete();

      // THEN
      expect(typematerielFormService.getTypemateriel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(typematerielService.update).toHaveBeenCalledWith(expect.objectContaining(typemateriel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypemateriel>>();
      const typemateriel = { id: 'ABC' };
      jest.spyOn(typematerielFormService, 'getTypemateriel').mockReturnValue({ id: null });
      jest.spyOn(typematerielService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typemateriel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: typemateriel }));
      saveSubject.complete();

      // THEN
      expect(typematerielFormService.getTypemateriel).toHaveBeenCalled();
      expect(typematerielService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITypemateriel>>();
      const typemateriel = { id: 'ABC' };
      jest.spyOn(typematerielService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ typemateriel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(typematerielService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
