import { ILocalisation, NewLocalisation } from './localisation.model';

export const sampleWithRequiredData: ILocalisation = {
  id: 'ad0543c7-c339-4562-a9ce-484bd4ba9b56',
};

export const sampleWithPartialData: ILocalisation = {
  id: 'd1321bc6-140b-4c1e-8bd6-6499df786047',
  bureauOrigine: 'e-markets',
  site: 'Knolls',
};

export const sampleWithFullData: ILocalisation = {
  id: '384e6b72-74eb-4d70-92c3-75dff32390ce',
  batiment: 'user-centric Kiribati',
  bureauOrigine: 'Jewelery Planner',
  bureauStockage: 'gold',
  site: 'analyzer Gorgeous port',
  ville: 'Borders Pike',
};

export const sampleWithNewData: NewLocalisation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
