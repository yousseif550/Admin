import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { NumeroInventaireService } from '../service/numero-inventaire.service';

import { NumeroInventaireComponent } from './numero-inventaire.component';

describe('NumeroInventaire Management Component', () => {
  let comp: NumeroInventaireComponent;
  let fixture: ComponentFixture<NumeroInventaireComponent>;
  let service: NumeroInventaireService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'numero-inventaire', component: NumeroInventaireComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [NumeroInventaireComponent],
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
      .overrideTemplate(NumeroInventaireComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NumeroInventaireComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(NumeroInventaireService);

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
    expect(comp.numeroInventaires?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to numeroInventaireService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getNumeroInventaireIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getNumeroInventaireIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
