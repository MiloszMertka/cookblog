import { UnitResource } from './unit.resource';

export class QuantityResource {
  private readonly _id: number | null;
  private readonly _amount: number;
  private readonly _unit: UnitResource;

  constructor(id: number, amount: number, unit: UnitResource) {
    this._id = id;
    this._amount = amount;
    this._unit = unit;
  }

  get id() {
    return this._id;
  }

  get amount() {
    return this._amount;
  }

  get unit() {
    return this._unit;
  }
}
