import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypemateriel } from '../typemateriel.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../typemateriel.test-samples';

import { TypematerielService } from './typemateriel.service';

const requireRestSample: ITypemateriel = {
  ...sampleWithRequiredData,
};

describe('Typemateriel Service', () => {
  let service: TypematerielService;
  let httpMock: HttpTestingController;
  let expectedResult: ITypemateriel | ITypemateriel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TypematerielService);
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

    it('should create a Typemateriel', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const typemateriel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(typemateriel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Typemateriel', () => {
      const typemateriel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(typemateriel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Typemateriel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Typemateriel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Typemateriel', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTypematerielToCollectionIfMissing', () => {
      it('should add a Typemateriel to an empty array', () => {
        const typemateriel: ITypemateriel = sampleWithRequiredData;
        expectedResult = service.addTypematerielToCollectionIfMissing([], typemateriel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typemateriel);
      });

      it('should not add a Typemateriel to an array that contains it', () => {
        const typemateriel: ITypemateriel = sampleWithRequiredData;
        const typematerielCollection: ITypemateriel[] = [
          {
            ...typemateriel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTypematerielToCollectionIfMissing(typematerielCollection, typemateriel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Typemateriel to an array that doesn't contain it", () => {
        const typemateriel: ITypemateriel = sampleWithRequiredData;
        const typematerielCollection: ITypemateriel[] = [sampleWithPartialData];
        expectedResult = service.addTypematerielToCollectionIfMissing(typematerielCollection, typemateriel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typemateriel);
      });

      it('should add only unique Typemateriel to an array', () => {
        const typematerielArray: ITypemateriel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const typematerielCollection: ITypemateriel[] = [sampleWithRequiredData];
        expectedResult = service.addTypematerielToCollectionIfMissing(typematerielCollection, ...typematerielArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const typemateriel: ITypemateriel = sampleWithRequiredData;
        const typemateriel2: ITypemateriel = sampleWithPartialData;
        expectedResult = service.addTypematerielToCollectionIfMissing([], typemateriel, typemateriel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(typemateriel);
        expect(expectedResult).toContain(typemateriel2);
      });

      it('should accept null and undefined values', () => {
        const typemateriel: ITypemateriel = sampleWithRequiredData;
        expectedResult = service.addTypematerielToCollectionIfMissing([], null, typemateriel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(typemateriel);
      });

      it('should return initial array if no Typemateriel is added', () => {
        const typematerielCollection: ITypemateriel[] = [sampleWithRequiredData];
        expectedResult = service.addTypematerielToCollectionIfMissing(typematerielCollection, undefined, null);
        expect(expectedResult).toEqual(typematerielCollection);
      });
    });

    describe('compareTypemateriel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTypemateriel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareTypemateriel(entity1, entity2);
        const compareResult2 = service.compareTypemateriel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareTypemateriel(entity1, entity2);
        const compareResult2 = service.compareTypemateriel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareTypemateriel(entity1, entity2);
        const compareResult2 = service.compareTypemateriel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
