import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MaterielService } from '../service/materiel.service';

import { MaterielComponent } from './materiel.component';

describe('Materiel Management Component', () => {
  let comp: MaterielComponent;
  let fixture: ComponentFixture<MaterielComponent>;
  let service: MaterielService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'materiel', component: MaterielComponent }]), HttpClientTestingModule],
      declarations: [MaterielComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(MaterielComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MaterielComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MaterielService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.materiels?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to materielService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getMaterielIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMaterielIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
