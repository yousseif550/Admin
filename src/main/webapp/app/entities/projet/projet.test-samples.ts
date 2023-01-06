import { IProjet, NewProjet } from './projet.model';

export const sampleWithRequiredData: IProjet = {
  id: '98f94b67-5196-4e43-ae1d-dd5d09734bac',
};

export const sampleWithPartialData: IProjet = {
  id: 'ecdaa04b-17df-4df5-8516-eda3c4cad5f3',
  nom: 'XSS enterprise',
  informations: 'solid protocol Loan',
};

export const sampleWithFullData: IProjet = {
  id: '127ee8c6-7df9-4b3b-bc9e-f2159eccdbd3',
  nom: 'Generic Accounts',
  dP: 'Assistant',
  stucture: 'Outdoors algorithm auxiliary',
  informations: 'solid',
};

export const sampleWithNewData: NewProjet = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
