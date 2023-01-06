import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { HistoriqueService } from '../service/historique.service';

import { HistoriqueComponent } from './historique.component';

describe('Historique Management Component', () => {
  let comp: HistoriqueComponent;
  let fixture: ComponentFixture<HistoriqueComponent>;
  let service: HistoriqueService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'historique', component: HistoriqueComponent }]), HttpClientTestingModule],
      declarations: [HistoriqueComponent],
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
      .overrideTemplate(HistoriqueComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HistoriqueComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HistoriqueService);

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
    expect(comp.historiques?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to historiqueService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getHistoriqueIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getHistoriqueIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
