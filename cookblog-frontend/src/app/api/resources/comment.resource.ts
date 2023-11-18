export class CommentResource {
  private readonly _id: number;
  private readonly _author: string;
  private readonly _content: string;

  constructor(id: number, author: string, content: string) {
    this._id = id;
    this._author = author;
    this._content = content;
  }

  get id() {
    return this._id;
  }

  get author() {
    return this._author;
  }

  get content() {
    return this._content;
  }
}
