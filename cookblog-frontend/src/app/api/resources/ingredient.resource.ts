import { QuantityResource } from './quantity.resource';

export class IngredientResource {
  private readonly _id: number | null;
  private readonly _name: string;
  private readonly _quantity: QuantityResource;

  constructor(id: number, name: string, quantity: QuantityResource) {
    this._id = id;
    this._name = name;
    this._quantity = quantity;
  }

  get id() {
    return this._id;
  }

  get name() {
    return this._name;
  }

  get quantity() {
    return this._quantity;
  }
}
