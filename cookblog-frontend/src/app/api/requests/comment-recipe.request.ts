export class CommentRecipeRequest {
  private readonly _author: string;
  private readonly _content: string;

  constructor(author: string, content: string) {
    this._author = author;
    this._content = content;
  }

  get author(): string {
    return this._author;
  }

  get content(): string {
    return this._content;
  }
}
