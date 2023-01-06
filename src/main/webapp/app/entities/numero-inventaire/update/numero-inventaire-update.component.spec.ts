import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NumeroInventaireFormService } from './numero-inventaire-form.service';
import { NumeroInventaireService } from '../service/numero-inventaire.service';
import { INumeroInventaire } from '../numero-inventaire.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';

import { NumeroInventaireUpdateComponent } from './numero-inventaire-update.component';

describe('NumeroInventaire Management Update Component', () => {
  let comp: NumeroInventaireUpdateComponent;
  let fixture: ComponentFixture<NumeroInventaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let numeroInventaireFormService: NumeroInventaireFormService;
  let numeroInventaireService: NumeroInventaireService;
  let materielService: MaterielService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NumeroInventaireUpdateComponent],
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
      .overrideTemplate(NumeroInventaireUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NumeroInventaireUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    numeroInventaireFormService = TestBed.inject(NumeroInventaireFormService);
    numeroInventaireService = TestBed.inject(NumeroInventaireService);
    materielService = TestBed.inject(MaterielService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Materiel query and add missing value', () => {
      const numeroInventaire: INumeroInventaire = { id: 'CBA' };
      const materielActuel: IMateriel = { id: '79546128-5f3b-4577-8871-6bebc1657b04' };
      numeroInventaire.materielActuel = materielActuel;

      const materielCollection: IMateriel[] = [{ id: '7a7b8264-cce9-4435-b25c-469298d9abae' }];
      jest.spyOn(materielService, 'query').mockReturnValue(of(new HttpResponse({ body: materielCollection })));
      const additionalMateriels = [materielActuel];
      const expectedCollection: IMateriel[] = [...additionalMateriels, ...materielCollection];
      jest.spyOn(materielService, 'addMaterielToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ numeroInventaire });
      comp.ngOnInit();

      expect(materielService.query).toHaveBeenCalled();
      expect(materielService.addMaterielToCollectionIfMissing).toHaveBeenCalledWith(
        materielCollection,
        ...additionalMateriels.map(expect.objectContaining)
      );
      expect(comp.materielsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const numeroInventaire: INumeroInventaire = { id: 'CBA' };
      const materielActuel: IMateriel = { id: '5da4329f-e85e-4d84-b734-819ecb03eb66' };
      numeroInventaire.materielActuel = materielActuel;

      activatedRoute.data = of({ numeroInventaire });
      comp.ngOnInit();

      expect(comp.materielsSharedCollection).toContain(materielActuel);
      expect(comp.numeroInventaire).toEqual(numeroInventaire);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INumeroInventaire>>();
      const numeroInventaire = { id: 'ABC' };
      jest.spyOn(numeroInventaireFormService, 'getNumeroInventaire').mockReturnValue(numeroInventaire);
      jest.spyOn(numeroInventaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ numeroInventaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: numeroInventaire }));
      saveSubject.complete();

      // THEN
      expect(numeroInventaireFormService.getNumeroInventaire).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(numeroInventaireService.update).toHaveBeenCalledWith(expect.objectContaining(numeroInventaire));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INumeroInventaire>>();
      const numeroInventaire = { id: 'ABC' };
      jest.spyOn(numeroInventaireFormService, 'getNumeroInventaire').mockReturnValue({ id: null });
      jest.spyOn(numeroInventaireService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ numeroInventaire: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: numeroInventaire }));
      saveSubject.complete();

      // THEN
      expect(numeroInventaireFormService.getNumeroInventaire).toHaveBeenCalled();
      expect(numeroInventaireService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INumeroInventaire>>();
      const numeroInventaire = { id: 'ABC' };
      jest.spyOn(numeroInventaireService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ numeroInventaire });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(numeroInventaireService.update).toHaveBeenCalled();
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
