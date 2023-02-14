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
  collaborateur?: Pick<ICollaborateurs, 'id'> | null;
  materiel?: Pick<IMateriel, 'id'> | null;
  bureauActuel?: Pick<ILocalisation, 'id'> | null;
  bureauDeplacement?: Pick<ILocalisation, 'id'> | null;
  localisation?: Pick<ILocalisation, 'id'> | null;
}

export type NewExtracDMOCSS = Omit<IExtracDMOCSS, 'id'> & { id: null };
