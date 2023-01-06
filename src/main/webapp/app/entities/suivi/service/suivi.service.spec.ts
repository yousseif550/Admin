import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISuivi } from '../suivi.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../suivi.test-samples';

import { SuiviService } from './suivi.service';

const requireRestSample: ISuivi = {
  ...sampleWithRequiredData,
};

describe('Suivi Service', () => {
  let service: SuiviService;
  let httpMock: HttpTestingController;
  let expectedResult: ISuivi | ISuivi[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SuiviService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Suivi', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const suivi = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(suivi).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Suivi', () => {
      const suivi = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(suivi).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Suivi', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Suivi', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Suivi', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSuiviToCollectionIfMissing', () => {
      it('should add a Suivi to an empty array', () => {
        const suivi: ISuivi = sampleWithRequiredData;
        expectedResult = service.addSuiviToCollectionIfMissing([], suivi);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(suivi);
      });

      it('should not add a Suivi to an array that contains it', () => {
        const suivi: ISuivi = sampleWithRequiredData;
        const suiviCollection: ISuivi[] = [
          {
            ...suivi,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSuiviToCollectionIfMissing(suiviCollection, suivi);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Suivi to an array that doesn't contain it", () => {
        const suivi: ISuivi = sampleWithRequiredData;
        const suiviCollection: ISuivi[] = [sampleWithPartialData];
        expectedResult = service.addSuiviToCollectionIfMissing(suiviCollection, suivi);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(suivi);
      });

      it('should add only unique Suivi to an array', () => {
        const suiviArray: ISuivi[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const suiviCollection: ISuivi[] = [sampleWithRequiredData];
        expectedResult = service.addSuiviToCollectionIfMissing(suiviCollection, ...suiviArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const suivi: ISuivi = sampleWithRequiredData;
        const suivi2: ISuivi = sampleWithPartialData;
        expectedResult = service.addSuiviToCollectionIfMissing([], suivi, suivi2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(suivi);
        expect(expectedResult).toContain(suivi2);
      });

      it('should accept null and undefined values', () => {
        const suivi: ISuivi = sampleWithRequiredData;
        expectedResult = service.addSuiviToCollectionIfMissing([], null, suivi, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(suivi);
      });

      it('should return initial array if no Suivi is added', () => {
        const suiviCollection: ISuivi[] = [sampleWithRequiredData];
        expectedResult = service.addSuiviToCollectionIfMissing(suiviCollection, undefined, null);
        expect(expectedResult).toEqual(suiviCollection);
      });
    });

    describe('compareSuivi', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSuivi(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareSuivi(entity1, entity2);
        const compareResult2 = service.compareSuivi(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareSuivi(entity1, entity2);
        const compareResult2 = service.compareSuivi(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareSuivi(entity1, entity2);
        const compareResult2 = service.compareSuivi(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
