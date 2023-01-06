import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SuiviService } from '../service/suivi.service';

import { SuiviComponent } from './suivi.component';

describe('Suivi Management Component', () => {
  let comp: SuiviComponent;
  let fixture: ComponentFixture<SuiviComponent>;
  let service: SuiviService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'suivi', component: SuiviComponent }]), HttpClientTestingModule],
      declarations: [SuiviComponent],
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
      .overrideTemplate(SuiviComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SuiviComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SuiviService);

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
    expect(comp.suivis?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to suiviService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getSuiviIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSuiviIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
