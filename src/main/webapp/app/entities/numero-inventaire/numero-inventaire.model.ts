import dayjs from 'dayjs/esm';
import { IMateriel } from 'app/entities/materiel/materiel.model';

export interface INumeroInventaire {
  id: string;
  zone?: string | null;
  valeur?: number | null;
  disponible?: boolean | null;
  ancienMateriel?: string | null;
  dateModification?: dayjs.Dayjs | null;
  materielActuel?: Pick<IMateriel, 'id'> | null;
}

export type NewNumeroInventaire = Omit<INumeroInventaire, 'id'> & { id: null };
