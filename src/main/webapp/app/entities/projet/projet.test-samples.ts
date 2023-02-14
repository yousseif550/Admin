import { IProjet, NewProjet } from './projet.model';

export const sampleWithRequiredData: IProjet = {
  id: '98f94b67-5196-4e43-ae1d-dd5d09734bac',
};

export const sampleWithPartialData: IProjet = {
  id: 'becdaa04-b17d-4fdf-9851-6eda3c4cad5f',
  nom: 'invoice',
};

export const sampleWithFullData: IProjet = {
  id: 'd5cdcce3-5cd9-49a1-a7ee-8c67df9b3bbc',
  nom: 'Principal Sausages',
  stucture: 'USB maroon Account',
  informations: 'Accounts',
};

export const sampleWithNewData: NewProjet = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
