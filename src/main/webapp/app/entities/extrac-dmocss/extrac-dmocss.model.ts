import dayjs from 'dayjs/esm';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { Etat } from 'app/entities/enumerations/etat.model';

export interface IExtracDMOCSS {
  id: string;
  adressePhysiqueDGFiP?: string | null;
  date?: dayjs.Dayjs | null;
  ipPcDgfip?: string | null;
  ipVpnIPSEC?: string | null;
  ioTeletravail?: string | null;
  statut?: Etat | null;
  numVersion?: string | null;
  collaborateur?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
  materiel?: Pick<IMateriel, 'id' | 'asset'> | null;
  bureauActuel?: Pick<ILocalisation, 'id' | 'bureau'> | null;
  bureauDeplacement?: Pick<ILocalisation, 'id' | 'bureau'> | null;
  localisation?: Pick<ILocalisation, 'id' | 'batiment'> | null;
}

export type NewExtracDMOCSS = Omit<IExtracDMOCSS, 'id'> & { id: null };
