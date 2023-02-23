import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TypematerielService } from '../service/typemateriel.service';

import { TypematerielComponent } from './typemateriel.component';

describe('Typemateriel Management Component', () => {
  let comp: TypematerielComponent;
  let fixture: ComponentFixture<TypematerielComponent>;
  let service: TypematerielService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'typemateriel', component: TypematerielComponent }]), HttpClientTestingModule],
      declarations: [TypematerielComponent],
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
      .overrideTemplate(TypematerielComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TypematerielComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TypematerielService);

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
    expect(comp.typemateriels?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to typematerielService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getTypematerielIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTypematerielIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
