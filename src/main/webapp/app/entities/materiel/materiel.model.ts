import dayjs from 'dayjs/esm';
import { ITypemateriel } from 'app/entities/typemateriel/typemateriel.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface IMateriel {
  id: string;
  utilisation?: string | null;
  modele?: string | null;
  asset?: string | null;
  dateAttribution?: dayjs.Dayjs | null;
  dateRendu?: dayjs.Dayjs | null;
  actif?: boolean | null;
  isHs?: boolean | null;
  cleAntiVol?: string | null;
  adressMAC?: string | null;
  stationDgfip?: string | null;
  ipdfip?: string | null;
  iPTeletravail?: string | null;
  bios?: string | null;
  majBios?: boolean | null;
  commentaire?: string | null;
  objet?: Pick<ITypemateriel, 'id' | 'type'> | null;
  localisation?: Pick<ILocalisation, 'id' | 'batiment'> | null;
  collaborateur?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
}

export type NewMateriel = Omit<IMateriel, 'id'> & { id: null };
