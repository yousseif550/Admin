import dayjs from 'dayjs/esm';
import { Type } from 'app/entities/enumerations/type.model';

export interface IMouvement {
  id: string;
  date?: dayjs.Dayjs | null;
  type?: Type | null;
  source?: string | null;
  destination?: string | null;
  user?: string | null;
}

export type NewMouvement = Omit<IMouvement, 'id'> & { id: null };
