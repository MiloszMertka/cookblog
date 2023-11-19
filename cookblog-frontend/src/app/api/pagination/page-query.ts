import { SortQuery } from './sort-query';
import { HttpParams } from '@angular/common/http';
import { Query } from './query';

export class PageQuery implements Query {
  private static readonly PAGE_QUERY_KEY = 'page';
  private static readonly SIZE_QUERY_KEY = 'size';

  private readonly _page: number;
  private readonly _size?: number;
  private readonly _sort?: SortQuery;

  constructor(page: number, size?: number, sort?: SortQuery) {
    this._page = page;
    this._size = size;
    this._sort = sort;
  }

  get page() {
    return this._page;
  }

  get size() {
    return this._size;
  }

  get sort() {
    return this._sort;
  }

  toHttpParams() {
    let params = new HttpParams();
    params = this.setPageParam(params);
    params = this.setSizeParam(params);
    return this.setSortParam(params);
  }

  private setPageParam(params: HttpParams) {
    return params.set(PageQuery.PAGE_QUERY_KEY, String(this._page));
  }

  private setSizeParam(params: HttpParams) {
    if (!this._size) {
      return params;
    }

    return params.set(PageQuery.SIZE_QUERY_KEY, String(this._size));
  }

  private setSortParam(params: HttpParams) {
    if (!this._sort) {
      return params;
    }

    const sortParams = this._sort.toHttpParams();
    return this.concatenateHttpParams(params, sortParams);
  }

  private concatenateHttpParams(params: HttpParams, otherParams: HttpParams) {
    otherParams.keys().forEach((key) => {
      params = params.set(key, otherParams.get(key)!);
    });

    return params;
  }
}
