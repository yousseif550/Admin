import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IExtracDMOCSS } from '../extrac-dmocss.model';
import { ExtracDMOCSSService } from '../service/extrac-dmocss.service';

import { ExtracDMOCSSRoutingResolveService } from './extrac-dmocss-routing-resolve.service';

describe('ExtracDMOCSS routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ExtracDMOCSSRoutingResolveService;
  let service: ExtracDMOCSSService;
  let resultExtracDMOCSS: IExtracDMOCSS | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ExtracDMOCSSRoutingResolveService);
    service = TestBed.inject(ExtracDMOCSSService);
    resultExtracDMOCSS = undefined;
  });

  describe('resolve', () => {
    it('should return IExtracDMOCSS returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultExtracDMOCSS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultExtracDMOCSS).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultExtracDMOCSS = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultExtracDMOCSS).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IExtracDMOCSS>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultExtracDMOCSS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultExtracDMOCSS).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
