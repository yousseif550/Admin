import dayjs from 'dayjs/esm';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';

export interface ICollaborateurs {
  id: string;
  nom?: string | null;
  identifiant?: string | null;
  tel?: number | null;
  prestataire?: boolean | null;
  isActif?: boolean | null;
  dateEntree?: dayjs.Dayjs | null;
  dateSortie?: dayjs.Dayjs | null;
  localisation?: Pick<ILocalisation, 'id' | 'batiment'> | null;
  projets?: Pick<IProjet, 'id' | 'nom'>[] | null;
  materiel?: Pick<IMateriel, 'id'> | null;
}

export interface MyObject {
  id: any;
  asset: any;
  objet: any;
}
export type NewCollaborateurs = Omit<ICollaborateurs, 'id'> & { id: null };
