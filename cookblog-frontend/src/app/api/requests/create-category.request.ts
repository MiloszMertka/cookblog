import { Request } from './request';

export class CreateCategoryRequest implements Request {
  private readonly _name: string;

  constructor(name: string) {
    this._name = name;
  }

  get name(): string {
    return this._name;
  }

  toRequestBody() {
    return {
      name: this._name,
    };
  }
}
