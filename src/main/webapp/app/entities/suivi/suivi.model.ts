import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { Etat } from 'app/entities/enumerations/etat.model';

export interface ISuivi {
  id: string;
  envoiKitAccueil?: Etat | null;
  documentSigner?: Etat | null;
  commandePCDom?: Etat | null;
  compteSSG?: Etat | null;
  listeNTIC?: Etat | null;
  accesTeams?: Etat | null;
  accesPulseDGFiP?: Etat | null;
  profilPCDom?: Etat | null;
  commanderPCDGFiP?: Etat | null;
  creationBALPDGFiP?: Etat | null;
  creationCompteAD?: Etat | null;
  soclagePC?: Etat | null;
  dmocssIpTT?: Etat | null;
  installationLogiciel?: Etat | null;
  commentaires?: string | null;
  collaborateur?: Pick<ICollaborateurs, 'id'> | null;
}

export type NewSuivi = Omit<ISuivi, 'id'> & { id: null };
