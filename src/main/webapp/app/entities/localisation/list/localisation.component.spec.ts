import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LocalisationService } from '../service/localisation.service';

import { LocalisationComponent } from './localisation.component';

describe('Localisation Management Component', () => {
  let comp: LocalisationComponent;
  let fixture: ComponentFixture<LocalisationComponent>;
  let service: LocalisationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'localisation', component: LocalisationComponent }]), HttpClientTestingModule],
      declarations: [LocalisationComponent],
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
      .overrideTemplate(LocalisationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LocalisationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LocalisationService);

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
    expect(comp.localisations?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to localisationService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getLocalisationIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getLocalisationIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
