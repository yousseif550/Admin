import { Etat } from 'app/entities/enumerations/etat.model';

import { ISuivi, NewSuivi } from './suivi.model';

export const sampleWithRequiredData: ISuivi = {
  id: '3699edb2-2bf9-4cf7-b7e7-2c45f9ed65d4',
};

export const sampleWithPartialData: ISuivi = {
  id: '446340a1-4526-418e-ab6c-854e451ef1ca',
  documentSigner: Etat['EnAttenteDeReponse'],
  compteSSG: Etat['Reatribution'],
  accesPulseDGFiP: Etat['Envoyer'],
  installationLogiciel: Etat['Envoyer'],
  commentaires: 'concept',
};

export const sampleWithFullData: ISuivi = {
  id: 'f3fa773d-27a9-4f17-998b-a279f1baec8b',
  envoiKitAccueil: Etat['Reatribution'],
  documentSigner: Etat['NonRealiser'],
  commandePCDom: Etat['NonRealiser'],
  compteSSG: Etat['Envoyer'],
  listeNTIC: Etat['NonRealiser'],
  accesTeams: Etat['OK'],
  accesPulseDGFiP: Etat['EnCours'],
  profilPCDom: Etat['EnCours'],
  commanderPCDGFiP: Etat['EnAttenteDeReponse'],
  creationBALPDGFiP: Etat['NonRealiser'],
  creationCompteAD: Etat['Envoyer'],
  soclagePC: Etat['Envoyer'],
  dmocssIpTT: Etat['NonRealiser'],
  installationLogiciel: Etat['EnCours'],
  commentaires: 'Research Cambridgeshire Borders',
};

export const sampleWithNewData: NewSuivi = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
