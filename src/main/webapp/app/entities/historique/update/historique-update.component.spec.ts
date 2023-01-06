import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HistoriqueFormService } from './historique-form.service';
import { HistoriqueService } from '../service/historique.service';
import { IHistorique } from '../historique.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { MaterielService } from 'app/entities/materiel/service/materiel.service';

import { HistoriqueUpdateComponent } from './historique-update.component';

describe('Historique Management Update Component', () => {
  let comp: HistoriqueUpdateComponent;
  let fixture: ComponentFixture<HistoriqueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let historiqueFormService: HistoriqueFormService;
  let historiqueService: HistoriqueService;
  let collaborateursService: CollaborateursService;
  let materielService: MaterielService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HistoriqueUpdateComponent],
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
      .overrideTemplate(HistoriqueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HistoriqueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    historiqueFormService = TestBed.inject(HistoriqueFormService);
    historiqueService = TestBed.inject(HistoriqueService);
    collaborateursService = TestBed.inject(CollaborateursService);
    materielService = TestBed.inject(MaterielService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Collaborateurs query and add missing value', () => {
      const historique: IHistorique = { id: 'CBA' };
      const ancienProprietaire: ICollaborateurs = { id: 'e17eaee5-44f3-4b32-a651-476d7b5c63d6' };
      historique.ancienProprietaire = ancienProprietaire;
      const nouveauProprietaire: ICollaborateurs = { id: 'd07f3c27-500e-41e8-b44f-e05b72f81f50' };
      historique.nouveauProprietaire = nouveauProprietaire;

      const collaborateursCollection: ICollaborateurs[] = [{ id: 'ae154f62-6ae6-4884-896c-b13dfbb671bb' }];
      jest.spyOn(collaborateursService, 'query').mockReturnValue(of(new HttpResponse({ body: collaborateursCollection })));
      const additionalCollaborateurs = [ancienProprietaire, nouveauProprietaire];
      const expectedCollection: ICollaborateurs[] = [...additionalCollaborateurs, ...collaborateursCollection];
      jest.spyOn(collaborateursService, 'addCollaborateursToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ historique });
      comp.ngOnInit();

      expect(collaborateursService.query).toHaveBeenCalled();
      expect(collaborateursService.addCollaborateursToCollectionIfMissing).toHaveBeenCalledWith(
        collaborateursCollection,
        ...additionalCollaborateurs.map(expect.objectContaining)
      );
      expect(comp.collaborateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Materiel query and add missing value', () => {
      const historique: IHistorique = { id: 'CBA' };
      const materiel: IMateriel = { id: '332d36c6-c34e-4ce8-97a5-e0be12d2e412' };
      historique.materiel = materiel;

      const materielCollection: IMateriel[] = [{ id: '35619d34-4b3f-48e8-8212-b46b0bc58b28' }];
      jest.spyOn(materielService, 'query').mockReturnValue(of(new HttpResponse({ body: materielCollection })));
      const additionalMateriels = [materiel];
      const expectedCollection: IMateriel[] = [...additionalMateriels, ...materielCollection];
      jest.spyOn(materielService, 'addMaterielToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ historique });
      comp.ngOnInit();

      expect(materielService.query).toHaveBeenCalled();
      expect(materielService.addMaterielToCollectionIfMissing).toHaveBeenCalledWith(
        materielCollection,
        ...additionalMateriels.map(expect.objectContaining)
      );
      expect(comp.materielsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const historique: IHistorique = { id: 'CBA' };
      const ancienProprietaire: ICollaborateurs = { id: '9aec76cd-4bcf-4e49-bac6-75dce0bf48f7' };
      historique.ancienProprietaire = ancienProprietaire;
      const nouveauProprietaire: ICollaborateurs = { id: 'bbfab64b-b465-4df8-adae-cfbc4bbb5205' };
      historique.nouveauProprietaire = nouveauProprietaire;
      const materiel: IMateriel = { id: '5b8556d4-25ac-4241-bf64-88bab19ff2b6' };
      historique.materiel = materiel;

      activatedRoute.data = of({ historique });
      comp.ngOnInit();

      expect(comp.collaborateursSharedCollection).toContain(ancienProprietaire);
      expect(comp.collaborateursSharedCollection).toContain(nouveauProprietaire);
      expect(comp.materielsSharedCollection).toContain(materiel);
      expect(comp.historique).toEqual(historique);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistorique>>();
      const historique = { id: 'ABC' };
      jest.spyOn(historiqueFormService, 'getHistorique').mockReturnValue(historique);
      jest.spyOn(historiqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historique }));
      saveSubject.complete();

      // THEN
      expect(historiqueFormService.getHistorique).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(historiqueService.update).toHaveBeenCalledWith(expect.objectContaining(historique));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistorique>>();
      const historique = { id: 'ABC' };
      jest.spyOn(historiqueFormService, 'getHistorique').mockReturnValue({ id: null });
      jest.spyOn(historiqueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historique: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: historique }));
      saveSubject.complete();

      // THEN
      expect(historiqueFormService.getHistorique).toHaveBeenCalled();
      expect(historiqueService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHistorique>>();
      const historique = { id: 'ABC' };
      jest.spyOn(historiqueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ historique });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(historiqueService.update).toHaveBeenCalled();
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
