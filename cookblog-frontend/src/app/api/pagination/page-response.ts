export class PageResponse<T> {
  private readonly _content: T[];
  private readonly _empty: boolean;
  private readonly _first: boolean;
  private readonly _last: boolean;
  private readonly _number: number;
  private readonly _size: number;
  private readonly _totalElements: number;
  private readonly _totalPages: number;

  constructor(
    content: T[],
    empty: boolean,
    first: boolean,
    last: boolean,
    number: number,
    size: number,
    totalElements: number,
    totalPages: number,
  ) {
    this._content = content;
    this._empty = empty;
    this._first = first;
    this._last = last;
    this._number = number;
    this._size = size;
    this._totalElements = totalElements;
    this._totalPages = totalPages;
  }

  get content() {
    return this._content;
  }

  get empty() {
    return this._empty;
  }

  get first() {
    return this._first;
  }

  get last() {
    return this._last;
  }

  get number() {
    return this._number;
  }

  get size() {
    return this._size;
  }

  get totalElements() {
    return this._totalElements;
  }

  get totalPages() {
    return this._totalPages;
  }
}
