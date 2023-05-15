import { IMateriel } from 'app/entities/materiel/materiel.model';

export interface IInformationsTech {
  id: string;
  ipdfipConnexion?: string | null;
  ipfixDMOCSS?: string | null;
  adressMAC?: string | null;
  iPTeletravail?: string | null;
  adresseDGFiP?: string | null;
  pcDGFiP?: Pick<IMateriel, 'id' | 'asset'> | null;
}

export type NewInformationsTech = Omit<IInformationsTech, 'id'> & { id: null };
