import { IInformationsTech, NewInformationsTech } from './informations-tech.model';

export const sampleWithRequiredData: IInformationsTech = {
  id: '57a0181d-f867-4f81-a2ff-ab73f6ed25d5',
};

export const sampleWithPartialData: IInformationsTech = {
  id: '19cd2ee0-1e8d-415a-bc14-a12c892eb85b',
  pcDGFiP: 'Shoes',
  adresseDGFiP: 'quantifying Baby Internal',
};

export const sampleWithFullData: IInformationsTech = {
  id: '607838f3-a6fd-4866-bf98-7999f9796300',
  pcDom: 'Regional',
  pcDGFiP: 'Dollar',
  adresseSSG: 'communities',
  adresseDGFiP: 'pink',
};

export const sampleWithNewData: NewInformationsTech = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
