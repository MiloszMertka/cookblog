import { SortDirection } from './sort-direction';
import { Query } from './query';
import { HttpParams } from '@angular/common/http';

export class SortQuery implements Query {
  private static readonly SORT_QUERY_KEY = 'sort';
  private static readonly SORT_QUERY_SEPARATOR = ',';

  private readonly _property: string;
  private readonly _direction: SortDirection;

  constructor(
    property: string,
    direction: SortDirection = SortDirection.Ascending,
  ) {
    this._property = property;
    this._direction = direction;
  }

  get property() {
    return this._property;
  }

  get direction() {
    return this._direction;
  }

  toHttpParams() {
    const sortParam = this._createSortParamString();
    return this._createSortHttpParam(sortParam);
  }

  _createSortParamString() {
    return `${this._property}${SortQuery.SORT_QUERY_SEPARATOR}${this._direction}`;
  }

  _createSortHttpParam(sortParamString: string) {
    const params = new HttpParams();
    return params.set(SortQuery.SORT_QUERY_KEY, sortParamString);
  }
}
