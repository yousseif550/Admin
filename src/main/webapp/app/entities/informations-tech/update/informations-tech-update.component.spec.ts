import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InformationsTechFormService } from './informations-tech-form.service';
import { InformationsTechService } from '../service/informations-tech.service';
import { IInformationsTech } from '../informations-tech.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';

import { InformationsTechUpdateComponent } from './informations-tech-update.component';

describe('InformationsTech Management Update Component', () => {
  let comp: InformationsTechUpdateComponent;
  let fixture: ComponentFixture<InformationsTechUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let informationsTechFormService: InformationsTechFormService;
  let informationsTechService: InformationsTechService;
  let materielService: MaterielService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InformationsTechUpdateComponent],
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
      .overrideTemplate(InformationsTechUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InformationsTechUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    informationsTechFormService = TestBed.inject(InformationsTechFormService);
    informationsTechService = TestBed.inject(InformationsTechService);
    materielService = TestBed.inject(MaterielService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Materiel query and add missing value', () => {
      const informationsTech: IInformationsTech = { id: 'CBA' };
      const pcDGFiP: IMateriel = { id: '5c1ccc4c-ab39-46c6-b7b0-4b7cfaf4c637' };
      informationsTech.pcDGFiP = pcDGFiP;

      const materielCollection: IMateriel[] = [{ id: 'bb3c094e-88b0-496f-86da-0c9e3c4f66de' }];
      jest.spyOn(materielService, 'query').mockReturnValue(of(new HttpResponse({ body: materielCollection })));
      const additionalMateriels = [pcDGFiP];
      const expectedCollection: IMateriel[] = [...additionalMateriels, ...materielCollection];
      jest.spyOn(materielService, 'addMaterielToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ informationsTech });
      comp.ngOnInit();

      expect(materielService.query).toHaveBeenCalled();
      expect(materielService.addMaterielToCollectionIfMissing).toHaveBeenCalledWith(
        materielCollection,
        ...additionalMateriels.map(expect.objectContaining)
      );
      expect(comp.materielsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const informationsTech: IInformationsTech = { id: 'CBA' };
      const pcDGFiP: IMateriel = { id: '94bb06e1-0ab7-4e71-9fa3-b460b9d5604c' };
      informationsTech.pcDGFiP = pcDGFiP;

      activatedRoute.data = of({ informationsTech });
      comp.ngOnInit();

      expect(comp.materielsSharedCollection).toContain(pcDGFiP);
      expect(comp.informationsTech).toEqual(informationsTech);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInformationsTech>>();
      const informationsTech = { id: 'ABC' };
      jest.spyOn(informationsTechFormService, 'getInformationsTech').mockReturnValue(informationsTech);
      jest.spyOn(informationsTechService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ informationsTech });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: informationsTech }));
      saveSubject.complete();

      // THEN
      expect(informationsTechFormService.getInformationsTech).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(informationsTechService.update).toHaveBeenCalledWith(expect.objectContaining(informationsTech));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInformationsTech>>();
      const informationsTech = { id: 'ABC' };
      jest.spyOn(informationsTechFormService, 'getInformationsTech').mockReturnValue({ id: null });
      jest.spyOn(informationsTechService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ informationsTech: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: informationsTech }));
      saveSubject.complete();

      // THEN
      expect(informationsTechFormService.getInformationsTech).toHaveBeenCalled();
      expect(informationsTechService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInformationsTech>>();
      const informationsTech = { id: 'ABC' };
      jest.spyOn(informationsTechService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ informationsTech });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(informationsTechService.update).toHaveBeenCalled();
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
  });
});
