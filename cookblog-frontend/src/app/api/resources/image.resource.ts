export class ImageResource {
  private readonly _id: number | null;
  private readonly _path: string;

  constructor(id: number, path: string) {
    this._id = id;
    this._path = path;
  }

  get id() {
    return this._id;
  }

  get path() {
    return this._path;
  }
}
