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

import { MouvementUpdateComponent } from './mouvement-update.component';

describe('Mouvement Management Update Component', () => {
  let comp: MouvementUpdateComponent;
  let fixture: ComponentFixture<MouvementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mouvementFormService: MouvementFormService;
  let mouvementService: MouvementService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const mouvement: IMouvement = { id: 'CBA' };

      activatedRoute.data = of({ mouvement });
      comp.ngOnInit();

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
});
