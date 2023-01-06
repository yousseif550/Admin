export interface ILocalisation {
  id: string;
  batiment?: string | null;
  bureau?: string | null;
  site?: string | null;
  ville?: string | null;
}

export type NewLocalisation = Omit<ILocalisation, 'id'> & { id: null };
