import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ExtracDMOCSSService } from '../service/extrac-dmocss.service';

import { ExtracDMOCSSComponent } from './extrac-dmocss.component';

describe('ExtracDMOCSS Management Component', () => {
  let comp: ExtracDMOCSSComponent;
  let fixture: ComponentFixture<ExtracDMOCSSComponent>;
  let service: ExtracDMOCSSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'extrac-dmocss', component: ExtracDMOCSSComponent }]), HttpClientTestingModule],
      declarations: [ExtracDMOCSSComponent],
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
      .overrideTemplate(ExtracDMOCSSComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExtracDMOCSSComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ExtracDMOCSSService);

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
    expect(comp.extracDMOCSSES?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to extracDMOCSSService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getExtracDMOCSSIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getExtracDMOCSSIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
