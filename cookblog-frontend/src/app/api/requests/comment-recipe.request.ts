import { Request } from './request';

export class CommentRecipeRequest implements Request {
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

  toRequestBody() {
    return {
      author: this._author,
      content: this._content,
    };
  }
}
