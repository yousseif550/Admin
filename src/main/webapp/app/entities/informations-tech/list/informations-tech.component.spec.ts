import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { InformationsTechService } from '../service/informations-tech.service';

import { InformationsTechComponent } from './informations-tech.component';

describe('InformationsTech Management Component', () => {
  let comp: InformationsTechComponent;
  let fixture: ComponentFixture<InformationsTechComponent>;
  let service: InformationsTechService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'informations-tech', component: InformationsTechComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [InformationsTechComponent],
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
      .overrideTemplate(InformationsTechComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InformationsTechComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InformationsTechService);

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
    expect(comp.informationsTeches?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to informationsTechService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getInformationsTechIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getInformationsTechIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
