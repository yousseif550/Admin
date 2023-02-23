import dayjs from 'dayjs/esm';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';

export interface ICollaborateurs {
  id: string;
  nom?: string | null;
  prenom?: string | null;
  identifiant?: string | null;
  tel?: number | null;
  prestataire?: boolean | null;
  isActif?: boolean | null;
  dateEntree?: dayjs.Dayjs | null;
  dateSortie?: dayjs.Dayjs | null;
  localisation?: Pick<ILocalisation, 'id'> | null;
  projets?: Pick<IProjet, 'id'>[] | null;
  materiel?: Pick<IMateriel, 'id'> | null;
}

export type NewCollaborateurs = Omit<ICollaborateurs, 'id'> & { id: null };
