import dayjs from 'dayjs/esm';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';

export interface IExtracDMOCSS {
  id: string;
  adressePhysiqueDGFiP?: string | null;
  bureauActuel?: string | null;
  bureauDeplacement?: string | null;
  date?: dayjs.Dayjs | null;
  ipPcDGFiP?: string | null;
  ipVpnIPSEC?: string | null;
  collaborateur?: Pick<ICollaborateurs, 'id'> | null;
  materiel?: Pick<IMateriel, 'id'> | null;
  localisation?: Pick<ILocalisation, 'id'> | null;
}

export type NewExtracDMOCSS = Omit<IExtracDMOCSS, 'id'> & { id: null };
