import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICollaborateurs } from '../collaborateurs.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../collaborateurs.test-samples';

import { CollaborateursService, RestCollaborateurs } from './collaborateurs.service';

const requireRestSample: RestCollaborateurs = {
  ...sampleWithRequiredData,
  dateEntree: sampleWithRequiredData.dateEntree?.format(DATE_FORMAT),
  dateSortie: sampleWithRequiredData.dateSortie?.format(DATE_FORMAT),
};

describe('Collaborateurs Service', () => {
  let service: CollaborateursService;
  let httpMock: HttpTestingController;
  let expectedResult: ICollaborateurs | ICollaborateurs[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CollaborateursService);
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

    it('should create a Collaborateurs', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const collaborateurs = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(collaborateurs).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Collaborateurs', () => {
      const collaborateurs = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(collaborateurs).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Collaborateurs', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Collaborateurs', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Collaborateurs', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCollaborateursToCollectionIfMissing', () => {
      it('should add a Collaborateurs to an empty array', () => {
        const collaborateurs: ICollaborateurs = sampleWithRequiredData;
        expectedResult = service.addCollaborateursToCollectionIfMissing([], collaborateurs);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(collaborateurs);
      });

      it('should not add a Collaborateurs to an array that contains it', () => {
        const collaborateurs: ICollaborateurs = sampleWithRequiredData;
        const collaborateursCollection: ICollaborateurs[] = [
          {
            ...collaborateurs,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCollaborateursToCollectionIfMissing(collaborateursCollection, collaborateurs);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Collaborateurs to an array that doesn't contain it", () => {
        const collaborateurs: ICollaborateurs = sampleWithRequiredData;
        const collaborateursCollection: ICollaborateurs[] = [sampleWithPartialData];
        expectedResult = service.addCollaborateursToCollectionIfMissing(collaborateursCollection, collaborateurs);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(collaborateurs);
      });

      it('should add only unique Collaborateurs to an array', () => {
        const collaborateursArray: ICollaborateurs[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const collaborateursCollection: ICollaborateurs[] = [sampleWithRequiredData];
        expectedResult = service.addCollaborateursToCollectionIfMissing(collaborateursCollection, ...collaborateursArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const collaborateurs: ICollaborateurs = sampleWithRequiredData;
        const collaborateurs2: ICollaborateurs = sampleWithPartialData;
        expectedResult = service.addCollaborateursToCollectionIfMissing([], collaborateurs, collaborateurs2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(collaborateurs);
        expect(expectedResult).toContain(collaborateurs2);
      });

      it('should accept null and undefined values', () => {
        const collaborateurs: ICollaborateurs = sampleWithRequiredData;
        expectedResult = service.addCollaborateursToCollectionIfMissing([], null, collaborateurs, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(collaborateurs);
      });

      it('should return initial array if no Collaborateurs is added', () => {
        const collaborateursCollection: ICollaborateurs[] = [sampleWithRequiredData];
        expectedResult = service.addCollaborateursToCollectionIfMissing(collaborateursCollection, undefined, null);
        expect(expectedResult).toEqual(collaborateursCollection);
      });
    });

    describe('compareCollaborateurs', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCollaborateurs(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareCollaborateurs(entity1, entity2);
        const compareResult2 = service.compareCollaborateurs(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareCollaborateurs(entity1, entity2);
        const compareResult2 = service.compareCollaborateurs(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareCollaborateurs(entity1, entity2);
        const compareResult2 = service.compareCollaborateurs(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
