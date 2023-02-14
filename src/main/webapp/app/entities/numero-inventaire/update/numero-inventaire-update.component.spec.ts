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
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';

import { NumeroInventaireUpdateComponent } from './numero-inventaire-update.component';

describe('NumeroInventaire Management Update Component', () => {
  let comp: NumeroInventaireUpdateComponent;
  let fixture: ComponentFixture<NumeroInventaireUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let numeroInventaireFormService: NumeroInventaireFormService;
  let numeroInventaireService: NumeroInventaireService;
  let materielService: MaterielService;
  let collaborateursService: CollaborateursService;

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
    collaborateursService = TestBed.inject(CollaborateursService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Materiel query and add missing value', () => {
      const numeroInventaire: INumeroInventaire = { id: 'CBA' };
      const materielActuel: IMateriel = { id: '79546128-5f3b-4577-8871-6bebc1657b04' };
      numeroInventaire.materielActuel = materielActuel;
      const ancienMateriel: IMateriel = { id: '7a7b8264-cce9-4435-b25c-469298d9abae' };
      numeroInventaire.ancienMateriel = ancienMateriel;

      const materielCollection: IMateriel[] = [{ id: '5da4329f-e85e-4d84-b734-819ecb03eb66' }];
      jest.spyOn(materielService, 'query').mockReturnValue(of(new HttpResponse({ body: materielCollection })));
      const additionalMateriels = [materielActuel, ancienMateriel];
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

    it('Should call Collaborateurs query and add missing value', () => {
      const numeroInventaire: INumeroInventaire = { id: 'CBA' };
      const ancienProprietaire: ICollaborateurs = { id: '01ff0804-3f19-4616-9f8c-0567ad75b564' };
      numeroInventaire.ancienProprietaire = ancienProprietaire;
      const nouveauProprietaire: ICollaborateurs = { id: '940ca409-73d7-4dc6-8d8e-e6abb3c7bb64' };
      numeroInventaire.nouveauProprietaire = nouveauProprietaire;

      const collaborateursCollection: ICollaborateurs[] = [{ id: '8820101c-821c-42eb-a583-c83838d06d90' }];
      jest.spyOn(collaborateursService, 'query').mockReturnValue(of(new HttpResponse({ body: collaborateursCollection })));
      const additionalCollaborateurs = [ancienProprietaire, nouveauProprietaire];
      const expectedCollection: ICollaborateurs[] = [...additionalCollaborateurs, ...collaborateursCollection];
      jest.spyOn(collaborateursService, 'addCollaborateursToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ numeroInventaire });
      comp.ngOnInit();

      expect(collaborateursService.query).toHaveBeenCalled();
      expect(collaborateursService.addCollaborateursToCollectionIfMissing).toHaveBeenCalledWith(
        collaborateursCollection,
        ...additionalCollaborateurs.map(expect.objectContaining)
      );
      expect(comp.collaborateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const numeroInventaire: INumeroInventaire = { id: 'CBA' };
      const materielActuel: IMateriel = { id: '446006e2-923f-4f01-8932-6b71375fcce7' };
      numeroInventaire.materielActuel = materielActuel;
      const ancienMateriel: IMateriel = { id: '4339f698-86c3-4742-ab44-f8bc056b548a' };
      numeroInventaire.ancienMateriel = ancienMateriel;
      const ancienProprietaire: ICollaborateurs = { id: 'fd7a117b-0c9e-477f-bb32-891ce1263d19' };
      numeroInventaire.ancienProprietaire = ancienProprietaire;
      const nouveauProprietaire: ICollaborateurs = { id: '87c6e4a9-fdc5-497b-b0f4-b6a9ba165b7b' };
      numeroInventaire.nouveauProprietaire = nouveauProprietaire;

      activatedRoute.data = of({ numeroInventaire });
      comp.ngOnInit();

      expect(comp.materielsSharedCollection).toContain(materielActuel);
      expect(comp.materielsSharedCollection).toContain(ancienMateriel);
      expect(comp.collaborateursSharedCollection).toContain(ancienProprietaire);
      expect(comp.collaborateursSharedCollection).toContain(nouveauProprietaire);
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
