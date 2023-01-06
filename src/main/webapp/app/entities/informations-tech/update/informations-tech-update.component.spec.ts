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
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { CollaborateursService } from 'app/entities/collaborateurs/service/collaborateurs.service';

import { InformationsTechUpdateComponent } from './informations-tech-update.component';

describe('InformationsTech Management Update Component', () => {
  let comp: InformationsTechUpdateComponent;
  let fixture: ComponentFixture<InformationsTechUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let informationsTechFormService: InformationsTechFormService;
  let informationsTechService: InformationsTechService;
  let collaborateursService: CollaborateursService;

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
    collaborateursService = TestBed.inject(CollaborateursService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Collaborateurs query and add missing value', () => {
      const informationsTech: IInformationsTech = { id: 'CBA' };
      const collaborateur: ICollaborateurs = { id: '7b5245e9-7eac-4aa8-be3c-4e56e3409edc' };
      informationsTech.collaborateur = collaborateur;

      const collaborateursCollection: ICollaborateurs[] = [{ id: 'ec8a6469-8416-4a84-b3c2-c59ef8f828a5' }];
      jest.spyOn(collaborateursService, 'query').mockReturnValue(of(new HttpResponse({ body: collaborateursCollection })));
      const additionalCollaborateurs = [collaborateur];
      const expectedCollection: ICollaborateurs[] = [...additionalCollaborateurs, ...collaborateursCollection];
      jest.spyOn(collaborateursService, 'addCollaborateursToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ informationsTech });
      comp.ngOnInit();

      expect(collaborateursService.query).toHaveBeenCalled();
      expect(collaborateursService.addCollaborateursToCollectionIfMissing).toHaveBeenCalledWith(
        collaborateursCollection,
        ...additionalCollaborateurs.map(expect.objectContaining)
      );
      expect(comp.collaborateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const informationsTech: IInformationsTech = { id: 'CBA' };
      const collaborateur: ICollaborateurs = { id: '4ea2a19a-0c44-485f-8e6b-2aa989d4a044' };
      informationsTech.collaborateur = collaborateur;

      activatedRoute.data = of({ informationsTech });
      comp.ngOnInit();

      expect(comp.collaborateursSharedCollection).toContain(collaborateur);
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
