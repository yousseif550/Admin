import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MouvementService } from '../service/mouvement.service';

import { MouvementComponent } from './mouvement.component';

describe('Mouvement Management Component', () => {
  let comp: MouvementComponent;
  let fixture: ComponentFixture<MouvementComponent>;
  let service: MouvementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'mouvement', component: MouvementComponent }]), HttpClientTestingModule],
      declarations: [MouvementComponent],
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
      .overrideTemplate(MouvementComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MouvementComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MouvementService);

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
    expect(comp.mouvements?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });

  describe('trackId', () => {
    it('Should forward to mouvementService', () => {
      const entity = { id: 'ABC' };
      jest.spyOn(service, 'getMouvementIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMouvementIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
